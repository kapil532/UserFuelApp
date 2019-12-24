package packag.nnk.com.userfuelapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.transaction.TransactionActivity;

public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

   /* @BindView(R.id.notification)
    Switch notification;*/

/*
    @BindView(R.id.logout)
    TextView logout;*/

/*

    @BindView(R.id.version)
    TextView version;
*/

    @BindView(R.id.checkBox2)
    CheckBox checkBox2;

    @BindView(R.id.checkBox3)
    CheckBox checkBox3;

    @BindView(R.id.version)
    TextView version;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_new_screen);
        ButterKnife.bind(this);


       /* logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertBox("Do you want to logout!");
            }
        });*/
        context = getApplicationContext();
        setupNavigation();
        initializeSw();
        getVersion();

    }

    private String VersionName;
    private String VersionCode;

    Context context;

    @OnClick(R.id.version)
    void logOut() {
        AppSharedPreUtils.getInstance(getApplicationContext()).clearAll();
        Intent history = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(history);
        finish();
    }

    void getVersion()
    {
        try {

            VersionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;

            /*I find usefull to convert vervion code into String, so it's ready for TextViev/server side checks*/
            version.setText("version : v" + VersionName);

            VersionCode = Integer.toString(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setupNavigation() {

        toolbar.setTitle("Setting");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    @Override
    protected void onStop() {
        super.onStop();
        CommonClass.saveGenericData("" + checkBox2.isChecked(), "checkBox2_NOTIFICATION_CHECKED", "checkBox2_NOTIFICATION_CHECKED_PRE", this);
        CommonClass.saveGenericData("" + checkBox3.isChecked(), "checkBox3_NOTIFICATION_CHECKED", "checkBox3_NOTIFICATION_CHECKED_PRE", this);
    }


    void initializeSw() {
        SwitchCompat switchCompat2 = (SwitchCompat) findViewById(R.id
                .Switch);
        switchCompat2.setOnCheckedChangeListener(this);
        if (CommonClass.returnGenericData("NOTIFICATION_CHECKED", "NOTIFICATION_CHECKED_PRE", this).equalsIgnoreCase("true")) {
            checkBox3.setAlpha(1f);
            checkBox2.setAlpha(1f);
            checkBox2.setClickable(true);
            checkBox3.setClickable(true);
            switchCompat2.setChecked(true);
        } else if (CommonClass.returnGenericData("NOTIFICATION_CHECKED", "NOTIFICATION_CHECKED_PRE", this).equalsIgnoreCase("false")) {
            checkBox3.setAlpha(0.2f);
            checkBox2.setAlpha(0.2f);
            checkBox2.setClickable(false);
            checkBox3.setClickable(false);
            switchCompat2.setChecked(false);
        } else {
            Log.d("CHEKCED ", "CHECKED --->");
            checkBox3.setAlpha(1f);
            checkBox2.setAlpha(1f);
            checkBox3.setChecked(true);
            checkBox2.setChecked(true);
            switchCompat2.setChecked(true);
        }

        if (CommonClass.returnGenericData("checkBox2_NOTIFICATION_CHECKED", "checkBox2_NOTIFICATION_CHECKED_PRE", this).equalsIgnoreCase("true")) {
            checkBox2.setChecked(true);
        } else if (CommonClass.returnGenericData("checkBox2_NOTIFICATION_CHECKED", "checkBox2_NOTIFICATION_CHECKED_PRE", this).equalsIgnoreCase("false")) {
            checkBox2.setChecked(false);
        } else {
            checkBox2.setChecked(true);
        }
        if (CommonClass.returnGenericData("checkBox3_NOTIFICATION_CHECKED", "checkBox3_NOTIFICATION_CHECKED_PRE", this).equalsIgnoreCase("true")) {
            checkBox3.setChecked(true);
        } else if (CommonClass.returnGenericData("checkBox3_NOTIFICATION_CHECKED", "checkBox3_NOTIFICATION_CHECKED_PRE", this).equalsIgnoreCase("false")) {
            checkBox3.setChecked(false);
        } else {
            checkBox3.setChecked(true);
        }
    }

    void showAlertBox(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        AppSharedPreUtils.getInstance(getApplicationContext()).clearAll();
                        openNextScreen();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    void openNextScreen() {
        Intent loginActivity = new Intent(getApplicationContext(), LoginActivity.class);
        loginActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginActivity);
        finish();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.Switch:
                Log.d("switch_compat", isChecked + "");
                if (isChecked) {
                    checkBox2.setAlpha(1f);
                    checkBox3.setAlpha(1f);
                    checkBox2.setClickable(true);
                    checkBox3.setClickable(true);
                } else {
                    checkBox3.setAlpha(0.2f);
                    checkBox2.setAlpha(0.2f);
                    checkBox2.setClickable(false);
                    checkBox3.setClickable(false);
                }
                CommonClass.saveGenericData("" + isChecked, "NOTIFICATION_CHECKED", "NOTIFICATION_CHECKED_PRE", this);
                break;

        }
    }
}
