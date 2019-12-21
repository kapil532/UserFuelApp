package packag.nnk.com.userfuelapp.base;

import android.app.Application;
import android.content.Context;

import com.google.android.libraries.places.api.Places;

public class BaseApplicationClass extends Application
{
    private static Context context;

    @Override
    public void onCreate()
    {
        super.onCreate();

        if (!Places.isInitialized())
        {
            Places.initialize(getApplicationContext(), CommonClass.GCP_KEY);
        }
        BaseApplicationClass.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return BaseApplicationClass.context;
    }
}
