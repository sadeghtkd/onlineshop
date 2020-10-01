package ghanbari.sadegh.newshop;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ghanbari.sadegh.newshop.adapters.MyCategoryRecyclerViewAdapter;
import ghanbari.sadegh.newshop.pojo.Category;
import ghanbari.sadegh.newshop.pojo.CategoryResult;
import ghanbari.sadegh.newshop.webservice.GetCategoryService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class CategoryFragment extends Fragment  {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CategoryFragment newInstance(int columnCount) {
        CategoryFragment fragment = new CategoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MyCategoryRecyclerViewAdapter(DummyContent.ITEMS, mListener));
            DividerItemDecoration itemDecoration =  new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(itemDecoration);
            //-----Get data from web service
            GetCategoryService service = RetrofitClientInstance.getRetrofitInstance().create(GetCategoryService.class);
            Call<CategoryResult> call = service.getAllCategories();

            call.enqueue(new Callback<CategoryResult>() {
                @Override
                public void onResponse(Call<CategoryResult> call, Response<CategoryResult> response) {
                    List<Category> listCats = response.body().getResults() ; // data returned from server
                    recyclerView.setAdapter(new MyCategoryRecyclerViewAdapter(getContext(), listCats, mListener));
                }

                @Override
                public void onFailure(Call<CategoryResult> call, Throwable t) {

                    Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });
            //-----
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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
    /*public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Category item);
    }*/
}
