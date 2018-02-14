package agile.core.orm.connector;

import android.database.Cursor;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public interface Connector {

    public Cursor rawQuery(String SQL);
    public boolean execSQL(String SQL);
    public boolean beginTransaction();
    public boolean rollback();
    public boolean commit();
    public String getConnectionString();

}
