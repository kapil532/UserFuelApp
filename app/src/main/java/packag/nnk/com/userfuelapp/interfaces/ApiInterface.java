package packag.nnk.com.userfuelapp.interfaces;

import com.google.gson.JsonObject;

import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.model.OtpRes;
import packag.nnk.com.userfuelapp.model.OtpValidateRes;
import packag.nnk.com.userfuelapp.model.Post;
import packag.nnk.com.userfuelapp.model.SlackMessage;
import packag.nnk.com.userfuelapp.petrol_bunk_details.GetList;
import packag.nnk.com.userfuelapp.transaction.Transaction;
import packag.nnk.com.userfuelapp.transaction.TransactionPojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface
{




    @GET("json?location=12.91304959,77.6421376&radius=1500&type=gas_station&key=AIzaSyBDCa_MSc0rmkV-IDo4CiOZRywm8jvG_2c")
    Call<GetList> getPetrolList();


    @POST(CommonClass.GET_OTP)
    Call<OtpRes> getOtp(@Body JsonObject json);

    @POST(CommonClass.GET_TRANSACTION)
    Call<TransactionPojo> getTransactionList(@Body JsonObject json);


    @POST(CommonClass.VALIDATE_OTP)
    Call<OtpValidateRes> otpValidate(@Body JsonObject json);

}
