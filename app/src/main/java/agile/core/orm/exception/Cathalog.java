package agile.core.orm.exception;

import java.util.HashSet;

public class Cathalog {

    private static HashSet<DataException> data;
    private static Cathalog _instance;

    /*
     * Constantes para uso das exceptions
     */
    public static int ATTEMPT_TO_CHANGE_ENTITY = 0;

    /*
     * Inicialização das mensagens das exceptions
     */
    public Cathalog() {
        data = new HashSet<>();
        data.add(new DataException(ATTEMPT_TO_CHANGE_ENTITY, "Attempt to change entity marked for deletion"));
    }

    public synchronized static Cathalog getInstance(){
        if (_instance == null) {
            _instance = new Cathalog();
        }
        return _instance;
    }

    public DataException findExceptionData(int constant) {
        for (DataException iData: data ) {
            if (iData.equals(constant)) {
                return iData;
            }
        }
        return null;
    }


}
