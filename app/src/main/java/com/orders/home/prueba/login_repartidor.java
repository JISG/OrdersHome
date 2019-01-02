package com.orders.home.prueba;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class login_repartidor extends AppCompatActivity implements View.OnClickListener {
    Button entrar;
    TextView usuario, password,si;
    String id;
    ArrayList<pedidos> registros;
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    String idRepartidor;
    int contador;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_repartidor);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        usuario = (TextView) findViewById(R.id.usuario);
        password = (TextView) findViewById(R.id.password);
        entrar = (Button) findViewById(R.id.entrar);
        entrar.setOnClickListener(this);
        requestQueue= Volley.newRequestQueue(this);
        requestQueue2= Volley.newRequestQueue(this);
        registros = new ArrayList<>();
        contador=0;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.entrar:
                final String username= usuario.getText().toString();
                final String contrasena= password.getText().toString();
                if(username.length()!=0){
                    if(contrasena.length()!=0){
                        Response.Listener<String> responseListener = new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if(success) {
                                        id = jsonResponse.getString("idUsuario");
                                        buscarIdRepartidor();
                                        buscarPedidos();
                                        String name = jsonResponse.getString("usuario");
                                        String tipoUser = jsonResponse.getString("tipo");
                                        Intent intent = new Intent(login_repartidor.this,lista_pedidos_por_entregar.class);
                                        intent.putExtra("idUsuario",id);
                                        intent.putExtra("name", name);
                                        intent.putExtra("username",username);
                                        intent.putExtra("tipo",tipoUser);
                                        intent.putExtra("password",contrasena);
                                        intent.putExtra("idRepartidor",idRepartidor);
                                        intent.putParcelableArrayListExtra("lista", registros);
                                        //

                                            registros = null;
                                            registros = new ArrayList<pedidos>();
                                            buscarPedidos();
                                            System.out.println("Registros: "+registros);
                                            if(contador==2) {
                                                contador = 0;
                                                startActivity(intent);
                                            }

                                    }else{
                                        AlertDialog.Builder builder = new AlertDialog.Builder(login_repartidor.this);
                                        builder.setMessage("Nombre de usuario o contraseña invalidos. Revise sus datos.")
                                                .setNegativeButton("Aceptar",null)
                                                .create().show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        };
                        LoginRequestRepartidor loginRequest = new LoginRequestRepartidor(username,contrasena,responseListener);
                        RequestQueue queue = Volley.newRequestQueue(login_repartidor.this);
                        queue.add(loginRequest);
                    }
                    else{
                        Toast.makeText(login_repartidor.this,"Ingrese una contraseña.",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(login_repartidor.this,"Ingrese un nombre de usuario.",Toast.LENGTH_SHORT).show();
                }


            break;
        }

    }

    public void buscarPedidos() {
        final String url = "https://sgvshop.000webhostapp.com/listarPedidos.php?idUsuario="+id;
        System.out.println("Buscando pedidos del id: "+id);

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pedidos lista = null;
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("succes");
                    if (success) {
                        registros = null;
                        registros = new ArrayList<>();
                        JSONArray json = jsonResponse.optJSONArray("pedidos");
                        for (int i = 0; i < json.length(); i++) {
                            lista = new pedidos();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            lista.setIdPedido(jsonObject.getInt("idPedido"));
                            lista.setIdUsuario(jsonObject.getInt("idUsuario"));
                            lista.setNombreCliente(jsonObject.getString("nombreCliente"));
                            lista.setDireccion(jsonObject.getString("direccion"));
                            lista.setKilos((float) jsonObject.getDouble("kilos"));
                            lista.setHoraEntrega(jsonObject.getString("horaEntrega"));
                            lista.setTipoTortilla(jsonObject.getString("tipoTortilla"));
                            registros.add(lista);
                        }
                        System.out.println("Registros: "+registros.toString());
                        contador++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(buscarRepartidor.this,"Registro Exitoso, Inicie Sesión.",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(login_repartidor.this);
                builder.setMessage("Repartidor no encontrado intente otra vez!")
                        .setNegativeButton("Aceptar", null)
                        .create().show();

            }
        });
        requestQueue.add(request);
    }

    public void buscarIdRepartidor(){
        final String url = "https://sgvshop.000webhostapp.com/buscarIdRepartidor.php?idUsuario="+id;
        StringRequest request2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("succes");
                    if (success) {
                        idRepartidor = jsonResponse.getString("idRepartidor");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(buscarRepartidor.this,"Registro Exitoso, Inicie Sesión.",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(login_repartidor.this);
                builder.setMessage("Error")
                        .setNegativeButton("Aceptar", null)
                        .create().show();
            }
        });
        requestQueue2.add(request2);

    }
}
