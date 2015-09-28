package com.wezen.madison.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wezen.madison.R;
import com.wezen.madison.model.HomeService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eder on 4/8/15.
 */
public class HomeServicesAdapter extends RecyclerView.Adapter<HomeServicesAdapter.ViewHolder> {
    private List<HomeService> mDataset;
    private Context context;

    public HomeServicesAdapter(List<HomeService> mDataset, Context context){
        this.mDataset = mDataset;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_drink,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final HomeService homeService = mDataset.get(position);
        viewHolder.tvDescription.setText(homeService.getDescription());
        viewHolder.tvServiceName.setText(homeService.getName());
        viewHolder.tvComments.setText(String.valueOf(homeService.getComments()));
        viewHolder.stars.setRating(homeService.getStars());
        Picasso.with(context).load(homeService.getUrlImage()).into(viewHolder.ivHomeService);


        viewHolder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceDetail = new Intent(context, ServiceDetailActivity.class);
                context.startActivity(serviceDetail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(int position, HomeService item){
        mDataset.add(position,item);
        notifyItemInserted(position);
    }

    public void remove(HomeService item){
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvServiceName;
        public View content;
        public ImageView ivHomeService;
        public TextView tvDescription;
        public TextView tvComments;
        public RatingBar stars;

        public ViewHolder(View v) {
            super(v);
            tvServiceName = (TextView)v.findViewById(R.id.tvItemServiceName);
            content = v.findViewById(R.id.itemGridContent);
            ivHomeService = (ImageView)v.findViewById(R.id.ivBeverage);
            tvDescription = (TextView)v.findViewById(R.id.tvItemServiceDescription);
            tvComments = (TextView)v.findViewById(R.id.numberOfComments);
            stars = (RatingBar)v.findViewById(R.id.ratingBar);
            
        }
    }


}