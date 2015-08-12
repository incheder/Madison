package com.wezen.madison.utils;

import com.parse.Parse;

/**
 * Created by eder on 5/25/15.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "","");
    }
}
