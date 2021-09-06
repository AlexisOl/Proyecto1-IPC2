/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author alexis
 */
import java.util.Date;

public class Factura {

    private int id;
    private String codigo;
    private Cliente cliente;
    private Usuario usuario;
    private Date fecha;
    private double costo;

    public Factura() {
    }

    public Factura(int id, String codigo, Cliente cliente, Usuario usuario, Date fecha, double costo) {
        this.id = id;
        this.codigo = codigo;
        this.cliente = cliente;
        this.usuario = usuario;
        this.fecha = fecha;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Factura{" + "id=" + id + ", codigo=" + codigo + ", cliente=" + cliente + ", usuario=" + usuario + ", fecha=" + fecha + ", costo=" + costo + '}';
    }

}
