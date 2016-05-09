package com.wezen.madison.history;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.wezen.madison.R;
import com.wezen.madison.model.HomeServiceRequest;
import com.wezen.madison.model.HomeServiceRequestStatus;
import com.wezen.madison.utils.AutofitRecyclerView;
import com.wezen.madison.utils.DialogActivity;
import com.wezen.madison.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends DialogActivity {

    private  List<HomeServiceRequest> requestList;
    private HistoryAdapter adapter;
    private ReviewDialogFragment dialog;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar)findViewById(R.id.historyToolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        AutofitRecyclerView rvHistory = (AutofitRecyclerView) findViewById(R.id.rvHistory);
        progressBar = (ProgressBar)findViewById(R.id.progressBarHistory);
        rvHistory.setHasFixedSize(true);
        requestList = new ArrayList<>();
        adapter = new HistoryAdapter(requestList,this);
        rvHistory.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getList(null);
    }

    private void getList(HomeServiceRequestStatus status) {
        HomeServiceRequest hs;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("HomeServiceRequest");
        if(status != null){
            query.whereEqualTo("status",status.getValue());
        }
        query.include("homeService");
        query.include("attendedBy");
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                progressBar.setVisibility(View.GONE);
                if (e == null) {
                    requestList.clear();
                    for (ParseObject po : list) {
                        HomeServiceRequest request = new HomeServiceRequest();
                        request.setLocation(new LatLng(po.getParseGeoPoint("userLocation").getLatitude(), po.getParseGeoPoint("userLocation").getLongitude()));
                        request.setName(po.getParseObject("homeService").getString("name"));
                        request.setDescription(po.getString("problemDescription"));
                        int status = po.getInt("status");
                        request.setStatus(HomeServiceRequestStatus.valueOf(status));
                        //request.setHomeServiceRequestID((po.getParseObject("homeService").getObjectId()));
                        request.setHomeServiceRequestID(po.getObjectId());
                        request.setDate(Utils.setDateFormat(po.getCreatedAt()));
                        if(po.getParseObject("homeService").getParseFile("image")!= null){
                            request.setImage(po.getParseObject("homeService").getParseFile("image").getUrl());
                        }
                        request.setWasRated(po.getBoolean("wasRated"));
                        request.setReview(po.getInt("rating"));
                        request.setId(po.getObjectId());
                        if(po.getParseUser("attendedBy")!= null){
                            request.setAttendedBy(po.getParseUser("attendedBy").getUsername());
                            if(po.getParseUser("attendedBy").getParseFile("userImage")!= null){
                                request.setAttendedByAvatar(po.getParseUser("attendedBy").getParseFile("userImage").getUrl());
                            }
                        }
                        request.setDateForService(po.getDate("dateForService"));
                        requestList.add(request);
                    }

                    adapter.notifyDataSetChanged();
                } else { // ups
                    Log.e("ERROR",e.getMessage());
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.all:
                getList(null);
                break;
            case R.id.send:
                getList(HomeServiceRequestStatus.ENVIADO);
                break;
            case R.id.asigned:
                getList(HomeServiceRequestStatus.CONFIRMADO);
                break;
            case R.id.done:
                getList(HomeServiceRequestStatus.COMPLETO);
                break;
            case R.id.canceled:
                getList(HomeServiceRequestStatus.CANCELADO);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*@Override
    public void onReviewDialogButtonClicked(int numStars, String comment, final int position) {
        dialog.dismiss();
        ParseObject review = new ParseObject("Review");
        review.put("numStars", numStars);
        review.put("comment",comment);
        review.put("fromUser", ParseUser.getCurrentUser());
        String id = requestList.get(position).getHomeServiceRequestID();
        ParseObject homeServiceID = ParseObject.createWithoutData("HomeServiceRequest",id);
        review.put("homeServiceRequest", homeServiceID);

        review.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                requestList.get(position).setWasRated(true);
                adapter.notifyDataSetChanged();

                if (e == null) {
                    Toast.makeText(HistoryActivity.this, getResources().getString(R.string.review_saved), Toast.LENGTH_SHORT).show();
                    //TODO actualizar el campo wasRated en la clase de los request, quitamos el boton y mostramos el rating bar con la calificaion recien mandada


                } else { //ups
                    Toast.makeText(HistoryActivity.this, getResources().getString(R.string.review_not_saved), Toast.LENGTH_SHORT).show();
                    requestList.get(position).setWasRated(false);
                    adapter.notifyDataSetChanged();

                }
            }
        });


    }*/
}
