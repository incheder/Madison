package com.wezen.madison.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by eder on 5/20/15.
 */
public class MyDatabese extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "madison.db";
    private static final int DATABASE_VERSION = 1;

    public MyDatabese(Context context, String name, String storageDirectory, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, storageDirectory, factory, version);

    }
}
