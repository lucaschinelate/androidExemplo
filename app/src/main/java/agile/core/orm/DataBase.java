package agile.core.orm;

import java.util.ArrayList;
import java.util.List;
import agile.core.orm.connector.Connector;
import android.database.Cursor;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class DataBase {
    private Connector connector;
    private List<String> DDAList; //DIRECT DATABASE ACCESSS LIST
    private String SQL;

    public void persist (Entity entity) {
        for (EntityField field: entity.getEntityFields()){
            SQL = field.getField();
        }
        DDAList.add(SQL);
    }

    public void delete (Entity entity) {
        for (EntityField field: entity.getEntityFields()){
            SQL = field.getField();
        }
        DDAList.add(SQL);
    }

    public DataBase(Connector connector) {
        DDAList = new ArrayList<String>();
        this.connector = connector;
    }

    public Connector getConnector() {
        return this.connector;
    }

    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    public boolean execSQL(String SQL) {
        return this.connector.execSQL(SQL);
    }

    public Cursor rawQuery(String SQL) {
        return this.connector.rawQuery(SQL);
    }

    public void beginTransaction () {
        this.connector.beginTransaction();
    }

    public void rollback() {
        this.connector.rollback();
    }

    public void commit (){
        this.connector.commit();
    }

    public boolean flush () {
        try {
            for (String command: DDAList) {
                this.connector.execSQL(command);
            }
            DDAList.clear();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
