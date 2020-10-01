package ghanbari.sadegh.newshop;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ghanbari.sadegh.newshop.adapters.MyBasketRecyclerViewAdapter;
import ghanbari.sadegh.newshop.database.AppDatabase;
import ghanbari.sadegh.newshop.database.Basket;
import ghanbari.sadegh.newshop.pojo.Product;
import ghanbari.sadegh.newshop.pojo.ProductResult;
import ghanbari.sadegh.newshop.webservice.GetProductService;
import helper.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnBasketListInteractionListener}
 * interface.
 */
public class BasketFragment extends Fragment {

    RecyclerView recyclerView;
    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnBasketListInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BasketFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BasketFragment newInstance(int columnCount) {
        BasketFragment fragment = new BasketFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            DbAsyncTask dbAsyncTask = new DbAsyncTask();
            dbAsyncTask.execute();
            //
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBasketListInteractionListener) {
            mListener = (OnBasketListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnBasketListInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Basket item);
    }

    class DbAsyncTask extends AsyncTask<Void,Void,List<Basket>>{

        @Override
        protected List<Basket> doInBackground(Void... voids) {

            AppDatabase db = AppDatabase.getInstance(getContext() );
            String userId = new Settings(getContext()).GetLoginInfo();
            List<Basket> basketList = db.basketDAO().getBasketByUser(userId );

            return basketList;
        }

        @Override
        protected void onPostExecute(final List<Basket> basketList) {
            super.onPostExecute(basketList);
            //-----Get data from web service
            GetProductService service = RetrofitClientInstance.getRetrofitInstance().create(GetProductService.class);
            String ids = "";

            for (Basket myBasket: basketList) {
                ids += "\""+myBasket.productId + "\",";
            }
            if(ids.length()==0)
                return;
            ids = ids.substring(0,ids.length() - 1);
            Call<ProductResult> call = service.getProductsByIds("{\"objectId\": {\"$in\":["+ids+"]} }");

            call.enqueue(new Callback<ProductResult>() {
                @Override
                public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                    List<Product> listItem = response.body().getProducts() ; // data returned from server
                    int i = 0;
                    for (Product p:listItem ) {
                        basketList.get(i).product = p;
                        i++;
                    }
                    recyclerView.setAdapter(new MyBasketRecyclerViewAdapter(getActivity(), basketList, mListener));
                }

                @Override
                public void onFailure(Call<ProductResult> call, Throwable t) {

                    Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
            //-----


        }
    }
}
