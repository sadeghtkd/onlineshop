package ghanbari.sadegh.newshop.webservice;

import ghanbari.sadegh.newshop.pojo.Order;
import ghanbari.sadegh.newshop.pojo.ParseResponse;
import ghanbari.sadegh.newshop.pojo.UserProfile;
import ghanbari.sadegh.newshop.pojo.UserProfileResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserProfileService {
    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
            ,"Content-Type:application/json"})
    @POST("userProfile")
    Call<ParseResponse> save(@Body UserProfile user);

    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
            ,"Content-Type:application/json"})
    @GET("userProfile")
    Call<UserProfileResult> getProfile(@Query("where") String where);
}
