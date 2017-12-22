package com.lin.downloadwork.basic;


import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;

/**
 * Created by linhui on 2017/12/7.
 */
public class SimpleFileListenerImp extends FileDownloadListener {
    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i(Entrance.TAG, "pending: ");

    }

    @Override
    protected void retry(BaseDownloadTask task, Throwable ex, int retryingTimes, int soFarBytes) {
        super.retry(task, ex, retryingTimes, soFarBytes);
        Log.i(Entrance.TAG, "retry: ");
    }

    @Override
    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
        Log.i(Entrance.TAG, "connected: ");
    }

    @Override
    protected void blockComplete(BaseDownloadTask task) throws Throwable {
        super.blockComplete(task);
        Log.i(Entrance.TAG, "blockComplete: ");
    }

    @Override
    protected void started(BaseDownloadTask task) {
        super.started(task);
        Log.i(Entrance.TAG, "started: ");
    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i(Entrance.TAG, "progress: ");
    }

    @Override
    protected void completed(BaseDownloadTask task) {
        Log.i(Entrance.TAG, "completed: ");
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i(Entrance.TAG, "paused: ");
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        Log.i(Entrance.TAG, "error: ");
        e.printStackTrace();
    }

    @Override
    protected void warn(BaseDownloadTask task) {
        Log.i(Entrance.TAG, "warn: ");
    }
}
