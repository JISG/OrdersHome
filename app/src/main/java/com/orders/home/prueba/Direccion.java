package com.orders.home.prueba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Direccion extends AppCompatActivity implements View.OnClickListener{
    Button mDireccionCasa, mDireccionOficina, mDireccionPareja, mOtraDireccion;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mOtraDireccion=(Button)findViewById(R.id.pedirOtraDireccion);
        mDireccionPareja=(Button)findViewById(R.id.direccionPareja);
        mDireccionCasa = (Button)findViewById(R.id.direccionCasa);
        mDireccionOficina = (Button)findViewById(R.id.direccionOficina);
        mDireccionCasa.setOnClickListener(this);
        mDireccionOficina.setOnClickListener(this);
        mDireccionPareja.setOnClickListener(this);
        mOtraDireccion.setOnClickListener(this);

        Intent intent = getIntent();
        name=intent.getStringExtra("name");

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.direccionCasa:
                break;
            case R.id.direccionOficina:
                break;
            case R.id.direccionPareja:
                break;
            case R.id.pedirOtraDireccion:
                intent = new Intent(Direccion.this,PedirOtraDireccion.class);
                intent.putExtra("name",name);
                startActivity(intent);
                break;

        }
    }
}
