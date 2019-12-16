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
import java.util.List;

import packag.nnk.com.userfuelapp.R;
import packag.nnk.com.userfuelapp.model.RangeTransaction;

/**
 * Created byKapil Katiyar on.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    private List<RangeTransaction> feedItemList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public MyRecyclerViewAdapter(Context context, List<RangeTransaction> feedItemList) {
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
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i)
    {
        final RangeTransaction feedItem = feedItemList.get(i);

        customViewHolder.title.setText(Html.fromHtml(feedItem.getPetrolBunkDetail().getPetrolBunkName()));
        customViewHolder.time.setText(Html.fromHtml(feedItem.getPaymentDate()));
        customViewHolder.address.setText(Html.fromHtml(feedItem.getPetrolBunkDetail().getPetrolBunkAddress()));
        customViewHolder.rupees.setText(Html.fromHtml("Paid Amount : "+feedItem.getAmount()));

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
}