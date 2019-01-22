package agile.core.orm.exception;

public class DataException {

    private int constant;
    private String message;

    public void setConstant(int value) {
        this.constant = value;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public int getConstant () {
        return this.constant;
    }

    public String getMessage() {
        return this.message;
    }

    public DataException(int constant, String message) {
        this.constant = constant;
        this.message = message;
    }

    @Override
    public String toString() {
        //return this.getMessage();
        return "Code: " + String.valueOf(this.getConstant()) + ", Message: " + this.getMessage();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;


        if (o instanceof Integer) {
            if (this.getConstant() == (int) o) return true;
        } else {
            DataException compare = (DataException) o;

            if (this.getConstant() == compare.getConstant()) return true;
        }


        return false;
    }

        @Override
    public int hashCode() {
        return this.getConstant();
    }

}
