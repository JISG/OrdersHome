package com.example.israelgutierrez.prueba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class QuienEres extends AppCompatActivity implements View.OnClickListener{

    Button repartidor,cliente,administrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quien_eres);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        repartidor = (Button) findViewById(R.id.repartidor);
        cliente = (Button) findViewById(R.id.cliente);
        administrador = (Button) findViewById(R.id.administrador);

        repartidor.setOnClickListener(this);
        cliente.setOnClickListener(this);
        administrador.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.administrador:
                Intent intent2 = new Intent(QuienEres.this,admi_entrar_crear.class);
                startActivity(intent2);
            break;
            case R.id.cliente:
                Intent i = new Intent(QuienEres.this,entrar_crear.class);
                startActivity(i);
            break;
            case R.id.repartidor:
                Intent intent = new Intent(QuienEres.this,entrar_crear_repartidor.class);
                startActivity(intent);
            break;

        }
    }
}
