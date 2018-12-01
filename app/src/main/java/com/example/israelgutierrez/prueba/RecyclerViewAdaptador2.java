package com.example.israelgutierrez.prueba;

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

public class RecyclerViewAdaptador2 extends RecyclerView.Adapter<RecyclerViewAdaptador2.ViewHolder> {

    static RequestQueue requestQueue;
    static int idPedido;
    public RecyclerViewAdaptador2() {

    }


    public static  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


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

        }

        public void setClicksListener() {
            notificar.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()){

                case R.id.notificar:
                    tomaDePedido();
                    NotificationCompat.Builder mBuilder;
                    NotificationManager mNotifyMgr =(NotificationManager) cont.getSystemService(NOTIFICATION_SERVICE);
                    int icono = R.mipmap.ic_launcher;
                    intent = new Intent(cont, lista_pedidos_por_entregar.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(cont, 0,intent, 0);
                    mBuilder = new NotificationCompat.Builder(cont);
                    mBuilder.setContentIntent (pendingIntent);
                    mBuilder.setSmallIcon(icono);
                    mBuilder.setContentTitle("OrdersHome pedido enviado");
                    mBuilder.setContentText("Su pedido de tortillas ha zarpado, ¡En hora buena!");
                    mBuilder.setVibrate(new long[] {100, 250, 100, 500});
                    mBuilder.setAutoCancel(true);
                    mNotifyMgr.notify(1, mBuilder.build());
                    break;
            }
        }

        private void tomaDePedido() {
            final String url = "https://sgvshop.000webhostapp.com/tomarPedido.php?idRepartidor="+idRepartidor+"&idPedido="+idPedido;

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    System.out.println("Toma de pedido lograda");
                    System.out.println("El idPedido es: "+idPedido);
                    System.out.println("El idRepartidor es: "+idRepartidor);
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
            requestQueue.add(request);
        }
    }

    public ArrayList<pedidos> pedidosLista;
    public static String idRepartidor;

    public RecyclerViewAdaptador2(ArrayList<pedidos> pedidosLista, String idRepartidor) {
        this.pedidosLista = pedidosLista;
        this.idRepartidor = idRepartidor;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pedidos,parent,false);
        RecyclerViewAdaptador2.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdaptador2.ViewHolder holder, int position) {
        idPedido = pedidosLista.get(position).getIdPedido();
        holder.nombre.setText(pedidosLista.get(position).getNombreCliente().toString());
        holder.direccion.setText(pedidosLista.get(position).getDireccion().toString());
        holder.horaEntrega.setText(pedidosLista.get(position).getHoraEntrega().toString());
        holder.kilos.setText(""+pedidosLista.get(position).getKilos());
        holder.tortilla.setText(pedidosLista.get(position).getTipoTortilla().toString());
        holder.setClicksListener();

        //holder.visualizarDireccion.setOnClickListener(this);
        //holder.notificar.setOnClickListener(this);

    }


    @Override
    public int getItemCount() {

        return pedidosLista.size();
    }
}
