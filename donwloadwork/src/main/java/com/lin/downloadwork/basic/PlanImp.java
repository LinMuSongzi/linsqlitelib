package com.lin.downloadwork.basic;


import android.util.Log;

import com.lin.downloadwork.business.WorkController;
import com.lin.downloadwork.business.model.DownLoadInfo;
import com.lin.downloadwork.business.work.BusinessWrap;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloader;

/**
 * Created by linhui on 2017/12/7.
 */
public class PlanImp implements Plan {
    private DownLoadInfo downLoadTable;
    private BaseDownloadTask baseDownloadTask;
    private int baseDownloadTaskId;
    private String objectId = "";

    private PlanImp(String object_id) {
        this.objectId = object_id;
    }

    static Plan getNewInstance(String object_id) {
        return new PlanImp(object_id);
    }

    @Override
    public void download() {
        if (baseDownloadTask == null) {
            downLoadTable = BusinessWrap.getInfoByObjectId(objectId);
            if(downLoadTable.getStatus() == IBasicInfo.COMPLETED_STATUS){
                Log.i(Entrance.TAG, "download: 下载已完成！无须再次此下载");
                return;
            }
            if (downLoadTable.getId() > 0) {
                download2();
            }
        } else {
            if (!baseDownloadTask.isRunning()) {
                if (baseDownloadTask.isUsing()) {
                    baseDownloadTask.reuse();
                }
                baseDownloadTask.start();
            }
        }
    }

    private void download2() {
        final DownLoadInfo downLoadInfo = downLoadTable.clone();
        final String  objectId = downLoadTable.getObjectId();
//        final DownLoadInfo downLoadInfo = downLoadTable.clone();
        baseDownloadTaskId = (baseDownloadTask = FileDownloader.getImpl().
                create(downLoadTable.getDownLoadUrl()).setListener(new SimpleFileListenerImp() {
            @Override
            protected void started(BaseDownloadTask task) {
                super.started(task);
                WorkController.getInstance().getInstall().onDownloading(downLoadInfo);
            }

            @Override
            protected void progress(BaseDownloadTask task, final int soFarBytes, final int totalBytes) {
                super.progress(task, soFarBytes, totalBytes);
                BusinessWrap.progress(objectId, soFarBytes, totalBytes);

            }

            @Override
            protected void completed(BaseDownloadTask task) {
                super.completed(task);
                WorkController.getInstance().removePlan(PlanImp.this);
                BusinessWrap.completed(objectId);
                WorkController.getInstance().getInstall().onDownloadComplete(downLoadInfo);

            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                super.paused(task, soFarBytes, totalBytes);
                BusinessWrap.paused(objectId);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                super.error(task, e);
                BusinessWrap.error(objectId);
                WorkController.getInstance().getInstall().onErrorDownload(downLoadInfo);
                PlanImp.this.error(e);
            }
        }).setPath(downLoadTable.getSavePath()).setSyncCallback(true)).start();
    }

    private void error(Throwable ex){
        WorkController.getInstance().getOperator().handlerFileDownloadException(ex);
    }

    @Override
    public void run() {
        download();
    }

    @Override
    public String getModelId() {
        return objectId;
    }

    @Override
    public boolean isRunning() {
        return baseDownloadTask != null && baseDownloadTask.isRunning();
    }

    @Override
    public void delete(boolean isDeleteFile) {
        this.pause();
        BusinessWrap.delete(objectId, downLoadTable != null ? downLoadTable.getSavePath() : "", isDeleteFile);
    }

    @Override
    public void reset() {
        this.pause();
        BusinessWrap.reset(objectId);
        WorkController.getInstance().download(objectId);
    }

    @Override
    public void pause() {
        if (baseDownloadTask != null && baseDownloadTask.isRunning()) {
            baseDownloadTask.pause();
            baseDownloadTask = null;
        } else {
            BusinessWrap.paused(objectId);
        }
    }

    @Override
    public String toString() {
        return "PlanImp{" +
                "downLoadTable=" + downLoadTable +
                ", baseDownloadTask=" + baseDownloadTask +
                ", baseDownloadTaskId=" + baseDownloadTaskId +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}
