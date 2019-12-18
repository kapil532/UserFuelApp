package packag.nnk.com.userfuelapp.fragment_view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.base.ApiUtils;
import packag.nnk.com.userfuelapp.base.AppSharedPreUtils;
import packag.nnk.com.userfuelapp.interfaces.ApiInterface;
import packag.nnk.com.userfuelapp.model.Balance;
import packag.nnk.com.userfuelapp.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment {
    private ApiInterface mApiService_;
    User user;
    TextView wallettext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        user = AppSharedPreUtils.getInstance(getContext()).getUserDetails();
        mApiService_ = new ApiUtils().getApiInterfaces();
        getBalance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wallet_fragment, container, false);
        wallettext = view.findViewById(R.id.wallettext);

        return view;
    }

    void getBalance() {
        Call<Balance> balance = mApiService_.getBalance(user.getUserId());
        balance.enqueue(new Callback<Balance>() {
            @Override
            public void onResponse(Call<Balance> call, Response<Balance> response) {
                Log.e("USER BALANCE", "bal--> " + response.body());
                try {
                    wallettext.setText("Wallet balance : " + getResources().getString(R.string.symbol_rs) + " " + response.body().getData());
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Balance> call, Throwable t) {

            }
        });

    }


}
