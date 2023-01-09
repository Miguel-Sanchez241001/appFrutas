package com.example.proyectoappmovil.entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Fruta implements Parcelable {
    private String idFruta;
    private String nombre;
    private String precio;
    private String tipo;
    private String linkIMG;

    public Fruta() {
    }

    public Fruta(String idFruta, String nombre, String precio, String tipo, String linkIMG) {
        this.idFruta = idFruta;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.linkIMG = linkIMG;
    }

    public Fruta(String nombre, String precio, String tipo, String linkIMG) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.linkIMG = linkIMG;
    }

    public String getIdFruta() {
        return idFruta;
    }

    public void setIdFruta(String idFruta) {
        this.idFruta = idFruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLinkIMG() {
        return linkIMG;
    }

    public void setLinkIMG(String linkIMG) {
        this.linkIMG = linkIMG;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idFruta);
        dest.writeString(this.nombre);
        dest.writeString(this.precio);
        dest.writeString(this.tipo);
        dest.writeString(this.linkIMG);
    }

    public void readFromParcel(Parcel source) {
        this.idFruta = source.readString();
        this.nombre = source.readString();
        this.precio = source.readString();
        this.tipo = source.readString();
        this.linkIMG = source.readString();
    }

    protected Fruta(Parcel in) {
        this.idFruta = in.readString();
        this.nombre = in.readString();
        this.precio = in.readString();
        this.tipo = in.readString();
        this.linkIMG = in.readString();
    }

    public static final Creator<Fruta> CREATOR = new Creator<Fruta>() {
        @Override
        public Fruta createFromParcel(Parcel source) {
            return new Fruta(source);
        }

        @Override
        public Fruta[] newArray(int size) {
            return new Fruta[size];
        }
    };

    @Override
    public String toString() {
        return "Fruta{" +
                "idFruta='" + idFruta + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio='" + precio + '\'' +
                ", tipo='" + tipo + '\'' +
                ", linkIMG='" + linkIMG + '\'' +
                '}';
    }
}
