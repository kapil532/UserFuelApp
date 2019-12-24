package packag.nnk.com.userfuelapp.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Currency;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.about_us.SuccessScreen;
import packag.nnk.com.userfuelapp.model.OtpValidateRes;
import packag.nnk.com.userfuelapp.model.User;

public class BaseActivity extends AppCompatActivity
{
    private ProgressDialog mProgressDialog;
    private AlertDialog alert;
    private Dialog dialog;
    private AnimationDrawable animationDrawable;
   public User user;
protected String URL_IMAGE;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
         user =   AppSharedPreUtils.getInstance(getApplicationContext()).getUserDetails();
         if(user != null)
             URL_IMAGE ="https://firebasestorage.googleapis.com/v0/b/gofueluser.appspot.com/o/images%2F"+user.getUserId()+".png?alt=media&token=19cffa0e-d0d2-4562-8ee6-75c9207c263f";
    }

    public SpannableString getSpannableString(Context context, String text)
    {

        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new TextAppearanceSpan(context, R.style.b), 0, 1, 0);
        spannableString.setSpan(new TextAppearanceSpan(context, R.style.u), 1, 2, 0);
        spannableString.setSpan(new TextAppearanceSpan(context, R.style.n), 2, 3, 0);
        spannableString.setSpan(new TextAppearanceSpan(context, R.style.c), 3, 4, 0);

        return spannableString;
    }

    public Typeface getMediumFont(Context context)
    {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/GOTHAMRND-MEDIUM.OTF");

        return typeface;
    }

    public Typeface getBookFont(Context context)
    {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/GOTHAM-ROUNDED-BOO.OTF");

        return typeface;
    }

    public void hideSoftKeyboard()
    {
        View view = this.getCurrentFocus();

        if (view != null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

            if (imm != null)
            {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

   /* public AnimationDrawable showGIF(ImageView imageView, int i)
    {
        imageView = (ImageView) findViewById(i);
        imageView.setBackgroundResource(R.drawable.anim);
        animationDrawable = (AnimationDrawable) imageView.getBackground();

        imageView.setVisibility(View.VISIBLE);

        return animationDrawable;
    }*/

    public void setDarkBackgroundButton(Button backgroundButton)
    {
        backgroundButton.setEnabled(true);
        backgroundButton.setClickable(true);
        backgroundButton.setBackground(getDrawable(R.drawable.dark_button_background));
    }

    public void setLightBackgroundButton(Button backgroundButton)
    {
        backgroundButton.setEnabled(false);
        backgroundButton.setClickable(false);
        backgroundButton.setBackground(getDrawable(R.drawable.light_button_background));
    }

    public void hideGIF(ImageView imageView)
    {
        if (animationDrawable.isRunning())
        {
            imageView.setVisibility(View.GONE);
            animationDrawable.stop();
        }
    }

    public Snackbar snackbar(CoordinatorLayout coordinatorLayout, String message)
    {
       return Snackbar.make(coordinatorLayout, message,Snackbar.LENGTH_LONG);
    }

    /*public void showProgressDialog()
    {
        try
        {
            if (mProgressDialog == null)
            {
                mProgressDialog = new ProgressDialog(this, R.style.MyProgressDialogTheme);
                mProgressDialog.setCancelable(false);
                //mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                //mProgressDialog.setMessage("Please Wait....");
            }

            mProgressDialog.show();
        }
        catch (Exception e)
        {

        }
    }*/

    /*public void showProgressDialog()
    {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.gif_screen);

        ImageView imageView = (ImageView)dialog.findViewById(R.id.gifImage);
        Glide.with(this)
                .asGif()
                .load(R.raw.loading_sr)// you may not need this
                .into(imageView);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void hideProgressDialog()
    {
        if (dialog.isShowing())
        {
            dialog.hide();
        }
    }*/

    public void hideProgressDialog()
    {
        try
        {
            if (mProgressDialog != null && mProgressDialog.isShowing())
            {
                mProgressDialog.dismiss();
            }
        }
        catch (Exception e)
        {

        }
    }

    public void showAlert(String message)
    {
        try
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Alert!");
            builder.setMessage(message);
            builder.setCancelable(false);
            /*TextView textView = (TextView) dialog.findViewById(android.R.id.message);
            Typeface face=Typeface.createFromAsset(getAssets(),"fonts/GOTHAM-ROUNDED-BOO.OTF");
            textView.setTypeface(face);*/
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {

                    alert.dismiss();
                }
            });
            alert = builder.create();
            alert.show();
        }
        catch (Exception e)
        {

        }

    }

    public void showProgressDialog()
    {
        try
        {
            if (mProgressDialog == null)
            {
                mProgressDialog = new ProgressDialog(this, R.style.MyProgressDialogTheme);
                mProgressDialog.setCancelable(false);
                /*mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                mProgressDialog.setMessage("Please Wait....");*/

                Log.e("fsd","check");
            }

            mProgressDialog.show();
        }
        catch (Exception e)
        {

        }
    }

    /*public SharedPreferences getSharedP(Context ctx)
    {
        SharedPreferences pref = ctx.getSharedPreferences(CommonClass.SP_BUNC, MODE_PRIVATE);
        //String userObject = pref.getString(key, "");
        return pref;
    }*/

    /*private void getActionBarr()
    {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
        {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        }
    }*/

    /*public boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;

        }
    }*/

    public String getCurrencySymbol(String countryCode, String TAG)
    {
        Locale locale = new Locale("EN",countryCode);
        Currency currency = Currency.getInstance(locale);
        String symbol = currency.getSymbol();

        Log.e(TAG,"Symbol " + symbol);

        return symbol;
    }

    public String getCurrencySymbolWithCurrCode(String currencyCode, String TAG)
    {
        Locale locale = new Locale("EN",currencyCode);
        Currency currency = Currency.getInstance(currencyCode);
        String symbol = currency.getSymbol();

        Log.e(TAG,"Symbol " + symbol);

        return symbol;
    }

    protected void showSuccessScreen()
    {
        Intent success = new Intent(this, SuccessScreen.class);

        startActivity(success);
    }

    public void printLogs(String TAG, String message)
    {
        Log.e(TAG, message);
    }


  protected   void setFont(TextView tc)
    {
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/GOTHAM-ROUNDED-BOO.OTF");
        tc.setTypeface(tf);
    }
}
