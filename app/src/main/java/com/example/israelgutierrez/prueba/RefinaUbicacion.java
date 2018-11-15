package com.example.israelgutierrez.prueba;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RefinaUbicacion extends FragmentActivity implements OnMapReadyCallback {
    LatLng nuevaDir;
    private GoogleMap mMap;
    Button bAceptar;

    String direccion,name;
    double latitud, longitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refina_ubicacion);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapRefinaUbicacion);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        name=intent.getStringExtra("name");
        latitud =intent.getDoubleExtra("latitud",0);
        longitud = intent.getDoubleExtra("longitud",0);
        direccion = intent.getStringExtra("direccion");
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
        bAceptar = (Button) findViewById(R.id.aceptarRefinaUbicacion);
        MarkerOptions mo = new MarkerOptions();

        LatLng puntoObtenidoDireccion = new LatLng(latitud,longitud);
        mo.position(puntoObtenidoDireccion);
        mMap.addMarker(mo);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(puntoObtenidoDireccion));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                nuevaDir = new LatLng(latLng.latitude, latLng.longitude);
                markerOptions.title("Su posición actual");
                mMap.clear();
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.addMarker(markerOptions);
            }
        });
        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RefinaUbicacion.this, "Ubicación actualizada ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RefinaUbicacion.this, ConfirmarDireccion.class);
                intent.putExtra("name",name);
                intent.putExtra("latitud",nuevaDir.latitude);
                intent.putExtra("longitud",nuevaDir.longitude);
                intent.putExtra("direccion",direccion);
                startActivity(intent);
            }
        });


    }
}
