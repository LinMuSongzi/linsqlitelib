package com.lin.downloadwork.basic;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.lin.downloadwork.BuildConfig;
import com.lin.downloadwork.business.model.DownLoadInfo;

import y.com.sqlitesdk.framework.business.Business;

/**
 * Created by linhui on 2017/12/12.
 */
public class DonwloadSqlLiteOpenHelp extends SQLiteOpenHelper {

    public DonwloadSqlLiteOpenHelp(Context context) {
        super(context, "tb_download.db", null, BuildConfig.VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            Business.getInstances().createTable(sqLiteDatabase, DownLoadInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion){
            case 20171212:
                break;



        }

    }
}
