package com.wezen.madison.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

public class DialogActivity extends AppCompatActivity implements NetworkDialogFragment.OnClickOrderDialog {

    protected NetworkResponseReceiver networkResponseReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkResponseReceiver = new NetworkResponseReceiver();
        IntentFilter mStatusIntentFilter = new IntentFilter(ConnectionChange.SHOW_DIALOG);
        LocalBroadcastManager.getInstance(this).registerReceiver(networkResponseReceiver, mStatusIntentFilter);
    }

    @Override
    public void onButtonClicked() {

    }

    protected class NetworkResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
           // if(intent.getExtras() != null){
                //String address = intent.getStringExtra(GeoCoderIntentService.DATA_ADDRESS);
                NetworkDialogFragment dialog = NetworkDialogFragment.newInstance("","");
                 dialog.show(getSupportFragmentManager(), null);
           // }

        }
    }
}
