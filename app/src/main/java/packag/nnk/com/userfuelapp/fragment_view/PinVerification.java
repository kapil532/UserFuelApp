package packag.nnk.com.userfuelapp.fragment_view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.goodiebag.pinview.Pinview;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.activities.MainActivity;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.interfaces.GetMessage;
import packag.nnk.com.userfuelapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinVerification extends DialogFragment implements TextWatcher
{

    private ApiInterface mApiService_;
    User user;
    EditText et_otp, editText_one,editText_two,editText_three,editText_four;
    Button button;
    Pinview pin;


    public static GetMessage getMessage;


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        user =   AppSharedPreUtils.getInstance(getContext()).getUserDetails();
        mApiService_ = new ApiUtils().getApiInterfaces();


//        getActivity().getWindow().setSoftInputMode(
////            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    protected   void setFont(TextView tc)
    {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/GOTHAM-ROUNDED-BOO.OTF");
        tc.setTypeface(tf);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pin_verification_fragment, container, false);

        et_otp= view.findViewById(R.id.et_otp);

        button = view.findViewById(R.id.verify_pin);
        setFont(et_otp);
        setFont(button);
        et_otp.setTransformationMethod(PasswordTransformationMethod.getInstance());
        // pin = (Pinview) view.findViewById(R.id.pinview);
//        pin.setInputType(Pinview.InputType.NUMBER);

       /* pin.setPinBackgroundRes(R.drawable.sample_background);
        pin.setPinHeight(40);
        pin.setPinWidth(40);
        pin.setInputType(Pinview.InputType.NUMBER);
        pin.setValue("1234");*/
        //myLayout.addView(pin);

       /* editText_one = view.findViewById(R.id.editTextone);
        editText_two = view.findViewById(R.id.editTexttwo);
        editText_three = view.findViewById(R.id.editTextthree);
        editText_four = view.findViewById(R.id.editTextfour);
        button = view.findViewById(R.id.verify_pin);

        editText_one.addTextChangedListener(this);
        editText_two.addTextChangedListener(this);
        editText_three.addTextChangedListener(this);
        editText_four.addTextChangedListener(this);*/

        // pin.requestFocus();
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    //   pin.setOtpCompletionListener(this);
       // pin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(et_otp.getText().toString().length() == 4)
                {
                    validatePin(et_otp.getText().toString());
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

                try {

                      JsonObject json =response.body();

                    if (json != null)
                    {

                        Log.e("PIN VERY","PIN-->IN");
                        if(getMessage != null)
                        {
                            Log.e("PIN VERY","PIN-->GET");
                            getMessage.getSuccessMessage("success");
                            dismiss();
                        }
                    }
                    else
                        {
                        Toast.makeText(getContext(), "Please try again!", Toast.LENGTH_LONG).show();

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
                    Toast.makeText(getContext(), "Please try again!", Toast.LENGTH_LONG).show();
                }
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


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        if (editable.length() == 1) {
            if (editText_one.length() == 1) {
                editText_two.requestFocus();
            }

            if (editText_two.length() == 1) {
                editText_three.requestFocus();
            }
            if (editText_three.length() == 1) {
                editText_four.requestFocus();
            }
        } else if (editable.length() == 0) {
            if (editText_four.length() == 0) {
                editText_three.requestFocus();
            }
            if (editText_three.length() == 0) {
                editText_two.requestFocus();
            }
            if (editText_two.length() == 0) {
                editText_one.requestFocus();
            }

        }
    }
}

