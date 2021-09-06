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
public class Usuario {

    private int id;
    private String username;
    private String password;
    private int tipo;
    private String estado;

    public Usuario() {
    }

    public Usuario(int id, String username, String password, int tipo, String estado) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoUsuario() {
        switch (tipo) {
            case 1:
                return "Área de fábrica";
            case 2:
                return "Área de punto de venta";
            case 3:
                return "Área de administración";
        }
        return "Sin tipo";
    }

    @Override
    public String toString() {
        return username;
    }

}
