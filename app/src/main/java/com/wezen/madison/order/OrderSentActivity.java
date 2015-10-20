package com.wezen.madison.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.wezen.madison.R;
import com.wezen.madison.categories.CategoriesActivity;
import com.wezen.madison.map.MapActivity;

import java.util.Timer;
import java.util.TimerTask;

public class OrderSentActivity extends AppCompatActivity {
    public  static final String LATITUDE = "latitud";
    public  static final String LONGITUDE = "longitud";
    public  static final String ID = "id";
    public  static final String PROBLEM = "problem";

    private ProgressBar progressBar;
    private LinearLayout orderSent;
    private LatLng myLatLng;
    private String id;
    private String problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sent);
        progressBar = (ProgressBar)findViewById(R.id.progressBarOrderSent);
        orderSent = (LinearLayout)findViewById(R.id.orderSentLayout);
        Button btnBack = (Button)findViewById(R.id.orderGoBack);
        btnBack.setOnClickListener(goBackClickListener);
        if(getIntent().getExtras()!= null){
            myLatLng = new LatLng(
                    getIntent().getDoubleExtra(LATITUDE,0),
                    getIntent().getDoubleExtra(LONGITUDE,0));
            id =  getIntent().getStringExtra(ID);
            problem =  getIntent().getStringExtra(PROBLEM);
        }
        Timer timer = new Timer();
       // timer.schedule(task, 3000);
        sendRequest();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_sent, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        orderSent.setVisibility(View.VISIBLE);
                    }
                });

            }
        };

    @Override
    public void onBackPressed() {
        goHome();
    }

    private void goHome(){
        Intent home = new Intent(this, CategoriesActivity.class);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(home);
    }

    private View.OnClickListener goBackClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            goHome();
        }
    };

    private void sendRequest(){

        ParseGeoPoint geoPoint = new ParseGeoPoint(myLatLng.latitude,myLatLng.longitude);
        ParseObject homeServices = ParseObject.createWithoutData("HomeServices",id);
        final ParseObject po = new ParseObject("HomeServiceRequest");
        po.put("userLocation",geoPoint);
        po.put("homeService",homeServices);
        po.put("user",ParseUser.getCurrentUser());
        po.put("problemDescription",problem);
        po.put("status",0);
        po.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                progressBar.setVisibility(View.GONE);
                orderSent.setVisibility(View.VISIBLE);
                if(e==null){

                } else {
                    //TODO guardar en el historial de NO enviados
                    //po.pinInBackground("NoEnviados");
                    TextView textViewOrderSent = (TextView)orderSent.findViewById(R.id.textview_order_sent);
                    if(textViewOrderSent!= null){
                        textViewOrderSent.setText(getResources().getString(R.string.order_not_sent));
                    }
                }

            }
        });

    }
}
