package packag.nnk.com.userfuelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPinActivity extends BaseActivity {

    @BindView(R.id.editText3)
    EditText firstPin;

    @BindView(R.id.editText4)
    EditText secondPin;

    @BindView(R.id.submit_pin)
    Button submit_pin;
    private ApiInterface mApiService_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_pin_screen);
        ButterKnife.bind(this);
        mApiService_ = new ApiUtils().getApiInterfaces();
        setFont(submit_pin);

    }


    @OnClick(R.id.submit_pin)
    void validateEdit()
    {
        if (firstPin.getText().toString().length() == 4) {

        } else {
            Toast.makeText(getApplicationContext(), "Please set pin", Toast.LENGTH_LONG).show();
            return;
        }
        if (secondPin.getText().toString().length() == 4) {

        } else {
            Toast.makeText(getApplicationContext(), "Please confirm pin", Toast.LENGTH_LONG).show();
            return;
        }


        if (firstPin.getText().toString().equalsIgnoreCase(secondPin.getText().toString())) {
            setThePin(firstPin.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Please check pin, pin mismatch!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    void setThePin(String pin) {

        JsonObject json = new JsonObject();
        try {
            json.addProperty("userId", "" + user.getUserId());
            json.addProperty("pin", "" + pin);
        } catch (Exception e) {

        }

        Call<String> payment = mApiService_.updatePin(json);
        payment.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
              //  Toast.makeText(getApplicationContext(), "--" + response.body(), Toast.LENGTH_LONG).show();

                Intent myAct = new Intent(getApplicationContext(), MainActivity.class);
                myAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myAct);
                finish();

                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
            }
        });


    }
}
