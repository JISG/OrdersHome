package com.example.israelgutierrez.prueba;

import android.os.Parcel;
import android.os.Parcelable;

public class Sucursales implements Parcelable{
    private int idSucursal;
    private String nombre;

    protected Sucursales(Parcel in) {
        idSucursal = in.readInt();
        nombre = in.readString();
    }

    public static final Creator<Sucursales> CREATOR = new Creator<Sucursales>() {
        @Override
        public Sucursales createFromParcel(Parcel in) {
            return new Sucursales(in);
        }

        @Override
        public Sucursales[] newArray(int size) {
            return new Sucursales[size];
        }
    };

    public Sucursales() {

    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idSucursal);
        parcel.writeString(nombre);
    }
}
