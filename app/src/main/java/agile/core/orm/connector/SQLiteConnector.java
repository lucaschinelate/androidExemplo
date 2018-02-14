package agile.core.orm.connector;

import android.database.Cursor;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class SQLiteConnector implements Connector {

    @Override
    public Cursor rawQuery(String SQL) {
        return null;
    }

    @Override
    public boolean execSQL(String SQL) {
        return false;
    }

    @Override
    public boolean beginTransaction() {
        return false;
    }

    @Override
    public boolean rollback() {
        return false;
    }

    @Override
    public boolean commit() {
        return false;
    }

    @Override
    public String getConnectionString() {
        return null;
    }

}
