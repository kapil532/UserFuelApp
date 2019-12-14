package packag.nnk.com.userfuelapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aflak.libraries.callback.FingerprintDialogSecureCallback;
import me.aflak.libraries.callback.PasswordCallback;
import me.aflak.libraries.dialog.FingerprintDialog;
import me.aflak.libraries.dialog.PasswordDialog;
import me.aflak.libraries.utils.FingerprintToken;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.about_us.AboutUsScreen;
import packag.nnk.com.userfuelapp.about_us.CustomSupportScreenActivity;
import packag.nnk.com.userfuelapp.about_us.SuccessScreen;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.OtpValidateRes;
import packag.nnk.com.userfuelapp.petrol_bunk_details.GetList;
import packag.nnk.com.userfuelapp.services.AutoCompleteAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

 public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener ,
         View.OnClickListener,
         FingerprintDialogSecureCallback,
         PasswordCallback
{
    private String TAG = MainActivity.class.getSimpleName();
    private ApiInterface mApiService;

    public Toolbar toolbar;

    public DrawerLayout drawerLayout;

    public NavController navController;

    public NavigationView navigationView;
    @BindView(R.id.image_icon)
    ImageView image_icon;


    @BindView(R.id.p_name)
    TextView p_name;

    @BindView(R.id.p_adress)
    TextView p_adress;

    @BindView(R.id.text_200)
    TextView text_200;


    @BindView(R.id.text_500)
    TextView text_500;

  /*  @BindView(R.id.appCompatTextView)
    TextView appCompatTextView;*/


    @BindView(R.id.text_1000)
    TextView text_1000;

    @BindView(R.id.other_money)
    EditText other_money;

    @BindView(R.id.submit)
    Button submit;

    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteAdapter adapter;
    TextView responseView;
    PlacesClient placesClient;


    int paymentPrice=0;
    String petrolBunkName="";
    String petrolID="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Retrofit

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.appCompatTextView);
        nav_user.setText("+91"+user.getGuest().getUsername());


        mApiService = new ApiUtils().getApiInterfacesForPetrolBunk();
        setupNavigation();
        getPetrolList();
        moneySelection();


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), CommonClass.GCP_KEY);
        }

        placesClient = Places.createClient(this);
        initAutoCompleteTextView();
    }

    // Setting Up One Time Navigation
    private void setupNavigation() {

        toolbar = findViewById(R.id.toolbar);
        ImageView menu = findViewById(R.id.menu);
        TextView title = findViewById(R.id.textHeader);
        title.setText(getResources().getString(R.string.go_fuels));
        drawerLayout = findViewById(R.id.drawer_layout);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {


                callDilouge();
               /* if(paymentPrice == 0)
                {
                  Toast.makeText(MainActivity.this,"Please select payment amount",Toast.LENGTH_LONG).show();
                }
                else
                {
                    showAlertBox("Hi you want to pay "+paymentPrice+" Rs. " +"to "+petrolBunkName +" .");
                }
*/
            }
        });

    }

    void callDilouge()
    {
        if(FingerprintDialog.isAvailable(this)) {
            FingerprintDialog.initialize(this)
                    .title(R.string.fingerprint_title)
                    .message(R.string.fingerprint_message)
                    .callback(this, "password")
                    .show();
        }
    }

    void showAlertBox(String message)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        showSuccessScreen();
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




    void getPetrolList() {
        Call<GetList> getList = mApiService.getPetrolList();
        getList.enqueue(new Callback<GetList>() {
            @Override
            public void onResponse(Call<GetList> call, Response<GetList> response)
            {
                try {
                    Log.e("RESPONSE", "--" + response.body().getResults().get(0).getName());
                    petrolBunkName =response.body().getResults().get(0).getName();
                    petrolID =response.body().getResults().get(0).getId();
                    setTheView(response.body().getResults().get(0).getIcon(), response.body().getResults().get(0).getName(), response.body().getResults().get(0).getVicinity());

                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onFailure(Call<GetList> call, Throwable t) {
                Log.e("RESPONSE", "--fail");
            }
        });
    }


    void setTheView(String icon, String name, String address) {
        Picasso.with(this).load(icon)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(image_icon);
        p_name.setText(name);
        p_adress.setText(address);


    }


    // for the firebase
    void fireBase() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        Log.e(TAG, "token " + token);

                        // Log and toast
                        //String msg = getString(R.string.msg_token_fmt, token);
                        //Log.d(TAG, msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);

        drawerLayout.closeDrawers();

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.first:
                // navController.navigate(R.id.firstFragment);

                Intent about = new Intent(this, AboutUsScreen.class);
                startActivity(about);
                break;

            case R.id.second:
                // navController.navigate(R.id.secondFragment);
                Intent su = new Intent(this, SuccessScreen.class);
                startActivity(su);
                break;

            case R.id.third:

                Intent support = new Intent(this, CustomSupportScreenActivity.class);
                startActivity(support);
                //navController.navigate(R.id.thirdFragment);
                break;

            case R.id.fourt:
                //navController.navigate(R.id.thirdFragment);

                Intent trans = new Intent(this, packag.nnk.com.userfuelapp.transaction.MainActivity.class);
                startActivity(trans);
                break;


        }
        return true;

    }


    void moneySelection() {
        text_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPrice =1000;
                text_1000.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_sel_round));

                text_200.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_500.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                setPadding();
            }
        });

        text_200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPrice=200;
                text_200.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_sel_round));
                text_1000.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_500.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                setPadding();
            }
        });

        text_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPrice=500;
                text_500.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_sel_round));
                text_200.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                text_1000.setBackground(getResources().getDrawable(
                        R.drawable.rect_select_round));
                setPadding();
            }
        });


    }
//

    void setPadding()

    {
        text_1000.setPadding(0,13,0,13);
        text_500.setPadding(0,13,0,13);
        text_200.setPadding(0,13,0,13);
    }

    private void initAutoCompleteTextView() {

        autoCompleteTextView = findViewById(R.id.auto);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(autocompleteClickListener);
        adapter = new AutoCompleteAdapter(this, placesClient);
        autoCompleteTextView.setAdapter(adapter);
    }

    private AdapterView.OnItemClickListener autocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            try {
                final AutocompletePrediction item = adapter.getItem(i);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }

//                To specify which data types to return, pass an array of Place.Fields in your FetchPlaceRequest
//                Use only those fields which are required.

                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);

                FetchPlaceRequest request = null;
                if (placeID != null) {
                    request = FetchPlaceRequest.builder(placeID, placeFields)
                            .build();
                }

                if (request != null) {
                    placesClient.fetchPlace(request).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onSuccess(FetchPlaceResponse task) {
                            responseView.setText(task.getPlace().getName() + "\n" + task.getPlace().getAddress());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            responseView.setText(e.getMessage());
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    public void onAuthenticationSucceeded() {

    }

    @Override
    public void onAuthenticationCancel() {

    }

    @Override
    public void onNewFingerprintEnrolled(FingerprintToken token) {
        PasswordDialog.initialize(this, token)
                .title(R.string.password_title)
                .message(R.string.password_message)
                .callback(this)
                .passwordType(PasswordDialog.PASSWORD_TYPE_TEXT)
                .show();
    }

    @Override
    public void onPasswordSucceeded() {

    }

    @Override
    public boolean onPasswordCheck(String password) {

        return password.equals("password");

    }

    @Override
    public void onPasswordCancel() {

    }

    @Override
    public void onClick(View view) {

    }
}
