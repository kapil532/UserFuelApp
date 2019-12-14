package packag.nnk.com.userfuelapp.transaction;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.BaseActivity;
import packag.nnk.com.userfuelapp.base.CommonClass;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionActivity extends BaseActivity {
    private static final String TAG = "RecyclerViewExample";

    private List<Transaction> feedsList;
    private MyRecyclerViewAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ApiInterface getApiInterfaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_list);
        ButterKnife.bind(this);

       // getApiInterfaces = new ApiUtils().getApiInterfaces();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedsList = readJsonData().getTransaction();
       // fetchTransactionDetailsOverNetwork();
        setAdapter();
        setupNavigation();
    }


    void fetchTransactionDetailsOverNetwork() {
        showProgressDialog();
        JsonObject json = new JsonObject();
        try {
            json.addProperty("user", "" + user.getGuest().getGuestId());
        } catch (Exception e) {


        }


        Call<TransactionPojo> call = getApiInterfaces.getTransactionList(json);
        call.enqueue(new Callback<TransactionPojo>() {
            @Override
            public void onResponse(Call<TransactionPojo> call, Response<TransactionPojo> response) {
                feedsList=   response.body().getTransaction();
                if(feedsList != null)
                {
                    setAdapter();
                }




            }

            @Override
            public void onFailure(Call<TransactionPojo> call, Throwable t) {
                hideProgressDialog();
            }
        });

    }


    private void setupNavigation() {

        toolbar.setTitle("Transaction");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                finish();
            }
        });


    }

    private TransactionPojo readJsonData() {
        String jsonString = CommonClass.getAssetJsonData(this, "transaction.json");
        Gson gson = new Gson();
        return gson.fromJson(jsonString, TransactionPojo.class);
    }

    void setAdapter() {
        adapter = new MyRecyclerViewAdapter(TransactionActivity.this, feedsList);
        mRecyclerView.setAdapter(adapter);

    }

}