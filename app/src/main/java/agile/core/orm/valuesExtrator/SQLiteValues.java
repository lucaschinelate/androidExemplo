package agile.core.orm.valuesExtrator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import agile.core.orm.connector.Connector;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class SQLiteValues implements ValuesExtrator {


    @Override
    public String getStringFromValue(Object value, Object Type) {
        String result;
        String tipo = ((Class) Type).getName();

        if (tipo == "java.lang.Integer") {
            result = value.toString();
        } else if (tipo == "java.lang.Double") {
            result = value.toString();
        } else if (tipo == "java.lang.Boolean") {
            result = value.toString();
        } else if (tipo == "java.lang.String") {
            result = "'" + value.toString() + "'";
        } else {
            result = value.toString();
        }

        return result;
    }
}
