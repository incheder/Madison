package com.wezen.madison.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.wezen.madison.order.OrderDialogFragment;

/**
 * Created by eder on 22/10/2015.
 */
public class ConnectionChange extends BroadcastReceiver {

    public static String SHOW_DIALOG = "action_show_sialog";


    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
        int type = activeNetworkInfo != null ? activeNetworkInfo.getType() : -1;
        if(isConnected){
            Toast.makeText(context, "Connected " + type, Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(context, "Not Connected "+ type, Toast.LENGTH_SHORT).show();
            //NetworkDialogFragment dialog = NetworkDialogFragment.newInstance("","");
           // dialog.show(context.gegetSupportFragmentManager(), null);
            //Intent dialogActivity = new Intent(context,DialogActivity.class);
            //dialogActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          //  context.startActivity(dialogActivity);

            Intent addressResultIntent = new Intent(SHOW_DIALOG);
            //addressResultIntent.putExtra(DATA_ADDRESS,address);
            LocalBroadcastManager.getInstance(context).sendBroadcast(addressResultIntent);
        }


    }
}
