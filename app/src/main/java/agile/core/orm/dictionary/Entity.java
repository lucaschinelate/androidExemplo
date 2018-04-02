package agile.core.orm.dictionary;

import java.util.List;

/**
 * Created by Lucas Chinelate on 15/02/2018.
 */

public class Entity {

    public String TableName;

    public List<Field> Fields;

    public List<Id> Ids;

    public int databaseAction;

    @Override
    public String toString() {
        String _name = "Entity {" +
                       " TableName='" + TableName + '\'' +
                       ",   Fields= {";

        for (Field _field: Fields) {
            _name += "{Name='" + _field.Name +"', Value='" + _field.Value + "'}";
            if (!_field.equals(Fields.get(Fields.size()-1))) {
                _name+=",";
            }
        }

        _name += " }, databaseAction='" + databaseAction + '\'' + '}';

        return _name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (TableName.equals(entity.TableName)) {

            for (Id idElementi:((Entity) o).Ids) {
                if (idElementi.Value == null) {
                    return false;
                }
                for (Id idElementz: this.Ids) {

                    if (idElementz.Value == null) {
                        return false;
                    }

                    if ((idElementi.Name.equals(idElementz.Name)) && (!idElementi.Value.equals(idElementz.Value))) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;

    }

    @Override
    public int hashCode() {
        return TableName.hashCode();
    }

}
