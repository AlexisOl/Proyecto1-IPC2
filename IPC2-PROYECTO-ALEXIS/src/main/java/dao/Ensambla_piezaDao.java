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
import model.Ensambla_pieza;
import model.Pieza;

public class Ensambla_piezaDao {

    Conexion conexion = new Conexion();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    MuebleDao muebleDao = new MuebleDao();
    PiezaDao piezaDao = new PiezaDao();

    private Ensambla_pieza toObject(ResultSet rs) {
        Ensambla_pieza objeto = null;
        try {
            objeto = new Ensambla_pieza();
            objeto.setId(rs.getInt(1));
            objeto.setMueble(muebleDao.search(rs.getInt(2)));
            objeto.setPieza(piezaDao.search(rs.getInt(3)));
            objeto.setCantidad(rs.getInt(4));
        } catch (SQLException e) {
            Message.VALUE = e.getMessage();
            System.out.println("Excepcion: toObject Ensambla_pieza" + e.getMessage());
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
            System.out.println("Error in executeQuery Ensambla_pieza: " + e.getMessage());
        }
        return false;
    }

    private ArrayList<Ensambla_pieza> executeQueryWithResult(String query) {
        ArrayList<Ensambla_pieza> lista = new ArrayList<>();
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
            System.out.println("Error in executeQueryWithResult Ensambla_pieza" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Ensambla_pieza> list(String filter) {
        String query = "SELECT e.id, e.mueble_id, e.pieza_id, e.cantidad "
                + "FROM Ensambla_pieza e "
                + "INNER JOIN Mueble m ON m.id = e.mueble_id "
                + "INNER JOIN Pieza p ON p.id = e.pieza_id "
                + "WHERE p.tipo LIKE '" + filter + "%' "
                + "OR m.nombre LIKE '" + filter + "%' "
                + "ORDER BY e.mueble_id asc";
        return executeQueryWithResult(query);
    }

    public boolean stockSuficiente(int mueble_id) {
        String query = "SELECT * FROM Ensambla_pieza "
                + "WHERE mueble_id = " + mueble_id;
        ArrayList<Ensambla_pieza> ensambla = executeQueryWithResult(query);
        for (Ensambla_pieza ensambla_pieza : ensambla) {
            if (ensambla_pieza.getPieza().getStock() < ensambla_pieza.getCantidad()) {
                return false;
            }
        }
        return true;
    }

    public void actualizarStock(int mueble_id) {
        String query = "SELECT * FROM Ensambla_pieza "
                + "WHERE mueble_id = " + mueble_id;
        ArrayList<Ensambla_pieza> ensambla = executeQueryWithResult(query);
        for (Ensambla_pieza ensambla_pieza : ensambla) {
            Pieza pieza = ensambla_pieza.getPieza();
            pieza.setStock(pieza.getStock() - ensambla_pieza.getCantidad());
            piezaDao.edit(pieza);
        }
    }

    public Ensambla_pieza search(int id) {
        String query = "SELECT * FROM Ensambla_pieza "
                + "WHERE id = " + id;
        ArrayList<Ensambla_pieza> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean add(Ensambla_pieza objeto) {
        String query = "INSERT INTO Ensambla_pieza "
                + "(mueble_id, pieza_id, cantidad)"
                + "VALUES ("
                + "" + objeto.getMueble().getId() + ", "
                + "" + objeto.getPieza().getId() + ", "
                + "" + objeto.getCantidad() + " "
                + ")";
        return executeQuery(query);
    }

    public boolean edit(Ensambla_pieza objeto) {
        String query = "UPDATE Ensambla_pieza SET "
                + "mueble_id = " + objeto.getMueble().getId() + ", "
                + "pieza_id = " + objeto.getPieza().getId() + ", "
                + "cantidad = " + objeto.getCantidad() + " "
                + "WHERE id = " + objeto.getId();
        return executeQuery(query);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Ensambla_pieza WHERE id = " + id;
        return executeQuery(query);
    }

}
