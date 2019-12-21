package packag.nnk.com.userfuelapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.OtpRes;
import packag.nnk.com.userfuelapp.ui.OtpEdittextClass;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

public class LoginActivity extends BaseActivity {

    String lat, lang;

    @BindView(R.id.signupMobile329)
    EditText signupMobile329;


    @BindView(R.id.getOtpButton329)
    Button getOtpButton329;


    @BindView(R.id.otpLayout329)
    LinearLayout otpLayout329;




    @BindView(R.id.pleaseLogin)
    TextView pleaseLogin;



    ApiInterface getApiInterfaces;
    String mobileNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        getApiInterfaces = new ApiUtils().getApiInterfaces();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat = extras.getString("lat");
            lang = extras.getString("lang");
        }

setFont(getOtpButton329);
        getOtpButton329.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signupMobile329.getText().toString().length() == 10) {

                    mobileNumber = signupMobile329.getText().toString();
                    getOtp(signupMobile329.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter proper mobile number!!", Toast.LENGTH_LONG).show();
                }
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
                Intent otpVer = new Intent(getApplicationContext(),OtpVerification.class);
                otpVer.putExtra("number",""+mobileNumber);
                startActivity(otpVer);

            }

            @Override
            public void onFailure(Call<OtpRes> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
            }
        });

    }
}
