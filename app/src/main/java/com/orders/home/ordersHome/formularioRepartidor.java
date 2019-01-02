package com.orders.home.ordersHome;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

public class formularioRepartidor extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    EditText nombreRepartidor, apellidoP, apellidoM,usuario,password;
    Button crearCuenta;
    RequestQueue requestQueue;

    ArrayList<String> sucursales;
    RequestQueue requestQueue2;
    Spinner prueba;
    String sucursal;
    int bandera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_repartidor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sucursales = new ArrayList<>();
        requestQueue2= Volley.newRequestQueue(this);
        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.password);
        nombreRepartidor = (EditText) findViewById(R.id.nombreRepartidor);
        apellidoP = (EditText) findViewById(R.id.apellidoP);
        apellidoM = (EditText) findViewById(R.id.apellidoM);
        crearCuenta= (Button) findViewById(R.id.crearCuenta);

        crearCuenta.setOnClickListener(this);

        prueba = (Spinner) findViewById(R.id.prueba);

        prueba.setOnItemSelectedListener(this);
        obtenerSucursales();

        requestQueue= Volley.newRequestQueue(this);

        String nombreR = nombreRepartidor.getText().toString() + " "+apellidoP.getText().toString()+ " "+apellidoM.getText().toString();
        Intent intent = getIntent();
        bandera = intent.getIntExtra("bandera",0);
    }

    public void agregarUsuario(){
        final String url = "https://sgvshop.000webhostapp.com/guardarRepartidor.php?usuario="+usuario.getText().toString()+"&contrasena="+password.getText().toString()+
                "&nombre="+nombreRepartidor.getText().toString()+"&apellidoP="+apellidoP.getText().toString()+"&apellidoM="+apellidoM.getText().toString()+"&sucursal="+sucursal;

        if(nombreRepartidor.getText().toString().trim().equalsIgnoreCase("")){
            nombreRepartidor.setError("Ingrese un nombre");
        }else if (apellidoM.getText().toString().trim().equalsIgnoreCase("")){
            apellidoM.setError("Ingrese un apellido");
        }else if (apellidoP.getText().toString().trim().equalsIgnoreCase("")){
            apellidoP.setError("Ingrese un apellido");
        }else if (usuario.getText().toString().trim().equalsIgnoreCase("")){
            usuario.setError("Ingrese un usuario");
        }else if (password.getText().toString().trim().equalsIgnoreCase("")){
            password.setError("Ingrese una contraseña");
        }else {
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    nombreRepartidor.setText("");
                    apellidoM.setText("");
                    apellidoP.setText("");
                    usuario.setText("");
                    password.setText("");
                    Toast.makeText(formularioRepartidor.this, "Registro Exitoso. Inicie Sesión", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(formularioRepartidor.this,buscarRepartidor.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(formularioRepartidor.this);
                    builder.setMessage("Fallo en registro, contacte con el administrador!")
                            .setNegativeButton("Aceptar", null)
                            .create().show();
                }
            });
            requestQueue.add(request);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.crearCuenta:
                if(bandera ==1){
                    agregarUsuario();
                }else {
                    agregarUsuario();
                    Intent intent = new Intent(formularioRepartidor.this, login_repartidor.class);
                    startActivity(intent);
                }
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

    public void obtenerSucursales(){

        final String url = "https://sgvshop.000webhostapp.com/SelecSucursales.php";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Sucursales lista= null;
                try {
                    sucursales.add("Selecciona");
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.orders.home.ordersHome.formularioRepartidor.this,android.R.layout.simple_spinner_item,sucursales);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    prueba.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(formularioRepartidor.this);
                builder.setMessage("Fallo en registro, contacte con el administrador!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();
            }
        });
        requestQueue2.add(request);
    }
}

