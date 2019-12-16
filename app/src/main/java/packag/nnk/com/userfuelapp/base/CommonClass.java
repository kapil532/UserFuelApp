package packag.nnk.com.userfuelapp.base;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class CommonClass
{
    public static final String BASE_URL = "http://driver.gudy.in/api/";
    public static final String SUCCESS_MESSGE = "success";
    public static final String MESSAGE_KEY = "message";
    public static final String MSG_CODE = "msg_code";
    public static final String GCP_KEY = "AIzaSyBDCa_MSc0rmkV-IDo4CiOZRywm8jvG_2c";
    public static final String API_STATUS = "api_status";
    public static final String SP = "sp_pref";
    public static final String USERID = "user_id";
    public static final String USER_TOKEN = "user_token";


    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 100; // 10 meters
    // The minimum time between updates in milliseconds
    public static final long MIN_TIME_BW_UPDATES = 100000; // 2 seconds




    //-------------------------//

    public static final String GET_OTP = "otp/create";
    public static final String GET_TRANSACTION = "/transaction_list";
    public static final String VALIDATE_OTP = "otp/validate";
    public static final String CHECK_BALANCE = "driver/checkBalance/";
    public static final String AUTH_REGISTER = "auth/register/";
    public static final String RANGE_TRANSACTION = "driver/rangeTransaction/";
    public static final String DRIVER_PAYMENT = " driver/bunkPayment/";

//    driver/bunkPayment/625ea288-c31c-4e21-ab5d-6d88af2a01fe

//    api/driver/rangeTransaction/627a72e0-baf4-4eb2-9d07-afd15c30717e/30

    public static final String GET_PETROL_BUNK_DETAILS="https://maps.googleapis.com/maps/api/place/nearbysearch/";
    public static final String POST_SUPPORT_SLACK="https://hooks.slack.com/services/";

//    https://hooks.slack.com/services/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


    public static String getAssetJsonData(Context context, String fileName) {
        String json;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
