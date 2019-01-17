package com.orders.home.ordersHome;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class login_repartidor extends AppCompatActivity implements View.OnClickListener {
    Button entrar;
    TextView usuario, password,si;
    String id;
    ArrayList<pedidos> registros;
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    String idRepartidor;
    int contador;
    double stop = 0;
    ArrayList<Cromosoma> cromoList;

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
//

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

                        Deliver[] orders = new Deliver[json.length()];

                        for (int i = 0; i < json.length(); i++) {
                            lista = new pedidos();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            int idPedido = jsonObject.getInt("idPedido");
                            int idUsuario = jsonObject.getInt("idUsuario");
                            String nombreCliente = jsonObject.getString("nombreCliente");
                            String direccion = jsonObject.getString("idireccion");
                            int kilos = jsonObject.getInt("kilos");
                            String horaEntrega = jsonObject.getString("horaEntrega");
                            String[] time = horaEntrega.split(":");

                            String tipoTortilla = jsonObject.getString("tipoTortilla");

                            orders[i] = new Deliver(kilos,Integer.parseInt(time[0]), Integer.parseInt(time[1]),tipoTortilla, idPedido);

                        }
                        cromoList = poblacionInicial(orders);
                        int index = 0;
                        while (stop(10000)){
                            index = seleccion();
                            ArrayList<Cromosoma> hijos = crossover(index);

                            for (int i = 0; i < hijos.size(); i++){
                                cromoList.add(hijos.get(i));
                            }
                            hijos.clear();
                        }

                        Deliver[] rutas = cromoList.get(index).getCromoToDeliver();
                        for (int i = 0; i < rutas.length; i++){

                            lista.setIdPedido(rutas[i].getId());
                            lista.setIdUsuario(0);
                            lista.setNombreCliente("Cliente "+i);
                            lista.setDireccion("Direccion");
                            lista.setKilos(rutas[i].getKdt());
                            lista.setHoraEntrega(rutas[i].getHour()+":"+rutas[i].getMinute());
                            lista.setTipoTortilla(rutas[i].getTipoTortilla());
                            registros.add(lista);
                            System.out.println("Registros: "+registros.toString());
                            contador++;
                        }

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

    public ArrayList<Cromosoma> poblacionInicial(Deliver[] pedidos){
        Deliver[] tempDeliver = new Deliver[pedidos.length];
        tempDeliver[0] = pedidos[0];
        tempDeliver[pedidos.length-1] = pedidos[pedidos.length-1];
        ArrayList<Deliver> shuff = new ArrayList<>();
        ArrayList<Cromosoma> cromoList = new ArrayList<>();


        for (int j = 1; j < pedidos.length - 1; j++){
            shuff.add(pedidos[j]);
        }

        for (int i = 0;  i < 10 ; i++){
            Collections.shuffle(shuff, new Random(i));

            for (int j = 0; j < shuff.size(); j++){
                tempDeliver[j+1] = shuff.get(j);
            }

            cromoList.add(new Cromosoma(tempDeliver.clone()));

        }
        return cromoList;
    }

    public int seleccion(){
        double bestFitness = cromoList.get(0).fitness(0);
        double currentFitness;
        int index = 0;

        for (int i = 0; i < cromoList.size(); i++){
            currentFitness = cromoList.get(i).fitness();
            if (currentFitness < bestFitness) {
                bestFitness = currentFitness;
                index = i;
            }
        }
        return index;
    }
    public ArrayList<Cromosoma> crossover(int index){
        Deliver[] parentOne = cromoList.get(index).getCromoToDeliver();
        Cromosoma cromo1 = null;
        Cromosoma cromo2 = null;
        ArrayList<Cromosoma> cromoChild = new ArrayList<>();

        for (int i = 0; i < cromoList.size(); i++) {
            double R = Math.random();
            if(i != index){ //&& R < 0.01){
                //System.out.println("cruza: " + index + " + " + i);
                Deliver[] parentTwo = cromoList.get(i).getCromoToDeliver();
                cromo1 = new Cromosoma(getChildRoute(parentOne, parentTwo));
                cromo2 = new Cromosoma(getChildRoute(parentTwo, parentOne));

                if(!cromo1.equals(cromo2)){
                    cromoChild.add(cromo1);
                }

                if(!cromo2.equals(cromo1)){
                    cromoChild.add(cromo2);
                }
            }
        }
        return cromoChild;
    }
    public Deliver[] getChildRoute(Deliver[] route1, Deliver[] route2){
        Deliver[] toReturn = new Deliver[route2.length];
        ArrayList<Deliver> temp = new ArrayList();
        Random r = new Random();

        int routeLen = route1.length;

        int indexOne = r.nextInt((route1.length - 2) + 1) + 1;
        int indexTwo = r.nextInt((route2.length - 2) + 1) + 1;

        while(indexTwo < indexOne){
            indexTwo = r.nextInt((route2.length - 2) + 1) + 1;
        }

        Deliver d = new Deliver(-1, -1, -1, "null", -1);
        for (int i = 0; i < routeLen; i++) {
            toReturn[i] = d;
        }

        for (int i = indexOne; i < indexTwo; i++) {
            toReturn[i] = route1[i];
        }
        ArrayList<Deliver> y = new ArrayList();
        int j = 0;
        for (int i = 1; i < route2.length-1; i++) {
            if(arrayContains(toReturn, route2[i].getId()) == false){
                y.add(route2[i]);
            }
        }
        j = 0;
        for (int i = 1; i < toReturn.length-1; i++) {
            if (toReturn[i].getNeighbor() == -1) {
                toReturn[i] = y.get(j);
                if(j < y.size()-1)
                    j++;
            }
        }
        toReturn[0] = route1[0];
        toReturn[routeLen - 1] = route1[0];
        return toReturn;
    }
    public boolean arrayContains(Deliver[] child, int id){
        int cont = 0;
        for (int i = 1; i < child.length-1; i++) {
            if(id == child[i].getId()){
                cont++;
            }
        }
        if (cont == 0) {
            return false;
        }else
            return true;
    }

    public boolean stop(int iterations){
        int size = cromoList.size();
        int index = 0;
        int cont = 0;

        double bestFitness = cromoList.get(0).fitness();
        double currentFitness = 0;

        for (int i = 0; i < size; i++) {
            currentFitness = cromoList.get(i).fitness();
            if(currentFitness < bestFitness){
                bestFitness = currentFitness;
                index = i;
            }
        }

        if(stop != currentFitness){
            stop = currentFitness;
            return true;
        }
        for (int i = 0; i < cromoList.size(); i++) {
            currentFitness = cromoList.get(i).fitness();
            if (currentFitness == bestFitness) {
                cont++;
            }
        }

        if(cont >= iterations && cont >= cromoList.size()/2){
            return false;
        }
        return true;
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
