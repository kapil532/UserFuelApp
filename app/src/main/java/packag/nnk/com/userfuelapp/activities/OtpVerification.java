package packag.nnk.com.userfuelapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.OtpRes;
import packag.nnk.com.userfuelapp.model.OtpValidateRes;
import packag.nnk.com.userfuelapp.ui.OtpEdittextClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerification extends BaseActivity {


    ApiInterface getApiInterfaces;
    String number;

    @BindView(R.id.et_otp)
    OtpEdittextClass et_otp;

    @BindView(R.id.signUpButtonn329)
    Button signUpButtonn329;


    @BindView(R.id.resendOtp329)
    TextView resendOtp329;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.otp_verification);
        ButterKnife.bind(this);

        getApiInterfaces = new ApiUtils().getApiInterfaces();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            number = extras.getString("number");
        }

        resendOtp329.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getOtp(number);
            }
        });
        signUpButtonn329.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_otp.getText().toString().length() == 4) {
                    validateOtp(et_otp.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter 4 digit!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    void validateOtp(String otp) {
        showProgressDialog();
        JsonObject json = new JsonObject();
        try {
            json.addProperty("mobile", "" + number);
            json.addProperty("otp", "" + otp);
        } catch (Exception e) {

        }
        Call<OtpValidateRes> validation = getApiInterfaces.otpValidate(json);
        validation.enqueue(new Callback<OtpValidateRes>() {
            @Override
            public void onResponse(Call<OtpValidateRes> call, Response<OtpValidateRes> response) {
                hideProgressDialog();
                Log.e("VALIDATION", "RESPONSE" + response.body());
                AppSharedPreUtils.getInstance(getApplicationContext()).saveDashBoardSectionData(response.body());

//                OtpValidateRes  user =   AppSharedPreUtils.getInstance(getApplicationContext()).getDashBoardSectionData();
                Intent myAct = new Intent(getApplicationContext(), MainActivity.class);
                myAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myAct);
                finish();


            }

            @Override
            public void onFailure(Call<OtpValidateRes> call, Throwable t) {
                hideProgressDialog();
            }
        });


    }


    void getOtp(String mobile) {
        showProgressDialog();
        JsonObject json = new JsonObject();
        try {
            json.addProperty("mobile", "" + mobile);
        } catch (Exception e) {

        }

        Call<OtpRes> getOtp = getApiInterfaces.getOtp(json);
        getOtp.enqueue(new Callback<OtpRes>() {
            @Override
            public void onResponse(Call<OtpRes> call, Response<OtpRes> response) {
                hideProgressDialog();
                Log.e("OTP POJO", "RES-->  " + response.body().getStatus());

                Toast.makeText(getApplicationContext(), "Otp sent to registered number!", Toast.LENGTH_LONG).show();


            }

            @Override
            public void onFailure(Call<OtpRes> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
            }
        });

    }

}
