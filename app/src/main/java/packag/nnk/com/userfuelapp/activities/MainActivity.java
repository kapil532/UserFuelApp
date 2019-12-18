package packag.nnk.com.userfuelapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;

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
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.base.ErrorUtils;
import packag.nnk.com.userfuelapp.fragment_view.PinVerification;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.interfaces.GetMessage;
import packag.nnk.com.userfuelapp.model.Balance;
import packag.nnk.com.userfuelapp.model.Payment;
import packag.nnk.com.userfuelapp.model.RangeTransaction;
import packag.nnk.com.userfuelapp.model.UserDetails;
import packag.nnk.com.userfuelapp.petrol_bunk_details.GetList;
import packag.nnk.com.userfuelapp.services.AutoCompleteAdapter;
import packag.nnk.com.userfuelapp.transaction.TransactionActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener,
        FingerprintDialogSecureCallback,
        PasswordCallback {
    private String TAG = MainActivity.class.getSimpleName();
    private ApiInterface mApiService;
    private ApiInterface mApiService_;

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


    Double paymentPrice = 0.0;
    String petrolBunkName = "";
    String petrolID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //Retrofit

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.appCompatTextView);
        nav_user.setText(user.getUsername());


        mApiService = new ApiUtils().getApiInterfacesForPetrolBunk();
        mApiService_ = new ApiUtils().getApiInterfaces();


        //getBalance();
        setupNavigation();
        getPetrolList();
        moneySelection();


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), CommonClass.GCP_KEY);
        }

        placesClient = Places.createClient(this);
        initAutoCompleteTextView();

        setThePin();


        other_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                makeUnselect();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    Log.e("EDIT", "editable" + Double.parseDouble(other_money.getText().toString()));
                    paymentPrice = Double.parseDouble(other_money.getText().toString());


                } catch (Exception e) {
                    paymentPrice = 0.0;
                }

            }
        });

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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // callDilouge();
                if (paymentPrice == 0) {
                    Toast.makeText(MainActivity.this, "Please select payment amount", Toast.LENGTH_LONG).show();
                } else {
//                    showAlertBox("Hi you want to pay "+paymentPrice+" Rs. " +"to "+petrolBunkName +" .");
                    FragmentManager manager = getSupportFragmentManager();
                    PinVerification alertDialogFragment = new PinVerification();

                    alertDialogFragment.show(manager, "fragment_edit_name");
                   PinVerification.getMessage =new GetMessage() {
                       @Override
                       public void getSuccessMessage(String s) {
                           doPayment(""+paymentPrice,"19f55b6f-dbf9-4c4e-91cb-db5e828d3669");
                       }
                   };

                }

            }
        });

    }

    void callDilouge() {
        if (FingerprintDialog.isAvailable(this)) {
            FingerprintDialog.initialize(this)
                    .title(R.string.fingerprint_title)
                    .message(R.string.fingerprint_message)
                    .callback(this, "password")
                    .show();
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
                        doPayment("200", petrolID);

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
            public void onResponse(Call<GetList> call, Response<GetList> response) {
                try {
                    //Log.e("RESPONSE", "--" + response.body().getResults().get(0).getName());
                    petrolBunkName = response.body().getResults().get(0).getName();
                    petrolID = response.body().getResults().get(0).getId();
                    setTheView(response.body().getResults().get(0).getIcon(), response.body().getResults().get(0).getName(), response.body().getResults().get(0).getVicinity());

                } catch (Exception e) {

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
                        //Toast.makeText(TransactionActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                Intent su = new Intent(this, DummyActivity.class);
                startActivity(su);
                break;

            case R.id.third:

                Intent support = new Intent(this, CustomSupportScreenActivity.class);
                startActivity(support);
                //navController.navigate(R.id.thirdFragment);
                break;

            case R.id.fourt:
                //navController.navigate(R.id.thirdFragment);

                Intent trans = new Intent(this, TransactionActivity.class);
                startActivity(trans);
                break;
            case R.id.setting:
                //navController.navigate(R.id.thirdFragment);

                Intent set = new Intent(this, SettingActivity.class);
                startActivity(set);
                break;

        }
        return true;

    }


    void moneySelection() {
        text_1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentPrice = 1000.0;
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
                paymentPrice = 200.0;
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
                paymentPrice = 500.0;
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

    void makeUnselect() {
        text_500.setBackground(getResources().getDrawable(
                R.drawable.rect_select_round));
        text_200.setBackground(getResources().getDrawable(
                R.drawable.rect_select_round));
        text_1000.setBackground(getResources().getDrawable(
                R.drawable.rect_select_round));
        setPadding();
    }
//

    void setPadding() {
        text_1000.setPadding(0, 13, 0, 13);
        text_500.setPadding(0, 13, 0, 13);
        text_200.setPadding(0, 13, 0, 13);
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


    void setThePin() {

//        {"userId":"3d5ba570-c512-42ad-98a0-901515181f51", "pin":"1234"}
        JsonObject json = new JsonObject();
        try {
            json.addProperty("userId", "" + user.getUserId());
            json.addProperty("pin", "1234");
        } catch (Exception e) {

        }

        Call<String> payment = mApiService_.updatePin(json);
        payment.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(), "--" + response.body(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }


    void doPayment(String price, String petrolID) {
        JsonObject json = new JsonObject();
        try {
            json.addProperty("driverId", "" + user.getUserId());
            json.addProperty("amount", "" + price);
            json.addProperty("petrolBunkId", "" + petrolID);
        } catch (Exception e) {

        }

        Call<Payment> payment = mApiService_.doPayment(json, user.getUserId());
        payment.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {


               try {
                   Log.e("MAINACTIVITY", "VALUES" + response.body());
                   Payment payment=  response.body();
                   if(payment.getStatus().equalsIgnoreCase("success"))
                   {
//                       showSuccessScreen();
                       Intent success = new Intent(getApplicationContext(), SuccessScreen.class);
                       success.putExtra("petr_name",""+petrolBunkName);
                       success.putExtra("petr_price",""+paymentPrice);
                       startActivity(success);

                   }
                   else
                   {
                       Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
                   }
               }
               catch (Exception e)
               {
                   Toast.makeText(getApplicationContext(),  ErrorUtils.getStatus(response).getMessage(), Toast.LENGTH_LONG).show();
               }


            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
            }
        });


    }


}

