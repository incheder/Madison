package com.wezen.madison.request;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by eder on 10/05/2016.
 */
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = AlarmBroadcastReceiver.class.getSimpleName();
    public static String NOTIFICATION_ID = TAG + "_notificaton_id";
    public static String NOTIFICATION = TAG + "_notification";


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID,0);
        notificationManager.notify(id,notification);
    }
}
