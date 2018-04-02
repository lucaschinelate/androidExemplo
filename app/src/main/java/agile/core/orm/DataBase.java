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

    public static final int INSERT_ACTION = 0;
    public static final int UPDATE_ACTION = 1;
    public static final int DELETE_ACTION = 2;

    private Connector connector;
    private String SQL;
    private HashSet<Entity> entities;

    private dictionaryHelper dictionary;

    public void persist (Object entity) throws Exception {
        SQL = "";

        dictionary = new dictionaryHelper();

        Entity iEntity = dictionary.extractEntity(entity);

        if (!entities.contains(iEntity)) {
            iEntity.databaseAction = INSERT_ACTION;
            entities.add(iEntity);
        } else {

            Entity oldEntity = this.findElementInCollection(iEntity);

            if (oldEntity.databaseAction == DELETE_ACTION) {
                throw new Exception("Attempt to change entity marked for deletion - " + iEntity.toString());
            }

            iEntity.databaseAction = oldEntity.databaseAction;

            entities.remove(oldEntity);
            entities.add(iEntity);
        }
    }

    public void delete (Object entity) throws Exception {
        SQL = "";
        Entity iEntity = dictionary.extractEntity(entity);

        if (!entities.contains(iEntity)) {
            throw new Exception("Entity not found for delete");
        }

        Entity oldEntity = this.findElementInCollection(iEntity);

        entities.remove(oldEntity);

        if (oldEntity.databaseAction != INSERT_ACTION) {
            iEntity.databaseAction = DELETE_ACTION ;
            entities.add(iEntity);
        }

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

    private Entity findElementInCollection (Entity compareElement) {
        for (Entity iEntity: entities ) {
            if (iEntity.equals(compareElement)) {
                return iEntity;
            }
        }
        return null;
    }

}
