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
public class Detalle_factura {

    private int id;
    private Factura factura;
    private Ensamblar_mueble ensamblar_mueble;

    public Detalle_factura() {
    }

    public Detalle_factura(int id, Factura factura, Ensamblar_mueble ensamblar_mueble) {
        this.id = id;
        this.factura = factura;
        this.ensamblar_mueble = ensamblar_mueble;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Ensamblar_mueble getEnsamblar_mueble() {
        return ensamblar_mueble;
    }

    public void setEnsamblar_mueble(Ensamblar_mueble ensamblar_mueble) {
        this.ensamblar_mueble = ensamblar_mueble;
    }

    @Override
    public String toString() {
        return "Detalle_factura{" + "id=" + id + ", factura=" + factura + ", ensamblar_mueble=" + ensamblar_mueble + '}';
    }

}
