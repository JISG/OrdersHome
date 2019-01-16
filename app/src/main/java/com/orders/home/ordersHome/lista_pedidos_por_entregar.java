package com.orders.home.ordersHome;

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
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class lista_pedidos_por_entregar extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerViewPedidos;
    private RecyclerViewAdaptador2 adaptadorPedidos;

    TextView txPedidos;
    Button cerrarSesion;
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    RequestQueue requestQueue3;
    double latitude;
    double longitude;
    String idRepartidor;
    ArrayList<pedidos> listaPedidos;
    ArrayList<coordenadas> coordenadas;
    ArrayList<coordenadas> nuevasCoordenadas;
    String idNotificacion;
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
        requestQueue2= Volley.newRequestQueue(this);
        requestQueue3= Volley.newRequestQueue(this);
        listaPedidos = new ArrayList<>();
        Intent intent = getIntent();
        listaPedidos = (ArrayList<pedidos>) intent.getSerializableExtra("lista");
        if(listaPedidos.isEmpty()){ //Message de NO HAY PEDIDOS
            txPedidos.setVisibility(View.VISIBLE);
        }

        if(listaPedidos.isEmpty()){
            listaPedidos =null;
            listaPedidos = new ArrayList<>();
        }


        idRepartidor= intent.getStringExtra("idRepartidor");
        adaptadorPedidos = new RecyclerViewAdaptador2(listaPedidos,idRepartidor);
        String idUsuario = intent.getStringExtra("idUsuario");
        guardarToken(idUsuario);
        selecCoordenadas();

        adaptadorPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Seleccion: "+listaPedidos.get(recyclerViewPedidos.getChildAdapterPosition(view)).getIdUsuario(),Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewPedidos.setAdapter(adaptadorPedidos);



        time time = new time();
        time.execute();

        LocationManager locationManager = (LocationManager) lista_pedidos_por_entregar.this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

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
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cerrarSesion:
                //recyclerViewPedidos.removeAllViews();
                Intent intent = new Intent(lista_pedidos_por_entregar.this,login_repartidor.class);
                startActivity(intent);
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
            //Hilo ejecutandose en segundo plano y registrando las coordenadas del repartidor;
            ejecutar();
            String lt = String.valueOf(latitude);
            String lg = String.valueOf(longitude);
            final String url = "https://sgvshop.000webhostapp.com/insertCoordenadas.php?idRepartidor="+idRepartidor+"&latitud="+lt+"&longitud="+lg;

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
            nuevasCoordenadas = new ArrayList<>();

            nuevasCoordenadas = coordenadas;
            if(!(nuevasCoordenadas==null)) {
                for (int i = 0; i < nuevasCoordenadas.size(); i++) {

                    idNotificacion = coordenadas.get(i).getIdUsuario();
                    //System.out.println("idUsuario: " + idNotificacion);
                    float distance = 0;
                    Location crntLocation = new Location("crntlocation");
                    crntLocation.setLatitude(latitude);
                    crntLocation.setLongitude(longitude);

                    Location newLocation = new Location("newlocation");
                    newLocation.setLatitude(Double.parseDouble(coordenadas.get(i).getLatitud()));
                    newLocation.setLongitude(Double.parseDouble(coordenadas.get(i).getLongitud()));


                    distance = crntLocation.distanceTo(newLocation);
                    System.out.println("Distance: "+distance);
                    if (distance < 200) {
                        enviarNotificacion(idNotificacion);
                       System.out.println("notificacion enviada");
                        System.out.println("idUsuario: "+coordenadas.get(i).getIdUsuario()+" latitud: "+coordenadas.get(i).getLatitud());
                        coordenadas.remove(i);
                    }

                }
            }
        }

        private void enviarNotificacion(String id) {
            final String mensaje = "Su pedido está a menos de 200 metros, favor de salir a recibirlo.";
            final String url = "https://sgvshop.000webhostapp.com/sendNotifications.php?idUsuario="+id+"&mensaje="+mensaje;

            StringRequest request2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //System.out.println("notificacion enviadaaaa");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(lista_pedidos_por_entregar.this);
                    builder.setMessage("Fallo en registro, contacte con el administrador!")
                            .setNegativeButton("Aceptar",null)
                            .create().show();
                    System.out.println("No se hizo el pedido");
                }
            });
            requestQueue2.add(request2);
        }
    }

    public void guardarToken(String idUsuario){
        final String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println(idUsuario);
        final String url = "https://sgvshop.000webhostapp.com/guardarToken.php?idUsuario="+idUsuario+"&token="+token;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println("Token guardado: "+token);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(lista_pedidos_por_entregar.this);
                builder.setMessage("Fallo en Guardar Token!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();

            }
        });
        requestQueue2.add(request);
    }

    public void selecCoordenadas(){
        final String url = "https://sgvshop.000webhostapp.com/selecCoordenadasCliente.php?idRepartidor="+idRepartidor;
        StringRequest request3 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                coordenadas lista = null;
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("succes");
                    if (success) {
                        coordenadas = null;
                        coordenadas = new ArrayList<>();

                        JSONArray json = jsonResponse.optJSONArray("coordenadas");
                        for (int i = 0; i < json.length(); i++) {
                            lista = new coordenadas();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            lista.setIdUsuario(jsonObject.getString("idUsuario"));
                            lista.setLatitud(jsonObject.getString("latitudCliente"));
                            lista.setLongitud(jsonObject.getString("longitudCliente"));
                            coordenadas.add(lista);
                        }
                        System.out.println("Coordenadas: "+coordenadas.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(lista_pedidos_por_entregar.this);
                builder.setMessage("Repartidor no encontrado intente otra vez!")
                        .setNegativeButton("Aceptar", null)
                        .create().show();
            }
        });
        requestQueue3.add(request3);
    }
}
