package agile.core.orm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import agile.core.orm.connector.Connector;
import android.database.Cursor;

import agile.core.orm.dictionary.*;
import agile.core.orm.dictionary.Entity;
import agile.core.orm.helpers.*;


/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class DataBase {
    private Connector connector;
    private String SQL;
    private HashSet<Entity> entities;

    private dictionaryHelper dictionary;

    public void persist (Object entity) throws Exception {
        SQL = "";

        dictionary = new dictionaryHelper();

        Entity iEntity = dictionary.extractEntity(entity);

        if (!entities.contains(iEntity)) {
            entities.add(iEntity);
        } else {
            entities.remove(iEntity);
            entities.add(iEntity);
        }
    }

    public void delete (Object entity) {
        SQL = "";
        Entity iEntity = dictionary.extractEntity(entity);
    }

    public DataBase(Connector connector) {
        entities = new HashSet<>();

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

    public boolean flush () throws Exception {
        try {

        } catch (Exception ex) {
            return false;
        }
        throw new Exception(entities.size() + " Records");
        //return true;
    }
}
