package packag.nnk.com.userfuelapp.base;

import android.content.Context;
import android.content.SharedPreferences;

public class IntroManager
{
    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public IntroManager(Context context)
    {
        this.context = context;
        preferences = context.getSharedPreferences("first", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setFirst(boolean isFirst)
    {
        editor.putBoolean("check", isFirst);
        editor.apply();
    }

    public boolean check()
    {
        return preferences.getBoolean("check", true);
    }
}
