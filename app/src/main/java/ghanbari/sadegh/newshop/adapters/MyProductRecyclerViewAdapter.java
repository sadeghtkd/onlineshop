package ghanbari.sadegh.newshop.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ghanbari.sadegh.newshop.OnListFragmentInteractionListener;
import ghanbari.sadegh.newshop.ProductFragment.OnProductListInteractionListener;
import ghanbari.sadegh.newshop.R;
import ghanbari.sadegh.newshop.pojo.Product;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Product} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProductRecyclerViewAdapter extends RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder> {

    private final List<Product> mValues;
    private final OnProductListInteractionListener mListener;
    private Context context;
    private int halfScreenWidth;

    public MyProductRecyclerViewAdapter(Context context , List<Product> items, OnProductListInteractionListener listener) {
        this.context = context;
        mValues = items;
        mListener = listener;
        halfScreenWidth = getHalfScreenWidth() / 2;
    }

    private  int getHalfScreenWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context ).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.priceView.setText(String.valueOf( mValues.get(position).getPrice()));
        holder.titleView.setText(mValues.get(position).getTitle());
        Picasso.get().load(holder.mItem.getPicture()).resize(halfScreenWidth, halfScreenWidth).into(holder.imgView);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imgView;
        public final TextView priceView;
        public final TextView titleView;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            priceView = (TextView) view.findViewById(R.id.tvPrice);
            titleView = (TextView) view.findViewById(R.id.tvTitle);
            imgView =  view.findViewById(R.id.imgProduct);
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}
