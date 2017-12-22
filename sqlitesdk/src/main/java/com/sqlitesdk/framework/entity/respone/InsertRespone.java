package com.sqlitesdk.framework.entity.respone;

import com.sqlitesdk.framework.entity.BaseIdEntity;
import com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by lpds on 2017/4/15.
 */
public class InsertRespone<T extends IModel<T>> extends BaseIdEntity {

    protected T model;

    public InsertRespone(long id, T model) {
        super(id);
        this.model = model;
    }

    public T getModel() {
        return model;
    }

    public void setT(T t) {
        this.model = t;
    }
}
