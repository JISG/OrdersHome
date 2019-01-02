package com.orders.home.ordersHome;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Usuario extends AppCompatActivity {
    TextView idUsuario, nombre, user,tipo,contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        idUsuario = (TextView) findViewById(R.id.idUsuario);
        nombre = (TextView) findViewById(R.id.name);
        user = (TextView) findViewById(R.id.username);
        tipo = (TextView) findViewById(R.id.tipo);
        contrasena= (TextView) findViewById(R.id.password);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String id = intent.getStringExtra("idUsuario");
        String tipoUser = intent.getStringExtra("tipo");

        idUsuario.setText(id);
        nombre.setText(name);
        user.setText(username);
        contrasena.setText(password);
        tipo.setText(tipoUser);

    }
}
