package com.wezen.madison.history;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
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
import com.wezen.madison.request.RequestActivity;
import com.wezen.madison.utils.Utils;

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
        final HomeServiceRequest item = list.get(position);
        holder.name.setText(item.getName());
        holder.date.setText(item.getDate());
        holder.description.setText(item.getDescription());
        if(item.getReview()!= null){
            holder.review.setRating(item.getReview());
        }

        Picasso.with(context)
                .load(item.getImage())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.default_image_placeholder)
                .into(holder.image);

        if(item.getStatus()!=null){
            holder.status.setText(item.getStatus().toString());
            setColorByStatus(holder.status, item.getStatus(), position);
            holder.status.setBackgroundColor(Utils.getColorByStatus(context,item.getStatus()));
        }
        /*if(item.getWasRated()){
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
                ((HistoryActivity) context).showRatingDialog(position);
            }
        });*/

        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent request = new Intent(context, RequestActivity.class);
                //request.putExtra(RequestActivity.REQUEST_ID, item.getId());
                request.putExtra(RequestActivity.REQUEST_IMAGE_URL,item.getImage());
                request.putExtra(RequestActivity.REQUEST_STATUS,item.getStatus().getValue());
                request.putExtra(RequestActivity.REQUEST_HOME_SERVICE_NAME, item.getName());
                request.putExtra(RequestActivity.REQUEST_PROBLEM_DESCRIPTION,item.getDescription());
                request.putExtra(RequestActivity.REQUEST_ATTENDED_BY,item.getAttendedBy() == null ? "": item.getAttendedBy());
                request.putExtra(RequestActivity.REQUEST_ATTENDED_BY_AVATAR,item.getAttendedByAvatar());
                if(item.getDateForService()!= null){
                    request.putExtra(RequestActivity.REQUEST_DATE_FOR_SERVICE,item.getDateForService().toString());
                }
                if(item.getWasRated()){
                    request.putExtra(RequestActivity.REQUEST_NUM_STARS,item.getReview());
                }
                if(!item.getWasRated() && item.getStatus() == HomeServiceRequestStatus.COMPLETO){
                    request.putExtra(RequestActivity.REQUEST_SHOW_RATING_BUTTON,true);
                }
                request.putExtra(RequestActivity.REQUEST_ID,item.getHomeServiceRequestID());


                context.startActivity(request);
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
        //public Button rating;
        public TextView status;
        public CardView content;

        public HistoryHolder(View itemView) {
            super(itemView);

            image = (ImageView)itemView.findViewById(R.id.history_image);
            name = (TextView)itemView.findViewById(R.id.history_name);
            description = (TextView)itemView.findViewById(R.id.history_item_description);
            status = (TextView)itemView.findViewById(R.id.history_item_status);
            date = (TextView)itemView.findViewById(R.id.history_item_date);
            review = (RatingBar)itemView.findViewById(R.id.historyItemRating);
            //rating = (Button)itemView.findViewById(R.id.buttonHistoryItem);
            content = (CardView)itemView.findViewById(R.id.historyItemContent);
        }
    }

    private void setColorByStatus(TextView textView, HomeServiceRequestStatus status, int position){
        int color = ContextCompat.getColor(context, R.color.transparent);
        switch (status) {
            case ENVIADO:
                color = ContextCompat.getColor(context, R.color.palette_green);
                break;
            case CONFIRMADO:
                color = ContextCompat.getColor(context, R.color.palette_yellow_dark);
                break;
            case CANCELADO:
                color = ContextCompat.getColor(context, R.color.palette_red);
                break;
            case COMPLETO:
                color = ContextCompat.getColor(context, R.color.palette_blue);
                break;
            case RECHAZADO:
                color = ContextCompat.getColor(context, R.color.palette_red);
                break;
        }
        list.get(position).setColorForStatus(color);
        textView.setBackgroundColor(color);

    }



}
