package packag.nnk.com.userfuelapp.services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);


    }

    @Override
    public void onNewToken(@NonNull String s)
    {
        super.onNewToken(s);
//        c2kXHNk5RJs:APA91bGExz1rnw3sYAF3GsU9avkEblVt4wdedMauaLEkt5UaIa8K_
//        9gyv48oniDjsQKHI2vJnNMrPVMoSSPiv01e_sEbhwneklV4vp-yBdzngkWk9aeKuNskAggH0FXZYRVcWNjQmRHy
        Log.e(TAG, "Token " + s);
    }
}
