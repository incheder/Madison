package com.wezen.madison.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.wezen.madison.R;
import com.wezen.madison.model.Beverage;
import com.wezen.madison.model.BeverageType;
import com.wezen.madison.model.HomeServiceRequestStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eder on 4/13/15.
 */
public class Utils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd 'de' MMM 'del 'yyyy 'a las' hh:mm a", Locale.getDefault());

    private Utils(){}

    public static boolean isNetworkEnable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static int getColorByStatus(Context context, HomeServiceRequestStatus status){
        int color = ContextCompat.getColor(context, R.color.transparent);
        switch (status) {
            case ENVIADO:
                color = ContextCompat.getColor(context, R.color.palette_green);
                break;
            case CONFIRMADO:
                color = ContextCompat.getColor(context, R.color.palette_yellow_dark);
                break;
            case CANCELADO:
                color = ContextCompat.getColor(context, R.color.palette_red);
                break;
            case COMPLETO:
                color = ContextCompat.getColor(context, R.color.palette_blue);
                break;
            case RECHAZADO:
                color = ContextCompat.getColor(context, R.color.palette_red);
                break;
        }
        return color;

    }

    public static String setDateFormat(Date date){
        String newFormat = "";
        newFormat = dateFormat.format(date);
        return newFormat;
    }


}
