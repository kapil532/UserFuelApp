package packag.nnk.com.userfuelapp.base;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import packag.nnk.com.userfuelapp.activities.LoginActivity;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.ApiError;
import packag.nnk.com.userfuelapp.model.FailedStatus;
import packag.nnk.com.userfuelapp.model.OtpValidateRes;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    //TO handle error
    // if json like this {"status":"failed","message":"Not having sufficient balance"}

    public static FailedStatus getStatus(final Response<?> response)
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
                ea.printStackTrace();
            }
        } catch (Exception eaa) {
            eaa.printStackTrace();
        }
        String finallyError = sb.toString();

        Log.e("EOOROR","ERROR"+finallyError);

        try
        {
            JSONObject json = new JSONObject(finallyError);
            String message =json.getString("error");
            if(message.equalsIgnoreCase("Your token has expired."))
            {
                AppSharedPreUtils.getInstance(BaseApplicationClass.getAppContext()).clearAll();
                Intent login = new Intent(BaseApplicationClass.getAppContext(), LoginActivity.class);
                BaseApplicationClass.getAppContext().startActivity(login);
            }

        }
        catch (Exception e)
        {

        }


        Gson gson = new Gson();

        return gson.fromJson(finallyError, FailedStatus.class);

    }
}