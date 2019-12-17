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
import packag.nnk.com.userfuelapp.model.otp_val.User;

import android.Manifest;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class SplashActivity extends BaseActivity implements LocationListener {
    private String TAG = SplashActivity.class.getSimpleName();

    private LatLngBounds BOUNDS_INDIA = new LatLngBounds(
            new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));

    private static final int ERROR_DIALOG_CODE = 101;
    private static final int PER_REQ_CODE = 102;
    private static final int REQ_LOCATION = 1100;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private String myPer[] = {Manifest.permission.ACCESS_FINE_LOCATION};

    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;

    private LocationManager locationManager;
    CountDownTimer yourCountDownTimer;
    private boolean isGPSEnabled, isNetworkEnabled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        if (isGooglePlayServicesAvailable()) {
            getLocationPermission();
        } else {
            Toast.makeText(this, "No google play services enabled", Toast.LENGTH_SHORT).show();
        }

        yourCountDownTimer  = new CountDownTimer(2000, 1000) {                     //geriye sayma

            public void onTick(long millisUntilFinished) {


            }

            public void onFinish() {
                openNextActivity();
            }
        }.start();

    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "3");

            createLocationRequest();

        } else {
            ActivityCompat.requestPermissions(this, myPer, PER_REQ_CODE);
        }
    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100 * 1000);

        requestToSwitchOnGPS();

        hideProgressDialog();
    }

    private void requestToSwitchOnGPS() {
        LocationSettingsRequest.Builder locationSettingsRequest = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(locationSettingsRequest.build());

        task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        getLocationPermission();

                        return;
                    }
                }
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        CommonClass.MIN_TIME_BW_UPDATES,
                        CommonClass.MIN_DISTANCE_CHANGE_FOR_UPDATES, SplashActivity.this);
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;

                    try {
                        resolvableApiException.startResolutionForResult(SplashActivity.this, REQ_LOCATION);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                        Log.e(TAG, "Exception createLocationRequest " + ex.getMessage());
                    }
                }
            }
        });
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

                    createLocationRequest();
                }
            }
        }
    }

    /*private void getUsersCurrentLocations()
    {
        showProgressDialog();

        locationManager = (LocationManager) this
                .getSystemService(LOCATION_SERVICE);

        // getting GPS status
        if (locationManager != null)
        {
            Log.e(TAG,"check 8");

            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    getLocationPermission();
                }
                else
                {
                    if (isNetworkEnabled)
                    {
                        hideProgressDialog();

                        Log.e(TAG,"check 9 isNetworkEnabled");

                        if (locationManager != null)
                        {
                            Location location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null)
                            {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                Log.e(TAG,"Latitude isNetworkEnabled " + latitude);
                                Log.e(TAG,"Longitude isNetworkEnabled " + longitude);

                                Geocoder gcd = new Geocoder(getBaseContext(),
                                        Locale.getDefault());
                                List<Address> addresses;
                                try
                                {
                                    addresses = gcd.getFromLocation(latitude,
                                            longitude, 1);
                                    if (addresses.size() > 0) {
                                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                        String locality = addresses.get(0).getLocality();
                                        String subLocality = addresses.get(0).getSubLocality();
                                        String state = addresses.get(0).getAdminArea();
                                        String country = addresses.get(0).getCountryName();
                                        String postalCode = addresses.get(0).getPostalCode();
                                        String knownName = addresses.get(0).getFeatureName();
                                        String subAdminArea = addresses.get(0).getSubAdminArea();

                                        Log.e(TAG,"address " + address);
                                        Log.e(TAG,"locality " + locality);
                                        Log.e(TAG,"subLocality " + subLocality);
                                        Log.e(TAG,"state " + state);
                                        Log.e(TAG,"postalCode " + postalCode);
                                        Log.e(TAG,"knownName " + knownName);
                                        Log.e(TAG,"subAdminArea " + subAdminArea);

                                        setLocationET.setText(address);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    hideProgressDialog();
                                    Log.e(TAG,"Exception " + e.getMessage());
                                    Toast.makeText(SplashActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                sendCityForClusterNew();
                            }
                            else
                            {
                                Log.e(TAG,"Location Null");


                                locationManager.requestLocationUpdates(
                                        LocationManager.NETWORK_PROVIDER,
                                        CommonClass.MIN_TIME_BW_UPDATES,
                                        CommonClass.MIN_DISTANCE_CHANGE_FOR_UPDATES, SplashActivity.this);
                                hideProgressDialog();
                            }
                        }
                        else
                        {
                            Log.e(TAG,"Location Manager Null");
                            hideProgressDialog();
                        }
                    }
                    else if (isGPSEnabled)
                    {
                        Log.e(TAG,"check 10 isGPSEnabled");

                        if (locationManager != null)
                        {
                            Location location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null)
                            {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();

                                Log.e(TAG,"Latitude isNetworkEnabled " + latitude);
                                Log.e(TAG,"Longitude isNetworkEnabled " + longitude);

                                Geocoder gcd = new Geocoder(getBaseContext(),
                                        Locale.getDefault());
                                List<Address> addresses;
                                try
                                {
                                    hideProgressDialog();

                                    addresses = gcd.getFromLocation(latitude,
                                            longitude, 1);
                                    if (addresses.size() > 0)
                                    {
                                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                        String locality = addresses.get(0).getLocality();
                                        String subLocality = addresses.get(0).getSubLocality();
                                        String state = addresses.get(0).getAdminArea();
                                        String country = addresses.get(0).getCountryName();
                                        String postalCode = addresses.get(0).getPostalCode();
                                        String knownName = addresses.get(0).getFeatureName();
                                        String subAdminArea = addresses.get(0).getSubAdminArea();

                                        Log.e(TAG,"address " + address);
                                        Log.e(TAG,"locality " + locality);
                                        Log.e(TAG,"subLocality " + subLocality);
                                        Log.e(TAG,"state " + state);
                                        Log.e(TAG,"postalCode " + postalCode);
                                        Log.e(TAG,"knownName " + knownName);
                                        Log.e(TAG,"subAdminArea " + subAdminArea);


                                        setLocationET.setText(address);
                                    }
                                    else
                                    {
                                        Log.e(TAG, "No Address");
                                        hideProgressDialog();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    hideProgressDialog();
                                    Log.e(TAG,"Exception " + e.getMessage());
                                    //Toast.makeText(HomeScreenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                sendCityForClusterNew();
                            }
                            else
                            {
                                Log.e(TAG, "location Null");

                                locationManager.requestLocationUpdates(
                                        LocationManager.NETWORK_PROVIDER,
                                        CommonClass.MIN_TIME_BW_UPDATES,
                                        CommonClass.MIN_DISTANCE_CHANGE_FOR_UPDATES, SplashActivity.this);

                                hideProgressDialog();
                            }
                        }
                        else
                        {
                            Log.e(TAG, "location Manager Null");

                            hideProgressDialog();
                        }
                    }
                    else
                    {
                        hideProgressDialog();
                    }
                }
            }
            else
            {
                if (isNetworkEnabled)
                {
                    Log.e(TAG,"check 9 isNetworkEnabled");

                    // Log.e(“Network”, “Network”);
                    if (locationManager != null) {
                        Location location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null)
                        {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            Log.e(TAG,"Latitude isNetworkEnabled " + latitude);
                            Log.e(TAG,"Longitude isNetworkEnabled " + longitude);

                            hideProgressDialog();

                            Geocoder gcd = new Geocoder(getBaseContext(),
                                    Locale.getDefault());
                            List<Address> addresses;
                            try {
                                addresses = gcd.getFromLocation(latitude,
                                        longitude, 1);
                                if (addresses.size() > 0) {
                                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    String locality = addresses.get(0).getLocality();
                                    String subLocality = addresses.get(0).getSubLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalCode = addresses.get(0).getPostalCode();
                                    String knownName = addresses.get(0).getFeatureName();
                                    String subAdminArea = addresses.get(0).getSubAdminArea();

                                    Log.e(TAG,"address " + address);
                                    Log.e(TAG,"locality " + locality);
                                    Log.e(TAG,"subLocality " + subLocality);
                                    Log.e(TAG,"state " + state);
                                    Log.e(TAG,"postalCode " + postalCode);
                                    Log.e(TAG,"knownName " + knownName);
                                    Log.e(TAG,"subAdminArea " + subAdminArea);

                                    setLocationET.setText(address);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e(TAG,"Exception " + e.getMessage());
                                hideProgressDialog();
                                Toast.makeText(SplashActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            sendCityForClusterNew();
                        }
                        else
                        {
                            Log.e(TAG,"Location Null");

                            locationManager.requestLocationUpdates(
                                    LocationManager.NETWORK_PROVIDER,
                                    CommonClass.MIN_TIME_BW_UPDATES,
                                    CommonClass.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                            hideProgressDialog();
                        }
                    }
                    else
                    {
                        Log.e(TAG,"Location Manager Null");
                        hideProgressDialog();
                    }
                }
                else if (isGPSEnabled)
                {
                    Log.e(TAG,"check 10 isGPSEnabled");

                    //Log.e(“GPS Enabled”, “GPS Enabled”);
                    if (locationManager != null)
                    {
                        Location location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null)
                        {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            Log.e(TAG,"Latitude isNetworkEnabled " + latitude);
                            Log.e(TAG,"Longitude isNetworkEnabled " + longitude);

                            Geocoder gcd = new Geocoder(getBaseContext(),
                                    Locale.getDefault());
                            List<Address> addresses;
                            try
                            {
                                hideProgressDialog();

                                addresses = gcd.getFromLocation(latitude,
                                        longitude, 1);
                                if (addresses.size() > 0)
                                {
                                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                    String locality = addresses.get(0).getLocality();
                                    String subLocality = addresses.get(0).getSubLocality();
                                    String state = addresses.get(0).getAdminArea();
                                    String country = addresses.get(0).getCountryName();
                                    String postalCode = addresses.get(0).getPostalCode();
                                    String knownName = addresses.get(0).getFeatureName();
                                    String subAdminArea = addresses.get(0).getSubAdminArea();

                                    Log.e(TAG,"address " + address);
                                    Log.e(TAG,"locality " + locality);
                                    Log.e(TAG,"subLocality " + subLocality);
                                    Log.e(TAG,"state " + state);
                                    Log.e(TAG,"postalCode " + postalCode);
                                    Log.e(TAG,"knownName " + knownName);
                                    Log.e(TAG,"subAdminArea " + subAdminArea);


                                    setLocationET.setText(address);
                                }
                                else
                                {
                                    Log.e(TAG, "No Address");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                hideProgressDialog();
                                Log.e(TAG,"Exception " + e.getMessage());
                                //Toast.makeText(HomeScreenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            sendCityForClusterNew();
                        }
                        else
                        {
                            Log.e(TAG, "location Null");

                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    CommonClass.MIN_TIME_BW_UPDATES,
                                    CommonClass.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                            hideProgressDialog();
                        }
                    }
                    else
                    {
                        Log.e(TAG, "location Manager Null");
                        hideProgressDialog();
                    }
                }
                else
                {
                    hideProgressDialog();
                }
            }
        }
        else
        {
            hideProgressDialog();
        }
    }*/

    @Override
    public void onLocationChanged(Location location)
    {

        Toast.makeText(this, "Location " + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();

       /* Intent loginActivity = new Intent(SplashActivity.this, LoginActivity.class);
        loginActivity.putExtra("lat", "" + location.getLatitude());
        loginActivity.putExtra("lang", "" + location.getLongitude());
        startActivity(loginActivity);
        finish();
*/
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    @Override
    protected void onStop() {
        super.onStop();
        yourCountDownTimer.cancel();
    }


    void openNextActivity()
    {

        //User user =   AppSharedPreUtils.getInstance(getApplicationContext()).getUserDetails();

        User user =   AppSharedPreUtils.getInstance(getApplicationContext()).getUserOtpDetails();

        if(user == null)
        {
            Intent loginActivity = new Intent(SplashActivity.this, LoginActivity.class);
//        loginActivity.putExtra("lat", "" + location.getLatitude());
//        loginActivity.putExtra("lang", "" + location.getLongitude());
            startActivity(loginActivity);
            finish();
        }
        else
        {
            Intent loginActivity = new Intent(SplashActivity.this, MainActivity.class);
//        loginActivity.putExtra("lat", "" + location.getLatitude());
//        loginActivity.putExtra("lang", "" + location.getLongitude());
            startActivity(loginActivity);
            finish();
        }


    }

}
