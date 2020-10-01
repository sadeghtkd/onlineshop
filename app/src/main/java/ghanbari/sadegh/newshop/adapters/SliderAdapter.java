package ghanbari.sadegh.newshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import ghanbari.sadegh.newshop.ProductDetailActivity;
import ghanbari.sadegh.newshop.R;
import ghanbari.sadegh.newshop.RetrofitClientInstance;
import ghanbari.sadegh.newshop.pojo.Product;
import ghanbari.sadegh.newshop.pojo.ProductResult;
import ghanbari.sadegh.newshop.webservice.GetProductService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private List<Product> listItem = new ArrayList<>();
    //private  List<Bitmap> bitmapList = new ArrayList<>();
    public SliderAdapter(final Context context){
        this.context = context;
        //-----Get data from web service
        GetProductService service = RetrofitClientInstance.getRetrofitInstance().create(GetProductService.class);
        Call<ProductResult> call = service.getFeaturedProducts("{\"banner\": {\"$exists\":true}}");

        call.enqueue(new Callback<ProductResult>() {
            @Override
            public void onResponse(Call<ProductResult> call, Response<ProductResult> response) {
                listItem = response.body().getProducts() ; // data returned from server

                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductResult> call, Throwable t) {
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
        //-----
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view =  LayoutInflater.from(context).inflate(R.layout.slider_item , null);
        ImageView imgSlider = view.findViewById(R.id.imageSlider);
        //imgSlider.setImageBitmap( bitmapList.get(position));
        Picasso.get().load( listItem.get(position).getBanner() ).into(imgSlider);
        imgSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(context , ProductDetailActivity.class);
                myIntent.putExtra("myItem" , listItem.get(position));
                context.startActivity(myIntent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
