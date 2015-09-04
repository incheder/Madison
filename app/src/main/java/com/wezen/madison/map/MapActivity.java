package com.wezen.madison.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.wezen.madison.fragment.OrderDialogFragment;
import com.wezen.madison.summary.SummaryActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity {
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ADDRESS = "address";

    private Toolbar toolbar;
    private EditText userAddressEditText;
    private boolean firstTime = true;
    private TextView userAddressTextView;
    private FloatingActionButton fab;


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        toolbar = (Toolbar)findViewById(R.id.mapToolbar);
        userAddressEditText = (EditText)findViewById(R.id.mapAddressEditTex);
        userAddressTextView = (TextView)findViewById(R.id.mapAddressTextView);
        userAddressTextView.setOnClickListener(addressTextViewListener);
        fab = (FloatingActionButton)findViewById(R.id.fabMap);
        fab.setOnClickListener(fabClickListener);
        fab.hide();
        setSupportActionBar(toolbar);
        setUpMapIfNeeded();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
       // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
       /* mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(new LatLng(
                        mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()),
                        16)
        ));*/
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    if(firstTime){
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng,16)));
                        firstTime = false;
                        fab.show();
                    }
                        userAddressTextView.setText(getAddress());
                        //userAddressEditText.setText(getAddress());

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
               // userAddressEditText.setVisibility(View.GONE);
               // userAddressTextView.setVisibility(View.VISIBLE);
               // userAddressTextView.setText(getAddress());
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
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                    CameraPosition.fromLatLngZoom(
                            new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()),
                            mMap.getCameraPosition().zoom)));

            //mMap.addMarker(new MarkerOptions().position(getCenterOfMap(mMap)));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private LatLng getCenterOfMap(GoogleMap mMap){
        VisibleRegion visibleRegion = mMap.getProjection().getVisibleRegion();
        Point x = mMap.getProjection().toScreenLocation(visibleRegion.farRight);
        Point y = mMap.getProjection().toScreenLocation(visibleRegion.nearRight);
        Point centerPoint = new Point((x.x / 2), (y.y / 2));
        LatLng centerFromPoint = mMap.getProjection().fromScreenLocation(centerPoint);
        return  centerFromPoint;
    }

    private String getAddress(){
        String addressLabel = "";
        Geocoder geo = new Geocoder(this, Locale.ENGLISH);
        try {
			List<Address> list = geo.getFromLocation(getCenterOfMap(mMap).latitude, getCenterOfMap(mMap).longitude,1);
            if(list != null && list.size() > 0){
                Address address = list.get(0);
                for(int i = 0; i < address.getMaxAddressLineIndex(); i ++){
                    if(address.getAddressLine(i) != null){
                        addressLabel = addressLabel + " " + address.getAddressLine(i);
                    }
                }
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
        return addressLabel;
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
               /* OrderDialogFragment dialog = new OrderDialogFragment();
                dialog.show( getSupportFragmentManager(),null);*/
                Intent summary = new Intent(MapActivity.this, SummaryActivity.class);
                LatLng latLng = getCenterOfMap(mMap);
                summary.putExtra(LATITUDE,latLng.latitude);
                summary.putExtra(LONGITUDE,latLng.longitude);
                summary.putExtra(ADDRESS,
                        userAddressEditText.getVisibility() == View.VISIBLE ?
                        userAddressEditText.getText().toString() :
                        userAddressTextView.getText().toString());

                startActivity(summary);
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
        userAddressTextView.setText(getAddress());
        hideKeyboard(userAddressEditText);
    }
}
