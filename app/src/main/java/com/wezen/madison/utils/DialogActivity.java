package com.wezen.madison.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;

import com.wezen.madison.R;

public class DialogActivity extends AppCompatActivity implements NetworkDialogFragment.OnClickOrderDialog {

    private static final String TOOLBAR_COLOR_BUNDLE = "toolbar";
    private static final String STATUSBAR_COLOR_BUNDLE = "statusBar";
    private static final String FAB_COLOR_BUNDLE = "fab";

    protected NetworkResponseReceiver networkResponseReceiver;
    NetworkDialogFragment dialog;

    private static Integer myStatusBarcolor;
    private static Integer myToolbarColor;
    private static Integer myFabColor;

    public Integer getMyStatusBarcolor() {
        return myStatusBarcolor;
    }

    public void setMyStatusBarcolor(Integer myStatusBarcolor) {
        DialogActivity.myStatusBarcolor = myStatusBarcolor;
    }

    public Integer getMyToolbarColor() {
        return myToolbarColor;
    }

    public void setMyToolbarColor(Integer myToolbarColor) {
        DialogActivity.myToolbarColor = myToolbarColor;
    }

    public Integer getMyFabColor() {
        return myFabColor;
    }

    public void setMyFabColor(Integer myFabColor) {
        DialogActivity.myFabColor = myFabColor;
    }

    public void restoreDefaultColors(){
        myStatusBarcolor = ContextCompat.getColor(this,R.color.primaryDark);
        myToolbarColor = ContextCompat.getColor(this,R.color.primary);
        myFabColor = ContextCompat.getColor(this,R.color.accent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState!= null){
            myToolbarColor = savedInstanceState.getInt(TOOLBAR_COLOR_BUNDLE);
            myStatusBarcolor = savedInstanceState.getInt(STATUSBAR_COLOR_BUNDLE);
            myFabColor = savedInstanceState.getInt(FAB_COLOR_BUNDLE);

        }

        networkResponseReceiver = new NetworkResponseReceiver();
        IntentFilter mStatusIntentFilter = new IntentFilter(ConnectionChange.SHOW_DIALOG);
        LocalBroadcastManager.getInstance(this).registerReceiver(networkResponseReceiver, mStatusIntentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!Utils.isNetworkEnable(this)){
            if(dialog == null){
                dialog = NetworkDialogFragment.newInstance("","");
                dialog.setCancelable(false);
            }
            if(!dialog. isAdded() && !dialog.isVisible()){
                dialog.show(getSupportFragmentManager(), null);
            }
        }
    }

    @Override
    public void onButtonClicked() {
        if(dialog != null){
            dialog.dismiss();
        }
        if(!Utils.isNetworkEnable(DialogActivity.this)){
            dialog.show(getSupportFragmentManager(), null);
        }

    }


    protected class NetworkResponseReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
           // if(intent.getExtras() != null){
                //String address = intent.getStringExtra(GeoCoderIntentService.DATA_ADDRESS);
            if(dialog == null){
                dialog = NetworkDialogFragment.newInstance("","");
                dialog.setCancelable(false);
                dialog.show(getSupportFragmentManager(), null);
            }
           // }

        }
    }

    protected void setColors( Context context, Toolbar toolbar,FloatingActionButton fab){
        if(toolbar!= null && fab!= null){
            toolbar.setBackgroundColor(getMyToolbarColor());
            //fab.setBackgroundTintList(createFabColors(getMyFabColor()));
            fab.setBackgroundTintList(ColorStateList.valueOf(getMyFabColor()));

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity)context).getWindow();
            window.setStatusBarColor(getMyStatusBarcolor());
        }
    }

    protected void setColors( Context context, Toolbar toolbar,FloatingActionButton fab, Toolbar bottomToolbar){
       setColors(context,toolbar,fab);
       bottomToolbar.setBackgroundColor(getMyToolbarColor());

    }

    /*private ColorStateList createFabColors(int color){
        int[][] states = new int[][] {
                new int[] { android.R.attr.state_enabled}, // enabled

        };

        int[] colors = new int[] {
                color

        };

        ColorStateList myList = new ColorStateList(states, colors);
        return myList;
    }*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(myToolbarColor != null && myStatusBarcolor!= null && myFabColor != null){
            outState.putInt(TOOLBAR_COLOR_BUNDLE, myToolbarColor);
            outState.putInt(STATUSBAR_COLOR_BUNDLE, myStatusBarcolor);
            outState.putInt(FAB_COLOR_BUNDLE,myFabColor);
        }
    }


}
