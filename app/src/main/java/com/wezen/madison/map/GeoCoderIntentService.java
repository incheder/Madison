package com.wezen.madison.map;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GeoCoderIntentService extends IntentService {
    private static final String ACTION_GET_ADDRESS = "com.wezen.madison.map.action.GET_ADDRESS";
    private static final String EXTRA_PARAM_LAT = "com.wezen.madison.map.extra.PARAM1";
    private static final String EXTRA_PARAM_LONG = "com.wezen.madison.map.extra.PARAM2";
    public static final String BROADCAST_SEND_ADDRESS = "com.wezen.madison.map.broadcast.SEND_ADDRESS";
    public static final String DATA_ADDRESS = "com.wezen.madison.map.data_ADDRESS";


    /**
     * Starts this service to perform action GetAddress with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionGetAddress(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, GeoCoderIntentService.class);
        intent.setAction(ACTION_GET_ADDRESS);
        intent.putExtra(EXTRA_PARAM_LAT, latitude);
        intent.putExtra(EXTRA_PARAM_LONG, longitude);
        context.startService(intent);
    }

    public GeoCoderIntentService() {
        super("GeoCoderIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_ADDRESS.equals(action)) {
                final double latitude = intent.getDoubleExtra(EXTRA_PARAM_LAT,0);
                final double longitude = intent.getDoubleExtra(EXTRA_PARAM_LONG,0);
                String address = doGeoCoderTask(latitude, longitude);
                handleActionGetAddress(address);
            }
        }
    }

    /**
     * Handle action GetAddress in the provided background thread with the provided
     * parameters.
     */
    private void handleActionGetAddress(String address) {
        Intent addressResultIntent = new Intent(BROADCAST_SEND_ADDRESS);
        addressResultIntent.putExtra(DATA_ADDRESS,address);
        LocalBroadcastManager.getInstance(this).sendBroadcast(addressResultIntent);
    }

    private String doGeoCoderTask(double latitude, double longitude){
        String addressLabel = "";
        Geocoder geo = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> list = geo.getFromLocation(latitude, longitude,1);
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
}
