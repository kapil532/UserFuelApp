package packag.nnk.com.userfuelapp.base;

import android.app.Application;

import com.google.android.libraries.places.api.Places;

public class BaseApplicationClass extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();

        if (!Places.isInitialized())
        {
            Places.initialize(getApplicationContext(), CommonClass.GCP_KEY);
        }
    }
}
