package agile.core.system;

/**
 * Classe Singleton para gerenciar a sessão do aplicativo
 *
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class Session {

    private static Session _instance;

    /*
     * Setar um valor na sessão baseado em uma key
     */
    public boolean setValue (String key, String value){
        return true;
    }

    /*
     * Retornar o valor de uma sessão de acordo com a key
     */
    public String getValue (String key) {
        return "teste";
    }

    /*
     * Metodo para definir o Singleton Pattern
     */
    public synchronized static Session getInstance(){
        if (_instance == null) {
            _instance = new Session();
        }
        return _instance;
    }

}
