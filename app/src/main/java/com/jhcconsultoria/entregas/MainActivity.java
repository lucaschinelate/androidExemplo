package com.jhcconsultoria.entregas;

import android.os.Bundle;
import agile.core.StandardActivity;
import agile.core.orm.DataBase;
import agile.core.orm.connector.SQLiteConnector;
import model.*;
import android.util.Log;

public class MainActivity extends StandardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cliente cliente = new Cliente();
        DataBase db = new DataBase(new SQLiteConnector());

        try {
            cliente.setNome("LUCAS CORREA CHINELATE");

            db.persist(cliente);

            //Log.i("AGILE",cliente.getNome());
        } catch (Exception e) {
            Log.i("AGILE",e.getMessage());
        }

    }
}
