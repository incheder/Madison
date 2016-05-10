package com.wezen.madison.request;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.wezen.madison.R;
import com.wezen.madison.model.HomeServiceRequestStatus;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by eder on 11/7/15.
 */
public class StatusRequestBroadcastReceiver extends com.parse.ParsePushBroadcastReceiver {
    private String PUSH_ID = "parsePushId";
    private String DATE = "date";
    private String REQUEST_HOME_SERVICE_NAME = "homeServiceName";
    private JSONObject jObject;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd 'de' MMM 'del 'yyyy 'a las' hh:mm a", Locale.getDefault());

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        if(jObject== null){
            jObject= getDataFromIntent(intent);
        }

        try {
            Date date =  dateFormat.parse(jObject.getString(DATE));
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR_OF_DAY, 1);
            //scheduleAlarmNotification(getNotification(jObject.getString(REQUEST_HOME_SERVICE_NAME),context),cal.getTimeInMillis(),context,jObject.getLong(PUSH_ID));
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        super.onPushReceive(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        //super.onPushOpen(context, intent);
        String IMAGE_URL = "imageUrl";
        String PROBLEM_DESCRIPTION = "problemDescription";
        String ATTENDED_BY = "attendedBy";
        String ATTENDED_BY_AVATAR = "attendedByAvatar";


        if(jObject == null){
            jObject= getDataFromIntent(intent);
        }
        Intent incomingRequest = new Intent(context,RequestActivity.class);
        try {
            //incomingRequest.putExtra(RequestActivity.REQUEST_ID,jObject.getString(PARSE_INCOMING_REQUEST));
            incomingRequest.putExtra(RequestActivity.REQUEST_IMAGE_URL,jObject.getString(IMAGE_URL));
            incomingRequest.putExtra(RequestActivity.REQUEST_STATUS, HomeServiceRequestStatus.CONFIRMADO.getValue());
            incomingRequest.putExtra(RequestActivity.REQUEST_HOME_SERVICE_NAME, jObject.getString(REQUEST_HOME_SERVICE_NAME));

            incomingRequest.putExtra(RequestActivity.REQUEST_PROBLEM_DESCRIPTION, jObject.getString(PROBLEM_DESCRIPTION));
            incomingRequest.putExtra(RequestActivity.REQUEST_ATTENDED_BY, jObject.getString(ATTENDED_BY));
            incomingRequest.putExtra(RequestActivity.REQUEST_ATTENDED_BY_AVATAR, jObject.getString(ATTENDED_BY_AVATAR));
            incomingRequest.putExtra(RequestActivity.REQUEST_DATE_FOR_SERVICE, jObject.getString(DATE));
            incomingRequest.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(incomingRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getDataFromIntent(Intent intent) {
        JSONObject data = null;
        try {
            String PARSE_DATA_KEY = "com.parse.Data";
            data = new JSONObject(intent.getExtras().getString(PARSE_DATA_KEY));
            Log.d("TAG","data: " + data);
        } catch (JSONException e) {
            // Json was not readable...
            Log.e("ERROR",e.getMessage());
        }
        return data;
    }

    private void scheduleAlarmNotification(Notification notification, long time, Context context,long id){
        Intent notificationIntent = new Intent(context,AlarmBroadcastReceiver.class);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION_ID,id);
        notificationIntent.putExtra(AlarmBroadcastReceiver.NOTIFICATION,notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + 10000;

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,futureInMillis,pendingIntent);
    }

    private Notification getNotification(String content, Context context){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getResources().getString(R.string.alarm_notification_title));
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);

        return builder.build();
    }
}
