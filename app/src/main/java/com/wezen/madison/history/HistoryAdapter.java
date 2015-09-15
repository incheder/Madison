package com.wezen.madison.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.wezen.madison.R;
import com.wezen.madison.model.HistoryService;
import com.wezen.madison.model.Review;

import java.util.List;

/**
 * Created by eder on 9/13/15.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private List<HistoryService> list;

    public HistoryAdapter(List<HistoryService> list){
        this.list = list;
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
    public void onBindViewHolder(HistoryHolder holder, int position) {
        HistoryService item = list.get(position);
        holder.name.setText(item.getName());
        holder.date.setText(item.getDate());
        holder.description.setText(item.getDescription());
        holder.review.setMax(item.getReview());
      //  holder.image.setImageBitmap(item.getImage());
        holder.status.setText(item.getStatus().toString());
        holder.rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
