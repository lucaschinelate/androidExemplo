package agile.core.orm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import agile.core.orm.connector.Connector;
import android.database.Cursor;

import agile.core.orm.dictionary.*;
import agile.core.orm.dictionary.Entity;
import agile.core.orm.helpers.*;
import agile.core.orm.valuesExtrator.SQLiteValues;
import agile.core.orm.valuesExtrator.ValuesExtrator;


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
    private List<String> flushDML;

    private dictionaryHelper dictionary;

    public Object persist (Object entity) throws Exception {
        SQL = "";

        Entity iEntity = dictionary.extractEntity(entity);

        if (!entities.contains(iEntity)) {
            iEntity.databaseAction = INSERT_ACTION;
            entities.add(iEntity);
        } else {

            Entity oldEntity = this.findElementInCollection(iEntity);

            if (oldEntity.databaseAction == DELETE_ACTION) {
                throw new Exception("Attempt to change entity marked for deletion - " + iEntity.toString());
            }

            iEntity.databaseAction = UPDATE_ACTION;

            entities.remove(oldEntity);
            entities.add(iEntity);
        }

        SQL = dictionary.getSQL(iEntity);
        flushDML.add(SQL);

        return entity;
    }

    public void delete (Object entity) throws Exception {
        SQL = "";
        Entity iEntity = dictionary.extractEntity(entity);

        if (!entities.contains(iEntity)) {
            throw new Exception("Entity not found for delete");
        }

        Entity oldEntity = this.findElementInCollection(iEntity);

        entities.remove(oldEntity);

        iEntity.databaseAction = DELETE_ACTION ;

        entities.add(iEntity);

        SQL = dictionary.getSQL(iEntity);
        flushDML.add(SQL);
    }

    public DataBase(Connector connector, ValuesExtrator valuesExtrator) {
        entities = new HashSet<>();
        flushDML = new ArrayList<String>();
        dictionary = new dictionaryHelper(valuesExtrator);
        this.connector = connector;
    }

    public DataBase(Connector connector) {
        this(connector, new SQLiteValues());
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
            for (String query: flushDML) {
                this.execSQL(query);
            }
            flushDML.clear();

            ArrayList<Entity> deletedEntity = new ArrayList<Entity>();

            for(Entity iEntity: entities) {
                if (iEntity.databaseAction == DELETE_ACTION) {
                    deletedEntity.add(iEntity);
                } else if (iEntity.databaseAction == INSERT_ACTION) {
                    iEntity.databaseAction = UPDATE_ACTION;
                }
            }

            for (Entity iEntity: deletedEntity) {
                entities.remove(iEntity);
            }

        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean clear () {
        entities.clear();
        return true;
    }

    private Entity findElementInCollection (Entity compareElement) {
        for (Entity iEntity: entities ) {
            if (iEntity.equals(compareElement)) {
                return iEntity;
            }
        }
        return null;
    }

    private String getQuery (Class entity, Filter[] filters) {
        Entity iEntity = dictionary.getStructure(entity);

        String query = "SELECT ";
        for   (Field field: iEntity.Fields) {
            if (query.equals("SELECT ")) {
                query += field.Name;
            } else {
                query += ", " + field.Name;
            }
        }

        query += " FROM " + iEntity.TableName + " WHERE";

        boolean findFilter;
        for (Filter filter: filters) {
            findFilter = false;
            for   (Field field: iEntity.Fields) {
                if (field.Name.equals(filter.fieldName)) {
                    findFilter = true;
                    if (field.Type.equals(String.class)) {
                        query += " " + field.Name + " = '" + filter.value + "'";
                    } else {
                        query += " " + field.Name + " = " + filter.value;
                    }
                    break;
                }
            }
            if (findFilter == false) {
                /* @ToDo Disparar expection generica */
            }
        }

        return query;
    }

    public Object[] findBy (Class entity, Filter[] filters) throws Exception {
        String query = this.getQuery(entity, filters);

        Cursor curValues = this.rawQuery(query);

        if (curValues.getCount() == 0)
            return null;

        Object[] result = dictionary.populate(entity, curValues );

        for(Object objEntity: result) {
            Entity iEntity = dictionary.extractEntity(objEntity);
            if (!entities.contains(iEntity)) {
                iEntity.databaseAction = UPDATE_ACTION;
                entities.add(iEntity);
            }
        }

        return result;
    }

    public Object findOneBy (Class entity, Filter[] filters) throws Exception {

        Object[] result = this.findBy(entity, filters);

        if (result == null) return null;

        return result[0];

    }

    public Object findOneBy (Class entity, Filter filter) throws Exception {

        Filter[] filters = {filter};

        return this.findOneBy(entity, filters);

    }

    public Object findOneBy (Class entity, Object value) throws Exception {

        Entity iEntity = dictionary.getStructure(entity);

        if (iEntity.Ids.size() == 0) {
            /* @ToDo Disparar expection "A entidade não tem nenhum ID definido" */
        }

        if (iEntity.Ids.size() > 1 ) {
            /* @ToDo Disparar expection "A entidade possui chave composta" */
        }

        Id id = iEntity.Ids.get(0);
        Filter filter = new Filter().setFieldName(id.Name).setValue(value.toString());

        return this.findOneBy(entity, filter);

    }

}
