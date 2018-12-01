package com.example.israelgutierrez.prueba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class entrar_crear extends AppCompatActivity implements View.OnClickListener{
    Button entrar,crearCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_crear);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        entrar = (Button) findViewById(R.id.iniciarSesion);
        crearCuenta = (Button) findViewById(R.id.crearCuenta);

        entrar.setOnClickListener(this);
        crearCuenta.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iniciarSesion:
                Intent intent = new Intent(entrar_crear.this,login_cliente.class);
                startActivity(intent);
            break;
            case R.id.crearCuenta:
                Intent intent1 = new Intent(entrar_crear.this,formulario_usuario_nuevo.class);
                startActivity(intent1);
            break;
        }
    }
}
