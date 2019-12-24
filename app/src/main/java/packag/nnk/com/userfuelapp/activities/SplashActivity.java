package packag.nnk.com.userfuelapp.activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.model.User;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.easywaylocation.EasyWayLocation;
import com.example.easywaylocation.Listener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

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

    private Double lati = 0.0, longi = 0.0;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (isGooglePlayServicesAvailable()) {
            getLocationPermission();
        } else {
            Toast.makeText(this, "No google play services enabled", Toast.LENGTH_SHORT).show();
        }
//        easyWayLocation = new EasyWayLocation(this, false, this);
//        easyWayLocation.startLocation();
        openLocation();
        if (permissionIsGranted()) {
            doLocationWork();
        } else {
            // Permission not granted, ask for it
            getLocationPermission();
        }
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public boolean permissionIsGranted() {

        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void doLocationWork() {
        lati = easyWayLocation.getLatitude();
        longi = easyWayLocation.getLongitude();
        Log.e("LOC", "LOC" + lati);
        if (lati != 0.0) {
            packag.nnk.com.userfuelapp.model.Location loc = new packag.nnk.com.userfuelapp.model.Location();
            loc.setLatitude(easyWayLocation.getLatitude());
            loc.setLongitude(easyWayLocation.getLongitude());
            openNextActivity();
        }
//        easyWayLocation.startLocation();
    }

    @Override
    public void locationOn() {
        // easyWayLocation.startLocation();

        //  doLocationWork();
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

        //  easyWayLocation.startLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // easyWayLocation.endUpdates();

    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "3");

            // easyWayLocation.startLocation();

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
//                    easyWayLocation = new EasyWayLocation(this, false, this);
//                    easyWayLocation.startLocation();
                    // createLocationRequest();

                    //  doLocationWork();
                    openLocation();
                }
            }
        }
    }


    void openLocation() {
        if (easyWayLocation == null) {
            easyWayLocation = new EasyWayLocation(this, false, this);
            easyWayLocation.startLocation();
        } else if (easyWayLocation.hasLocationEnabled()) {
            easyWayLocation.startLocation();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressBar.setVisibility(View.GONE);
//        easyWayLocation.endUpdates();

//        yourCountDownTimer.cancel();
    }

    void openNextActivity() {

        if (easyWayLocation != null) {
            if (easyWayLocation.hasLocationEnabled()) {
                easyWayLocation.endUpdates();
                easyWayLocation = null;
            }

            User user = AppSharedPreUtils.getInstance(getApplicationContext()).getUserDetails();

            try {

                if (user == null) {
                    Intent loginActivity = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(loginActivity);
                    finish();
                } else if (user.getRole().equalsIgnoreCase("guest")) {
                    Intent myAct = new Intent(getApplicationContext(), UserCreateActivity.class);
                    myAct.putExtra("number", "" + user.getMobile());
                    startActivity(myAct);
                    finish();
                } else if (!user.getIsPinAvailable()) {
                    Intent myAct = new Intent(getApplicationContext(), SetPinActivity.class);
                    startActivity(myAct);
                    finish();

                } else {
                    Intent loginActivity = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(loginActivity);
                    finish();
                }
            } catch (Exception e) {
                Intent loginActivity = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(loginActivity);
                finish();
            }

        }
    }


}
