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
import model.Pieza;

public class PiezaDao {

    Conexion conexion = new Conexion();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    private Pieza toObject(ResultSet rs) {
        Pieza objeto = null;
        try {
            objeto = new Pieza();
            objeto.setId(rs.getInt(1));
            objeto.setTipo(rs.getString(2));
            objeto.setCosto(rs.getDouble(3));
            objeto.setStock(rs.getInt(4));
        } catch (SQLException e) {
            Message.VALUE = e.getMessage();
            System.out.println("Excepcion: toObject Pieza" + e.getMessage());
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
            System.out.println("Error in executeQuery Pieza: " + e.getMessage());
        }
        return false;
    }

    private ArrayList<Pieza> executeQueryWithResult(String query) {
        ArrayList<Pieza> lista = new ArrayList<>();
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
            System.out.println("Error in executeQueryWithResult Pieza" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Pieza> list(String filter, String order, String stock) {
        String query = "SELECT * FROM Pieza "
                + "WHERE tipo LIKE '" + filter + "%' "
                + "ORDER BY stock " + order;
        switch (stock) {
            case "por agotarse/agotado":
                query = "SELECT * FROM Pieza "
                        + "WHERE tipo LIKE '" + filter + "%' "
                        + "AND stock < 3 "
                        + "ORDER BY stock " + order;
                break;
            case "suficiente":
                query = "SELECT * FROM Pieza "
                        + "WHERE tipo LIKE '" + filter + "%' "
                        + "AND stock >= 3 "
                        + "ORDER BY stock " + order;
                break;
        }
        return executeQueryWithResult(query);
    }

    public Pieza search(int id) {
        String query = "SELECT * FROM Pieza "
                + "WHERE id = " + id;
        ArrayList<Pieza> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Pieza searchByTipo(String tipo) {
        String query = "SELECT * FROM Pieza "
                + "WHERE tipo = '" + tipo + "'";
        ArrayList<Pieza> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean add(Pieza objeto) {
        String query = "INSERT INTO Pieza "
                + "(tipo, costo, stock)"
                + "VALUES ("
                + "'" + objeto.getTipo() + "', "
                + "" + objeto.getCosto() + ", "
                + "" + objeto.getStock() + " "
                + ")";
        return executeQuery(query);
    }

    public boolean edit(Pieza objeto) {
        String query = "UPDATE Pieza SET "
                + "tipo = '" + objeto.getTipo() + "', "
                + "costo = " + objeto.getCosto() + ", "
                + "stock = " + objeto.getStock() + " "
                + "WHERE id = " + objeto.getId();
        return executeQuery(query);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Pieza WHERE id = " + id;
        return executeQuery(query);
    }

}