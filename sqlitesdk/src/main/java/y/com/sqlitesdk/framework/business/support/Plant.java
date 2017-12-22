package y.com.sqlitesdk.framework.business.support;

import y.com.sqlitesdk.framework.sqliteinterface.Execute;

/**
 * Created by linhui on 2017/8/25.
 */
public interface Plant{
    int MODIFY = 0x99a;
    int INSTER = 0x99b;
    int DELETE = 0x99c;
    int SELECT = 0x99d;

    void execute();

    String buildSql();


}
