package agile.core.orm.helpers;

import android.database.Cursor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import agile.core.orm.DataBase;
import agile.core.orm.dictionary.Entity;
import agile.core.orm.dictionary.Field;
import agile.core.orm.dictionary.Id;
import agile.core.orm.valuesExtrator.ValuesExtrator;

/**
 * Created by Lucas Chinelate on 15/02/2018.
 */

public class dictionaryHelper {

    private ValuesExtrator valuesExtrator;

    public dictionaryHelper(ValuesExtrator valuesExtrator) {
        this.valuesExtrator = valuesExtrator;
    }

    public <T> T[] populate( Class<T> entity, Cursor cursor) throws Exception {

        Annotation annotation = null;
        cursor.moveToFirst();

        T[] arrayResult = (T[]) new Object[cursor.getCount()];

        String[] str = null;
        str = new String[10];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            T objEntity =  entity.newInstance();

            for (java.lang.reflect.Field att: entity.getDeclaredFields()) {
                annotation = att.getAnnotation(agile.core.orm.annotation.Field.class);
                if (annotation != null) {
                    agile.core.orm.annotation.Field fieldAnnotation = (agile.core.orm.annotation.Field) annotation;
                    att.setAccessible(true);

                    if (att.getType().equals(Integer.class)) {
                        att.set(objEntity, cursor.getInt(cursor.getColumnIndex(fieldAnnotation.fieldName().toString())));
                    } else {
                        att.set(objEntity, cursor.getString(cursor.getColumnIndex(fieldAnnotation.fieldName().toString())));
                    }
                }
            }

            arrayResult[i] = objEntity;
        }

        return arrayResult;
    }

    public Entity getStructure (Class object) {
        return this.translate(null, object , false);
    }

    public Entity extractEntity (Object object) {
        return this.translate(object, object.getClass(),true);
    }

    public String getSQL (Entity iEntity) {
        String _sql = "";

        if (iEntity.databaseAction == DataBase.INSERT_ACTION) {
            String _sqlFields = "";
            String _sqlValues = "";

            for (Field field: iEntity.Fields) {
                String _value = valuesExtrator.getStringFromValue(field.Value, field.Type);

                if (!(_sqlFields.equals(""))) _sqlFields = _sqlFields + ", ";
                _sqlFields = _sqlFields + field.Name;

                if (!(_sqlValues.equals(""))) _sqlValues = _sqlValues + ", ";
                _sqlValues = _sqlValues + _value;
            }

            _sql = "INSERT INTO " + iEntity.TableName + "(" + _sqlFields + ") VALUES (" + _sqlValues + ")";

        } else if (iEntity.databaseAction == DataBase.UPDATE_ACTION) {
            String _sqlFields = "";
            String _sqlId = "";

            for (Field field: iEntity.Fields) {
                String _value = valuesExtrator.getStringFromValue(field.Value, field.Type);

                if (!(_sqlFields.equals(""))) _sqlFields = _sqlFields + ", ";
                _sqlFields = _sqlFields + field.Name + " = " + _value;
            }

            for (Id id: iEntity.Ids) {
                String _value = valuesExtrator.getStringFromValue(id.Value, id.Type);

                if (!(_sqlId.equals(""))) _sqlId = _sqlId + " AND ";
                _sqlId = id.Name + " = " + _value;
            }

            _sql = "UPDATE " + iEntity.TableName + " SET " + _sqlFields + " WHERE " + _sqlId;

        } else if (iEntity.databaseAction == DataBase.DELETE_ACTION) {

            String _sqlId = "";
            for (Id id: iEntity.Ids) {
                String _value = valuesExtrator.getStringFromValue(id.Value, id.Type);

                if (!(_sqlId.equals(""))) _sqlId = _sqlId + " AND ";
                _sqlId = id.Name + " = " + _value;
            }

            _sql = "DELETE FROM " + iEntity.TableName + " WHERE " + _sqlId;
        }

        return _sql;
    }

    private Entity translate (Object object, Class clss, boolean values) {
        Entity entity = new Entity();
        Annotation annotation = null;

        annotation = clss.getAnnotation(agile.core.orm.annotation.Entity.class);

        if (annotation == null) {
            /*
             * @Todo para disparar uma expection:, não é um objeto do tipo Entity
             */
        }

        agile.core.orm.annotation.Entity entityAnnotation = (agile.core.orm.annotation.Entity) annotation;

        entity.TableName = entityAnnotation.tableName();
        entity.Fields = new ArrayList<Field>();
        entity.Ids = new ArrayList<Id>();

        for (java.lang.reflect.Field att: clss.getDeclaredFields()) {

            annotation = att.getAnnotation(agile.core.orm.annotation.Field.class);
            if (annotation != null) {
                agile.core.orm.annotation.Field fieldAnnotation = (agile.core.orm.annotation.Field) annotation;
                att.setAccessible(true);

                /*
                 * Recupera os annotations IDs da entidade
                 */
                annotation = att.getAnnotation(agile.core.orm.annotation.Id.class);
                if (annotation != null) {
                    try {
                        Id id = new Id();
                        id.Name = fieldAnnotation.fieldName();
                        id.Type = att.getType();

                        if (values == true) {
                            id.Value = att.get(object);
                        }

                        entity.Ids.add(id);
                    } catch (IllegalAccessException e) {
                    /*
                     * @ToDo Disparar expection generica
                     */
                    }
                }

                /*
                 * Recupera o valor dos campos
                 */
                try {
                    Field field = new Field();
                    field.Name = fieldAnnotation.fieldName();
                    field.Type = att.getType();

                    if (values == true) {
                        field.Value = att.get(object);
                    }

                    entity.Fields.add(field);
                } catch (IllegalAccessException e) {
                    /*
                     * @ToDo Disparar expection generica
                     */
                }
            }
        }

        return entity;
    }

}
