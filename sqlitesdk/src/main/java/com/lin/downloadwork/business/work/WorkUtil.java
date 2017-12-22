package com.lin.downloadwork.business.work;


import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.lin.downloadwork.basic.IBasicInfo;
import com.lin.downloadwork.basic.provide.DownLoadProvider;
import com.lin.downloadwork.business.Operator;
import com.lin.downloadwork.business.WorkController;
import com.lin.downloadwork.business.callback.OperatorRespone;
import com.lin.downloadwork.business.event.InsertEvent;
import com.lin.downloadwork.business.model.DownLoadInfo;
import com.sqlitesdk.framework.business.Business;
import com.sqlitesdk.framework.business.BusinessUtil;
import com.sqlitesdk.framework.db.Access;
import com.sqlitesdk.framework.sqliteinterface.Execute;
import com.sqlitesdk.framework.util.StringDdUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by linhui on 2017/12/9.
 */
class WorkUtil {

    static OperatorRespone findOperatorRespone(int code) {
        synchronized (WorkController.getInstance().getOperator().getOperatorRespones()) {
            for (OperatorRespone operatorRespone : ((Operator) WorkController.getInstance()).getOperatorRespones()) {
                if (operatorRespone.getCode() == code) {
                    return operatorRespone;
                }
            }
            return null;
        }
    }

    static void addOperatorRespone(OperatorRespone operatorRespone) {
        WorkController.getInstance().getOperator().addOperatorRespone(operatorRespone);
    }

    static void removeOperatorRespone(OperatorRespone operatorRespone) {
        WorkController.getInstance().getOperator().removeOperatorRespone(operatorRespone);
    }

    /**
     * 添加一个下载任务任务
     *
     * @param downLoadTable
     */
    static void addDownloadTask(final DownLoadInfo downLoadTable) {

        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                long leng = Business.getInstances().insert(sqLiteDatabase, downLoadTable);
                boolean flag = false;
                if (leng > 0) {
                    notifyAllQueryDownload(null);
                    flag = true;
                }
                EventBus.getDefault().post(new InsertEvent(downLoadTable.getObjectId(), flag));
            }

            @Override
            public void onExternalError() {

            }
        });

    }

    /**
     * 暂停
     *
     * @param objectId
     * @param soFarBytes
     * @param totalBytes
     */
    static void progress(final String objectId, final long soFarBytes, final long totalBytes) {
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set status = %d,current = %d,toTal = %d where object_id = '%s'",
                        DownLoadInfo.TB_NAME, DownLoadInfo.DOING_STATUS, soFarBytes, totalBytes, objectId);
                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });
    }

    /**
     * 完成
     *
     * @param object_id
     */
    static void completed(String object_id) {
        modiStatus(object_id, IBasicInfo.COMPLETED_STATUS);
    }

    /**
     * 暂停
     *
     * @param object_id
     */
    static void paused(String object_id) {
        modiStatus(object_id, IBasicInfo.PAUSE_STATUS);
    }

    /**
     * 错误
     * 有可能没有存储权限，或者磁盘不够，或者网络错误
     *
     * @param object_id
     */
    static void error(String object_id) {
        modiStatus(object_id, IBasicInfo.ERROR_STATUS);
    }

    /**
     * 等待状态
     *
     * @param id
     */
    static void waitting(String id) {
        modiStatus(id, IBasicInfo.WAITTING_STATUS);
//        notifyStatus();
    }

    /**
     * 改变状态
     *
     * @param objectId
     * @param status
     */
    static void modiStatus(String objectId, final int status) {
        final String object_id = objectId;
        final int s = status;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set status = %d where object_id = '%s'",
                        DownLoadInfo.TB_NAME, s, object_id);
                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
                checkStatus(status);
            }

            @Override
            public void onExternalError() {

            }
        });
    }

    /**
     * 改变状态
     *
     * @param savePath
     * @param status
     */
    static void modiStatus2(final String savePath, final int status) {
        final int s = status;
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                String sql = String.format(
                        "update %s set status = %d where save_path = '%s'",
                        DownLoadInfo.TB_NAME, s, savePath);
                sqLiteDatabase.execSQL(sql);
                notifyAllQueryDownload(null);
                checkStatus(status);
            }

            @Override
            public void onExternalError() {

            }
        });
    }


    private static void checkStatus(int status) {

        switch (status) {
            case IBasicInfo.COMPLETED_STATUS:
            case IBasicInfo.PAUSE_STATUS:
            case IBasicInfo.WAITTING_STATUS:
                notifyStatus();
                break;
            default:
                break;
        }


    }

    /**
     * 唤醒contentprovide
     *
     * @param c
     */
    static void notifyAllQueryDownload(ContentObserver c) {
        WorkController.getContext().getContentResolver().notifyChange(DownLoadProvider.CONTENT_QUERY_ALL_URI, c);

    }

    static void notifyStatus() {
        WorkController.getContext().
                getContentResolver().
                notifyChange(
                        DownLoadProvider.CONTENT_QUERY_StATUS_URI,
                        null);
    }

    /**
     * 找到对应状态的下载文件
     *
     * @param code
     * @param stutas
     */
    static void findStutasDownloadList(final int code, final int stutas) {
        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                List<DownLoadInfo> loadTables = BusinessUtil.reflectCursor(sqLiteDatabase.rawQuery("select * from " +
                        DownLoadInfo.TB_NAME + " where status = " + stutas, null), DownLoadInfo.class);
                useOperatorRespone(code, loadTables);
            }

            @Override
            public void onExternalError() {

            }
        });
    }

    static Collection<DownLoadInfo> findStutasDownloadList2(Context context) {


        return BusinessUtil.reflectCursor(
                context.getContentResolver().query(DownLoadProvider.CONTENT_QUERY_StATUS_URI,
                        null,
                        "status = ?",
                        new String[]{
                                String.valueOf(IBasicInfo.WAITTING_STATUS),
                        },
                        null,
                        null), DownLoadInfo.class);

    }

    /**
     * 使用操作回调
     *
     * @param code   标识
     * @param object 回调对象
     */
    static void useOperatorRespone(int code, Object object) {
        final OperatorRespone operatorRespone
                = findOperatorRespone(code);
        if (operatorRespone != null) {
            operatorRespone.success(object);
        }
    }

    /**
     * 删除某个id，刷新页面
     *
     * @param object_id
     */
    static void resolverDeleteInfoById(String object_id) {
        if (!StringDdUtil.isNull(object_id)) {
            WorkController.getContext().getContentResolver().delete(
                    DownLoadProvider.CONTENT_DELETE_URI, "object_id = ?",
                    new String[]{String.valueOf(object_id)});
        }
    }

    /**
     * 删除
     *
     * @param object_id
     * @param savePath
     */
    static void delete(final String object_id, final String savePath, boolean isDeleteFile) {


        if (!StringDdUtil.isNull(object_id) && !StringDdUtil.isNull(savePath)) {
            if (isDeleteFile) {
                deleteSavePath(savePath);
            }
            resolverDeleteInfoById(object_id);
        } else if (!StringDdUtil.isNull(object_id) && StringDdUtil.isNull(savePath)) {
            IBasicInfo info = WorkUtil.getInfoByObjectId(object_id);
            if (isDeleteFile) {
                deleteSavePath(info == null ? "" : info.getSavePath());
            }
            resolverDeleteInfoById(object_id);
        } else if (StringDdUtil.isNull(object_id) && !StringDdUtil.isNull(savePath)) {
            if (isDeleteFile) {
                deleteSavePath(savePath);
            }
            DownLoadInfo info = WorkUtil.getInfoBySavePath(savePath);
            if (info != null && info.getId() > 0) {
                resolverDeleteInfoById(info.getObjectId());
            }
        }

    }

    /**
     * 根据存储路径获取单行消息
     *
     * @param savePath
     * @return
     */
    static DownLoadInfo getInfoBySavePath(final String savePath) {
        final DownLoadInfo[] downLoadInfo = {null};
        Access.runCustomThread(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                downLoadInfo[0] = BusinessUtil.reflectCursorOne(
                        sqLiteDatabase.query(
                                DownLoadInfo.TB_NAME, null, "save_path = ?",
                                new String[]{savePath}, null, null, null),
                        DownLoadInfo.class,
                        true);
            }

            @Override
            public void onExternalError() {

            }
        });
        return downLoadInfo[0];
    }

    /**
     * 安装或者启动
     *
     * @param context
     * @param packageName
     * @param appPath
     */
    static void launchApp(Context context, String packageName, String appPath) {


        // 启动目标应用
        if (new File("/data/data/" + packageName).exists()) {
            WorkController.getInstance().getInstall().onOpenApk(appPath);
            // 获取目标应用安装包的Intent
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(
                    packageName);
            context.startActivity(intent);
        }
        // 安装目标应用
        else {
            WorkController.getInstance().getInstall().onStartInstall(appPath);
            Intent intent = new Intent();
            // 设置目标应用安装包路径
            intent.setDataAndType(Uri.fromFile(new File(appPath)),
                    "application/vnd.android.package-archive");
            context.startActivity(intent);
        }

    }


    static DownLoadInfo getInfoByObjectId(final String objectId) {
        final DownLoadInfo[] downLoadInfo = {null};
        Access.runCustomThread(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {

                Cursor cursor = sqLiteDatabase.query(DownLoadInfo.TB_NAME, null, "object_id = ?", new String[]{objectId}, null, null, null, null);

                downLoadInfo[0] = BusinessUtil.reflectCursorOne(cursor, DownLoadInfo.class, true);
            }

            @Override
            public void onExternalError() {

            }
        });
        return downLoadInfo[0];
    }

    /**
     * 重新下载
     *
     * @param object_id
     */
    static void reset(final String object_id) {
        IBasicInfo downLoadInfo = WorkUtil.getInfoByObjectId(object_id);

        if (downLoadInfo != null) {
            deleteSavePath(downLoadInfo.getSavePath());
        }
    }

    /**
     * 删除源文件
     *
     * @param savePath
     */
    static void deleteSavePath(String savePath) {

        if (!StringDdUtil.isNull(savePath)) {
            if (!StringDdUtil.isNull(savePath)) {
                File f = new File(savePath);
                try {
                    if (!f.delete()) {
                        f = new File(savePath + ".temp");
                        f.delete();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    /**
     * 正在下载的doing状态的若是被杀死进曾，则下次打开初始化的时候自动修改为pause状态
     */
    static void scannerStatusException() {

        Access.run(new Execute() {
            @Override
            public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
                sqLiteDatabase.execSQL(String.format(
                        "update %s set status = %d where status = %d",
                        DownLoadInfo.TB_NAME, IBasicInfo.PAUSE_STATUS, IBasicInfo.DOING_STATUS));
                notifyAllQueryDownload(null);
            }

            @Override
            public void onExternalError() {

            }
        });


    }


    /**
     * 安装应用
     */
    static void installApp(Context context, String filePath) {
        if (context == null)
            return;
        if (filePath == null)
            return;
        Uri uri = Uri.fromFile(new File(filePath));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void previewPhoto(String path) {
        File file = null;
        try {
            file = new File(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (file != null && file.isFile()) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            WorkController.getContext().startActivity(intent);
        }
    }

    static void previewVideo(String path) {
        File file = null;
        try {
            file = new File(path);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (file != null && file.isFile()) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "video/*");
            WorkController.getContext().startActivity(intent);
        }
    }

//    static void previewText(String path) {
//        File file = null;
//        try {
//            file = new File(path);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        if (file != null && file.isFile()) {
//            Intent intent = new Intent();
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setAction(android.content.Intent.ACTION_VIEW);
//            intent.setDataAndType(Uri.fromFile(file), "video/*");
//            WorkController.getContext().startActivity(intent);
//        }
//    }

}
