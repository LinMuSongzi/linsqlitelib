package y.com.sqlitesdk.framework.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import y.com.sqlitesdk.framework.IfeimoSqliteSdk;

/**
 * Created by lpds on 2017/4/18.
 */
public final class SdkConfig {

    public static final String DB_NAME = "ifeimo_db";
    private static SdkConfig sdkConfig;

    static{
        sdkConfig = new SdkConfig();
        loadConfig();
    }

    private SdkConfig (){
    }

    static void loadConfig(){

    }

    public static SdkConfig getInstances(){
        return sdkConfig;
    }

    SQLiteDatabase getDefaultWriter(){
        return IfeimoSqliteSdk.IPMAIN.getApplication().openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE,null);
    }

}
