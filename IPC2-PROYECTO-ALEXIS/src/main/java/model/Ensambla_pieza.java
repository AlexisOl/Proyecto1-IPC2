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
public class Ensambla_pieza {

    private int id;
    private Mueble mueble;
    private Pieza pieza;
    private int cantidad;

    public Ensambla_pieza() {
    }

    public Ensambla_pieza(int id, Mueble mueble, Pieza pieza, int cantidad) {
        this.id = id;
        this.mueble = mueble;
        this.pieza = pieza;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Mueble getMueble() {
        return mueble;
    }

    public void setMueble(Mueble mueble) {
        this.mueble = mueble;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Ensambla_pieza{" + "id=" + id + ", mueble=" + mueble + ", pieza=" + pieza + ", cantidad=" + cantidad + '}';
    }

}
