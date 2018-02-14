package agile.core;

import android.app.Activity;
import agile.core.system.*;

/**
 * Created by Lucas Chinelate on 14/02/2018.
 */

public class StandardActivity extends Activity {

    	/*
	 * Método Para Pegar a Sessão
	 */

    public static Session getSession(){
        return Session.getInstance();
    }

}
