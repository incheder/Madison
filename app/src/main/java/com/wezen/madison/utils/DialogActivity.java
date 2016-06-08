package com.wezen.madison.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.wezen.madison.R;
import com.wezen.madison.history.HistoryActivity;
import com.wezen.madison.history.ReviewDialogFragment;

public class DialogActivity extends AppCompatActivity implements NetworkDialogFragment.OnClickOrderDialog, ReviewDialogFragment.OnClickReviewDialog {

    private static final String TOOLBAR_COLOR_BUNDLE = "toolbar";
    private static final String STATUSBAR_COLOR_BUNDLE = "statusBar";
    private static final String FAB_COLOR_BUNDLE = "fab";

    protected NetworkResponseReceiver networkResponseReceiver;
    NetworkDialogFragment dialog;
    ReviewDialogFragment reviewDialog;

    private static Integer myStatusBarcolor;
    private static Integer myToolbarColor;
    private static Integer myFabColor;
    private SharedPreferences sharedPref;

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
            if(!dialog.isAdded() && !dialog.isVisible()){
                dialog.show(getSupportFragmentManager(), null);
            }
        } else {//there is a connection, now we have to display a rating dialog if needed
            sharedPref = getSharedPreferences(getString(R.string.my_pref),Context.MODE_PRIVATE);
            boolean pending = sharedPref.getBoolean(getString(R.string.pendin_review_pref),false );
            String name = sharedPref.getString(getString(R.string.name_review_pref),"");
            String avatar = sharedPref.getString(getString(R.string.avatar_review_pref),"");
            String id_request = sharedPref.getString(getString(R.string.id_request_review_pref),"");
            if(pending){
                if(reviewDialog == null){
                    reviewDialog = ReviewDialogFragment.newInstance(name,avatar,id_request);
                    reviewDialog.setCancelable(false);
                }
                if(!reviewDialog.isAdded() && !reviewDialog.isVisible()){
                    reviewDialog.show(getSupportFragmentManager(), null);
                }
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

    @Override
    public void onReviewDialogButtonClicked(int numStars, String comment, String idRequest) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.pendin_review_pref), false);
        editor.putString(getString(R.string.name_review_pref), "");
        editor.putString(getString(R.string.avatar_review_pref), "");
        editor.apply();
        ParseObject review = new ParseObject("Review");
        review.put("numStars", numStars);
        review.put("comment",comment);
        review.put("fromUser", ParseUser.getCurrentUser());
        ParseObject homeServiceID = ParseObject.createWithoutData("HomeServiceRequest", idRequest);
        review.put("homeServiceRequest", homeServiceID);

        review.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Toast.makeText(DialogActivity.this, getResources().getString(R.string.review_saved), Toast.LENGTH_SHORT).show();
                    //TODO actualizar el campo wasRated en la clase de los request, quitamos el boton y mostramos el rating bar con la calificaion recien mandada


                } else { //ups
                    Toast.makeText(DialogActivity.this, getResources().getString(R.string.review_not_saved), Toast.LENGTH_SHORT).show();
                    //requestList.get(position).setWasRated(false);
                    //adapter.notifyDataSetChanged();

                }
            }
        });

        reviewDialog.dismiss();
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
        }

        if(fab!=null){
            fab.setBackgroundTintList(ColorStateList.valueOf(getMyFabColor()));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((Activity)context).getWindow();
            window.setStatusBarColor(getMyStatusBarcolor());
        }
    }

    protected void setColors( Context context, Toolbar toolbar,FloatingActionButton fab, Toolbar bottomToolbar, ImageView marker){
       setColors(context,toolbar,fab);
       bottomToolbar.setBackgroundColor(getMyToolbarColor());
       marker.setColorFilter(getMyFabColor());

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
