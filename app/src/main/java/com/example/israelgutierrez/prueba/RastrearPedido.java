package com.example.israelgutierrez.prueba;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class RastrearPedido extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    RequestQueue requestQueue;
    String latitud;
    String longitud,idUsuario;
    Double lt=16.6252679,lg=-93.0972671 ;
    GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rastrear_pedido);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        requestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        idUsuario = intent.getStringExtra("idUsuario");
        time time = new time();
        time.execute();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapa=mMap;
        mapa.clear();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lt, lg);
        System.out.println("Latitud: "+lt);
        System.out.println("Longitud: "+lg);
        mapa.addMarker(new MarkerOptions().position(sydney));
        mapa.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mapa.moveCamera(CameraUpdateFactory.zoomTo(16));
    }

    public void hilo() {
        try {
            Thread.sleep(1000);

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
            final String url = "https://sgvshop.000webhostapp.com/ubicacionRepartidor.php?idUsuario=" + idUsuario;

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("succes");
                        if (success) {
                            latitud = jsonResponse.getString("latitud");
                            longitud = jsonResponse.getString("longitud");
                            lt= Double.valueOf(latitud);
                            lg= Double.valueOf(longitud);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RastrearPedido.this);
                    builder.setMessage("Error")
                            .setNegativeButton("Aceptar", null)
                            .create().show();
                    System.out.println("Falló");
                }
            });
            requestQueue.add(request);
            onMapReady(mapa);
        }
    }
}
