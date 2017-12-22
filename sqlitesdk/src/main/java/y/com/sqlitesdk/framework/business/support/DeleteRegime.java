package y.com.sqlitesdk.framework.business.support;

import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import y.com.sqlitesdk.framework.interface_model.IModel;

/**
 * Created by linhui on 2017/9/2.
 */
public class DeleteRegime<T extends IModel<T>> extends Regime<T> {

    private Map<String, String> whereMap = new HashMap<>();

    protected DeleteRegime(T t) {
        super(t);
    }

    @Override
    @Deprecated
    public String buildSql() {
        return null;
    }


    public DeleteRegime useId() {
        return where(new String[]{"id"}, new Object[]{super.model.getId()});
    }


    public DeleteRegime where(String[] keys, Object[] values) {

        if (keys.length != values.length) {
            return this;
        }
        for (int i = 0; i < keys.length; i++) {
            if (values[i] instanceof String) {
                whereMap.put(keys[i], String.format("'%s'", values[i]));
            } else {
                whereMap.put(keys[i], values[i].toString());
            }
        }

        return this;
    }


    @Override
    public void onExecute(SQLiteDatabase sqLiteDatabase) throws Exception {
        if (whereMap.size() > 0) {
            Collection<String> collection = whereMap.values();

            String whereClauss = "-1";

            for (String key : whereMap.keySet()) {
                if (whereClauss.equals("-1")) {
                    whereClauss = "";
                } else {
                    whereClauss += " AND";
                }
                whereClauss += String.format(" %s = ?", key);
            }
            sqLiteDatabase.delete(super.tableName, whereClauss, collection.toArray(new String[collection.size()]));
        } else {
            sqLiteDatabase.delete(super.tableName, null, null);
        }

    }
}
