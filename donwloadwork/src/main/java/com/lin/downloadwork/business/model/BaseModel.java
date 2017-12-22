package com.lin.downloadwork.business.model;


import y.com.sqlitesdk.framework.annotation.TBColumn;
import y.com.sqlitesdk.framework.annotation.TBPrimarykey;
import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/12/9.
 */
public abstract class BaseModel<T> implements IModel<T> {

    @TBPrimarykey
    protected int id;
    @TBColumn(notNull = true)
    protected long create_time = System.currentTimeMillis();

    @Override
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public long getCreateTime() {
        return create_time;
    }
}
