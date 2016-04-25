package com.wezen.madison.utils;

import com.parse.Parse;
import com.parse.ParsePush;

/**
 * Created by eder on 5/25/15.
 */
public class Application extends android.app.Application {

    public ToolbarColorSinleton toolbarColorSinleton;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "xJzgkwGEHuFFIBazGyGWeHmbn9cSWaQ2F9oPHFhb", "dSdG1ACby08fUT6oCxxxZxWyXq5sTzm3zW51YNNS");
        ParsePush.subscribeInBackground("Client");
    }
}
