package com.wezen.madison.summary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
import com.wezen.madison.utils.DialogActivity;

public class SummaryActivity extends DialogActivity implements  OrderDialogFragment.OnClickOrderDialog {

    private MapView mapView;
    private LatLng myLatLng;
    private String id;
    private EditText editTextPtoblem;
    private String address;
    private String name;


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
        editTextPtoblem = (EditText)findViewById(R.id.edit_text_problem);

        if(getIntent().getExtras()!= null){
            myLatLng = new LatLng(
                    getIntent().getDoubleExtra(MapActivity.LATITUDE,0),
                    getIntent().getDoubleExtra(MapActivity.LONGITUDE,0));
            id =  getIntent().getStringExtra(MapActivity.HOME_SERVICE_ID);

           address = getIntent().getStringExtra(MapActivity.ADDRESS);
           name = getIntent().getStringExtra(MapActivity.HOME_SERVICE_NAME);
        }
        userAddress.setText(address);

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
            if(TextUtils.isEmpty(editTextPtoblem.getText().toString())){
                editTextPtoblem.setError(getResources().getString(R.string.please_add_a_description));
                return;
            }

            OrderDialogFragment dialog = OrderDialogFragment.newInstance(name,address);
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
        orderSent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        orderSent.putExtra(OrderSentActivity.LATITUDE, myLatLng.latitude);
        orderSent.putExtra(OrderSentActivity.LONGITUDE,myLatLng.longitude);
        orderSent.putExtra(OrderSentActivity.ID,id);
        orderSent.putExtra(OrderSentActivity.PROBLEM,editTextPtoblem.getText().toString());

        startActivity(orderSent);
    }
}
