package com.example.israelgutierrez.prueba;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class prueba extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{
    public static final String TAG = prueba.class.getName();
    private static final String CERO = "0";
    private static final String DOS_PUNTOS = ":";
    private static final String BARRA = "/";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Hora
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    //Widgets
    EditText  etHora;
    ImageButton ibObtenerHora;
    Spinner prueba;
    TextView idCliente,idRepartidor,kilos,nombre,seleccion;

    //Spinner
    ArrayList<String> sucursales;
    RequestQueue requestQueue;

    //Recycler
    private RecyclerView recyclerViewPedidos;
    private RecyclerViewAdaptador adaptadorPedidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);



        sucursales = new ArrayList<>();
        etHora = (EditText) findViewById(R.id.et_mostrar_hora_picker);
        ibObtenerHora = (ImageButton) findViewById(R.id.ib_obtener_hora);
        requestQueue= Volley.newRequestQueue(this);
        ibObtenerHora.setOnClickListener(this);
        seleccion = (TextView) findViewById(R.id.seleccion);
        prueba = (Spinner) findViewById(R.id.prueba);

        prueba.setOnItemSelectedListener(this);
        obtenerSucursales();




       // seleccion.setText("pedo");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_obtener_hora:
                obtenerHora();
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        seleccion.setText(""+adapterView.getItemAtPosition(i));



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        seleccion.setText("hola");

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
                        sucursales.add(""+jsonObject.getInt("idSucursal")+" "+jsonObject.getString("nombre"));

                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.example.israelgutierrez.prueba.prueba.this,android.R.layout.simple_spinner_item,sucursales);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    prueba.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(prueba.this);
                builder.setMessage("Fallo en registro, contacte con el administrador!")
                        .setNegativeButton("Aceptar",null)
                        .create().show();

            }
        });
        requestQueue.add(request);
    }

}
