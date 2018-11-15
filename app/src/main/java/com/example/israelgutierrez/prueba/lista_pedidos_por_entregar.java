package com.example.israelgutierrez.prueba;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class lista_pedidos_por_entregar extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerViewPedidos;
    private RecyclerViewAdaptador2 adaptadorPedidos;

    TextView txPedidos;
    Button cerrarSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos_por_entregar);

        recyclerViewPedidos = (RecyclerView) findViewById(R.id.listaNotificaciones);
        recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(this));
        txPedidos = (TextView) findViewById(R.id.txtPedidos);
        cerrarSesion = (Button) findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);
        txPedidos.setVisibility(View.INVISIBLE);

        ArrayList<pedidos> prueba = (ArrayList<pedidos>) getIntent().getSerializableExtra("lista");
        if(prueba.isEmpty()){
            txPedidos.setVisibility(View.VISIBLE);
        }

        adaptadorPedidos = new RecyclerViewAdaptador2(prueba);
        recyclerViewPedidos.setAdapter(adaptadorPedidos);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cerrarSesion:
                recyclerViewPedidos.removeAllViews();
                Intent intent = new Intent(lista_pedidos_por_entregar.this,login_repartidor.class);
                startActivity(intent);

        }
    }
}
