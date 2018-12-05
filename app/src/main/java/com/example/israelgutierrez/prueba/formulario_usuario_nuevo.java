package com.example.israelgutierrez.prueba;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class formulario_usuario_nuevo extends AppCompatActivity implements View.OnClickListener{

    EditText nombre,apellidoM,apellidoP,usuario,password;
    Button crear;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_usuario_nuevo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        nombre = (EditText)findViewById(R.id.nombre);
        apellidoM = (EditText) findViewById(R.id.apellidoM);
        apellidoP = (EditText) findViewById(R.id.apellidoP);
        usuario = (EditText) findViewById(R.id.usuario);
        password = (EditText) findViewById(R.id.password);

        crear = (Button) findViewById(R.id.crearCuenta);
       /* iniciarSesion = (Button) findViewById(R.id.iniciarSesion);
 iniciarSesion.setOnClickListener(this);
*/
        crear.setOnClickListener(this);

        requestQueue= Volley.newRequestQueue(this);

    }


    public void agregarUsuario(){
        final String url = "https://sgvshop.000webhostapp.com/guardar.php?usuario="+usuario.getText().toString()+"&contrasena="+password.getText().toString()+
                "&nombre="+nombre.getText().toString()+"&apellidoP="+apellidoP.getText().toString()+"&apellidoM="+apellidoM.getText().toString();

        if(nombre.getText().toString().trim().equalsIgnoreCase("")){
            nombre.setError("Ingrese un nombre");
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
                    nombre.setText("");
                    apellidoM.setText("");
                    apellidoP.setText("");
                    usuario.setText("");
                    password.setText("");
                    Toast.makeText(formulario_usuario_nuevo.this, "Registro Exitoso, Inicie Sesión.", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(formulario_usuario_nuevo.this);
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
        switch (view.getId()) {
            case R.id.crearCuenta:
                agregarUsuario();
                Intent intent = new Intent(formulario_usuario_nuevo.this,login_cliente.class);
                startActivity(intent);
                // break;
            /*case R.id.iniciarSesion:
                Intent intent2 = new Intent(formulario_usuario_nuevo.this,login_cliente.class);
                startActivity(intent2);
*/
                break;

        }

    }
}
