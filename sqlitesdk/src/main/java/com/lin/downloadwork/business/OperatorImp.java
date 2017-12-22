package com.lin.downloadwork.business;


import com.lin.downloadwork.business.callback.FileDownloadExceptionListener;
import com.lin.downloadwork.business.callback.OperatorRespone;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by linhui on 2017/12/18.
 */
final class OperatorImp implements Operator {

    private final Set<OperatorRespone> operatorRespones = new HashSet<>();
    private final Set<FileDownloadExceptionListener> fileDownloadOutOfSpaceExceptionListeners = new HashSet<>();

    @Deprecated
    private final Object LOCK = new Object();


    @Override
    public void addFileDownloadException(FileDownloadExceptionListener fileDownloadOutOfSpaceExceptionListener) {
        if (fileDownloadOutOfSpaceExceptionListener != null) {
            fileDownloadOutOfSpaceExceptionListeners.add(fileDownloadOutOfSpaceExceptionListener);
        }
    }

    @Override
    public void removeFileDownloadException(Object fileDownloadOutOfSpaceExceptionListener) {
        if (fileDownloadOutOfSpaceExceptionListener != null) {
            fileDownloadOutOfSpaceExceptionListeners.remove(fileDownloadOutOfSpaceExceptionListener);
        }
    }

    @Override
    public void addOperatorRespone(OperatorRespone operatorRespone) {
        if (operatorRespone != null) {
            operatorRespones.add(operatorRespone);
        }
    }

    @Override
    public void removeOperatorRespone(OperatorRespone operatorRespone) {
        if (operatorRespone != null) {
            operatorRespones.remove(operatorRespone);
        }
    }

    @Override
    public Set<OperatorRespone> getOperatorRespones() {
        return operatorRespones;
    }

    @Override
    public Set<FileDownloadExceptionListener> getFileDownloadExceptionListeners() {
        return fileDownloadOutOfSpaceExceptionListeners;
    }

    @Override
    public void handlerFileDownloadException(Throwable throwable) {
        Iterator<FileDownloadExceptionListener> iterator
                = fileDownloadOutOfSpaceExceptionListeners.iterator();
        while(iterator.hasNext()){
            iterator.next().onException(throwable);
        }
    }

}
