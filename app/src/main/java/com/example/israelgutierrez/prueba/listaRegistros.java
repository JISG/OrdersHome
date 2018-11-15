package com.example.israelgutierrez.prueba;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class listaRegistros implements Parcelable {
    private String nombreCliente;
    private int kilos;
    private String direccion;
    private int total=kilos;

    protected listaRegistros(Parcel in) {
        nombreCliente = in.readString();
        kilos = in.readInt();
        direccion = in.readString();
    }

    public listaRegistros() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombreCliente);
        dest.writeInt(kilos);
        dest.writeString(direccion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<listaRegistros> CREATOR = new Creator<listaRegistros>() {
        @Override
        public listaRegistros createFromParcel(Parcel in) {
            return new listaRegistros(in);
        }

        @Override
        public listaRegistros[] newArray(int size) {
            return new listaRegistros[size];
        }
    };

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getKilos() {
        return kilos;
    }

    public void setKilos(int kilos) {
        this.kilos = kilos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
