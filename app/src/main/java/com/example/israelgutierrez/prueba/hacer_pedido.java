package com.example.israelgutierrez.prueba;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class hacer_pedido extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner opciones,direcciones;
    TextView user,pago;
    Button  horaEntrega,pedir;
    ImageView imgUser;
    String name;
    String sucursalSeleccionada;
    RadioGroup tipo;

    ArrayList<listaDirecciones> nuevaListaDirecciones = new ArrayList<>();

    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    //Widgets
    EditText etHora;
    ImageButton ibObtenerHora;

    //Spinner
    ArrayList<String> sucursales;
    ArrayList<String> tagDirecciones;
    ArrayList<String> lat;

    RequestQueue requestQueue2;
    RequestQueue requestQueue3;
    RequestQueue requestQueue;
    RequestQueue requestQueue4;
    Spinner prueba;
    String eleccion;
    //INSERT

    String fecha;
    String latitud;
    String longitud;
    String direccionPedido;
    String kilos;
    String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_pedido);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        sucursales = new ArrayList<>();
        tagDirecciones = new ArrayList<>();
        user = (TextView) findViewById(R.id.user);
        direcciones = (Spinner) findViewById(R.id.direcciones);

        //horaEntrega = (Button) findViewById(R.id.horaEntrega);
        pedir = (Button) findViewById(R.id.pedir);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        pago = (TextView) findViewById(R.id.pago);



        pedir.setOnClickListener(this);
        imgUser.setOnClickListener(this);

        etHora = (EditText) findViewById(R.id.et_mostrar_hora_picker);


        ibObtenerHora = (ImageButton) findViewById(R.id.ib_obtener_hora);

        ibObtenerHora.setOnClickListener(this);

        tipo = (RadioGroup) findViewById(R.id.tipo);

        requestQueue2= Volley.newRequestQueue(this);
        requestQueue3= Volley.newRequestQueue(this);
        requestQueue= Volley.newRequestQueue(this);
        requestQueue4 = Volley.newRequestQueue(this);

        prueba = (Spinner) findViewById(R.id.sucursales);

        prueba.setOnItemSelectedListener(this);
        direcciones.setOnItemSelectedListener(this);
        obtenerSucursales();

        Intent intent = getIntent();
         name = intent.getStringExtra("name");
        System.out.println("name: "+name);
        String username = intent.getStringExtra("username");
        String password = intent.getStringExtra("password");
        String id = intent.getStringExtra("idUsuario");
        String tipoUser = intent.getStringExtra("tipo");
        System.out.println("IdUsuario: "+id);
        idUsuario=id;
        guardarToken(idUsuario);
        obtenerDirecciones(id);


        user.setText("Bienvenido "+name);
        opciones = (Spinner) findViewById(R.id.spinner);
        opciones.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_item);
        opciones.setAdapter(adapter);
        pago.setText("");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        fecha = dateFormat.format(date);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imgUser:
                intent = new Intent(hacer_pedido.this, miCuenta.class);
                intent.putExtra("idUsuario",idUsuario);
                startActivity(intent);
                break;

            case R.id.ib_obtener_hora:
                obtenerHora();
                break;
            case R.id.pedir:
                hacePedido();

                AlertDialog.Builder builder = new AlertDialog.Builder(hacer_pedido.this);
                builder.setMessage("Tu pedido está en camino, Gracias por usar OrdersHome!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();
                etHora.setText("");
                pago.setText("$ ");
            break;
        }

    }

    private void obtenerHora(){
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                String horaFormateada =  (hourOfDay < 9)? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                String minutoFormateado = (minute < 9)? String.valueOf(CERO + minute):String.valueOf(minute);

                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }

                etHora.setText(horaFormateada + DOS_PUNTOS + minutoFormateado + " " + AM_PM);
            }

        }, hora, minuto, false);

        recogerHora.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(adapterView.getId()==R.id.spinner) {
            kilos  = (String) adapterView.getItemAtPosition(position);
            Double pagar = Double.valueOf((kilos));
            pago.setText("$ " + pagar * 15);
        }
        if(adapterView.getId()==R.id.sucursales){
            sucursalSeleccionada = (String) adapterView.getItemAtPosition(position);
        }

        if(adapterView.getId()==R.id.direcciones) {
            latitud = nuevaListaDirecciones.get(position).getLatitud();
            longitud = nuevaListaDirecciones.get(position).getLongitud();
            direccionPedido = nuevaListaDirecciones.get(position).getDireccion();

        }


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

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.example.israelgutierrez.prueba.hacer_pedido.this,android.R.layout.simple_spinner_item,sucursales);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    prueba.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(hacer_pedido.this);
                builder.setMessage("Fallo en registro, contacte con el administrador!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();

            }
        });
        requestQueue2.add(request);
    }

    public void obtenerDirecciones(String id){

        final String url = "https://sgvshop.000webhostapp.com/selecDirecciones.php?idUsuario="+id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    tagDirecciones.add("Selecciona");
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray json = jsonResponse.optJSONArray("direcciones");
                    boolean success =  jsonResponse.getBoolean("succes");
                    if(success) {
                        for (int i = 0; i < json.length(); i++) {
                            listaDirecciones listaDirecciones = new listaDirecciones();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            Log.d("Mensaje: ", "onResponse: "+jsonObject.getInt("idDireccion"));
                            listaDirecciones.setIdDireccion(jsonObject.getInt("idDireccion"));
                            listaDirecciones.setiDUsuario(jsonObject.getInt("idUsuario"));
                            listaDirecciones.setDireccion(jsonObject.getString("direccion"));
                            listaDirecciones.setLatitud(jsonObject.getString("latitud"));
                            listaDirecciones.setLongitud(jsonObject.getString("longitud"));
                            listaDirecciones.setTag(jsonObject.getString("tag"));
                            tagDirecciones.add((jsonObject.getString("tag")));
                            nuevaListaDirecciones.add(listaDirecciones);
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.example.israelgutierrez.prueba.hacer_pedido.this, android.R.layout.simple_spinner_item, tagDirecciones);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        direcciones.setAdapter(adapter);
                    }else{
                        Toast.makeText(hacer_pedido.this,"No hay regsitros",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(hacer_pedido.this);
                builder.setMessage("Fallo en registro, contacte con el administrador!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();

            }
        });
        requestQueue3.add(request);
    }

    public void hacePedido(){
        int radioButtonId = tipo.getCheckedRadioButtonId();


        View radioButton = tipo.findViewById(radioButtonId);
        int indice = tipo.indexOfChild(radioButton);


        RadioButton rb = (RadioButton) tipo.getChildAt(indice);
        String tipoTortilla = rb.getText().toString();

        etHora.setText(etHora.getText().toString().replace(" ","%20"));
        final String url = "https://sgvshop.000webhostapp.com/insertPedido.php?idUsuario="+idUsuario+"&nombreCliente="+name+"&direccion="+direccionPedido+
                "&kilos="+kilos+"&horaEntrega="+etHora.getText().toString()+"&fecha="+fecha+"&nombreSucursal="+sucursalSeleccionada+"&tipoTortilla="+tipoTortilla;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(hacer_pedido.this);
                builder.setMessage("Fallo en realización de pedido, contacte con el administrador!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();

            }
        });
        requestQueue.add(request);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(hacer_pedido.this);
                builder.setMessage("Fallo en Guardar Token!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();

            }
        });
        requestQueue4.add(request);

    }
}
