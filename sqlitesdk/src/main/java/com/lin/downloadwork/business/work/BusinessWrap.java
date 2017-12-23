package com.lin.downloadwork.business.work;

import android.content.Context;
import android.database.ContentObserver;

import com.lin.downloadwork.business.callback.OperatorRespone;
import com.lin.downloadwork.business.model.DownLoadInfo;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.Collection;

/**
 * Created by linhui on 2017/12/11.
 */
public class BusinessWrap {

    /**
     * 添加一个下载任务任务（必须先添加任务，才能下载）
     *
     * @param downLoadTable
     */
    public static void addDownloadTask(DownLoadInfo downLoadTable) {
        WorkUtil.addDownloadTask(downLoadTable);
    }

    /**
     * 暂停
     *
     * @param object_id
     * @param soFarBytes
     * @param totalBytes
     */
    public static void progress(String object_id, final long soFarBytes, final long totalBytes) {
        WorkUtil.progress(object_id, soFarBytes, totalBytes);
    }

    /**
     * 完成
     *
     * @param ObjectId
     */
    public static void completed(String ObjectId) {
        WorkUtil.completed(ObjectId);
    }

    /**
     * 暂停
     *
     * @param ObjectId
     */
    public static void paused(String ObjectId) {
        WorkUtil.paused(ObjectId);
    }

    /**
     * 唤醒等待状态，进行下载
     */
    public static void notifyStatus(){
        WorkUtil.notifyStatus();
    }

    /**
     * 错误
     * 有可能没有存储权限，或者磁盘不够，或者网络错误
     *
     * @param ObjectId
     */
    public static void error(String ObjectId) {
        WorkUtil.error(ObjectId);
    }

    /**
     * 等待
     * @param ObjectId
     */
    public static void waitting(String ObjectId){WorkUtil.waitting(ObjectId);}

    /**
     * 对象id，查找（业务项的唯一标识）
     * @param object_id
     * @return
     */
    public static DownLoadInfo getInfoByObjectId(String object_id) {
        return WorkUtil.getInfoByObjectId(object_id);
    }

    /**
     * 唤醒contentprovide
     *
     * @param c
     */
    public static void notifyAllQueryDownload(ContentObserver c) {
        WorkUtil.notifyAllQueryDownload(c);
    }

    /**
     * 找到对应状态的下载文件
     *
     * @param code
     * @param stutas
     */
    public static void findStutasDownloadList(int code, int stutas) {
        WorkUtil.findStutasDownloadList(code, stutas);
    }

    public static void addOperatorRespone(OperatorRespone operatorRespone) {
        WorkUtil.addOperatorRespone(operatorRespone);
    }

    public static void removeOperatorRespone(OperatorRespone operatorRespone) {
        WorkUtil.removeOperatorRespone(operatorRespone);
    }

    /**
     * 删除下载文件与数据库行，不建议直接使用
     *
     * @param object_id
     * @param savePath
     */
    public static void delete(String object_id, String savePath,boolean isDeleteFile) {
        WorkUtil.delete(object_id, savePath,isDeleteFile);
    }

    /**
     * 开启app
     * @param context
     * @param packageName
     * @param appPath
     */
    public static void launchApp(Context context, String packageName, String appPath) {
        WorkUtil.launchApp(context, packageName, appPath);
    }

    /**
     * 重新下载删除源文件，不建议直接使用
     *
     * @param table
     */
    public static void reset(String table) {
        WorkUtil.reset(table);
    }

    /**
     * 只删除文件
     *
     * @param savePath
     */
    public static void deleteSavePath(String savePath) {
        WorkUtil.deleteSavePath(savePath);
    }

    /**
     * 根据path获取一行数据看信息
     *
     * @param savePath
     * @return
     */
    public static DownLoadInfo getInfoBySavePath(String savePath) {
        return WorkUtil.getInfoBySavePath(savePath);
    }

    /**
     * 暂停所有
     */
    public static void pauseAll() {
        FileDownloader.getImpl().pauseAll();
    }

    /**
     * 扫描正在执行的并且把他们改变为已暂停，因为正在执行的进程被异常关闭后，状态还没来得及修复
     */
    public static void scannerStatusException() {
        WorkUtil.scannerStatusException();
    }

    /**
     * 改变状态，根据存储地址
     * @param appPath
     * @param notHadStatus
     */
    public static void modiStatus2(String appPath, int notHadStatus) {
        WorkUtil.modiStatus2(appPath,notHadStatus);
    }

    public static Collection<? extends DownLoadInfo> findStutasDownloadList2(Context context) {
        return WorkUtil.findStutasDownloadList2(context);
    }

    public static void installApp(Context context, String filePath) {
        WorkUtil.installApp(context,filePath);
    }

    public static void previewPhoto(String path){
        WorkUtil.previewPhoto(path);
    }

    public static void previewVideo(String path){
        WorkUtil.previewVideo(path);
    }

    public static void addTaskNoReplace(DownLoadInfo downLoadTable) {
        WorkUtil.addTaskNoReplace(downLoadTable);
    }

    public static void addAndDownload(DownLoadInfo downLoadTable) {
        WorkUtil.addAndDownload(downLoadTable);
    }
}
