package com.example.proyectoappmovil.entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {
    private String idUsuario;
    private String nombre;
    private String apellido;
    private Login login;
    public Usuario() {
    }
    protected Usuario(Parcel in) {
        idUsuario = in.readString();
        nombre = in.readString();
        apellido = in.readString();
        login = in.readParcelable(Login.class.getClassLoader());
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public Usuario(String nombre, String apellido, Login login) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.login = login;
    }

    public Usuario(String idUsuario, String nombre, String apellido, Login login) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.login = login;
    }



    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", Nombre='" + nombre + '\'' +
                ", Apellido='" + apellido + '\'' +
                ", login=" + login.toString() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idUsuario);
        parcel.writeString(nombre);
        parcel.writeString(apellido);
        parcel.writeParcelable(login, i);
    }
}
