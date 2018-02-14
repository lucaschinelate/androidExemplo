package agile.core.orm;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class EntityField {
    private String _Field;
    private String _Member;
    private String _DefaultValue;
    private int    _valueListIndex;

    public EntityField (){
        this._Field = "";
        this._Member = "";
        this._DefaultValue = null;
        this._valueListIndex = -1;
    }

    public String getField() {
        return _Field;
    }

    public String getMember() {
        return _Member;
    }

    public String getDefaultValue() {
        return _DefaultValue;
    }

    public int getValueListIndex() {
        return _valueListIndex;
    }

    public void setField(String Value) {
        this._Field = Value;
    }

    public void setMember(String Value) {
        this._Member = Value;
    }

    public void setDefaultValue (String Value) {
        this._DefaultValue = Value;
    }

    public void setValueListIndex (int Value) {
        this._valueListIndex = Value;
    }

}
