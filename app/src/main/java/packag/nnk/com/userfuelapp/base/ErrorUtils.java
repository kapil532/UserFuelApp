package packag.nnk.com.userfuelapp.base;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.ApiError;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

   /*public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                ApiClient.class
                        .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }

        return error;
    }*/


    public static ApiError parseError(final Response<?> response) {
        JSONObject bodyObj = null;
        boolean success;
        ArrayList messages = new ArrayList<>();

        try {
            String errorBody = response.errorBody().string();

            if (errorBody != null) {
                bodyObj = new JSONObject(errorBody);

                success = bodyObj.getBoolean("success");
                JSONArray errors = bodyObj.getJSONArray("errors");

                for (int i = 0; i < errors.length(); i++) {
                    messages.add(errors.get(i));
                }
            } else {
                success = false;
                messages.add("Unable to parse error");
            }
        } catch (Exception e) {
            e.printStackTrace();

            success = false;
            messages.add("Unable to parse error");
        }

        return new ApiError();
    }
}