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

public class Ensamblar_mueble {

    private int id;
    private Mueble mueble;
    private Usuario usuario;
    private String identificador;
    private Date fecha;
    private String estado;

    public Ensamblar_mueble() {
    }

    public Ensamblar_mueble(int id, Mueble mueble, Usuario usuario, String identificador, Date fecha, String estado) {
        this.id = id;
        this.mueble = mueble;
        this.usuario = usuario;
        this.identificador = identificador;
        this.fecha = fecha;
        this.estado = estado;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return identificador + ", " + mueble.getNombre();
    }

}
