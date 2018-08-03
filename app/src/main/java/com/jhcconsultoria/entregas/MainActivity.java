package com.jhcconsultoria.entregas;

import agile.core.orm.DataBase;
import agile.core.orm.Filter;
import agile.core.orm.connector.SQLiteConnector;
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

            txtHeloWorld.setText("Iniciando app");

            Cliente cliente = (Cliente) db.findOneBy(Cliente.class, 3);

            if (cliente != null) {
                txtHeloWorld.setText("O nome do cliente é: " + cliente.getNome());
            } else {
                txtHeloWorld.setText("Nenhum Cliente encontrado com este código");
            }

            //addCliente(1,"LUCAS CORREA CHINELATE");
            //addCliente(2, "RODRIGO");
            //removeCliente(2);

            //db.flush();

        } catch (Exception e) {
            txtHeloWorld.setText(e.toString());
        }

    }

    private void addCliente(int idCliente, String nomeCliente) throws Exception {
        Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cliente.setNome(nomeCliente);
        db.persist(cliente);
    }

    private void removeCliente(int idCliente) throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId(idCliente);
        db.delete(cliente);
    }

}