package packag.nnk.com.userfuelapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.ApiError;
import packag.nnk.com.userfuelapp.model.User;
import packag.nnk.com.userfuelapp.model.UserDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCreateActivity extends BaseActivity {


    @BindView(R.id.editText)
    EditText name;

    @BindView(R.id.email_optional)
    EditText email_optional;


    @BindView(R.id.editText3)
    EditText pin;

    @BindView(R.id.editText2)
    EditText driverId;


    @BindView(R.id.spinner)
    Spinner spinner;

    @BindView(R.id.submitDetails)
    Button submitDetails;
    private ApiInterface mApiService_;

String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_screen);
        ButterKnife.bind(this);
        mApiService_ = new ApiUtils().getApiInterfaces();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            number = extras.getString("number");
        }

        submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateFields();


            }
        });

    }


    void createUser(String pin, String driverAgra, String email) {
        showProgressDialog();
        JsonObject json = new JsonObject();
        try {
            json.addProperty("email", "" + email);
            json.addProperty("password", "" + pin);
            json.addProperty("role", "driver");
            json.addProperty("driverAggregator", "" + driverAgra);
            json.addProperty("mobile", ""+number);
        } catch (Exception e) {

        }

        Call<UserDetails> payment = mApiService_.createUser(json);
        payment.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                hideProgressDialog();


            try {


                AppSharedPreUtils
                        .getInstance(getApplicationContext()).saveUserDetails(response.body().getUser());
                User user = response.body().getUser();
                if (user != null) {
                    Intent myAct = new Intent(getApplicationContext(), MainActivity.class);
                    myAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myAct);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {

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
                showMessage(finallyError);
            }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    void showMessage(String message)
    {
        Gson gson = new GsonBuilder().create();
        ApiError mverror = new    ApiError();
        mverror = gson.fromJson(message, ApiError.class);
        Toast.makeText(getApplicationContext(),mverror.getError().toString(),Toast.LENGTH_LONG).show();
    }

    void validateFields() {
        if (name.getText().toString().length() == 0) {
            makeToast("Please enter name");
            return;
        }

        if (pin.getText().toString().length() == 0) {
            makeToast("Please set pin");
            return;
        }
        createUser(pin.getText().toString(), (String) spinner.getSelectedItem(), email_optional.getText().toString());
    }


    void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}
