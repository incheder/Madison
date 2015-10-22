package com.wezen.madison.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.wezen.madison.R;
import com.wezen.madison.model.Beverage;
import com.wezen.madison.model.BeverageType;

import java.util.ArrayList;

/**
 * Created by eder on 4/13/15.
 */
public class Utils {

    private Utils(){}

    public static boolean isNetworkEnable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


}
