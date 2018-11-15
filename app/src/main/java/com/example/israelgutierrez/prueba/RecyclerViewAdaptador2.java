package com.example.israelgutierrez.prueba;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;

public class RecyclerViewAdaptador2 extends RecyclerView.Adapter<RecyclerViewAdaptador2.ViewHolder> {


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


        }

        public void setClicksListener() {
            notificar.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()){

                case R.id.notificar:
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
    }

    public ArrayList<pedidos> pedidosLista;

    public RecyclerViewAdaptador2(ArrayList<pedidos> pedidosLista) {
        this.pedidosLista = pedidosLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pedidos,parent,false);
        RecyclerViewAdaptador2.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdaptador2.ViewHolder holder, int position) {
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
