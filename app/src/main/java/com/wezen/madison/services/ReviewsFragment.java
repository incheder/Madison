package com.wezen.madison.services;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wezen.madison.R;
import com.wezen.madison.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewsFragment extends Fragment {

    private static final String ARG_HOME_SERVICE_ID = "home_service_id";
    private static final String ARG_PARAM2 = "param2";

    private String mHomeServiceId;
    private String mParam2;
    private List<Review> reviews;
    private ReviewsAdapter adapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewsFragment newInstance(String param1, String param2) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HOME_SERVICE_ID, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHomeServiceId = getArguments().getString(ARG_HOME_SERVICE_ID);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rvReviews);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        reviews = new ArrayList<>();
        adapter = new ReviewsAdapter(reviews, getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reviews.clear();
        getReviews();
    }

    private void getReviews(){


        ParseObject holder = ParseObject.createWithoutData("HomeServices",mHomeServiceId);
        ParseQuery<ParseObject> innerQuery = ParseQuery.getQuery("HomeServiceRequest");
        innerQuery.whereEqualTo("homeService",holder);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Review");
        query.orderByDescending("createdAt");
        query.include("fromUser");
        query.whereMatchesQuery("homeServiceRequest",innerQuery);
        //query.whereEqualTo("homeService", holder);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    for (ParseObject po: list) {
                        Review review = new Review();
                        review.setId(po.getObjectId());
                        review.setComment(po.getString("comment"));
                        review.setStars(po.getInt("numStars"));
                        review.setDate(po.getCreatedAt().toString());
                        ParseObject user = po.getParseObject("fromUser");
                        if(user!= null){
                            if(user.getParseFile("userImage")!= null){
                                review.setUserAvatar(user.getParseFile("userImage").getUrl());
                            }
                            review.setUserName(user.getString("username"));
                        }
                        reviews.add(review);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("TAG",e.getMessage());
                }

            }
        });
    }




}
