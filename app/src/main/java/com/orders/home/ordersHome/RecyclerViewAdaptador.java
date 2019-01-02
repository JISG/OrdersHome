package com.orders.home.ordersHome;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static  class ViewHolder extends RecyclerView.ViewHolder{
        private TextView usuario,cantidad,direccion;

        public ViewHolder(View itemView) {
            super(itemView);

            usuario = (TextView) itemView.findViewById(R.id.usuario);
            direccion = (TextView) itemView.findViewById(R.id.direccion);
            cantidad = (TextView) itemView.findViewById(R.id.cantidad);

        }
    }

    public ArrayList<listaRegistros> pedidosLista;

    public RecyclerViewAdaptador(ArrayList<listaRegistros> pedidosLista) {
        this.pedidosLista = pedidosLista;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entregado,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.usuario.setText(pedidosLista.get(position).getNombreCliente().toString());
        holder.direccion.setText(pedidosLista.get(position).getDireccion().toString());
        holder.cantidad.setText(""+pedidosLista.get(position).getKilos());

    }

    @Override
    public int getItemCount() {

        return pedidosLista.size();
    }
}
