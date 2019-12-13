package packag.nnk.com.userfuelapp.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IRetrofit
{
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("TR7F3CV8Q/BQWAYTZEW/a00cSuEec1E6AxicyeCVVSvD")
    Call<String> postRawJSON(@Body JsonObject locationPost);
}