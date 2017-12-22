package com.lin.downloadwork.business;

import com.lin.downloadwork.business.callback.InstallListener;
import com.lin.downloadwork.business.model.DownLoadInfo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by linhui on 2017/12/19.
 */
class InstallManager implements Install {

    private static InstallManager INSTALL_MAMAGER;
    static {
        INSTALL_MAMAGER = new InstallManager();
    }
    static InstallManager getInstance() {
        return INSTALL_MAMAGER;
    }
    private InstallManager() {}
    private final Set<InstallListener> installListeners = new HashSet<>();

    @Override
    public void onDownloadComplete(DownLoadInfo downLoadInfo) {
        Iterator<InstallListener> listenerIterator = convertIterator();
        while (listenerIterator.hasNext()){
            listenerIterator.next().onDownloadComplete(downLoadInfo);
        }
//        BusinessWrap.installApp(WorkController.getContext(),downLoadInfo.getSavePath());
    }

    @Override
    public void onDownloading(DownLoadInfo downLoadInfo) {
        Iterator<InstallListener> listenerIterator = convertIterator();
        while (listenerIterator.hasNext()){
            listenerIterator.next().onDownloading(downLoadInfo);
        }
    }

    @Override
    public void onErrorDownload(DownLoadInfo downLoadInfo) {
        Iterator<InstallListener> listenerIterator = convertIterator();
        while (listenerIterator.hasNext()){
            listenerIterator.next().onErrorDownload(downLoadInfo);
        }
    }

    @Override
    public void onInstallComplete(DownLoadInfo downLoadInfo) {
        Iterator<InstallListener> listenerIterator = convertIterator();
        while (listenerIterator.hasNext()){
            listenerIterator.next().onInstallComplete(downLoadInfo);
        }
    }

    @Override
    public void onStartInstall(String path) {
        Iterator<InstallListener> listenerIterator = convertIterator();
        while (listenerIterator.hasNext()){
            listenerIterator.next().onStartInstall(path);
        }
    }

    @Override
    public void onOpenApk(String path) {
        Iterator<InstallListener> listenerIterator = convertIterator();
        while (listenerIterator.hasNext()){
            listenerIterator.next().onOpenApk(path);
        }
    }

    @Override
    public void onApkPathError(String path) {
        Iterator<InstallListener> listenerIterator = convertIterator();
        while (listenerIterator.hasNext()){
            listenerIterator.next().onOpenApk(path);
        }
    }

    @Override
    public void addInstallListener(InstallListener installListener) {
        if (installListener != null) {
            installListeners.add(installListener);
        }
    }

    @Override
    public void removeInstallListener(InstallListener installListener) {
        if (installListener != null) {
            installListeners.remove(installListener);
        }
    }

    private Iterator<InstallListener> convertIterator(){
        return installListeners.iterator();
    }

}
