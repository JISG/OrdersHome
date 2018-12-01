package com.example.israelgutierrez.prueba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class listadoPedidosEntregados extends AppCompatActivity implements View.OnClickListener {

    //Recycler
    private RecyclerView recyclerViewPedidos;
    private RecyclerViewAdaptador adaptadorPedidos;
    TextView total;
    Button buscarOtroRepartidor,cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_pedidos_entregados);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        recyclerViewPedidos = (RecyclerView) findViewById(R.id.pedidos);
        recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(this));

        buscarOtroRepartidor= (Button) findViewById(R.id.buscarOtroRepartidor);
        cerrarSesion = (Button) findViewById(R.id.cerrarSesion);

        total= (TextView) findViewById(R.id.total);

        ArrayList<listaRegistros> prueba = (ArrayList<listaRegistros>) getIntent().getSerializableExtra("lista");

        buscarOtroRepartidor.setOnClickListener(this);
        cerrarSesion.setOnClickListener(this);

        //Adaptador Recycler
        adaptadorPedidos = new RecyclerViewAdaptador(prueba);
        recyclerViewPedidos.setAdapter(adaptadorPedidos);
        int cantidad=0;
        for(int i=0;i<prueba.size();i++){
            int kg =prueba.get(i).getKilos();
            cantidad =cantidad+kg;
        }
        total.setText(""+cantidad);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buscarOtroRepartidor:
                recyclerViewPedidos.removeAllViews();
                Intent intent = new Intent(listadoPedidosEntregados.this,buscarRepartidor.class);
                startActivity(intent);
            break;

            case R.id.cerrarSesion:
                recyclerViewPedidos.removeAllViews();
                Intent intent1 = new Intent(listadoPedidosEntregados.this,MainActivity.class);
                startActivity(intent1);
            break;
        }
    }
}
