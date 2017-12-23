package com.lin.downloadwork.business;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.lin.downloadwork.basic.Entrance;
import com.lin.downloadwork.basic.Plan;
import com.lin.downloadwork.basic.provide.DownLoadProvider;
import com.lin.downloadwork.business.callback.FileDownloadExceptionListener;
import com.lin.downloadwork.business.callback.InstallListener;
import com.lin.downloadwork.business.callback.OperatorRespone;
import com.lin.downloadwork.business.model.DownLoadInfo;
import com.lin.downloadwork.business.work.BusinessWrap;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by linhui on 2017/12/11.
 */
public class WorkController implements Controller, Operator, Subscription, Install {

    static WorkController downLoadViewController;

    public static Controller getInstance() {
        return downLoadViewController;
    }

    static {
        downLoadViewController = new WorkController();
    }

    private Context context;
    private Handler handler;
    private IObserverWork contentObserver;
    private List<DownLoadInfo> loadInfos = new ArrayList<>();//等待的队列
    private Operator operatorImp = new OperatorImp();
    /**
     * 当前想下载的id
     */
    private String thisDownLoadObjectId;
    /**
     * 下载计划
     */
    private final Set<Plan> PLANS = new HashSet<Plan>() {
        @Override
        public boolean add(Plan o) {
            synchronized (WorkController.this) {
                Log.i(Entrance.TAG, "add: 新增一个任务 plan = " + o);
                Log.i(Entrance.TAG, "add: 当前工头 " + (size() + 1));
                return super.add(o);
            }
        }

        @Override
        public boolean remove(Object o) {
            synchronized (WorkController.this) {
                Log.i(Entrance.TAG, "add: 移除一个任务 plan = " + o);
                return super.remove(o);
            }
        }

        @Override
        public void clear() {
            Log.i(Entrance.TAG, "clear: 清空任务栏");
            super.clear();
        }
    };

    private WorkController() {
        HandlerThread handlerThread = new HandlerThread("DownLoadViewController");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    /**
     * 根据数据库表格objectId得到下载计划
     *
     * @param object_id
     * @return
     */
    private Plan userDownLoadImp(String object_id) {
        for (Plan plan : PLANS) {
            if (object_id.equals(plan.getModelId())) {
                return plan;
            }
        }
        Plan plan = Entrance.createSimplePlan(object_id);
        PLANS.add(plan);
        return plan;
    }

    @Override
    public void init(final Context context) {
        this.context = context;
        FileDownloader.setup(context);
        initContentObserver();
    }

    private void initContentObserver() {
        contentObserver = ContentObserverWork.create(this, "download_thead", new Runnable() {
            @Override
            public void run() {
                /**
                 * 线程里必须bind 下载服务，不然下载会掉失败
                 */
                FileDownloader.getImpl().bindService();
            }
        });
        scanner();
        getContext().getContentResolver().registerContentObserver(DownLoadProvider.CONTENT_QUERY_StATUS_URI, true, contentObserver.getContentObserver());

    }

    private void scanner() {
        BusinessWrap.scannerStatusException();
    }

    /**
     * 暂停
     *
     * @param object_id 数据库 下载表 id
     */
    @Override
    public void pause(final String object_id) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                userDownLoadImp(object_id).pause();
            }
        });
    }

    /**
     * 下载
     *
     * @param object_id
     */
    @Override
    public synchronized void download(String object_id) {
        /**
         * 先改为等待状态，之后刷新数据库 contentObserver
         */
        thisDownLoadObjectId = object_id;
        BusinessWrap.waitting(object_id);
    }

    /**
     * 真正的下载
     *
     * @param downLoadInfo
     */
    private synchronized void download2(DownLoadInfo downLoadInfo) {
        if (getRunningPlan() < MAX_DOWNLOAD_COUNT) {
            handler.post(userDownLoadImp(downLoadInfo.getObjectId()));
        }

    }

    /**
     * 删除
     *
     * @param objectId
     */
    @Override
    public void delete(final String objectId, final boolean isDeleteFile) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Plan plan = userDownLoadImp(objectId);
                plan.delete(isDeleteFile);
                PLANS.remove(plan);
            }
        });

    }

    @Override
    public void reset(final String objectId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                userDownLoadImp(objectId).reset();
            }
        });
    }

    /**
     * 添加一个新任务
     *
     * @param downLoadTable
     */
    @Override
    public void addTask(DownLoadInfo downLoadTable) {
        BusinessWrap.addDownloadTask(downLoadTable);
    }

    @Override
    public void addTaskNoReplace(DownLoadInfo downLoadTable) {
        BusinessWrap.addTaskNoReplace(downLoadTable);
    }

    @Override
    public void addAndDownload(DownLoadInfo downLoadTable) {
        BusinessWrap.addAndDownload(downLoadTable);
    }

    @Override
    public void pauseAll() {
        BusinessWrap.pauseAll();
    }

    @Override
    public void deleteSavePath(String savePath) {
        BusinessWrap.deleteSavePath(savePath);
    }

    @Override
    public void removePlan(Plan plan) {
        if (plan != null) {
            PLANS.remove(plan);
        }
    }

    @Override
    public void releaseAll() {
        this.pauseAll();
        PLANS.clear();
    }

    @Override
    public Operator getOperator() {
        return this;
    }

    @Override
    public Install getInstall() {
        return this;
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    @Override
    public Set<OperatorRespone> getOperatorRespones() {
        return operatorImp.getOperatorRespones();
    }

    @Override
    public Set<FileDownloadExceptionListener> getFileDownloadExceptionListeners() {
        return operatorImp.getFileDownloadExceptionListeners();
    }

    @Override
    public void handlerFileDownloadException(Throwable throwable) {
        operatorImp.handlerFileDownloadException(throwable);
    }

    @Override
    public void addFileDownloadException(FileDownloadExceptionListener fileDownloadOutOfSpaceExceptionListener) {
        operatorImp.addFileDownloadException(fileDownloadOutOfSpaceExceptionListener);
    }

    @Override
    public void removeFileDownloadException(Object fileDownloadOutOfSpaceExceptionListener) {
        operatorImp.removeFileDownloadException(fileDownloadOutOfSpaceExceptionListener);
    }

    @Override
    public void addOperatorRespone(OperatorRespone operatorRespone) {
        operatorImp.addOperatorRespone(operatorRespone);
    }

    @Override
    public void removeOperatorRespone(OperatorRespone operatorRespone) {
        operatorImp.removeOperatorRespone(operatorRespone);
    }

    public static Context getContext() {
        return downLoadViewController.context;
    }

    private int getRunningPlan() {
        int i = 0;
        for (Plan plan : PLANS) {
            if (plan.isRunning()) {
                i++;
            }
        }
        return i;
    }

    /**
     * 刷新，等待的就会进行下载
     *
     * @param selfChange
     */
    @Override
    public void onChange(boolean selfChange) {
        Log.i(Entrance.TAG, "onChange: 刷新等待");
        loadInfos.clear();
        loadInfos.addAll(BusinessWrap.findStutasDownloadList2(getContext()));
        if (loadInfos.size() > 0) {
            List<DownLoadInfo> collections = new ArrayList<>(loadInfos);
            DownLoadInfo thisDownloadInfo = null;
            for (DownLoadInfo d : collections) {
                /**
                 * 优先找到当前的选择的下载
                 */
                if (d.getObjectId().equals(thisDownLoadObjectId)) {
                    thisDownloadInfo = d;
                    break;
                }
            }

            if (thisDownloadInfo == null) {
                final DownLoadInfo info = collections.get(0);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        download2(info);
                    }
                });
            } else {
                final DownLoadInfo info = thisDownloadInfo;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        download2(info);
                    }
                });

            }
        }
    }

    @Override
    public void onDownloadComplete(DownLoadInfo downLoadInfo) {
        InstallManager.getInstance().onDownloadComplete(downLoadInfo);
    }

    @Override
    public void onDownloading(DownLoadInfo downLoadInfo) {
        InstallManager.getInstance().onDownloading(downLoadInfo);
    }

    @Override
    public void onErrorDownload(DownLoadInfo downLoadInfo) {
        InstallManager.getInstance().onErrorDownload(downLoadInfo);
    }

    @Override
    public void onInstallComplete(DownLoadInfo downLoadInfo) {
        InstallManager.getInstance().onInstallComplete(downLoadInfo);
    }

    @Override
    public void onStartInstall(String path) {
        InstallManager.getInstance().onStartInstall(path);
    }

    @Override
    public void onOpenApk(String path) {
        InstallManager.getInstance().onOpenApk(path);
    }

    @Override
    public void onApkPathError(String path) {
        InstallManager.getInstance().onApkPathError(path);
    }

    @Override
    public void addInstallListener(InstallListener installListener) {
        InstallManager.getInstance().addInstallListener(installListener);
    }

    @Override
    public void removeInstallListener(InstallListener installListener) {
        InstallManager.getInstance().removeInstallListener(installListener);
    }
}
