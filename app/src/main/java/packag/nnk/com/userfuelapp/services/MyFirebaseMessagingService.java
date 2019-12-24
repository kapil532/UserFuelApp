package packag.nnk.com.userfuelapp.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private String TAG = MyFirebaseMessagingService.class.getSimpleName();
    // This is the Notification Channel ID. More about this in the next section
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

//        {amount=200.0, message=Amount debited}
        Log.e("REMOTE MESSAGE", "----" + remoteMessage.getData());
        showNotification("goFuels", "Hi your goFuel wallet " + remoteMessage.getData().get("message") + " " + getResources().getString(R.string.symbol_rs)
                + " " + remoteMessage.getData().get("amount") + ".");

    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.e(TAG, "Token " + s);
        AppSharedPreUtils.getInstance(getApplicationContext()).saveStringValues("FIREBASE_KEY", s);
    }

    int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);

    void showNotification(String title, String description) {

        NotificationCompat.Builder drivingNotifBldr = new NotificationCompat.Builder(getApplicationContext(), "Message")
                .setContentTitle(title)
                .setContentText(description)
                .setLights(Color.BLUE, 500, 500)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.gofuels))
                .setSmallIcon(R.drawable.ic_about)
                .setAutoCancel(true);

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Since android Oreo notification channel is needed.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Message", "Message", android.app.NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(iUniqueId, drivingNotifBldr.build());
    }
}
