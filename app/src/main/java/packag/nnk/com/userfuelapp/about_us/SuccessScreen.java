package packag.nnk.com.userfuelapp.about_us;

import android.os.Bundle;
import android.os.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.check_view.CheckView;

public class SuccessScreen extends BaseActivity {


    @BindView(R.id.checkbox)
    CheckView checkbox;

    final Handler handler = new Handler();
    final Handler handler2 = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_screen);
        ButterKnife.bind(this);



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                checkbox.check();
            }
        }, 200);


        handler2.postDelayed(new Runnable() {
            @Override
            public void run()
            {
               finish();
            }
        }, 3000);


    }

    @Override
    protected void onStop() {
        super.onStop();
      /*  if(handler!= null)
        {
            handler.
        }*/
    }
}
