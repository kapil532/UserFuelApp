package packag.nnk.com.userfuelapp.fragment_view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinVerification extends DialogFragment
{

    private ApiInterface mApiService_;
    User user;
    EditText pin;
    Button button;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        user =   AppSharedPreUtils.getInstance(getContext()).getUserDetails();
        mApiService_ = new ApiUtils().getApiInterfaces();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pin_verification_fragment, container, false);
        pin = view.findViewById(R.id.pin_verification);
        button = view.findViewById(R.id.verify_pin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.getText().toString().length() == 4)
                {
                    validatePin(pin.getText().toString());
                }
                else
                {
                    Toast.makeText(getContext(),"Please enter the pin!",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }



    void validatePin(String pin)
    {
        button.setClickable(false);
        button.setText("Veryfing....");

        JsonObject json = new JsonObject();
        try {
            json.addProperty("userId", "" + user.getUserId());
            json.addProperty("pin", ""+pin);
        } catch (Exception e) {

        }

        Call<JsonObject> payment = mApiService_.validatePin(json);
        payment.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response)
            {
                Log.e("PIN VERY","PIN-->"+response.body());

             /*
                if()
                {

                }*/
                button.setClickable(true);
                button.setText("Verify again");

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                button.setClickable(true);
                button.setText("Verify again");

            }
        });

    }
}

