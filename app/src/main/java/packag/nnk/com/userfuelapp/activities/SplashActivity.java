package packag.nnk.com.userfuelapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.model.OtpValidateRes;
import packag.nnk.com.userfuelapp.model.User;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class SplashActivity extends BaseActivity implements Listener {
    private String TAG = SplashActivity.class.getSimpleName();

    private LatLngBounds BOUNDS_INDIA = new LatLngBounds(
            new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));

    private static final int ERROR_DIALOG_CODE = 101;
    private static final int PER_REQ_CODE = 102;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private String myPer[] = {Manifest.permission.ACCESS_FINE_LOCATION};


    EasyWayLocation easyWayLocation;
    private Double lati, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (isGooglePlayServicesAvailable()) {
            getLocationPermission();
        } else {
            Toast.makeText(this, "No google play services enabled", Toast.LENGTH_SHORT).show();
        }
        easyWayLocation = new EasyWayLocation(this, false,this);
    }

    @Override
    public void locationOn() {
        Toast.makeText(this, "Location ON", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void currentLocation(Location location) {
        StringBuilder data = new StringBuilder();
        data.append(location.getLatitude());
        data.append(" , ");
        data.append(location.getLongitude());

        Log.e("LOC", "LOC" + data);
        packag.nnk.com.userfuelapp.model.Location loc = new packag.nnk.com.userfuelapp.model.Location();
        loc.setLatitude(location.getLatitude());
        loc.setLongitude(location.getLongitude());
        AppSharedPreUtils.getInstance(getApplicationContext()).saveLocation(loc);

        openNextActivity();
//      /  getLocationDetail.getAddress(location.getLatitude(), location.getLongitude(), "xyz");
    }

    @Override
    public void locationCancelled() {
        Toast.makeText(this, "Location Cancelled", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EasyWayLocation.LOCATION_SETTING_REQUEST_CODE:
                easyWayLocation.onActivityResult(resultCode);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        easyWayLocation.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        easyWayLocation.endUpdates();

    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "3");

//            createLocationRequest();
            easyWayLocation = new EasyWayLocation(this, false, this);

        } else {
            ActivityCompat.requestPermissions(this, myPer, PER_REQ_CODE);
        }
    }


    private Boolean isGooglePlayServicesAvailable() {
        Log.e("Services", "Checking Google Play Services");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS) {
            Log.e(TAG, "Services Available");
            return true;
        } else {
            if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
                Log.e(TAG, "1");
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, available, ERROR_DIALOG_CODE);
                dialog.show();

                return true;
            } else {
                Log.e(TAG, "2");
                return false;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");

        switch (requestCode) {
            case PER_REQ_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Log.e(TAG, "6");
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            Toast.makeText(this, "Permissions are mandatory", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    easyWayLocation = new EasyWayLocation(this, false, this);
                    // createLocationRequest();
                }
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        easyWayLocation.endUpdates();

//        yourCountDownTimer.cancel();
    }

    void openNextActivity() {
        easyWayLocation.endUpdates();
        User user = AppSharedPreUtils.getInstance(getApplicationContext()).getUserDetails();
        if (user == null) {
            Intent loginActivity = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(loginActivity);
            finish();
        } else {
            Intent loginActivity = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(loginActivity);
            finish();
        }
    }


}
