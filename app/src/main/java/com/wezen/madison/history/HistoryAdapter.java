package com.wezen.madison.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wezen.madison.R;
import com.wezen.madison.model.HomeServiceRequest;
import com.wezen.madison.model.HomeServiceRequestStatus;

import java.util.List;

/**
 * Created by eder on 9/13/15.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private List<HomeServiceRequest> list;
    private Context context;

    public HistoryAdapter(List<HomeServiceRequest> list, Context context){
        this.list = list;
        this.context = context;
    }




    @Override
    public HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        HistoryHolder vh;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history,parent,false);
        vh = new HistoryHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(HistoryHolder holder, final int position) {
        HomeServiceRequest item = list.get(position);
        holder.name.setText(item.getName());
        holder.date.setText(item.getDate());
        holder.description.setText(item.getDescription());
        holder.review.setMax(item.getReview());
        Picasso.with(context).load(item.getImage()).into(holder.image);
        if(item.getStatus()!=null){
            holder.status.setText(item.getStatus().toString());
        }
        if(item.getWasRated()){
            holder.rating.setVisibility(View.GONE);
            holder.review.setVisibility(View.VISIBLE);
        }
        if( item.getStatus() != HomeServiceRequestStatus.COMPLETO){
            holder.rating.setVisibility(View.GONE);
        } else if(!item.getWasRated()){
            holder.rating.setVisibility(View.VISIBLE);
        }

        holder.rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HistoryActivity) context).showBottomSheet(position);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView date;
        public TextView description;
        public RatingBar review;
        public ImageView image;
        public Button rating;
        public TextView status;

        public HistoryHolder(View itemView) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.history_image);
            name = (TextView)itemView.findViewById(R.id.history_name);
            description = (TextView)itemView.findViewById(R.id.history_item_description);
            status = (TextView)itemView.findViewById(R.id.history_item_status);
            date = (TextView)itemView.findViewById(R.id.history_item_date);
            review = (RatingBar)itemView.findViewById(R.id.historyItemRating);
            rating = (Button)itemView.findViewById(R.id.buttonHistoryItem);
        }
    }


}
