package packag.nnk.com.userfuelapp.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import packag.nnk.com.userfuelapp.model.Location;
import packag.nnk.com.userfuelapp.model.OtpValidateRes;
import packag.nnk.com.userfuelapp.model.User;

public class AppSharedPreUtils {

    private static AppSharedPreUtils sharePref = new AppSharedPreUtils();
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private AppSharedPreUtils() {} //prevent creating multiple instances by making the constructor private

    //The context passed into the getInstance should be application level context.
    public static AppSharedPreUtils getInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
            editor = sharedPreferences.edit();
        }
        return sharePref;
    }

    public void saveDashBoardSectionData(OtpValidateRes dataModel){
        Gson gson = new Gson();
        String json = gson.toJson(dataModel);
        editor.putString("OtpValidateRes", json);
        editor.commit();
    }

    public OtpValidateRes getDashBoardSectionData(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("OtpValidateRes", "");
        Log.e("SHARED", "OtpValidateRes Image -> " + json);


        return gson.fromJson(json, OtpValidateRes.class);
    }


    public void saveLocation(Location dataModel){
        Gson gson = new Gson();
        String json = gson.toJson(dataModel);
        editor.putString("OtpValidateRes", json);
        editor.commit();
    }

    public Location getLocation(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("OtpValidateRes", "");
        Log.e("SHARED", "OtpValidateRes Image -> " + json);


        return gson.fromJson(json, Location.class);
    }



    public void saveUserDetails(User dataModel){
        Gson gson = new Gson();
        String json = gson.toJson(dataModel);
        editor.putString("USERDETAIL", json);
        editor.commit();
    }

    public User getUserDetails(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("USERDETAIL", "");
        Log.e("SHARED", "OtpValidateRes Image -> " + json);


        return gson.fromJson(json, User.class);
    }



    public void saveStringValues(String valuesKey, String values){
        editor.putString(valuesKey, values);
        editor.commit();
    }

    public String getStringValues(String valuesKey){
        return sharedPreferences.getString(valuesKey, "");
    }

    public void saveIntValues(String valuesKey, int values){
        editor.putInt(valuesKey, values);
        editor.commit();
    }

    public int getIntValues(String valuesKey){
        return sharedPreferences.getInt(valuesKey, 0);
    }

    public void removeValues(String valueKey) {
        editor.remove(valueKey);
        editor.commit();
    }

    public void clearAll() {
        editor.clear();
        editor.commit();
    }

}
