package ghanbari.sadegh.newshop.adapters;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import ghanbari.sadegh.newshop.BasketFragment.OnBasketListInteractionListener;
import ghanbari.sadegh.newshop.R;
import ghanbari.sadegh.newshop.database.AppDatabase;
import ghanbari.sadegh.newshop.database.Basket;

import java.text.DecimalFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Basket} and makes a call to the
 * specified {@link OnBasketListInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBasketRecyclerViewAdapter extends RecyclerView.Adapter<MyBasketRecyclerViewAdapter.ViewHolder> {

    private  final Context context;
    private final List<Basket> mValues;
    private final OnBasketListInteractionListener mListener;

    public MyBasketRecyclerViewAdapter(Context context, List<Basket> items, OnBasketListInteractionListener listener) {
        this.context = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_basket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Basket basket = mValues.get(position);
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf( position + 1));
        holder.mContentView.setText(mValues.get(position).product.getTitle());
        holder.txtCount.setText(String.valueOf(basket.count));
        DecimalFormat priceFormatter = new DecimalFormat("#,###");
        holder.mPriceView.setText(priceFormatter.format( basket.product.getPrice()));
        holder.txtCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(holder.txtCount.getText().toString().equals(""))
                    return;
                basket.count = Integer.parseInt( holder.txtCount.getText().toString());
                new DbTask().execute(basket);
            }
        });
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
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DbDeleteTask().execute(basket);
            }
        });
    }

    class DbTask extends AsyncTask<Basket,Void,Void> {

        @Override
        protected Void doInBackground(Basket... baskets) {
            AppDatabase db = AppDatabase.getInstance(context);

            db.basketDAO().update(baskets[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    class DbDeleteTask extends AsyncTask<Basket,Void,Void> {

        @Override
        protected Void doInBackground(Basket... baskets) {
            AppDatabase db = AppDatabase.getInstance(context);

            db.basketDAO().delete(baskets[0]);
            mValues.remove(baskets[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mPriceView;
        public final EditText txtCount;
        public final ImageButton btnDelete;
        public Basket mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
            mPriceView = (TextView) view.findViewById(R.id.tvPrice);
            txtCount = view.findViewById(R.id.txtCount);
            btnDelete = view.findViewById(R.id.btnDelete);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
