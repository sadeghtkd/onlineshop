package ghanbari.sadegh.newshop;

import android.content.Context;
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

import ghanbari.sadegh.newshop.adapters.MyProductRecyclerViewAdapter;
import ghanbari.sadegh.newshop.pojo.Category;
import ghanbari.sadegh.newshop.pojo.Product;
import ghanbari.sadegh.newshop.pojo.ProductResult;
import ghanbari.sadegh.newshop.webservice.GetProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProductFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private OnProductListInteractionListener mListener;
    RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProductFragment newInstance(int columnCount) {
        ProductFragment fragment = new ProductFragment();
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
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MyProductRecyclerViewAdapter(DummyContent.ITEMS, mListener));
            showAll();
        }

        return view;
    }

    public void showAll(){
        //-----Get data from web service
        GetProductService service = RetrofitClientInstance.getRetrofitInstance().create(GetProductService.class);
        Call<ProductResult> call = service.getAllProducts();

        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                List<Product> listItem = response.body().getProducts() ; // data returned from server
                recyclerView.setAdapter(new MyProductRecyclerViewAdapter(getContext(), listItem, mListener));
                ((MainActivity)getActivity()).hideProgress();
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {

                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        //-----
    }
    public  void search(String term){
        term = term.replace("\"","").replace("\n","");
        //-----Get data from web service
        GetProductService service = RetrofitClientInstance.getRetrofitInstance().create(GetProductService.class);
        Call<ProductResult> call = service.searchProducts("{\"title\":{\"$text\":{\"$search\":{\"$term\":\""+term+"\"}}}}");

        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                List<Product> listItem = response.body().getProducts() ; // data returned from server

                recyclerView.setAdapter(new MyProductRecyclerViewAdapter(getContext(), listItem, mListener));
                if(listItem.size()==0)
                    Toast.makeText(getContext() , getString(R.string.msgNotFound),Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).hideProgress();
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {

                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        //-----
    }


    public  void FilterByCat(Category catItem){

        //-----Get data from web service
        GetProductService service = RetrofitClientInstance.getRetrofitInstance().create(GetProductService.class);
        Call<ProductResult> call = service.getProductsByCat("{\"catId\":\""+catItem.getObjectId()+"\"}");

        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                List<Product> listItem = response.body().getProducts() ; // data returned from server
                recyclerView.setAdapter(new MyProductRecyclerViewAdapter(getContext(), listItem, mListener));
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {

                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        //-----
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductListInteractionListener) {
            mListener = (OnProductListInteractionListener) context;
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
    public interface OnProductListInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Product item);
    }
}
