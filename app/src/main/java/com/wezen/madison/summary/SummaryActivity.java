package com.wezen.madison.summary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wezen.madison.R;
import com.wezen.madison.order.OrderDialogFragment;
import com.wezen.madison.map.MapActivity;
import com.wezen.madison.order.OrderSentActivity;

public class SummaryActivity extends AppCompatActivity implements  OrderDialogFragment.OnClickOrderDialog {

    private MapView mapView;
    private LatLng myLatLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.summaryToolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mapView = (MapView)findViewById(R.id.mapview);
        TextView userAddress = (TextView) findViewById(R.id.summaryUserAddress);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSummary);

        if(getIntent().getExtras()!= null){
            myLatLng = new LatLng(
                    getIntent().getDoubleExtra(MapActivity.LATITUDE,0),
                    getIntent().getDoubleExtra(MapActivity.LONGITUDE,0));
            userAddress.setText(getIntent().getStringExtra(MapActivity.ADDRESS));
        }



        fab.setOnClickListener(fabListener);
        mapView.onCreate(savedInstanceState);
        GoogleMap map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);
        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this);

        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(myLatLng, 16);
        map.moveCamera(cameraUpdate);
        map.addMarker(new MarkerOptions().position(myLatLng));
        map.getUiSettings().setAllGesturesEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
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

    View.OnClickListener fabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            OrderDialogFragment dialog = new OrderDialogFragment();
            dialog.show( getSupportFragmentManager(),null);
        }
    };

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onButtonClicked() {

        Intent orderSent = new Intent(this, OrderSentActivity.class);
        //orderSent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(orderSent);
    }
}
