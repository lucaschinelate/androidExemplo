package agile.core.orm.helpers;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import agile.core.orm.dictionary.Entity;
import agile.core.orm.dictionary.Field;
import agile.core.orm.dictionary.Id;

/**
 * Created by Lucas Chinelate on 15/02/2018.
 */

public class dictionaryHelper {

    public Entity extractEntity (Object object) {
        Entity entity = new Entity();

        Annotation annotation = object.getClass().getAnnotation(agile.core.orm.annotation.Entity.class);
        if (annotation == null) {
            /*
             * @Todo para disparar uma expection:, não é um objeto do tipo Entity
             */
        }

        agile.core.orm.annotation.Entity entityAnnotation = (agile.core.orm.annotation.Entity) annotation;

        entity.TableName = entityAnnotation.tableName();
        entity.Fields = new ArrayList<Field>();
        entity.Ids = new ArrayList<Id>();

        for (java.lang.reflect.Field att: object.getClass().getDeclaredFields()) {

            if (att.isAnnotationPresent()) {

            }

            annotation = att.getAnnotation(agile.core.orm.annotation.Field.class);
            if (annotation != null) {
                agile.core.orm.annotation.Field fieldAnnotation = (agile.core.orm.annotation.Field) annotation;
                att.setAccessible(true);

                annotation = att.getAnnotation(agile.core.orm.annotation.Id.class);
                if (annotation != null) {
                    try {
                        Id id = new Id();
                        id.Name = fieldAnnotation.fieldName();
                        id.Value = att.get(object);
                        id.Type = att.getType();
                        entity.Ids.add(id);
                    } catch (IllegalAccessException e) {
                    /*
                     * @ToDo Disparar expection generica
                     */
                    }
                }

                try {
                    Field field = new Field();
                    field.Name = fieldAnnotation.fieldName();
                    field.Value = att.get(object);
                    field.Type = att.getType();
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
