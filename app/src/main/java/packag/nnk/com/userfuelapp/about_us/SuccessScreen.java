package packag.nnk.com.userfuelapp.about_us;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.activities.MainActivity;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.check_view.CheckView;
import packag.nnk.com.userfuelapp.transaction.TransactionActivity;

public class SuccessScreen extends BaseActivity {


    @BindView(R.id.checkbox)
    CheckView checkbox;

    @BindView(R.id.p_name)
    TextView p_name;


    @BindView(R.id.done)
    Button done;

    @BindView(R.id.history)
    Button history;


    String petr_name,petr_price;

    final Handler handler = new Handler();
    final Handler handler2 = new Handler();
    String petrolID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_screen);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();



        if (bundle != null) {
            petr_name = bundle.getString("petr_name");
            petr_price = bundle.getString("petr_price");
            petrolID = bundle.getString("petrolID");
        }
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());

        p_name.setText("You have paid  \n\n " +
                "Amount paid : "+getResources().getString(R.string.symbol_rs)+" "+petr_price+"\n" +
                "  Bunk Name : "+petr_name +" !\n"+
                "  Bunk Id : "+petrolID +" \n"+
                "       Time : "+currentTime+"\n"+
                "       Date : "+currentDate);

        handler.postDelayed(my,400);
        setFont(done);
        setFont(history);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       /* showNotification("payment done","You have paid  \n\n " +
                "Amount paid : "+getResources().getString(R.string.symbol_rs)+" "+petr_price+"\n" +
                "  Bunk Name : "+petr_name +" !\n"+
                "  Bunk Id : "+petrolID +" \n"+
                "       Time : "+currentTime+"\n"+
                "       Date : "+currentDate);*/

        createSimpleNotification(getApplicationContext());

      /*  handler2.postDelayed(new Runnable() {
            @Override
            public void run()
            {
               finish();
            }
        }, 3000);*/


    }

    @OnClick(R.id.history)
    void openHistoryPage()
    {
        Intent history = new Intent(getApplicationContext(), TransactionActivity.class);
        startActivity(history);
        finish();
    }
    public static final String NOTIFICATION_CHANNEL_ID = "channel_id";
   Runnable my= new Runnable() {
        @Override
        public void run() {
            //Do something after 100ms
            checkbox.check();
        }
    };
    @Override
    protected void onStop() {
        super.onStop();
       if(handler!= null)
        {
            handler.removeCallbacks(my);
        }
    }

    void showNotification(String title,String description)
    {

//Notification Channel ID passed as a parameter here will be ignored for all the Android versions below 8.0
      /*  NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title);
        builder.setContentText(description);
        builder.setSmallIcon(R.drawable.ic_about);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        Notification notification = builder.build();

*/
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //---PendingIntent to launch activity if the user selects
        // the notification---
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
       // notificationIntent.putExtra("mytype", "2 minutes later?");
        PendingIntent contentIntent = PendingIntent.getActivity(this, 201, notificationIntent, 0);

        //create the notification
        Notification notif = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_about)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setWhen(System.currentTimeMillis())  //When the event occurred, now, since noti are stored by time.
                .setContentTitle(title)   //Title message top row.
                .setContentText(description)  //message when looking at the notification, second row
                .setContentIntent(contentIntent)  //what activity to open.
                .setAutoCancel(true)   //allow auto cancel when pressed.
                .setChannelId(NOTIFICATION_CHANNEL_ID)
                .build();  //finally build and return a Notification.

        //Show the notification
        nm.notify(201, notif);

    }


    public void createSimpleNotification(Context context) {
        // Creates an explicit intent for an Activity
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent(context, SuccessScreen.class);

        // Creating a artifical activity stack for the notification activity
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(SuccessScreen.class);
        stackBuilder.addNextIntent(resultIntent);

        // Pending intent to the notification manager
        PendingIntent resultPending = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Building the notification
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_about) // notification icon
                .setContentTitle("I'm a simple notification") // main title of the notification
                .setContentText("I'm the text of the simple notification") // notification text
                .setContentIntent(resultPending); // notification intent

        // mId allows you to update the notification later on.
        nm.notify(10, mBuilder.build());
    }

}
