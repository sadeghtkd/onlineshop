package ghanbari.sadegh.newshop.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ghanbari.sadegh.newshop.OnOrderItemInteractionListener;
import ghanbari.sadegh.newshop.R;
import ghanbari.sadegh.newshop.RetrofitClientInstance;
import ghanbari.sadegh.newshop.pojo.Order;
import ghanbari.sadegh.newshop.pojo.Product;
import ghanbari.sadegh.newshop.pojo.ProductResult;
import ghanbari.sadegh.newshop.webservice.GetProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

import java.text.ParseException;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Order} and makes a call to the
 * specified {@link OnOrderItemInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder> {

    private final List<Order> mValues;
    private final OnOrderItemInteractionListener mListener;
    private Context context;

    public MyOrderRecyclerViewAdapter(Context context, List<Order> items, OnOrderItemInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.tvOrderId.setText(mValues.get(position).getObjectId());

        String miladi = mValues.get(position).getCreatedAt().split("\\.")[0];
        PersianDateFormat persianDateFormat = new PersianDateFormat();
        String shamsi = null ;
        try {
            shamsi = persianDateFormat.parseGrg(miladi,"yyyy-MM-dd'T'HH:mm:ss").toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tvDate.setText(shamsi);
        String status = mValues.get(position).getStatus();
        holder.tvStatus.setText(status);
        if(status.equals("جدید"))
            holder.ivStatus.setImageResource(R.drawable.ic_new);
        else if(status.equals("در حال بررسی"))
            holder.ivStatus.setImageResource(R.drawable.ic_time);
        else if(status.equals("ارسال شده"))
            holder.ivStatus.setImageResource(R.drawable.ic_ok);

        holder.btnShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.progressBar.setVisibility(View.VISIBLE);
                GetProductService service = RetrofitClientInstance.getRetrofitInstance().create(GetProductService.class);
                Call<ProductResult> call = service.getProduct("{\"objectId\":\""+mValues.get(position).getProductId()+"\"}");
                call.enqueue(new Callback<ProductResult>() {
                    @Override
                    public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                        List<Product> items = response.body().getProducts();
                        Product myProduct = items.get(0);
                        holder.tvProduct.setText(myProduct.getTitle() +"\n"+context.getString(R.string.lbCount)+ mValues.get(position).getCount());
                        Picasso.get().load( myProduct.getPicture()).into( holder.ivProduct);
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ProductResult> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvOrderId;
        public final TextView tvDate;
        public final TextView tvStatus;
        public final ImageView ivStatus;
        public final Button btnShowDetail;
        public final TextView tvProduct;
        public final ImageView ivProduct;
        public final ProgressBar progressBar;

        public Order mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvOrderId = (TextView) view.findViewById(R.id.tvOrderId);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvStatus =  view.findViewById(R.id.tvOrderStatus);
            ivStatus =  view.findViewById(R.id.ivStatus);
            tvProduct =  view.findViewById(R.id.tvProduct);
            btnShowDetail =  view.findViewById(R.id.btnShowDetails);
            ivProduct =  view.findViewById(R.id.ivProduct);
            progressBar =  view.findViewById(R.id.progressBar);
        }


    }
}
