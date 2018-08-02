package agile.core.orm.connector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class SQLiteConnector implements Connector {
    private String _dataBaseName;
    static SQLiteDatabase db;

    @Override
    public Cursor rawQuery(String SQL) {
        Cursor result = db.rawQuery(SQL, null);
        return result;
    }

    @Override
    public boolean execSQL(String SQL) {
        return false;
    }

    @Override
    public boolean beginTransaction() {
        db.beginTransaction();
        return true;
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
        return _dataBaseName;
    }

    public SQLiteConnector(Context context, Integer resource) {
        _dataBaseName = context.getResources().getString(resource);
        db = SQLiteDatabase.openDatabase(_dataBaseName, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
