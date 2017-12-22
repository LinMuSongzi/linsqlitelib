package com.lin.downloadwork.business.callback;


import com.lin.downloadwork.business.IHierarchy;

/**
 * Created by linhui on 2017/12/18.
 */
public interface FileDownloadExceptionListener extends IHierarchy {


    void onException(Throwable ex);

}
