package com.example.israelgutierrez.prueba;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class pedidos implements Parcelable {
    private int idUsuario;
    private int idRepartidor;
    private String nombreCliente;
    private String direccion;
    private float kilos;
    private String horaEntrega;
    private String fecha;
    private String nombreSucursal;
    private String tipoTortilla;

    protected pedidos(Parcel in) {
        idUsuario = in.readInt();
        idRepartidor = in.readInt();
        nombreCliente = in.readString();
        direccion = in.readString();
        kilos = in.readFloat();
        horaEntrega = in.readString();
        fecha = in.readString();
        nombreSucursal = in.readString();
        tipoTortilla = in.readString();
    }

    public static final Creator<pedidos> CREATOR = new Creator<pedidos>() {
        @Override
        public pedidos createFromParcel(Parcel in) {
            return new pedidos(in);
        }

        @Override
        public pedidos[] newArray(int size) {
            return new pedidos[size];
        }
    };

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(int idRepartidor) {
        this.idRepartidor = idRepartidor;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getKilos() {
        return kilos;
    }

    public void setKilos(float kilos) {
        this.kilos = kilos;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    public pedidos() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idUsuario);
        parcel.writeInt(idRepartidor);
        parcel.writeString(nombreCliente);
        parcel.writeString(direccion);
        parcel.writeFloat(kilos);
        parcel.writeString(horaEntrega);
        parcel.writeString(fecha);
        parcel.writeString(nombreSucursal);
        parcel.writeString(tipoTortilla);
    }

    public String getTipoTortilla() {
        return tipoTortilla;
    }

    public void setTipoTortilla(String tipoTortilla) {
        this.tipoTortilla = tipoTortilla;
    }
}
