package com.jhcconsultoria.entregas;

import agile.core.orm.DataBase;
import agile.core.orm.connector.SQLiteConnector;
import agile.core.system.activity.StandardActivity;
import model.Cliente;
import android.widget.TextView;
import org.androidannotations.annotations.*;

@EActivity(R.layout.activity_main)
public class MainActivity extends StandardActivity {

    private DataBase db;

    @ViewById TextView txtHeloWorld;

    @AfterViews
    void testeCliente() {
        db = new DataBase(new SQLiteConnector());

        try {
            addCliente(1,"LUCAS CORREA CHINELATE");
            addCliente(1, "RODRIGO");
            db.flush();
            txtHeloWorld.setText("Clientes Inseridos com sucesso");
        } catch (Exception e) {
            txtHeloWorld.setText(e.getMessage());
        }
    }

    private void addCliente(int idCliente, String nomeCliente) throws Exception {
        Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cliente.setNome(nomeCliente);
        db.persist(cliente);
    }

}