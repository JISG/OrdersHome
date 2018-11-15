package com.example.israelgutierrez.prueba;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ConfirmarDireccion extends FragmentActivity implements OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;
    Button bRefinaUbicacion, bConfirmarCambioUbc,casa,oficina,pareja;
    String name;
    String direccion,idUsuario,tag;
    TextView direcIngresada;
    double latitud, longitud;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmar_direccion);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapConfirmaUbicacion);
        mapFragment.getMapAsync(this);
        bRefinaUbicacion=(Button) findViewById(R.id.refinarUbicacion);
        bRefinaUbicacion.setOnClickListener(this);
        bConfirmarCambioUbc = (Button) findViewById(R.id.confirmarCambioDireccion);
        bConfirmarCambioUbc.setOnClickListener(this);
        direcIngresada = (TextView) findViewById(R.id.direcIngresada);
        Intent intent = getIntent();
        name=intent.getStringExtra("name");
        latitud =intent.getDoubleExtra("latitud",0);
        longitud = intent.getDoubleExtra("longitud",0);
        direccion = intent.getStringExtra("direccion");
        idUsuario = intent.getStringExtra("idUsuario");
        requestQueue= Volley.newRequestQueue(this);
        casa = (Button) findViewById(R.id.casa);
        oficina = (Button) findViewById(R.id.oficina);
        pareja = (Button) findViewById(R.id.pareja);
        casa.setOnClickListener(this);
        oficina.setOnClickListener(this);
        pareja.setOnClickListener(this);
        casa.setBackgroundColor(Color.GRAY);
        pareja.setBackgroundColor(Color.GRAY);
        oficina.setBackgroundColor(Color.GRAY);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        direcIngresada.setText(direccion);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(sydney).title(direccion));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.refinarUbicacion:
                intent = new Intent(ConfirmarDireccion.this,RefinaUbicacion.class);
                intent.putExtra("latitud",latitud);
                intent.putExtra("longitud",longitud);
                intent.putExtra("direccion",direccion);
                intent.putExtra("name",name);
                intent.putExtra("idUsuario",idUsuario);
                startActivity(intent);
                break;
            case R.id.confirmarCambioDireccion:
                agregarAdmi();
                intent = new Intent(ConfirmarDireccion.this,miCuenta.class);
                intent.putExtra("name",name);
                intent.putExtra("idUsuario",idUsuario);
                startActivity(intent);
            break;
            case R.id.casa:
                casa.setBackgroundColor(Color.RED);
                oficina.setBackgroundColor(Color.GRAY);
                pareja.setBackgroundColor(Color.GRAY);
                tag= "Casa";
                break;
            case R.id.oficina:
                casa.setBackgroundColor(Color.GRAY);
                oficina.setBackgroundColor(Color.RED);
                pareja.setBackgroundColor(Color.GRAY);
                tag= "Oficina";
                break;
            case R.id.pareja:
                casa.setBackgroundColor(Color.GRAY);
                oficina.setBackgroundColor(Color.GRAY);
                pareja.setBackgroundColor(Color.RED);
                tag= "Pareja";
                break;


        }
    }

    public void agregarAdmi(){

        final String url = "https://sgvshop.000webhostapp.com/insertDireccion.php?idUsuario="+idUsuario+"&direccion="+direccion+
                "&latitud="+latitud+"&longitud="+longitud+"&tag="+tag;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(ConfirmarDireccion.this,"Direccion agregada correctamente!.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ConfirmarDireccion.this,miCuenta.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmarDireccion.this);
                builder.setMessage("Fallo en registro, contacte con el administrador!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();

            }
        });
        requestQueue.add(request);

    }
}
