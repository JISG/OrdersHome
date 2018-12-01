package com.example.israelgutierrez.prueba;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;

public class PedirOtraDireccion extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    Button bfindDirection, bConfirmarDireccion;
    private GoogleMap mMap;
    TextView direccion;
    String name,idUsuario;

    LatLng pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_otra_direccion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bConfirmarDireccion=(Button)findViewById(R.id.confirmarDireccion);
        bConfirmarDireccion.setOnClickListener(this);

        Intent intent = getIntent();
        name=intent.getStringExtra("name");
        idUsuario = intent.getStringExtra("idUsuario");
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
        final Geocoder geo = new Geocoder(this);
        mMap = googleMap;
        final LatLng defaultPos = new LatLng(16.753042050024227,-93.11604291200638);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultPos));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(16));


        bfindDirection = (Button) findViewById(R.id.findDirection);
        bfindDirection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                boolean encontrado = false;
                mMap.clear();
                direccion = (TextView) findViewById(R.id.escribirDireccion);

                int maxResultados = 1;
                List<Address> adress = null;
                String direc = direccion.getText().toString();
                //String direc = "zoomat";
                try {
                    adress = geo.getFromLocationName(direc, maxResultados);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                 pos = defaultPos;
                try {
                    pos = new LatLng(adress.get(0).getLatitude(), adress.get(0).getLongitude());
                    encontrado=true;
                }catch (Exception ex){
                    Toast.makeText(PedirOtraDireccion.this, "No se ha encontrado la dirección especificada", Toast.LENGTH_SHORT).show();
                }



                // Add a marker in Sydney and move the camera
                if(encontrado) {
                    mMap.addMarker(new MarkerOptions().position(pos).title(direc));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(16));
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.confirmarDireccion:
                intent = new Intent(PedirOtraDireccion.this,ConfirmarDireccion.class);
                intent.putExtra("name",name);
                intent.putExtra("latitud",pos.latitude);
                intent.putExtra("longitud",pos.longitude);
                intent.putExtra("direccion",direccion.getText().toString());
                intent.putExtra("idUsuario",idUsuario);
                startActivity(intent);
        }
    }
}
