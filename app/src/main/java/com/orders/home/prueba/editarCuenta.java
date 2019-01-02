package com.orders.home.prueba;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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


public class editarCuenta extends AppCompatActivity implements View.OnClickListener{
    TextView bienvenida,nombre,usuario,apellidoP,apellidoM,password,cerrarSesion;
    RequestQueue requestQueue;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cuenta);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bienvenida = (TextView) findViewById(R.id.bienvenida);
        nombre = (TextView) findViewById(R.id.nombre);
        usuario = (TextView) findViewById(R.id.usuario);
        apellidoP = (TextView) findViewById(R.id.apellidoP);
        apellidoM = (TextView) findViewById(R.id.apellidoM);
        password = (TextView) findViewById(R.id.password);
        cerrarSesion = (TextView) findViewById(R.id.cerrarSesion);

        cerrarSesion.setOnClickListener(this);
        requestQueue= Volley.newRequestQueue(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("idUsuario");
        obtenerDatos();
    }


    public void obtenerDatos(){

        final String url = "https://sgvshop.000webhostapp.com/selectDatosCliente.php?idUsuario="+id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    bienvenida.setText("Hola que tal "+jsonResponse.getString("nombre"));
                    nombre.setText(jsonResponse.getString("nombre"));
                    apellidoP.setText(jsonResponse.getString("apellidoP"));
                    apellidoM.setText(jsonResponse.getString("apellidoM"));
                    usuario.setText(jsonResponse.getString("usuario"));
                    password.setText(jsonResponse.getString("contrasena"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(editarCuenta.this);
                builder.setMessage("Error en algo, contacte con el administrador!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();

            }
        });
        requestQueue.add(request);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cerrarSesion:

                Intent intent = new Intent(editarCuenta.this,MainActivity.class);
                startActivity(intent);
        }

    }
}
