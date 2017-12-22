package com.lin.downloadwork.business;


import com.lin.downloadwork.business.callback.FileDownloadExceptionListener;
import com.lin.downloadwork.business.callback.OperatorRespone;

import java.util.Set;

/**
 * Created by linhui on 2017/12/11.
 */
public interface Operator {

    void addFileDownloadException(FileDownloadExceptionListener fileDownloadOutOfSpaceExceptionListener);

    void removeFileDownloadException(Object fileDownloadOutOfSpaceExceptionListener);

    void addOperatorRespone(OperatorRespone operatorRespone);

    void removeOperatorRespone(OperatorRespone operatorRespone);

    Set<OperatorRespone> getOperatorRespones();

    Set<FileDownloadExceptionListener> getFileDownloadExceptionListeners();

    /**
     *
     * swich(throwable){
     *     case FileDownloadOutOfSpaceException 空间不足
     *          break;
     *     case UnknownHostException 无网络或者硬件损坏
     *          break
     * }
     * @param throwable
     */
    void handlerFileDownloadException(Throwable throwable);


}
