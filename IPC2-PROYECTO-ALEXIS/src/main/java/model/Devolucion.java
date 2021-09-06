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

public class Devolucion {

    private int id;
    private Date fecha_devolucion;
    private double perdida;
    private Detalle_factura detalle_factura;

    public Devolucion() {
    }

    public Devolucion(int id, Date fecha_devolucion, double perdida, Detalle_factura detalle_factura) {
        this.id = id;
        this.fecha_devolucion = fecha_devolucion;
        this.perdida = perdida;
        this.detalle_factura = detalle_factura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha_devolucion() {
        return fecha_devolucion;
    }

    public void setFecha_devolucion(Date fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public double getPerdida() {
        return perdida;
    }

    public void setPerdida(double perdida) {
        this.perdida = perdida;
    }

    public Detalle_factura getDetalle_factura() {
        return detalle_factura;
    }

    public void setDetalle_factura(Detalle_factura detalle_factura) {
        this.detalle_factura = detalle_factura;
    }

    @Override
    public String toString() {
        return "Devolucion{" + "id=" + id + ", fecha_devolucion=" + fecha_devolucion + ", perdida=" + perdida + ", detalle_factura=" + detalle_factura + '}';
    }

}
