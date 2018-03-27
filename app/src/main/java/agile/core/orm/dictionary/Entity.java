package agile.core.orm.dictionary;

import java.util.List;

/**
 * Created by Lucas Chinelate on 15/02/2018.
 */

public class Entity {

    public String TableName;

    public List<Field> Fields;

    public List<Id> Ids;

    public String databaseAction;

    @Override
    public boolean equals(Object o) {
        return true;
    }
}
