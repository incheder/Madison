package com.wezen.madison.map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
import com.wezen.madison.R;
import com.wezen.madison.summary.SummaryActivity;
import com.wezen.madison.utils.DialogActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends DialogActivity {
    //public static final String LATITUDE = "latitude";
    //public static final String LONGITUDE = "longitude";
    //public static final String ADDRESS = "address";
    public static final String HOME_SERVICE_ID = "id";
    public static final String HOME_SERVICE_NAME = "name";
    public static final String HOME_SERVICE_DESCRIPTION = "description";
    public static final String HOME_SERVICE_PROVIDER = "serviceProvider";

    private EditText userAddressEditText;
    private boolean firstTime = true;
    private TextView userAddressTextView;
    private FloatingActionButton fab;
    private GeoCoderResponseReceiver geoCoderResponseReceiver;
    private String id;
    private String name;
    private String description;
    private String serviceProvider;


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mapToolbar);
        userAddressEditText = (EditText)findViewById(R.id.mapAddressEditTex);
        userAddressTextView = (TextView)findViewById(R.id.mapAddressTextView);
        userAddressTextView.setOnClickListener(addressTextViewListener);
        fab = (FloatingActionButton)findViewById(R.id.fabMap);

        fab.setOnClickListener(fabClickListener);
        fab.hide();
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        id = getIntent().getExtras().getString(HOME_SERVICE_ID);
        name = getIntent().getExtras().getString(HOME_SERVICE_NAME);
        description = getIntent().getExtras().getString(HOME_SERVICE_DESCRIPTION);
        serviceProvider = getIntent().getExtras().getString(HOME_SERVICE_PROVIDER);

        Toolbar bottomToolbar = (Toolbar)findViewById(R.id.mapToolbarBottom);

        setUpMapIfNeeded();
        geoCoderResponseReceiver = new GeoCoderResponseReceiver();
        IntentFilter mStatusIntentFilter = new IntentFilter(GeoCoderIntentService.BROADCAST_SEND_ADDRESS);
        LocalBroadcastManager.getInstance(this).registerReceiver(geoCoderResponseReceiver, mStatusIntentFilter);

        setColors(this,toolbar,fab,bottomToolbar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(geoCoderResponseReceiver);
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            mMap = googleMap;
                            if (mMap != null) {
                                setUpMap();
                            }
                        }
                    });


        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    if(firstTime){
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng,16)));
                        firstTime = false;
                        fab.show();
                    }
                    getAddress();
                   // userAddressTextView.setText(getAddress());
                }
            }
        });

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                noMoreInput();
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideKeyboard(userAddressEditText);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_location) {
            if(mMap.getMyLocation()!=null){
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        CameraPosition.fromLatLngZoom(
                                new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()),
                                mMap.getCameraPosition().zoom)));
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private LatLng getCenterOfMap(GoogleMap mMap){
        VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
        Point x = mMap.getProjection().toScreenLocation(visibleRegion.farRight);
        Point y = mMap.getProjection().toScreenLocation(visibleRegion.nearRight);
        Point centerPoint = new Point((x.x / 2), (y.y / 2));
        return mMap.getProjection().fromScreenLocation(centerPoint);
    }

    private void getAddress(){

        LatLng geo = getCenterOfMap(mMap);
        GeoCoderIntentService.startActionGetAddress(this,geo.latitude,geo.longitude);

    }

    View.OnClickListener addressTextViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            userAddressTextView.setVisibility(View.GONE);
            userAddressEditText.setVisibility(View.VISIBLE);
            userAddressEditText.setText(userAddressTextView.getText());
            userAddressEditText.requestFocus();
            showKeyboard(userAddressEditText);
        }
    };

    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                Intent summary = new Intent(MapActivity.this, SummaryActivity.class);
                LatLng latLng = getCenterOfMap(mMap);
                summary.putExtra(SummaryActivity.LATITUDE, latLng.latitude);
                summary.putExtra(SummaryActivity.LONGITUDE, latLng.longitude);
                summary.putExtra(SummaryActivity.HOME_SERVICE_ID, id);
                summary.putExtra(SummaryActivity.HOME_SERVICE_NAME, name);
                summary.putExtra(SummaryActivity.HOME_SERVICE_DESCRIPTION, description);
                summary.putExtra(SummaryActivity.HOME_SERVICE_PROVIDER, serviceProvider);
                String userAddress = null;
                if (userAddressEditText.getVisibility() == View.VISIBLE) {
                    userAddress = userAddressEditText.getText().toString();

                } else{
                    userAddress = userAddressTextView.getText().toString();
                }
                if (!userAddress.equals("")){
                    summary.putExtra(SummaryActivity.ADDRESS,userAddress);
                    startActivity(summary);
                }
        }
    };

    private void showKeyboard(EditText editText){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        editText.setSelection(editText.getText().length());
    }

    private void hideKeyboard(EditText editText){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void  noMoreInput(){
        userAddressEditText.setVisibility(View.GONE);
        userAddressTextView.setVisibility(View.VISIBLE);
       // userAddressTextView.setText(getAddress());
        getAddress();
        hideKeyboard(userAddressEditText);
    }

    private class GeoCoderResponseReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getExtras() != null){
                String address = intent.getStringExtra(GeoCoderIntentService.DATA_ADDRESS);
                userAddressTextView.setText(address);
            }

        }
    }


}
