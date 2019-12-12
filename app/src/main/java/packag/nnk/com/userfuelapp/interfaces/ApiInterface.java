package packag.nnk.com.userfuelapp.interfaces;

import packag.nnk.com.userfuelapp.model.Post;
import packag.nnk.com.userfuelapp.petrol_bunk_details.GetList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface
{


    @POST("/posts")
    @FormUrlEncoded
    Call<Post> saveData(@Field("title") String title, @Field("body") String body, @Field("userId") Integer userId);

    @GET("json?location=12.91304959,77.6421376&radius=1500&type=gas_station&key=AIzaSyBDCa_MSc0rmkV-IDo4CiOZRywm8jvG_2c")
   Call<GetList> getPetrolList();

}
