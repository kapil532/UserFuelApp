package packag.nnk.com.userfuelapp.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import packag.nnk.com.userfuelapp.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_screen);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        ;
        if (bundle != null) {
            petr_name = bundle.getString("petr_name");
            petr_price = bundle.getString("petr_price");
        }
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());

        p_name.setText("You have paid  \n\n " +
                "Amount paid : "+getResources().getString(R.string.symbol_rs)+" "+petr_price+"\n" +
                "  Bunk Name : "+petr_name +" !\n"+
                "       Time : "+currentTime+"\n"+
                "       Date : "+currentDate);

        handler.postDelayed(my,400);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
