package ghanbari.sadegh.newshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import ghanbari.sadegh.newshop.adapters.MyBasketRecyclerViewAdapter;
import ghanbari.sadegh.newshop.database.AppDatabase;
import ghanbari.sadegh.newshop.database.Basket;
import ghanbari.sadegh.newshop.pojo.Order;
import ghanbari.sadegh.newshop.pojo.ParseResponse;
import ghanbari.sadegh.newshop.pojo.Product;
import ghanbari.sadegh.newshop.pojo.ProductResult;
import ghanbari.sadegh.newshop.webservice.GetProductService;
import ghanbari.sadegh.newshop.webservice.SaveOrderService;
import helper.Settings;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BasketActivity extends AppCompatActivity implements BasketFragment.OnBasketListInteractionListener {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressBar = findViewById(R.id.progressBar3);
        findViewById(R.id.btnSaveOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                new SaveOrdersAsyncTask().execute();
            }
        });
    }

    class SaveOrdersAsyncTask extends AsyncTask<Void,Void, List<Basket>> {

        @Override
        protected List<Basket> doInBackground(Void... voids) {

            final AppDatabase db = AppDatabase.getInstance(BasketActivity.this );
            String userId = new Settings(BasketActivity.this).GetLoginInfo();
            List<Basket> basketList = db.basketDAO().getBasketByUser(userId );


            for (final Basket myBasket: basketList) {
                Order order = new Order();
                order.setCount(myBasket.count);
                order.setProductId(myBasket.productId);
                order.setUserId(userId);


                SaveOrderService service = RetrofitClientInstance.getRetrofitInstance().create(SaveOrderService.class);
                Call<ParseResponse> call = service.saveOrder(order);
                call.enqueue(new Callback<ParseResponse>() {
                    @Override
                    public void onResponse(Call<ParseResponse> call, Response<ParseResponse> response) {
                        new deleteDbTask().execute(myBasket);
                    }

                    @Override
                    public void onFailure(Call<ParseResponse> call, Throwable t) {

                    }
                });
            }
            return basketList;
        }

        @Override
        protected void onPostExecute(final List<Basket> basketList) {
            super.onPostExecute(basketList);
            Toast.makeText(BasketActivity.this , getString(R.string.orderDone),Toast.LENGTH_LONG).show();
            finish();
        }
    }

    class  deleteDbTask extends  AsyncTask<Basket,Void,Void>{

        @Override
        protected Void doInBackground(Basket... baskets) {
            final AppDatabase db = AppDatabase.getInstance(BasketActivity.this );
            db.basketDAO().delete(baskets[0]);
            return null;
        }
    }
    @Override
    public void onListFragmentInteraction(Basket item) {

    }
}
