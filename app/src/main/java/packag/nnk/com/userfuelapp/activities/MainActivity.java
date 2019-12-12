package packag.nnk.com.userfuelapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.about_us.AboutUsScreen;
import packag.nnk.com.userfuelapp.about_us.CustomSupportScreenActivity;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.petrol_bunk_details.GetList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    private String TAG = MainActivity.class.getSimpleName();
    private ApiInterface mApiService;

    public Toolbar toolbar;

    public DrawerLayout drawerLayout;

    public NavController navController;

    public NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrofit
        //mApiService = ApiUtils.getApiInterfacesForPetrolBunk();
        setupNavigation();
        //  getPetrolList();
    }

    // Setting Up One Time Navigation
    private void setupNavigation() {

        toolbar = findViewById(R.id.toolbar);
        ImageView menu = findViewById(R.id.menu);
        TextView title = findViewById(R.id.textHeader);
        title.setText("Go Fuels");

        //setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

    }


    void getPetrolList() {
        Call<GetList> getList = mApiService.getPetrolList();
        getList.enqueue(new Callback<GetList>() {
            @Override
            public void onResponse(Call<GetList> call, Response<GetList> response) {
                Log.e("RESPONSE", "--" + response.body().getResults().get(0).getName());
            }

            @Override
            public void onFailure(Call<GetList> call, Throwable t) {
                Log.e("RESPONSE", "--fail");
            }
        });
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


//

}
