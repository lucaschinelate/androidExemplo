package agile.core.orm.helpers;

import android.database.Cursor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;

import agile.core.orm.dictionary.Entity;
import agile.core.orm.dictionary.Field;
import agile.core.orm.dictionary.Id;

/**
 * Created by Lucas Chinelate on 15/02/2018.
 */

public class dictionaryHelper {


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
