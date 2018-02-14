package com.jhcconsultoria.entregas;

import android.os.Bundle;
import agile.core.StandardActivity;
import model.*;
import android.util.Log;

public class MainActivity extends StandardActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClienteTest cliente = new ClienteTest();
        try {
            cliente.setNome("LUCAS CORREA CHINELATE");
            Log.i("AGILE",cliente.getNome());
        } catch (Exception e) {
            Log.i("AGILE",e.getMessage());
        }

    }
}
