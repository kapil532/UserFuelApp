package packag.nnk.com.userfuelapp.transaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import packag.nnk.com.userfuelapp.model.RangeTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionActivity extends BaseActivity {
    private static final String TAG = "RecyclerViewExample";

    private List<Transaction> feedsList;
    private List<RangeTransaction> feedsList_tran;
    private MyRecyclerViewAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.no_text)
    TextView no_text;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ApiInterface getApiInterfaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_list);
        ButterKnife.bind(this);

        getApiInterfaces = new ApiUtils().getApiInterfaces();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
     //   feedsList_tran = (List<RangeTransaction>) readJsonData();
        getRangeTransaction();

        setupNavigation();
    }

    void getRangeTransaction()
    {
        showProgressDialog();
        Call<RangeTransaction> balance = getApiInterfaces.getRangeTransaction(user.getUserId());
        balance.enqueue(new Callback<RangeTransaction>() {
            @Override
            public void onResponse(Call<RangeTransaction> call, Response<RangeTransaction> response) {
                Log.e("USER BALANCE","bal--> "+response.body());

                RangeTransaction tran = response.body();
                if(tran != null)
                {
                    feedsList_tran = (List<RangeTransaction>) response.body();
                    setAdapter();
                    no_text.setVisibility(View.GONE);
                }
                else
                {
                   no_text.setVisibility(View.VISIBLE);
                }
                hideProgressDialog();


            }

            @Override
            public void onFailure(Call<RangeTransaction> call, Throwable t) {
                hideProgressDialog();
                no_text.setVisibility(View.VISIBLE);
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
        adapter = new MyRecyclerViewAdapter(TransactionActivity.this, feedsList_tran);
        mRecyclerView.setAdapter(adapter);

    }

}