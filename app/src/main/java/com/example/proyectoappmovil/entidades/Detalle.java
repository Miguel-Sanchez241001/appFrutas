package com.example.proyectoappmovil.entidades;

public class Detalle {
    private Integer idDetale;
    private Fruta fruta;
    private Double Cantidad;
    private Double Precio;

    public Integer getIdDetale() {
        return idDetale;
    }

    public void setIdDetale(Integer idDetale) {
        this.idDetale = idDetale;
    }

    public Fruta getFruta() {
        return fruta;
    }

    public void setFruta(Fruta fruta) {
        this.fruta = fruta;
    }

    public Double getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Double cantidad) {
        Cantidad = cantidad;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public Detalle(Fruta fruta, Double cantidad, Double precio) {
        this.fruta = fruta;
        Cantidad = cantidad;
        Precio = precio;
    }

    public Detalle(Integer idDetale, Fruta fruta, Double cantidad, Double precio) {
        this.idDetale = idDetale;
        this.fruta = fruta;
        Cantidad = cantidad;
        Precio = precio;
    }
}
