package com.example.proyectoappmovil.entidades;

public class Pedido {
    private Integer idPedido;
    private String Fecha;
    private Double Total;
    private Detalle detalle;
    private Usuario usuario;

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pedido(String fecha, Double total, Detalle detalle, Usuario usuario) {
        Fecha = fecha;
        Total = total;
        this.detalle = detalle;
        this.usuario = usuario;
    }

    public Pedido(Integer idPedido, String fecha, Double total, Detalle detalle, Usuario usuario) {
        this.idPedido = idPedido;
        Fecha = fecha;
        Total = total;
        this.detalle = detalle;
        this.usuario = usuario;
    }
}
