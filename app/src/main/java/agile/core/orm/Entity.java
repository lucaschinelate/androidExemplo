package agile.core.orm;

import java.util.ArrayList;
import java.util.List;
import agile.core.orm.EntityField;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class Entity {
    private String _tableName;
    private List<EntityField> fields;
    private List<Object> valueList;

    public Entity() {
        valueList = new ArrayList<Object>();
        fields = new ArrayList<EntityField>();
    }

    protected void setTableName(String Value) {
        this._tableName = Value;
    }

    protected void addField (String Field, String Member) {
        this.addField(Field, Member,"");
    }

    protected void addField (String Field, String Member, String DefaultValue) {
        EntityField field = new EntityField();
        field.setField(Field);
        field.setMember(Member);
        field.setDefaultValue(DefaultValue);
        field.setValueListIndex(valueList.size());
        this.fields.add(field);
        valueList.add(DefaultValue);

    }

    protected Object getFieldValue(String Field) throws Exception {
        for (EntityField field :fields) {
            if (field.getField().equalsIgnoreCase(Field)) {
                return valueList.get(field.getValueListIndex());
            }
        }
        throw new Exception("Field " + Field + " not found in referenced entity " + _tableName);
    }

    protected void setFieldValue(String Field, Object Value) throws Exception {
        for (EntityField field :fields) {
            if (field.getField().equalsIgnoreCase(Field)) {
                valueList.set(field.getValueListIndex(), Value);
                return;
            }
        }
        throw new Exception("Field " + Field + " not found in referenced entity " + _tableName);
    }

    public List<EntityField> getEntityFields() {
        return this.fields;
    }

    public List<Object> getValueList() {
        return this.valueList;
    }

}
