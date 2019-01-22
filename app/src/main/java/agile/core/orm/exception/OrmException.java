package agile.core.orm.exception;

import agile.core.orm.dictionary.Entity;
import agile.core.system.Session;

public class OrmException extends java.lang.Exception{

    private int constant;
    private Object complement;
    private DataException data;

    public OrmException(int constant) {
        this(constant,null);
    }

    public OrmException(int constant, Object complement) {
        this.constant = constant;
        this.complement = complement;

        Cathalog cathalog = Cathalog.getInstance();
        data = cathalog.findExceptionData(this.constant);
    }

    public int getNumber() {
        return this.constant;
    }

    @Override
    public String toString() {
        String message = "";
        if (data == null) {
            message =  "Exception not found";
        } else {
            message = data.toString();
        }

        String entityString = "";
        if (complement != null) {
            entityString = " - " + complement.toString();
        }

        message = "Code: " + String.valueOf(this.constant) + ", Message: " + message;
        return message + entityString;
    }

    @Override
    public String getMessage(){
        if (data == null) {
            return "Exception not found";
        }
        return data.getMessage();
    }

}
