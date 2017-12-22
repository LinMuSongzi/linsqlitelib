package y.com.sqlitesdk.framework.business.support;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import y.com.sqlitesdk.framework.db.Access;
import y.com.sqlitesdk.framework.entity.respone.ModifyRespone;
import y.com.sqlitesdk.framework.interface_model.IModel;
import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/8/25.
 */
public abstract class Regime<T extends IModel<T>> implements Plant,Execute {
    protected T model;
    protected String action;
    protected String tableName;
    protected String sql;
    protected Map<String,String> whereMap;

    private Regime(){}

    protected Regime(T t){
        model = t;
//        tableName = t.getTableName();
    }

    public static final <T extends IModel<T>> Plant insert(T t){
        return new InsertRegime<T>(t);
    }

    public static final <T extends IModel<T>> Plant delete(T t){

        return new DeleteRegime<T>(t);

    }

    public static final <T extends IModel<T>> Plant modify(T t){

        return new ModifyRegime<T>(t);

    }


    public static final <T extends Class<T>> Plant query() {

        return null;
    }


    @Override
    public final void execute(){
        Access.run(this);
    }


    @Override
    public void onExternalError() {

    }
}
