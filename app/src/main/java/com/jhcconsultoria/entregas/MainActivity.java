package com.jhcconsultoria.entregas;

import agile.core.orm.DataBase;
import agile.core.orm.connector.SQLiteConnector;
import agile.core.system.activity.StandardActivity;
import model.Cliente;
import android.widget.TextView;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class MainActivity extends StandardActivity {

    @ViewById TextView txtHeloWorld;

    @AfterViews
    void testeCliente() {
        Cliente cliente = new Cliente();
        DataBase db = new DataBase(new SQLiteConnector());

        try {
            cliente.setNome("LUCAS CORREA CHINELATE");
            db.persist(cliente);
            txtHeloWorld.setText(cliente.getNome());
        } catch (Exception e) {
            txtHeloWorld.setText(e.getMessage());
        }
    }

}
