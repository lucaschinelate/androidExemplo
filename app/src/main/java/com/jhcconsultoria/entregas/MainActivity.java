package com.jhcconsultoria.entregas;

import agile.core.orm.DataBase;
import agile.core.orm.connector.SQLiteConnector;
import agile.core.orm.valuesExtrator.ValuesExtrator;
import agile.core.system.activity.StandardActivity;
import model.Cliente;
import android.widget.TextView;
import org.androidannotations.annotations.*;
import android.database.sqlite.SQLiteDatabase;

@EActivity(R.layout.activity_main)
public class MainActivity extends StandardActivity {

    private DataBase db;
    SQLiteDatabase db2;

    @ViewById TextView txtHeloWorld;

    @AfterViews
    void testeCliente() {
        db = new DataBase(new SQLiteConnector(this, R.string.principal));

        try {
            txtHeloWorld.setText("INICIO");

            Cliente cliPriscila = (Cliente) db.findOneBy(Cliente.class, 1);

            if (cliPriscila != null) {
                cliPriscila.setNome("PRISCILA A");
                db.persist(cliPriscila);
            }

            Cliente cliRodrigo  = addCliente(3, "RODRIGO");

            db.delete(cliRodrigo);

            Cliente cliRodrigo2  = addCliente(4, "RODRIGAO");

            db.flush();

            txtHeloWorld.setText("FIM");
        } catch (Exception e) {
            txtHeloWorld.setText(e.toString());
        }

    }

    private Cliente addCliente(int idCliente, String nomeCliente) throws Exception {
        Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cliente.setNome(nomeCliente);
        return (Cliente) db.persist(cliente);
    }

}