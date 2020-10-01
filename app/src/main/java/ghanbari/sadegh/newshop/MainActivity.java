package ghanbari.sadegh.newshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import ghanbari.sadegh.newshop.adapters.SliderAdapter;
import ghanbari.sadegh.newshop.pojo.Category;
import ghanbari.sadegh.newshop.pojo.Order;
import ghanbari.sadegh.newshop.pojo.ParseResponse;
import ghanbari.sadegh.newshop.pojo.Product;
import ghanbari.sadegh.newshop.pojo.UserProfile;
import ghanbari.sadegh.newshop.pojo.UserProfileResult;
import ghanbari.sadegh.newshop.webservice.SaveOrderService;
import ghanbari.sadegh.newshop.webservice.UserProfileService;
import helper.NetworkUtil;
import helper.Settings;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener , OnListFragmentInteractionListener , ProductFragment.OnProductListInteractionListener {

    NetReceivers netReceivers = new NetReceivers();

    final int REQ_CODE_LOGIN = 1001;
    ProductFragment productFragment;
    ProgressBar mainProgress;
    boolean searchSubmit = false;
    View layoutConnection;
    private Animation myAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        layoutConnection = findViewById(R.id.layoutConnection);
        mainProgress = findViewById(R.id.progressBarMain);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //----
        Settings settings = new Settings(this);
        if(settings.GetLoginInfo() == null)
            startActivityForResult(new Intent(this , LoginActivity.class), REQ_CODE_LOGIN);
        else
        {
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvUsername)).setText(settings.GetUsername());
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvEmail)).setText(settings.GetEmail());
            final ImageView imgProfile = navigationView.getHeaderView(0).findViewById(R.id.imageView);
            imgProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this , ProfileActivity.class));
                }
            });
            ///////
            final UserProfileService service = RetrofitClientInstance.getRetrofitInstance().create(UserProfileService.class);
            Call<UserProfileResult> callGet = service.getProfile("{\"userId\" :\""+new Settings(this).GetLoginInfo()+"\"}");
            callGet.enqueue(new Callback<UserProfileResult>() {
                @Override
                public void onResponse(Call<UserProfileResult> call, Response<UserProfileResult> response) {
                    List<UserProfile> list = response.body().getResults();
                    if(list.size() > 0){
                        UserProfile user = list.get( list.size() -1 );

                        byte[] picArr = user.getPic();
                        Bitmap bmp = BitmapFactory.decodeByteArray(picArr , 0 , picArr.length);
                        imgProfile.setImageBitmap(bmp);
                    }
                }

                @Override
                public void onFailure(Call<UserProfileResult> call, Throwable t) {

                }
            });
        }

        navigationView.getHeaderView(0).findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Settings(MainActivity.this).Logout();
                finish();
            }
        });

        productFragment = (ProductFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        ViewPager viewPager = findViewById(R.id.viewPager);
        SliderAdapter pagerAdapter = new SliderAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);

        // optional
        pagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        registerReceiver(netReceivers, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        myAnim = AnimationUtils.loadAnimation(this , R.anim.my_alpha);
        //Testy
        Order order = new Order();
        order.setCount(1);
        order.setProductId("sdfseere");
        order.setUserId("aaaaa");


        SaveOrderService service = RetrofitClientInstance.getRetrofitInstance().create(SaveOrderService.class);
        Call<ParseResponse> call = service.saveOrder(order);
        call.enqueue(new Callback<ParseResponse>() {
            @Override
            public void onResponse(Call<ParseResponse> call, Response<ParseResponse> response) {
                ParseResponse res =  response.body();
            }

            @Override
            public void onFailure(Call<ParseResponse> call, Throwable t) {

            }
        });
    }

    class NetReceivers extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = NetworkUtil.getConnectivityStatusString(context);

                layoutConnection.setVisibility(status == null ? View.VISIBLE : View.GONE);
                if(status!=null)
                    productFragment.showAll();
                else
                {
                    layoutConnection.startAnimation(myAnim);
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netReceivers);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem =  menu.findItem(R.id.action_search);

        SearchView searchView =(SearchView) searchItem.getActionView() ;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSubmit = true;
                productFragment.search(query);
                mainProgress.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("") && searchSubmit){
                    Toast.makeText(MainActivity.this  , "Search Cleared",Toast.LENGTH_LONG).show();
                    mainProgress.setVisibility(View.VISIBLE);
                    productFragment.showAll();
                    searchSubmit = false;
                }
                return false;
            }
        });
        return true;
    }

    public void hideProgress(){
        mainProgress.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Orders) {
            startActivity(new Intent(this , MyOrdersActivity.class));
            return true;
        }else if (id == R.id.action_AboutUs) {
            startActivity(new Intent(this , AboutUsActivity.class));
            return true;
        }else if (id == R.id.action_support) {
            startActivity(new Intent(this , SupportActivity.class));
            return true;
        }
        else  if (id == R.id.action_basket) {
            startActivity(new Intent(this , BasketActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Category item) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        productFragment.FilterByCat(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_CODE_LOGIN && resultCode!=RESULT_OK)
            finish();

    }

    @Override
    public void onListFragmentInteraction(Product item) {
        //Toast.makeText(this , "Item:"+item.getTitle() , Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this , ProductDetailActivity.class);
        myIntent.putExtra("myItem" , item);
        startActivity(myIntent);
    }


}
