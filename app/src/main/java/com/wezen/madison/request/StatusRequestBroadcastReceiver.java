package com.wezen.madison.request;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wezen.madison.model.HomeServiceRequestStatus;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eder on 11/7/15.
 */
public class StatusRequestBroadcastReceiver extends com.parse.ParsePushBroadcastReceiver {

    private static final String PARSE_INCOMING_REQUEST = "homeServiceRequest";
    private static final String REQUEST_HOME_SERVICE_NAME = "homeServiceName";
    private String IMAGE_URL = "imageUrl";
    private String PROBLEM_DESCRIPTION = "problemDescription";
    private String ATTENDED_BY = "attendedBy";

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        //super.onPushOpen(context, intent);
        JSONObject jObject= getDataFromIntent(intent);
        Intent incomingRequest = new Intent(context,RequestActivity.class);
        try {
            //incomingRequest.putExtra(RequestActivity.REQUEST_ID,jObject.getString(PARSE_INCOMING_REQUEST));
            incomingRequest.putExtra(RequestActivity.REQUEST_IMAGE_URL,jObject.getString(IMAGE_URL));
            incomingRequest.putExtra(RequestActivity.REQUEST_STATUS, HomeServiceRequestStatus.CONFIRMADO.getValue());
            incomingRequest.putExtra(RequestActivity.REQUEST_HOME_SERVICE_NAME, jObject.getString(REQUEST_HOME_SERVICE_NAME));
            incomingRequest.putExtra(RequestActivity.REQUEST_PROBLEM_DESCRIPTION, jObject.getString(PROBLEM_DESCRIPTION));
            incomingRequest.putExtra(RequestActivity.REQUEST_ATTENDED_BY, jObject.getString(ATTENDED_BY));
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
}
