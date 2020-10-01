package ghanbari.sadegh.newshop.webservice;

import ghanbari.sadegh.newshop.pojo.CategoryResult;
import ghanbari.sadegh.newshop.pojo.ProductResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetProductService {

    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
    ,"Content-Type:application/json"})
    @GET("product")
    Call<ProductResult> getAllProducts();


    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
            ,"Content-Type:application/json"})
    @GET("product")
    Call<ProductResult> getProductsByIds(@Query("where") String where);

    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
            ,"Content-Type:application/json"})
    @GET("product")
    Call<ProductResult> getProductsByCat(@Query("where") String where);

    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
            ,"Content-Type:application/json"})
    @GET("product")
    Call<ProductResult> getFeaturedProducts(@Query("where") String where);

    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
            ,"Content-Type:application/json"})
    @GET("product")
    Call<ProductResult> searchProducts(@Query("where") String where);

    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
            ,"Content-Type:application/json"})
    @GET("product")
    Call<ProductResult> getProduct(@Query("where") String where);

}
