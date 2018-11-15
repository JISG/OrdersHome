package com.example.israelgutierrez.prueba;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login_cliente extends AppCompatActivity{
    TextView usuario,password;
    Button entrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cliente);

        usuario = (TextView) findViewById(R.id.usuario);
        password= (TextView) findViewById(R.id.password);

        entrar =  (Button) findViewById(R.id.entrar);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username= usuario.getText().toString();
                final String contrasena= password.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String id = jsonResponse.getString("idUsuario");
                                String name = jsonResponse.getString("usuario");
                                String tipoUser = jsonResponse.getString("tipo");

                                Intent intent = new Intent(login_cliente.this,hacer_pedido.class);
                                //Toast.makeText(login_cliente.this,"idUsuario= "+id,Toast.LENGTH_SHORT).show();
                                intent.putExtra("idUsuario",id);
                                intent.putExtra("name", name);
                                intent.putExtra("username",username);
                                intent.putExtra("tipo",tipoUser);
                                intent.putExtra("password",contrasena);

                                login_cliente.this.startActivity(intent);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(login_cliente.this);
                                builder.setMessage("Nombre de usuario o contraseña invalidos. Revise sus datos.")
                                        .setNegativeButton("Aceptar",null)
                                        .create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };


                LoginRequest loginRequest = new LoginRequest(username,contrasena,responseListener);
                RequestQueue queue = Volley.newRequestQueue(login_cliente.this);
                queue.add(loginRequest);

            }
        });

    }

}
