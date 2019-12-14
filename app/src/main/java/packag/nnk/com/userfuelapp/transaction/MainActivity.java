package packag.nnk.com.userfuelapp.transaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.CommonClass;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewExample";

    private List<Transaction> feedsList;
    private MyRecyclerViewAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_list);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedsList = readJsonData().getTransaction();
        setAdapter();
        setupNavigation();
    }








    void  fetchTransactionDetailsOverNetwork()
    {

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
        adapter = new MyRecyclerViewAdapter(MainActivity.this, feedsList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(FeedItem item) {
                Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_LONG).show();

            }
        });

    }

}