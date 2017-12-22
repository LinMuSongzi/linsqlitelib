package com.sqlitesdk.framework;


import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lpds on 2017/4/14.
 */
public interface AppMain {


    Application getApplication();

    void runOnUiThread(Runnable runnable);

    SQLiteDatabase getSQLiteDatabase();

}
