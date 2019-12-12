package packag.nnk.com.userfuelapp.base;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils implements Contants
{
    private SharedPreferences sharedPreferences;
    private static PreferenceUtils preferenceUtils = null;

    public static PreferenceUtils getInstance()
    {
        if (preferenceUtils == null)
        {
            preferenceUtils = new PreferenceUtils();
        }

        return preferenceUtils;
    }

    private SharedPreferences getShar(Context context)
    {
        return context.getSharedPreferences(SP_PREF,Context.MODE_PRIVATE);
    }

    public void setToken(Context context, String token)
    {
        getShar(context).edit().putString(TOKEN, token).apply();
    }

    public String getToken(Context context)
    {
        return getShar(context).getString(TOKEN, "");
    }
}
