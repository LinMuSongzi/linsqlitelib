package com.lin.downloadwork.business.event;

/**
 * Created by linhui on 2017/12/14.
 */
public class InsertEvent {

    public InsertEvent(String objectId, boolean isSuccess) {
        this.objectId = objectId;
        this.isSuccess = isSuccess;
    }

    public String objectId = "";

    public boolean isSuccess = false;

}
