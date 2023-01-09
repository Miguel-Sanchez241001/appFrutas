package com.example.proyectoappmovil.entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Login implements Parcelable {

    private String id;
    private String correo;
    private String password;
    private Integer admin;

    protected Login(Parcel in) {
        id = in.readString();
        correo = in.readString();
        password = in.readString();
        if (in.readByte() == 0) {
            admin = null;
        } else {
            admin = in.readInt();
        }
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Login(String correo, String password, Integer admin) {
        this.correo = correo;
        this.password = password;
        this.admin = admin;
    }

    public Login(String id, String correo, String password, Integer admin) {
        this.id = id;
        this.correo = correo;
        this.password = password;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "Login{" +
                "id='" + id + '\'' +
                ", Correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", Admin=" + admin +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(correo);
        parcel.writeString(password);
        if (admin == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(admin);
        }
    }
}
