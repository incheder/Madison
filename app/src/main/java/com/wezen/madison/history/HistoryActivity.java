package com.wezen.madison.history;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wezen.madison.R;
import com.wezen.madison.model.HomeService;
import com.wezen.madison.model.HomeServiceRequest;
import com.wezen.madison.model.HomeServiceRequestStatus;
import com.wezen.madison.utils.AutofitRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private  List<HomeServiceRequest> requestList;
    private HistoryAdapter adapter;

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
        rvHistory.setHasFixedSize(true);
        requestList = new ArrayList<>();
        adapter = new HistoryAdapter(requestList);
        rvHistory.setAdapter(adapter);
        getList(null);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getList(HomeServiceRequestStatus status) {
        HomeServiceRequest hs;

        ParseQuery<ParseObject> query = ParseQuery.getQuery("HomeServiceRequest");
        if(status != null){
            query.whereEqualTo("status",status.getValue());
        }
        query.include("homeService");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if(e == null){
                    requestList.clear();
                    for (ParseObject po : list) {
                        HomeServiceRequest request = new HomeServiceRequest();
                        request.setLocation(new LatLng(po.getParseGeoPoint("userLocation").getLatitude(), po.getParseGeoPoint("userLocation").getLongitude()));
                        request.setName(po.getParseObject("homeService").getString("name"));
                        request.setDescription(po.getString("problemDescription"));
                        int status = po.getInt("status");
                        request.setStatus(HomeServiceRequestStatus.valueOf(status));
                        requestList.add(request);
                    }

                    adapter.notifyDataSetChanged();
                } else { // ups

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
                getList(HomeServiceRequestStatus.ASIGNADO);
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
}
