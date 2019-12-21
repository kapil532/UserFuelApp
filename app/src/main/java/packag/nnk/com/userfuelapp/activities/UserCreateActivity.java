package packag.nnk.com.userfuelapp.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.isapanah.awesomespinner.AwesomeSpinner;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.base.ImagePickerActivity;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.ApiError;
import packag.nnk.com.userfuelapp.model.User;
import packag.nnk.com.userfuelapp.model.UserDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCreateActivity extends BaseActivity {


    @BindView(R.id.editText)
    EditText name;

    @BindView(R.id.email_optional)
    EditText email_optional;


    @BindView(R.id.editText3)
    EditText pin;

    @BindView(R.id.editText2)
    EditText driverId;

    @BindView(R.id.skip)
    TextView skip;

    @BindView(R.id.profileIcon)
    CircleImageView profileIcon;



    @BindView(R.id.my_spinner)
    AwesomeSpinner spinner;

    @BindView(R.id.submitDetails)
    Button submitDetails;
    private ApiInterface mApiService_;

    String number;
    public static final int REQUEST_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit_screen);
        ButterKnife.bind(this);
        mApiService_ = new ApiUtils().getApiInterfaces();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            number = extras.getString("number");
        }

        loadProfileDefault();
        List<String> categories = new ArrayList<String>();
        categories.add("Ola");
        categories.add("Uber");
        categories.add("Other");

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(categoriesAdapter);
        spinner.setOnSpinnerItemClickListener(new AwesomeSpinner.onSpinnerItemClickListener<String>() {
            @Override
            public void onItemSelected(int position, String itemAtPosition) {
                //TODO YOUR ACTIONS
            }
        });

        submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateFields();

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAct = new Intent(getApplicationContext(), MainActivity.class);
                myAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myAct);
                finish();
            }
        });

        setFont(submitDetails);
//        setFont(spinner);
        setFont(name);
        setFont(email_optional);
        setFont(driverId);
        setTheValues();
    }


    void setTheValues()
    {
//        user
        name.setText(user.getUsername());
        email_optional.setText(""+user.getEmail());
    }

    void createUser(String pin, String driverAgra, String email) {
        showProgressDialog();
        JsonObject json = new JsonObject();
        try {
            json.addProperty("email", "" + email);
            json.addProperty("password", "" );
            json.addProperty("role", "driver");
            json.addProperty("driverAggregator", "" + driverAgra);
            json.addProperty("mobile", "" + number);
            json.addProperty("cabNumber", "" + number);
        } catch (Exception e) {

        }

        Call<UserDetails> payment = mApiService_.createUser(json);
        payment.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                hideProgressDialog();


                try {


                    AppSharedPreUtils
                            .getInstance(getApplicationContext()).saveUserDetails(response.body().getUser());
                    User user = response.body().getUser();
                    if (user != null) {
                        Intent myAct = new Intent(getApplicationContext(), SetPinActivity.class);
                        myAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(myAct);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please try again!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    BufferedReader reader = null;
                    StringBuilder sb = new StringBuilder();
                    try {
                        reader = new BufferedReader(new InputStreamReader(response.errorBody().byteStream()));
                        String line;
                        try {
                            while ((line = reader.readLine()) != null) {
                                sb.append(line);
                            }
                        } catch (IOException ea) {
                            e.printStackTrace();
                        }
                    } catch (Exception eaa) {
                        e.printStackTrace();
                    }
                    String finallyError = sb.toString();
                    showMessage(finallyError);
                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    void showMessage(String message) {
        Gson gson = new GsonBuilder().create();
        ApiError mverror = new ApiError();
        mverror = gson.fromJson(message, ApiError.class);
        Toast.makeText(getApplicationContext(), mverror.getError().toString(), Toast.LENGTH_LONG).show();
    }

    void validateFields() {
        if (name.getText().toString().length() == 0) {
            makeToast("Please enter name");
            return;
        }

      /*  if (pin.getText().toString().length() == 0) {
            makeToast("Please set pin");
            return;
        }*/
        createUser(pin.getText().toString(), (String) spinner.getSelectedItem(), email_optional.getText().toString());
    }


    void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }





    private void loadProfile(String url) {
        Log.d("USER", "Image cache path: " + url);

        Picasso.with(this).load(url)
                .into(profileIcon);
        profileIcon.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    private void loadProfileDefault() {
        Picasso.with(this).load(R.drawable.icon_pro)
                .into(profileIcon);
        profileIcon.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }






    // my button click function
    @OnClick({R.id.icon_plus, R.id.profileIcon})
    void onProfileImageClick() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        } else {
                            // TODO - handle permission denied case
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getApplicationContext(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(resultCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




}




