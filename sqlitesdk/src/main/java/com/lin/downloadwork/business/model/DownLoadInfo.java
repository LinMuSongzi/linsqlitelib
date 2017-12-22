package com.lin.downloadwork.business.model;


import com.lin.downloadwork.basic.IBasicInfo;
import com.sqlitesdk.framework.annotation.TBColumn;
import com.sqlitesdk.framework.annotation.TBTable;
import com.sqlitesdk.framework.util.MD5Util;

/**
 * Created by linhui on 2017/12/7.
 */
public class DownLoadInfo extends BaseModel<DownLoadInfo> implements IBasicInfo {

    @TBTable
    public static final String TB_NAME = "tb_download_info";

    private boolean isNotitfyShowDownLoadStutas = false;

    @TBColumn(notNull = true, unique = true)
    private String object_id;
    @TBColumn(notNull = true)
    private String name;
    @TBColumn()
    private String pic_url;
    @TBColumn()
    private String download_url;
    @TBColumn(unique = true)
    private String save_path;
    @TBColumn(notNull = true)
    private int status = NOT_HAD_STATUS;
    @TBColumn()
    private long toTal;
    @TBColumn()
    private long current;
    @TBColumn(notNull = true)
    private int donwloadType = APK_TYPE;

    //    gameId
    public int getDonwloadType() {
        return donwloadType;
    }

    public void setDonwloadType(int donwloadType) {
        this.donwloadType = donwloadType;
    }

    @Override
    public void setDownLoadId(String id) {
        this.id = Integer.parseInt(id);
    }

    @Override
    public String getDownLoadId() {
        return String.valueOf(id);
    }

    @Override
    public boolean isNotitfyShowDownLoadStutas() {
        return isNotitfyShowDownLoadStutas;
    }

    @Override
    public String getObjectId() {
        return object_id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPicUrl() {
        return pic_url;
    }

    @Override
    public String getDownLoadUrl() {
        return download_url;
    }

    @Override
    public String getSavePath() {
        return save_path;
    }

    @Override
    public long getToTalLeng() {
        return toTal;
    }

    @Override
    public long getCurrentLeng() {
        return current;
    }

    @Override
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setToTal(long toTal) {
        this.toTal = toTal;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public void setNotitfyShowDownLoadStutas(boolean notitfyShowDownLoadStutas) {
        isNotitfyShowDownLoadStutas = notitfyShowDownLoadStutas;
    }

    public void setCreateTime(long create_time) {
        this.create_time = create_time;
    }

    public void setObjectId(String object_id) {
        this.object_id = MD5Util.convert(object_id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicUrl(String pic_url) {
        this.pic_url = pic_url;
    }

    public void setDownloadUrl(String download_url) {
        this.download_url = download_url;
    }

    public void setSavePath(String save_path) {
        this.save_path = save_path;
    }

    @Override
    public String toString() {
        return "DownLoadTable{" +
                "isNotitfyShowDownLoadStutas=" + isNotitfyShowDownLoadStutas +
                ", object_id='" + object_id + '\'' +
                ", name='" + name + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", download_url='" + download_url + '\'' +
                ", save_path='" + save_path + '\'' +
                ", status=" + status +
                ", toTal=" + toTal +
                ", current=" + current + '\'' +
                '}';
    }

    public static String externalObject(String object_id) {

        return MD5Util.convert(object_id);
    }

}
