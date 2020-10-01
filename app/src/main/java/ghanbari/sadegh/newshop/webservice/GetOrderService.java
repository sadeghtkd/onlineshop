package ghanbari.sadegh.newshop.webservice;

import ghanbari.sadegh.newshop.pojo.CategoryResult;
import ghanbari.sadegh.newshop.pojo.OrderResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetOrderService {

    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
    ,"Content-Type:application/json"})
    @GET("order")
    Call<OrderResult> getMyOrders(@Query("where") String where);
}
