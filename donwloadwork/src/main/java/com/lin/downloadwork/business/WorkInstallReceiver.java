package com.lin.downloadwork.business;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by linhui on 2017/12/19.
 */
public class WorkInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("WorkInstallReceiver", "onReceive: " + intent);

        switch (intent.getAction()) {
            case Intent.ACTION_PACKAGE_ADDED:
//                WorkController.downLoadViewController.onInstallComplete(intent.getData());
                break;
            case Intent.ACTION_PACKAGE_REMOVED:
                break;
            case Intent.ACTION_PACKAGE_FIRST_LAUNCH:
                break;
            case Intent.ACTION_PACKAGE_DATA_CLEARED:
                break;
            case Intent.ACTION_PACKAGE_CHANGED:
                break;
            case Intent.ACTION_PACKAGE_REPLACED:
                break;
            case Intent.ACTION_INSTALL_PACKAGE:
                break;
            case Intent.ACTION_PACKAGE_FULLY_REMOVED:
                break;
        }

    }
}
