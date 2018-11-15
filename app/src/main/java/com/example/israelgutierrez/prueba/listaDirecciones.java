package com.example.israelgutierrez.prueba;

import android.os.Parcel;
import android.os.Parcelable;

public class listaDirecciones implements Parcelable {
    private int idDireccion;
    private int idUsuario;
    private String direccion;
    private String latitud;
    private String longitud;
    private String tag;

    protected listaDirecciones(Parcel in) {
        idDireccion = in.readInt();
        idUsuario = in.readInt();
        direccion = in.readString();
        latitud = in.readString();
        longitud = in.readString();
        tag = in.readString();
    }

    public listaDirecciones(){

    }

    public static final Creator<listaDirecciones> CREATOR = new Creator<listaDirecciones>() {
        @Override
        public listaDirecciones createFromParcel(Parcel in) {
            return new listaDirecciones(in);
        }

        @Override
        public listaDirecciones[] newArray(int size) {
            return new listaDirecciones[size];
        }
    };

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getiDUsuario() {
        return idUsuario;
    }

    public void setiDUsuario(int iDUsuario) {
        this.idUsuario = iDUsuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idDireccion);
        parcel.writeInt(idUsuario);
        parcel.writeString(direccion);
        parcel.writeString(latitud);
        parcel.writeString(longitud);
        parcel.writeString(tag);
    }
}
