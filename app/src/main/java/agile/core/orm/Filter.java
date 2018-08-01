package agile.core.orm;

import android.database.Cursor;

import java.util.HashSet;

import agile.core.orm.connector.Connector;
import agile.core.orm.dictionary.Entity;
import agile.core.orm.dictionary.Field;
import agile.core.orm.helpers.dictionaryHelper;


/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class Filter {

    public String fieldName;

    public String value;

    public Filter setFieldName(String value) {
        this.fieldName = value;
        return this;
    }

    public Filter setValue(String value) {
        this.value = value;
        return this;
    }
}
