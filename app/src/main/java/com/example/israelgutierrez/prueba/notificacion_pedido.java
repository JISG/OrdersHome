package com.example.israelgutierrez.prueba;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.NotificationCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class notificacion_pedido extends AppCompatActivity implements View.OnClickListener{
    Button mNotificar;
    Button mverDireccion;

    RequestQueue requestQueue;

    double latitude;
    double longitude;
    private static final String TAG = notificacion_pedido.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_pedido);

        mNotificar=(Button) findViewById(R.id.notificar);
        mverDireccion =(Button) findViewById(R.id.verDireccion);

        mverDireccion.setOnClickListener(this);
        mNotificar.setOnClickListener(this);

        requestQueue= Volley.newRequestQueue(this);

        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()){
            case R.id.verDireccion:

                intent = new Intent(this, verDireccion.class);
                startActivity(intent);
                break;
            case R.id.notificar:
                time time = new time();
                time.execute();
               /* NotificationCompat.Builder mBuilder;
                NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                int icono = R.mipmap.ic_launcher;
                intent = new Intent(notificacion_pedido.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(notificacion_pedido.this, 0,intent, 0);
                mBuilder = new NotificationCompat.Builder(getApplicationContext());
                mBuilder.setContentIntent (pendingIntent);
                mBuilder.setSmallIcon(icono);
                mBuilder.setContentTitle("OrdersHome pedido enviado");
                mBuilder.setContentText("Su pedido de tortillas ha zarpado, ¡En hora buena!");
                mBuilder.setVibrate(new long[] {100, 250, 100, 500});
                mBuilder.setAutoCancel(true);
                mNotifyMgr.notify(1, mBuilder.build());
                */
                // Acquire a reference to the system Location Manager
                LocationManager locationManager = (LocationManager) notificacion_pedido.this.getSystemService(Context.LOCATION_SERVICE);

                // Define a listener that responds to location updates
                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        // Called when a new location is found by the network location provider.
                        //tvUbicacion.setText("" + location.getLatitude() + " " + location.getLongitude());
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };

                // Register the listener with the Location Manager to receive location updates
                int permissionCheck = ContextCompat.checkSelfPermission(notificacion_pedido.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                break;
        }
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
            String lt = String.valueOf(latitude);
            String lg = String.valueOf(longitude);
            Toast.makeText(notificacion_pedido.this, "Cada 10 segundos: "+ lt + " "+ lg, Toast.LENGTH_SHORT).show();

            final String url = "https://sgvshop.000webhostapp.com/insertCoordenadas.php?idRepartidor="+2+"&latitud="+lt+"&longitud="+lg;

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(notificacion_pedido.this);
                    builder.setMessage("Fallo en la notificación del pedido, contacte con el administrador!")
                            .setNegativeButton("Aceptar",null)
                            .create().show();

                }
            });
            requestQueue.add(request);


        }

    }
}
