package com.example.israelgutierrez.prueba;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class buscarRepartidor extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    Button buscarRepartidor,agregarRepartidor;
    EditText repartidor;
    ArrayList<listaRegistros> registros;
    RequestQueue requestQueue;
    Spinner sucursalesSP;
    ArrayList<String> sucursales;
    RequestQueue requestQueue2;
    String sucursal;
    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_repartidor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sucursales = new ArrayList<>();
        requestQueue2= Volley.newRequestQueue(this);
        buscarRepartidor = (Button) findViewById(R.id.buscarRepartidor);
        //sucursal = (EditText) findViewById(R.id.sucursal);
        repartidor = (EditText) findViewById(R.id.repartidor);
        agregarRepartidor =(Button) findViewById(R.id.agregarRepartidor);
        buscarRepartidor.setOnClickListener(this);
        agregarRepartidor.setOnClickListener(this);
        sucursalesSP = (Spinner) findViewById(R.id.sucursales);
        sucursalesSP.setOnItemSelectedListener(this);
        Intent intent = getIntent();

        idUsuario = intent.getStringExtra("idUsuario");
        obtenerSucursales(idUsuario);


        requestQueue= Volley.newRequestQueue(this);

        registros = new ArrayList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buscarRepartidor:
                final String sucu= sucursal.toString();
                final String repar= repartidor.getText().toString();
                final String url = "https://sgvshop.000webhostapp.com/buscarRepartidor.php?sucursal="+sucu+"&repartidor="+repar;
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listaRegistros lista= null;
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("succes");
                            if(success) {
                                JSONArray json = jsonResponse.optJSONArray("usuario");
                                for (int i = 0; i < json.length(); i++) {
                                    lista = new listaRegistros();
                                    JSONObject jsonObject = null;
                                    jsonObject = json.getJSONObject(i);
                                    lista.setNombreCliente(jsonObject.getString("nombreCliente"));
                                    lista.setKilos((float) jsonObject.getDouble("kilos"));
                                    lista.setDireccion(jsonObject.getString("direccion"));
                                    registros.add(lista);
                                }

                                Intent intent = new Intent(buscarRepartidor.this, listadoPedidosEntregados.class);
                                intent.putParcelableArrayListExtra("lista", registros);
                                buscarRepartidor.this.startActivity(intent);
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(buscarRepartidor.this);
                                builder.setMessage("Nombre de repartidor o Sucursal incorrectos. Revise sus datos.")
                                        .setNegativeButton("Aceptar",null)
                                        .create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(buscarRepartidor.this,"Registro Exitoso, Inicie Sesión.",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(buscarRepartidor.this);
                        builder.setMessage("Repartidor no encontrado intente otra vez!")
                                .setNegativeButton("Aceptar",null)
                                .create().show();

                    }
                });
                requestQueue.add(request);
            break;

            case R.id.agregarRepartidor:
                Intent intent = new Intent(buscarRepartidor.this,formularioRepartidor.class);
                intent.putExtra("bandera",1);
                startActivity(intent);

            break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        sucursal = (String) adapterView.getItemAtPosition(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void obtenerSucursales(String idUsuario){

        final String url = "https://sgvshop.000webhostapp.com/SelecSucursales.php?idUsuario="+idUsuario;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Sucursales lista= null;
                try {
                    sucursales.add("Seleccione Sucursal");
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray json = jsonResponse.optJSONArray("sucursales");
                    for(int i=0;i<json.length();i++){
                        lista = new Sucursales();
                        JSONObject jsonObject = null;
                        jsonObject=json.getJSONObject(i);
                        lista.setIdSucursal(jsonObject.getInt("idSucursal"));
                        lista.setNombre(jsonObject.getString("nombre"));
                        sucursales.add((jsonObject.getString("nombre")));

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.example.israelgutierrez.prueba.buscarRepartidor.this,android.R.layout.simple_spinner_item,sucursales);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sucursalesSP.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(buscarRepartidor.this);
                builder.setMessage("Fallo en registro, contacte con el administrador!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();

            }
        });
        requestQueue2.add(request);
    }
}
