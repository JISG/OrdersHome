package com.example.israelgutierrez.prueba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class miCuenta extends AppCompatActivity implements View.OnClickListener{
    TextView ajustes,notificaciones,direcciones;
    String idUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_cuenta);

        ajustes = (TextView) findViewById(R.id.ajustes);
        notificaciones = (TextView) findViewById(R.id.notificaciones);
        direcciones = (TextView) findViewById(R.id.direcciones);

        direcciones.setOnClickListener(this);
        ajustes.setOnClickListener(this);
        notificaciones.setOnClickListener(this);

        Intent intent = getIntent();
        idUsuario= intent.getStringExtra("idUsuario");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ajustes:
                Intent intent = new Intent(miCuenta.this,editarCuenta.class);
                intent.putExtra("idUsuario",idUsuario);
                startActivity(intent);
                break;
            case R.id.direcciones:
                Intent intent1 = new Intent(miCuenta.this,PedirOtraDireccion.class);
                intent1.putExtra("idUsuario",idUsuario);
                startActivity(intent1);
                break;
            case R.id.notificaciones:
                Intent intent2 = new Intent(miCuenta.this, RastrearPedido.class);
                intent2.putExtra("idUsuario", idUsuario);
                startActivity(intent2);
        }

    }
}
