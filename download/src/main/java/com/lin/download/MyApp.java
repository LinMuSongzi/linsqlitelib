package com.lin.download;

import android.app.Application;

import com.lin.downloadwork.basic.Entrance;

/**
 * Created by linhui on 2017/12/5.
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Entrance.init(this);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                e.printStackTrace();
            }
        });

    }
}
