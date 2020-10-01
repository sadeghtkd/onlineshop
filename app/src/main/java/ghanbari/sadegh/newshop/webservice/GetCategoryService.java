package ghanbari.sadegh.newshop.webservice;

import ghanbari.sadegh.newshop.pojo.CategoryResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GetCategoryService {

    @Headers({"X-Parse-REST-API-Key:Svk9Wgx7w5TuRNK7k1x9Yml4p97FrmirvdeEz38Q"
            ,"X-Parse-Application-Id:SkLSi9HFtkuZYzlydqwhGMmVtlSGble0rLfZMM3z"
    ,"Content-Type:application/json"})
    @GET("category")
    Call<CategoryResult> getAllCategories();
}
