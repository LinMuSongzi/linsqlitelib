package com.sqlitesdk.framework.entity.respone;


import com.sqlitesdk.framework.entity.BaseEntity;

/**
 * Created by lpds on 2017/4/17.
 */
public class InsertListRespone extends BaseEntity {

    boolean isiInsers;

    public InsertListRespone(boolean isiInsers) {
        this.isiInsers = isiInsers;
    }

    public boolean isiInsers() {
        return isiInsers;
    }

    public InsertListRespone setIsiInsers(boolean isiInsers) {
        this.isiInsers = isiInsers;
        return this;
    }
}
