package packag.nnk.com.userfuelapp.interfaces;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.model.ApiError;
import packag.nnk.com.userfuelapp.model.Balance;
import packag.nnk.com.userfuelapp.model.OtpRes;
import packag.nnk.com.userfuelapp.model.OtpValidateRes;
import packag.nnk.com.userfuelapp.model.Payment;
import packag.nnk.com.userfuelapp.model.Post;
import packag.nnk.com.userfuelapp.model.RangeTransaction;
import packag.nnk.com.userfuelapp.model.SlackMessage;
import packag.nnk.com.userfuelapp.model.UserDetails;
import packag.nnk.com.userfuelapp.model.otp_val.UserVal;
import packag.nnk.com.userfuelapp.petrol_bunk_details.GetList;
import packag.nnk.com.userfuelapp.transaction.Transaction;
import packag.nnk.com.userfuelapp.transaction.TransactionPojo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface
{




    @GET("json?location=12.91304959,77.6421376&radius=1500&type=gas_station&key=AIzaSyBDCa_MSc0rmkV-IDo4CiOZRywm8jvG_2c")
    Call<GetList> getPetrolList();

//    api/driver/checkBalance/625ea288-c31c-4e21-ab5d-6d88af2a01fe

    @GET(CommonClass.CHECK_BALANCE+"{userId}")
    Call<Balance> getBalance(@Path("userId") String userId);

    @GET(CommonClass.RANGE_TRANSACTION+"{userId}")
    Call<RangeTransaction> getRangeTransaction(@Path("userId") String userId);

//    {
//        "driverId":"625ea288-c31c-4e21-ab5d-6d88af2a01fe",
//            "amount":"500",
//            "petrolBunkId":"19f55b6f-dbf9-4c4e-91cb-db5e828d3669"
//    }
    @POST(CommonClass.DRIVER_PAYMENT)
    Call<Payment> doPayment(@Body JsonObject json);

//    {"email": "sam@m.com", "password":"sam3", "role":"driver","driverAggregator":"ola","mobile":"9686391100"}
    @POST(CommonClass.AUTH_REGISTER)
    Call<UserDetails> createUser(@Body JsonObject json);



    @POST(CommonClass.GET_OTP)
    Call<OtpRes> getOtp(@Body JsonObject json);

    @POST(CommonClass.RANGE_TRANSACTION)
    Call<TransactionPojo> getTransactionList(@Body JsonObject json);


    @POST(CommonClass.VALIDATE_OTP)
    Call<UserVal> otpValidate(@Body JsonObject json);



}
