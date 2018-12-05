package com.example.israelgutierrez.prueba;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class formulario_empresa extends AppCompatActivity implements View.OnClickListener{

    EditText nombreAdmi, apellidoP, apellidoM,usuario,password,sucursal;
    Button crearCuenta,iniciarSesion;
    RequestQueue requestQueue;
    TextView masSucursal;
    String s2="",s3="",s4="";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_empresa);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        nombreAdmi = (EditText) findViewById(R.id.name);
        apellidoP = (EditText) findViewById(R.id.ap);
        apellidoM = (EditText) findViewById(R.id.am);
        usuario = (EditText) findViewById(R.id.userr);
        password = (EditText) findViewById(R.id.pass);
        sucursal = (EditText) findViewById(R.id.sucu);
        crearCuenta= (Button) findViewById(R.id.crearr);
        masSucursal= (TextView) findViewById(R.id.osuco);

        crearCuenta.setOnClickListener(this);
        masSucursal.setOnClickListener(this);

        requestQueue= Volley.newRequestQueue(this);


        Intent intent = getIntent();
        String nombre =intent.getStringExtra("nombre");
        String user = intent.getStringExtra("usuario");
        String apellidoMa = intent.getStringExtra("apellidoM");
        String apellidoPa = intent.getStringExtra("apellidoP");
        String sucu = intent.getStringExtra("sucursal");
        String contra = intent.getStringExtra("password");
        String sucursal2 = intent.getStringExtra("sucursal2");
        String sucursal3 = intent.getStringExtra("sucursal3");
        String sucursal4 = intent.getStringExtra("sucursal4");

        s2= sucursal2;
        s3= sucursal3;
        s4=sucursal4;



        nombreAdmi.setText(nombre);
        apellidoP.setText(apellidoPa);
        apellidoM.setText(apellidoMa);
        sucursal.setText(sucu);
        usuario.setText(user);
        password.setText(contra);
    }

    public void agregarAdmi(){
             url = "https://sgvshop.000webhostapp.com/guardarAdministrador.php?usuario="+usuario.getText().toString()+"&contrasena="+password.getText().toString()+
                    "&nombre="+nombreAdmi.getText().toString()+"&apellidoP="+apellidoP.getText().toString()+"&apellidoM="+apellidoM.getText().toString()+"&sucursal="+sucursal.getText().toString()+"&sucursal2="+s2+"&sucursal3="+s3+"&sucursal4="+s4;


        if(nombreAdmi.getText().toString().trim().equalsIgnoreCase("")){
            nombreAdmi.setError("Ingrese un nombre");
        }else if (apellidoM.getText().toString().trim().equalsIgnoreCase("")){
            apellidoM.setError("Ingrese un apellido");
        }else if (apellidoP.getText().toString().trim().equalsIgnoreCase("")){
            apellidoP.setError("Ingrese un apellido");
        }else if (sucursal.getText().toString().trim().equalsIgnoreCase("")){
            sucursal.setError("Ingrese una sucursal");
        }else if (usuario.getText().toString().trim().equalsIgnoreCase("")){
            usuario.setError("Ingrese un usuario");
        }else if (password.getText().toString().trim().equalsIgnoreCase("")){
            password.setError("Ingrese una contraseña");
        }else {

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    nombreAdmi.setText("");
                    apellidoM.setText("");
                    apellidoP.setText("");
                    usuario.setText("");
                    password.setText("");
                    sucursal.setText("");
                    Toast.makeText(formulario_empresa.this, "Registro Exitoso, Inicie Sesión.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(formulario_empresa.this, login_empresa.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(formulario_empresa.this);
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
        switch(view.getId()){
            case R.id.crearr:
                agregarAdmi();
                //Toast.makeText(formulario_empresa.this,"Hecho.",Toast.LENGTH_SHORT).show();
            break;

            case R.id.osuco:

               Intent intent2 = new Intent(formulario_empresa.this,masSucursales.class);
                intent2.putExtra("nombre",nombreAdmi.getText().toString());
                intent2.putExtra("apellidoM",apellidoM.getText().toString());
                intent2.putExtra("apellidoP",apellidoP.getText().toString());
                intent2.putExtra("usuario",usuario.getText().toString());
                intent2.putExtra("password",password.getText().toString());
                intent2.putExtra("sucursal",sucursal.getText().toString());
               startActivity(intent2);
            break;
        }
    }
}
