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

import java.util.List;

import ghanbari.sadegh.newshop.adapters.MyOrderRecyclerViewAdapter;
import ghanbari.sadegh.newshop.pojo.Order;
import ghanbari.sadegh.newshop.pojo.OrderResult;
import ghanbari.sadegh.newshop.webservice.GetOrderService;
import helper.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnOrderItemInteractionListener}
 * interface.
 */
public class OrderFragment extends Fragment implements OnOrderItemInteractionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnOrderItemInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrderFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OrderFragment newInstance(int columnCount) {
        OrderFragment fragment = new OrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //recyclerView.setAdapter(new MyOrderRecyclerViewAdapter(DummyContent.ITEMS, mListener));

            GetOrderService service = RetrofitClientInstance.getRetrofitInstance().create(GetOrderService.class);
            Call<OrderResult> call = service.getMyOrders("{\"userId\":\""+new Settings(getContext()).GetLoginInfo() +"\"}");
            call.enqueue(new Callback<OrderResult>() {
                @Override
                public void onResponse(Call<OrderResult> call, Response<OrderResult> response) {
                     List<Order> items = response.body().getResults();
                     recyclerView.setAdapter( new MyOrderRecyclerViewAdapter(getContext(), items , OrderFragment.this ));
                }

                @Override
                public void onFailure(Call<OrderResult> call, Throwable t) {

                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListFragmentInteraction(Order item) {

    }


}
