package com.example.israelgutierrez.prueba;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class lista_pedidos_por_entregar extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerViewPedidos;
    private RecyclerViewAdaptador2 adaptadorPedidos;

    TextView txPedidos;
    Button cerrarSesion;
    RequestQueue requestQueue;
    double latitude;
    double longitude;
    String idRepartidor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedidos_por_entregar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        recyclerViewPedidos = (RecyclerView) findViewById(R.id.listaNotificaciones);
        recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(this));
        txPedidos = (TextView) findViewById(R.id.txtPedidos);
        cerrarSesion = (Button) findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(this);
        txPedidos.setVisibility(View.INVISIBLE);
        requestQueue= Volley.newRequestQueue(this);
        final ArrayList<pedidos> prueba = (ArrayList<pedidos>) getIntent().getSerializableExtra("lista");
        if(prueba.isEmpty()){
            txPedidos.setVisibility(View.VISIBLE);
        }

        Intent intent = getIntent();
        idRepartidor= intent.getStringExtra("idRepartidor");
        adaptadorPedidos = new RecyclerViewAdaptador2(prueba,idRepartidor);

        adaptadorPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Toast.makeText(getApplicationContext(),"Seleccion: "+prueba.get(recyclerViewPedidos.getChildAdapterPosition(view)).getIdUsuario(),Toast.LENGTH_SHORT).show();


            }
        });
        recyclerViewPedidos.setAdapter(adaptadorPedidos);



        time time = new time();
        time.execute();

        LocationManager locationManager = (LocationManager) lista_pedidos_por_entregar.this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                //tvUbicacion.setText("" + location.getLatitude() + " " + location.getLongitude());
                //System.out.println("OnLocation");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                //System.out.println("latitud: "+latitude);
                //System.out.println("Longitud: "+longitude);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

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

    public void hilo() {
        try {
            Thread.sleep(1000);
            //System.out.println("Hiloooo");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ejecutar() {
        time time = new time();
        time.execute();
    }

    public class time extends AsyncTask<Void, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {

            for (int i = 1; i <= 10; i++) {
                hilo();
            }
            return true;


        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            String lt = String.valueOf(latitude);
            String lg = String.valueOf(longitude);
            System.out.println("latitud: "+latitude);
            System.out.println("longitud: "+longitude);
            Toast.makeText(lista_pedidos_por_entregar.this, "Cada 10 segundos: "+ lt + " "+ lg, Toast.LENGTH_SHORT).show();
           // System.out.println("OnPost");
            final String url = "https://sgvshop.000webhostapp.com/insertCoordenadas.php?idRepartidor="+idRepartidor+"&latitud="+lt+"&longitud="+lg;

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //System.out.println("Hecho!");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(lista_pedidos_por_entregar.this);
                    builder.setMessage("Fallo en la notificación del pedido, contacte con el administrador!")
                            .setNegativeButton("Aceptar",null)
                            .create().show();

                }
            });
            requestQueue.add(request);


        }

    }
}
