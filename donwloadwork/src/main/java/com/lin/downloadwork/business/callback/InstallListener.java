package com.lin.downloadwork.business.callback;

import com.lin.downloadwork.business.IHierarchy;
import com.lin.downloadwork.business.model.DownLoadInfo;

/**
 * Created by linhui on 2017/12/19.
 */
public interface InstallListener extends IHierarchy {

    /**
     * 下载完毕 13
     * @param downLoadInfo
     */
    void onDownloadComplete(DownLoadInfo downLoadInfo);

    /**
     * 正在下载 12
     * @param downLoadInfo
     */
    void onDownloading(DownLoadInfo downLoadInfo);

    /**
     * 正在下载 14
     * @param downLoadInfo
     */
    void onErrorDownload(DownLoadInfo downLoadInfo);

    /**
     * 安装完毕
     * @param downLoadInfo
     */
    void onInstallComplete(DownLoadInfo downLoadInfo);

    /**
     * 启动安装 15
     * @param path
     */
    void onStartInstall(String path);

    /**
     * 打开目标文件 16
     * @param path
     */
    void onOpenApk(String path);

    void onApkPathError(String path);

}
