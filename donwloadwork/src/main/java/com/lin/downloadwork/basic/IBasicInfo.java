package com.lin.downloadwork.basic;

import com.lin.downloadwork.business.model.UrlInfo;

/**
 * Created by linhui on 2017/12/7.
 */
public interface IBasicInfo extends UrlInfo {

    int WAITTING_STATUS = 150;
    int PAUSE_STATUS = 100;
    int COMPLETED_STATUS = 200;
    int DOING_STATUS = 201;
    int ERROR_STATUS = 402;
    int NOT_HAD_STATUS = 400;

    int APK_TYPE = 1000;
    int APK_FEIMO_TYPE = 2000;
    int PHOTO_TYPE = 1001;
    int MUSIC_TYPE = 1050;
    int VIDEO_TYPE = 1100;
    int SIMPLE_TYPE = 100;

    void setDownLoadId(String id);

    String getDownLoadId();

    boolean isNotitfyShowDownLoadStutas();

    String getObjectId();

    String getName();

    String getPicUrl();

    String getSavePath();

    long getToTalLeng();

    long getCurrentLeng();

    int getStatus();


}
