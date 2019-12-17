package packag.nnk.com.userfuelapp.about_us;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;


import org.json.JSONException;
import org.json.JSONObject;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.interfaces.IRetrofit;
import packag.nnk.com.userfuelapp.model.SlackMessage;
import packag.nnk.com.userfuelapp.petrol_bunk_details.GetList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kapil on 6/2/17.
 */

public class CustomSupportScreenActivity extends BaseActivity {
    private final String TAG = CustomSupportScreenActivity.class.getName();

    private final int REQUEST_PHONE_CALL = 1001;
    private CheckBox one, two, three, four;
    private Toolbar mToolbar;
    private TextView title, callNow, faq_touch;
    private EditText editbox;
    private Button submit_button;
    private String array[] = {"I'm unable to make payment", "I'm unable to get message", "I'd like a call back as soon as possible"};
    private String text;
    IRetrofit getApiInterfacesForSlack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_support);
        getApiInterfacesForSlack = new ApiUtils().getApiInterfacesForSlack();


        initViews();
        initListeners();

        checkAndCallHelpline();
    }

    private void initViews() {
        one = (CheckBox) findViewById(R.id.one);
        two = (CheckBox) findViewById(R.id.two);
        three = (CheckBox) findViewById(R.id.three);
        four = (CheckBox) findViewById(R.id.four);
        editbox = (EditText) findViewById(R.id.edit_box);
        callNow = (TextView) findViewById(R.id.callNow);
        faq_touch = (TextView) findViewById(R.id.faq_touch);
        faq_touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        callNow.setVisibility(View.GONE);

        submit_button = (Button) findViewById(R.id.submit_button);
        submit_button.setVisibility(View.GONE);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Support");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


    }

    private void initListeners() {
        editbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                   /* two.setChecked(false);
                    three.setChecked(false);
                    one.setChecked(false);
                    //editbox.setVisibility(View.VISIBLE);*/
                    four.setChecked(true);
                    openButton(0);
                } else {
                   /* two.setChecked(false);
                    three.setChecked(false);
                    one.setChecked(false);*/
                    four.setChecked(false);
                    //openButton(1);
                    //editbox.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*two.setChecked(false);
                three.setChecked(false);
                four.setChecked(false);
                editbox.setVisibility(View.GONE);*/
                openButton(0);
                text = "I'm unable to make a booking";
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //one.setChecked(false);
                //three.setChecked(false);
                //four.setChecked(false);
                openButton(0);
                text = "I'm unable to checkout";
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //two.setChecked(false);
                //one.setChecked(false);
                //four.setChecked(false);
                //editbox.setVisibility(View.GONE);
                openButton(0);
                text = "I'd like a call back as soon as possible";
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //two.setChecked(false);
                //three.setChecked(false);
                //one.setChecked(false);
                //editbox.setVisibility(View.VISIBLE);
                openButton(0);
                text = "" + editbox.getText().toString();
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked'
                finish();
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!one.isChecked() && !two.isChecked() && !three.isChecked() && !four.isChecked()) {
                    Toast.makeText(CustomSupportScreenActivity.this, "Please select one issue!", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    String s = "";
                    if (one.isChecked()) {
                        s = array[0];
                    }
                    if (two.isChecked()) {
                        s = s + "\n" + array[1];
                    }
                    if (three.isChecked()) {
                        s = s + "\n" + array[2];
                    }
                    if (four.isChecked()) {
                        if (editbox.getText().length() > 1) {
                            // automaticBooking(editbox.getText().toString());
                            s = s + "\n" + editbox.getText().toString();
                        } else {
                            editbox.setError("Please write something!");
                            return;
                        }
                    }
                    automaticBooking(s);
                }
            }
        });
    }

    private void checkAndCallHelpline() {

        callNow.setVisibility(View.VISIBLE);
        callNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(CustomSupportScreenActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // CALL_PHONE permission has not been granted.
                    requestPhoneCallPermission();
                } else {
                    callHelpline();
                }
            }
        });

    }


    private void automaticBooking(String message) {

        showProgressDialog();
        JsonObject slac = new JsonObject();
        try {
            slac.addProperty("text",user.getUsername()+"   "+message);
        }
        catch (Exception e)
        {

        }

        Log.e("Message", "Message-->" + slac.toString());
        Call<String> getList = getApiInterfacesForSlack.postRawJSON(slac);
        getList.enqueue(new Callback<String>() {


            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                hideProgressDialog();
                Log.e("Message", "Message--->" + response.body());
                message("Someone will be in touch with you shortly!");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                hideProgressDialog();
                message("Someone will be in touch with you shortly!");

            }
        });


        // TODO: Implement this method to send token to your app server.
        // JSONObject table = new JSONObject();







      /*  try {

          //  table.put("channel", "#customer_support");
           // table.put("username", "" + network.getUserName());
           // table.put("text", message + "  Phone: " + network.getMobile() + " Email : " + network.getEmail() );
            ///table.put("icon_emoji", ":pray:");

        } catch (JSONException e) {
        }*/

        //  PostData.call(this,table, "https://hooks.slack.com/services/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", bookingAuto);
    }

    public static String messageToShow = "";
/*
    PostData.PostCommentJsonResponseListener bookingAuto = new PostData.PostCommentJsonResponseListener() {


        @Override
        public void requestStarted() {
            showProgressDialog();
        }

        @Override
        public void requestEndedWithError(VolleyError error) {
            // TODO Auto-generated method stub
            hideProgressDialog();
            Log.d("ERROR", "ERROR" + error.getMessage());
            message("Someone will be in touch with you shortly!");
        }

        @Override
        public void requestCompleted(JSONObject message) {

            hideProgressDialog();

            message("Someone will be in touch with you shortly!");

        }
    };*/

    private void openButton(int key) {

        switch (key) {
            case 0:
                submit_button.setVisibility(View.VISIBLE);
                break;

            case 1:
                submit_button.setVisibility(View.GONE);
                break;
        }
    }

    private void message(String message) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);
        builder1.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    /**
     * Requests the Phone call permission.
     * If the permission has been denied previously, a SnackBar will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestPhoneCallPermission() {
        Log.i(TAG, "CALL_PHONE permission has NOT been granted. Requesting permission.");

        // BEGIN_INCLUDE(CALL_PHONE_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            Log.i(TAG,
                    "Displaying CALL_PHONE permission rationale to provide additional context.");
            Snackbar.make(editbox, "Permission",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(CustomSupportScreenActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    REQUEST_PHONE_CALL);
                        }
                    })
                    .show();
        } else {
            // CALL_PHONE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_PHONE_CALL);
        }
        // END_INCLUDE(CALL_PHONE_permission_request)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_CALL) {
            // Received permission result for PHONE_CALL permission.
            Log.i(TAG, "Received response for PHONE_CALL permission request.");

            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // PHONE_CALL permission has been granted, preview can be displayed
                Log.i(TAG, "PHONE_CALL permission has now been granted. Showing preview.");
                callHelpline();
            } else {
                Log.i(TAG, "PHONE_CALL permission was NOT granted.");
                Snackbar.make(editbox, "Permission done",
                        Snackbar.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @SuppressLint("MissingPermission")
    private void callHelpline() {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:+919886129128"));
        startActivity(intent);
    }
}
