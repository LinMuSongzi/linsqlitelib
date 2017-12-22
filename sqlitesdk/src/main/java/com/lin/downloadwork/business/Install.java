package com.lin.downloadwork.business;


import com.lin.downloadwork.business.callback.InstallListener;

/**
 * Created by linhui on 2017/12/19.
 */
public interface Install extends InstallListener {



    void addInstallListener(InstallListener installListener);

    void removeInstallListener(InstallListener installListener);

}
