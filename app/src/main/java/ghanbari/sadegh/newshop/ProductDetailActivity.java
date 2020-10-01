package ghanbari.sadegh.newshop;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import ghanbari.sadegh.newshop.database.AppDatabase;
import ghanbari.sadegh.newshop.database.Basket;
import ghanbari.sadegh.newshop.pojo.Product;
import helper.Settings;

public class ProductDetailActivity extends AppCompatActivity {

    Product myProduct;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myProduct = getIntent().getParcelableExtra("myItem");

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DbTask().execute();
            }
        });

        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvDescription = findViewById(R.id.tvDescription);
        ImageView ivProduct = findViewById(R.id.imgProduct);


        tvPrice.setText(String.valueOf( myProduct.getPrice()));
        tvDescription.setText(myProduct.getDescription());
        Picasso.get().load(myProduct.getPicture()).into(ivProduct);
        setTitle(myProduct.getTitle());

    }

    class DbTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase db = AppDatabase.getInstance(ProductDetailActivity.this);
            List<Basket> existItems = db.basketDAO().getBasketByProduct(myProduct.getObjectId());
            if( existItems.size() == 0) {

                Basket basket = new Basket();
                basket.productId = myProduct.getObjectId();
                basket.userId = new Settings(ProductDetailActivity.this).GetLoginInfo();
                basket.count = 1;
                db.basketDAO().insert(basket);
            }else{
                //کالا در سبد وجود داشته و به تعداد آن یکی افزوده شود
                existItems.get(0).count++;
                db.basketDAO().update(existItems.get(0));
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Snackbar.make(fab, getString(R.string.addedToBasket), Snackbar.LENGTH_LONG)
                    .setAction("", null).show();
        }
    }

}
