package packag.nnk.com.userfuelapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.ui.OtpEdittextClass;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends BaseActivity {

    String lat,lang;

    @BindView(R.id.signupMobile329)
    EditText signupMobile329;


    @BindView(R.id.getOtpButton329)
    Button getOtpButton329;


    @BindView(R.id.otpLayout329)
    LinearLayout otpLayout329;


    @BindView(R.id.resendOtp329)
    TextView resendOtp329;

    @BindView(R.id.et_otp)
    OtpEdittextClass et_otp;

    @BindView(R.id.signUpButtonn329)
    Button signUpButtonn329;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();


        if (extras != null)
        {
            lat = extras.getString("lat");
            lang = extras.getString("lang");
        }




    }
}
