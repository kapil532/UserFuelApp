package packag.nnk.com.userfuelapp.transaction;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.model.History_;
import packag.nnk.com.userfuelapp.model.RangeTransaction;

/**
 * Created byKapil Katiyar on.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<History_> feedItemList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<History_> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final History_ feedItem = feedItemList.get(i);

        customViewHolder.title.setText(Html.fromHtml(feedItem.getPetrolBunkDetail().getPetrolBunkName()));
        customViewHolder.time.setText("Time : "+Html.fromHtml(dateFromat(feedItem.getPaymentDate())));
        customViewHolder.address.setText(Html.fromHtml(feedItem.getPetrolBunkDetail().getPetrolBunkAddress()));
        NumberFormat format = new DecimalFormat("0.#");
        customViewHolder.rupees.setText(Html.fromHtml("Paid Amount : " + mContext.getResources().getString(R.string.symbol_rs)
                + " " + feedItem.getAmount()));

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView thumbnail;
        protected TextView title;
        protected TextView address;
        protected TextView time;
        protected TextView rupees;

        public CustomViewHolder(View view) {
            super(view);
            this.thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            this.title = (TextView) view.findViewById(R.id.title);
            this.address = (TextView) view.findViewById(R.id.address);
            this.time = (TextView) view.findViewById(R.id.time);
            this.rupees = (TextView) view.findViewById(R.id.rupees);
        }
    }


    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    String dateFromat(String date) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm dd/MM/yyyy");

        Date d = null;
        try {
            d = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return output.format(d);
    }
}