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

import androidx.cardview.widget.CardView;
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

    @BindView(R.id.time)
    TextView time;

    @BindView(R.id.transaction_id)
    TextView transaction_id;

    @BindView(R.id.bunkName)
    TextView bunkName;


    @BindView(R.id.paidAmount)
    TextView paidAmount;


    @BindView(R.id.supportLayout)
    CardView supportLayout;





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
        String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

        time.setText(currentTime +" on "+currentDate);
        transaction_id.setText(petrolID);
        bunkName.setText(petr_name);
        paidAmount.setText(getResources().getString(R.string.symbol_rs)+" "+petr_price);


        handler.postDelayed(my,400);
        setFont(done);
        setFont(history);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    @OnClick(R.id.supportLayout)
    void openSupport()
    {
        Intent history = new Intent(getApplicationContext(), CustomSupportScreenActivity.class);
        startActivity(history);
        finish();
    }



    @OnClick(R.id.history)
    void openHistoryPage()
    {
        Intent history = new Intent(getApplicationContext(), TransactionActivity.class);
        startActivity(history);
        finish();
    }
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



}
