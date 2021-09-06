/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author alexis
 */

import config.Conexion;
import helper.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Cliente;

public class ClienteDao {

    Conexion conexion = new Conexion();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    private Cliente toObject(ResultSet rs) {
        
        Cliente objeto = null;
        try {
            objeto = new Cliente();
            objeto.setId(rs.getInt(1));
            objeto.setNombre(rs.getString(2));
            objeto.setNit(rs.getString(3));
            objeto.setDireccion(rs.getString(4));
            objeto.setMunicipio(rs.getString(5));
            objeto.setDepartamento(rs.getString(6));
        } catch (SQLException e) {
            Message.VALUE = e.getMessage();
            System.out.println("Excepcion: toObject Cliente" + e.getMessage());
        }
        return objeto;
    }

    private boolean executeQuery(String query) {
        try {
            connection = conexion.getConnection();
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
            ps.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            Message.VALUE = e.getMessage();
            System.out.println("Error in executeQuery Cliente: " + e.getMessage());
        }
        return false;
    }

    private ArrayList<Cliente> executeQueryWithResult(String query) {
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            connection = conexion.getConnection();
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(toObject(rs));
            }
            rs.close();
            ps.close();
            connection.close();
        } catch (SQLException e) {
            Message.VALUE = e.getMessage();
            System.out.println("Error in executeQueryWithResult Cliente" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Cliente> list(String filter) {
        String query = "SELECT * FROM Cliente "
                + "WHERE nit LIKE '" + filter + "%' "
                + "ORDER BY nit asc";
        return executeQueryWithResult(query);
    }

    public Cliente search(int id) {
        String query = "SELECT * FROM Cliente "
                + "WHERE id = " + id;
        ArrayList<Cliente> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Cliente searchByNit(String nit) {
        String query = "SELECT * FROM Cliente "
                + "WHERE nit = '" + nit + "'";
        ArrayList<Cliente> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean add(Cliente objeto) {
        String query = "INSERT INTO Cliente "
                + "(nombre, nit, direccion, municipio, departamento)"
                + "VALUES ("
                + "'" + objeto.getNombre() + "', "
                + "'" + objeto.getNit() + "', "
                + "'" + objeto.getDireccion() + "', "
                + "'" + objeto.getMunicipio() + "', "
                + "'" + objeto.getDepartamento() + "' "
                + ")";
        if (objeto.getMunicipio() == null) {
            query = "INSERT INTO Cliente "
                    + "(nombre, nit, direccion)"
                    + "VALUES ("
                    + "'" + objeto.getNombre() + "', "
                    + "'" + objeto.getNit() + "', "
                    + "'" + objeto.getDireccion() + "' "
                    + ")";
        }
        return executeQuery(query);
    }

    public boolean edit(Cliente objeto) {
        String query = "UPDATE Cliente SET "
                + "nombre = '" + objeto.getNombre() + "', "
                + "nit = '" + objeto.getNit() + "', "
                + "direccion = '" + objeto.getDireccion() + "' "
                + "municipio = '" + objeto.getMunicipio() + "', "
                + "departamento = '" + objeto.getDepartamento() + "' "
                + "WHERE id = " + objeto.getId();
        return executeQuery(query);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Cliente WHERE id = " + id;
        return executeQuery(query);
    }

}
