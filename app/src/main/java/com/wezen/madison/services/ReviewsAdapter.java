package com.wezen.madison.services;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wezen.madison.R;
import com.wezen.madison.model.Review;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by eder on 9/13/15.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private List<Review> reviews;
    private Context context;

    public ReviewsAdapter(List<Review> reviews, Context context){
        this.reviews = reviews;
        this.context = context;
    }



    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        ReviewHolder vh;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review,parent,false);
        vh = new ReviewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.name.setText(review.getUserName());
        holder.date.setText(review.getDate());
        holder.comment.setText(review.getComment());
        holder.date.setText(review.getDate());
        holder.stars.setRating(review.getStars());
        Picasso.with(context).load(review.getUserAvatar()).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView date;
        public TextView comment;
        public RatingBar stars;
        public CircleImageView avatar;

        public ReviewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.reviewUserName);
            date = (TextView)itemView.findViewById(R.id.reviewDate);
            comment = (TextView)itemView.findViewById(R.id.reviewComment);
            stars = (RatingBar)itemView.findViewById(R.id.reviewBar);
            avatar = (CircleImageView)itemView.findViewById(R.id.reviewAvatar);
        }
    }


}
