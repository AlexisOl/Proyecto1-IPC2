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
public class Cliente {

    private int id;
    private String nombre;
    private String nit;
    private String direccion;
    private String municipio;
    private String departamento;

    public Cliente() {
    }

    public Cliente(int id, String nombre, String nit, String direccion, String municipio, String departamento) {
        this.id = id;
        this.nombre = nombre;
        this.nit = nit;
        this.direccion = direccion;
        this.municipio = municipio;
        this.departamento = departamento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public boolean isValid() {
        return (!nit.trim().isEmpty() && !nombre.trim().isEmpty() && !direccion.trim().isEmpty());
    }

    public String informacion() {
        return "Nombre: " + nombre + "<br>Nit: " + nit + "<br>Dirección: " + direccion
                + "<br>Municipio: " + municipio + "<br>Departamento: " + departamento;
    }

    @Override
    public String toString() {
        return nit;
    }
}