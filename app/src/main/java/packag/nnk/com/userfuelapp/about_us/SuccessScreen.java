package packag.nnk.com.userfuelapp.about_us;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.check_view.CheckView;

public class SuccessScreen extends BaseActivity {


    @BindView(R.id.checkbox)
    CheckView checkbox;

    @BindView(R.id.p_name)
    TextView p_name;


    @BindView(R.id.done)
    Button done;


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
        p_name.setText("You have paid "+getResources().getString(R.string.symbol_rs)+" "+petr_price+" at "+petr_name +" !");

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
