package com.example.israelgutierrez.prueba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class entrar_crear_repartidor extends AppCompatActivity implements View.OnClickListener{
    Button entrar,crear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_crear_repartidor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        entrar = (Button) findViewById(R.id.iniciarSesion);
        crear = (Button) findViewById(R.id.crearCuenta);

        entrar.setOnClickListener(this);
        crear.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iniciarSesion:
                Intent intent = new Intent(entrar_crear_repartidor.this,login_repartidor.class);
                startActivity(intent);
            break;

            case R.id.crearCuenta:
                Intent intent1 = new Intent(entrar_crear_repartidor.this,formularioRepartidor.class);
                startActivity(intent1);
        }

    }
}
