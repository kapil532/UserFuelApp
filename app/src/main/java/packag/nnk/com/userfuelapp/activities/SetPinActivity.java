package packag.nnk.com.userfuelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.User;
import packag.nnk.com.userfuelapp.model.UserDetails;
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

        firstPin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    secondPin.setFocusable(true);
                }
                return false;
            }

        });


        secondPin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    validateEdit();
                }
                return false;
            }
        });

    }


    @OnClick(R.id.submit_pin)
    void validateEdit() {
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
        showProgressDialog();
        JsonObject json = new JsonObject();
        try {
            json.addProperty("userId", "" + user.getUserId());
            json.addProperty("pin", "" + pin);
        } catch (Exception e) {

        }

        Call<UserDetails> payment = mApiService_.updatePin(json);
        payment.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {

                hideProgressDialog();


                try {


                    AppSharedPreUtils
                            .getInstance(getApplicationContext()).saveUserDetails(response.body().getUser());
                    User user = response.body().getUser();
                    if (user != null) {
                        Toast.makeText(getApplicationContext(), "Your secret pin set successfully!", Toast.LENGTH_LONG).show();
                        Intent myAct = new Intent(getApplicationContext(), MainActivity.class);
                        myAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myAct);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    BufferedReader reader = null;
                    StringBuilder sb = new StringBuilder();
                    try {
                        reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                        String line;
                        try {
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                            }
                        } catch (IOException ea) {
                            e.printStackTrace();
                        }
                    } catch (Exception eaa) {
                        e.printStackTrace();
                    }
                    String finallyError = sb.toString();
                    makeToast(finallyError);
                }

            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), t.getMessage() + "Please try again!", Toast.LENGTH_LONG).show();
            }
        });


    }

    void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}
