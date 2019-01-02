package com.orders.home.prueba;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

public class RecyclerViewAdaptador2 extends RecyclerView.Adapter<RecyclerViewAdaptador2.ViewHolder> implements View.OnClickListener {
    static RequestQueue requestQueue;
    static RequestQueue requestQueue2;
     int idPedido;
     int posicion;
    public ArrayList<pedidos> pedidosLista = new ArrayList<>();
    public  String idRepartidor;
     int idUsuario;
    private View.OnClickListener listener;

    public RecyclerViewAdaptador2() {

    }


    public RecyclerViewAdaptador2(ArrayList<pedidos> pedidosLista, String idRepartidor) {

        this.pedidosLista = pedidosLista;
        //System.out.println("Pedidos que llegan: "+pedidosLista);
        this.idRepartidor = idRepartidor;
    }

    @Override
    public void onClick(View view) {
        if(listener !=null){
            listener.onClick(view);
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        Context cont;


        private TextView nombre,kilos,direccion,horaEntrega,tortilla;
        private Button notificar;
        //private Button visualizarDireccion;

        public ViewHolder(View itemView) {
            super(itemView);
            cont = itemView.getContext();

            nombre = (TextView) itemView.findViewById(R.id.nombre);
            direccion = (TextView) itemView.findViewById(R.id.direccion);
            kilos = (TextView) itemView.findViewById(R.id.kilos);
            horaEntrega = (TextView) itemView.findViewById(R.id.horaEntrega);
            notificar = (Button) itemView.findViewById(R.id.notificar);
            //visualizarDireccion = (Button) itemView.findViewById(R.id.visualizarDireccion);
            tortilla = (TextView) itemView.findViewById(R.id.tortilla);
            requestQueue= Volley.newRequestQueue(cont);
            requestQueue2= Volley.newRequestQueue(cont);

        }

        public void setClicksListener() {
            notificar.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()){
                case R.id.notificar:
                    idUsuario = pedidosLista.get(getAdapterPosition()).getIdUsuario();

                    tomaDePedido();
                    enviarNotificacion();
                break;
            }
        }

        private void enviarNotificacion() {
            final String url = "https://sgvshop.000webhostapp.com/sendNotifications.php?idUsuario="+idUsuario;

            StringRequest request2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(cont);
                    builder.setMessage("Fallo en registro, contacte con el administrador!")
                            .setNegativeButton("Aceptar",null)
                            .create().show();
                    System.out.println("No se hizo el pedido");

                }
            });
            requestQueue2.add(request2);
        }



        private void tomaDePedido() {
            final String url = "https://sgvshop.000webhostapp.com/tomarPedido.php?idRepartidor="+idRepartidor+"&idPedido="+idPedido;

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //System.out.println("Notificacion enviada de su pedido ah sarpado");

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(cont);
                    builder.setMessage("Fallo en la toma de pedido, contacte con el administrador!")
                            .setNegativeButton("Aceptar",null)
                            .create().show();
                    //System.out.println("No se hizo el pedido");

                }
            });
            requestQueue.add(request);
        }
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pedidos,parent,false);
        RecyclerViewAdaptador2.ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdaptador2.ViewHolder holder, int position) {
        posicion=position;
        idPedido = pedidosLista.get(position).getIdPedido();
        holder.nombre.setText(pedidosLista.get(position).getNombreCliente().toString());
        holder.direccion.setText(pedidosLista.get(position).getDireccion().toString());
        holder.horaEntrega.setText(pedidosLista.get(position).getHoraEntrega().toString());
        holder.kilos.setText(""+pedidosLista.get(position).getKilos());
        holder.tortilla.setText(pedidosLista.get(position).getTipoTortilla().toString());
        holder.setClicksListener();

    }


    @Override
    public int getItemCount() {

        return pedidosLista.size();
    }
}
