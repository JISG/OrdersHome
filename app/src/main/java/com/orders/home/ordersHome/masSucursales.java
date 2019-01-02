package com.orders.home.ordersHome;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class masSucursales extends AppCompatActivity implements View.OnClickListener{
    Button aceptar;
    EditText sucursal2,sucursal3,sucursal4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_sucursales);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sucursal2 = (EditText) findViewById(R.id.sucursal2);
        sucursal3 = (EditText) findViewById(R.id.sucursal3);
        sucursal4 = (EditText) findViewById(R.id.sucursal4);

        aceptar = (Button) findViewById(R.id.aceptar);
        aceptar.setOnClickListener(this);




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.aceptar:

                Intent intent2 = getIntent();
                String nombre=  intent2.getStringExtra("nombre");
                String apellidoP=  intent2.getStringExtra("apellidoP");
                String apellidoM=  intent2.getStringExtra("apellidoM");
                String sucursal=  intent2.getStringExtra("sucursal");
                String usuario=  intent2.getStringExtra("usuario");
                String password=  intent2.getStringExtra("password");


                String s2=sucursal2.getText().toString();
                String s3=sucursal3.getText().toString();
                String s4=sucursal4.getText().toString();
                Intent intent = new Intent(masSucursales.this,formulario_empresa.class);
                intent.putExtra("sucursal2",s2);
                intent.putExtra("sucursal3", s3);
                intent.putExtra("sucursal4",s4);
                intent.putExtra("nombre",nombre);
                intent.putExtra("apellidoP",apellidoP);
                intent.putExtra("apellidoM",apellidoM);
                intent.putExtra("sucursal",sucursal);
                intent.putExtra("usuario",usuario);
                intent.putExtra("password",password);
                startActivity(intent);
            break;
        }
    }
}
