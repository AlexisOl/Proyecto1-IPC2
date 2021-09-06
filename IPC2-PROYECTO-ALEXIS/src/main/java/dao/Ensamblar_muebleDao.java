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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.Ensamblar_mueble;

public class Ensamblar_muebleDao {

    Conexion conexion = new Conexion();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    MuebleDao muebleDao = new MuebleDao();
    UsuarioDao usuarioDao = new UsuarioDao();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Ensamblar_mueble toObject(ResultSet rs) {
        Ensamblar_mueble objeto = null;
        try {
            objeto = new Ensamblar_mueble();
            objeto.setId(rs.getInt(1));
            objeto.setMueble(muebleDao.search(rs.getInt(2)));
            objeto.setUsuario(usuarioDao.search(rs.getInt(3)));
            objeto.setIdentificador(rs.getString(4));
            objeto.setFecha(sdf.parse(rs.getString(5)));
            objeto.setEstado(rs.getString(6));
        } catch (Exception e) {
            Message.VALUE = e.getMessage();
            System.out.println("Excepcion: toObject Ensamblar_mueble" + e.getMessage());
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
            System.out.println("Error in executeQuery Ensamblar_mueble: " + e.getMessage());
        }
        return false;
    }

    private ArrayList<Ensamblar_mueble> executeQueryWithResult(String query) {
        ArrayList<Ensamblar_mueble> lista = new ArrayList<>();
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
            System.out.println("Error in executeQueryWithResult Ensamblar_mueble" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Ensamblar_mueble> list(String filter, String order, String state) {
        String query = "SELECT e.id, e.mueble_id, e.usuario_id, e.identificador,"
                + "e.fecha, e.estado "
                + "FROM Ensamblar_mueble e "
                + "INNER JOIN Mueble m ON m.id = e.mueble_id "
                + "WHERE m.nombre LIKE '" + filter + "%' ";
        if (!state.isEmpty()) {
            query += " AND estado = '" + state + "' ";
        }
        query += "ORDER BY fecha " + order;
        return executeQueryWithResult(query);
    }

    public ArrayList<Ensamblar_mueble> listByEstado(String filter, String state) {
        String query = "SELECT e.id, e.mueble_id, e.usuario_id, e.identificador,"
                + "e.fecha, e.estado "
                + "FROM Ensamblar_mueble e "
                + "INNER JOIN Mueble m ON m.id = e.mueble_id "
                + "WHERE (m.nombre LIKE '" + filter + "%' "
                + "OR e.identificador LIKE '" + filter + "%') ";
        if (!state.isEmpty()) {
            query += " AND estado = '" + state + "' ";
        }
        query += "ORDER BY fecha desc";
        return executeQueryWithResult(query);
    }

    public Ensamblar_mueble search(int id) {
        String query = "SELECT * FROM Ensamblar_mueble "
                + "WHERE id = " + id;
        ArrayList<Ensamblar_mueble> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Ensamblar_mueble searchByIdentificador(String identificador) {
        String query = "SELECT * FROM Ensamblar_mueble "
                + "WHERE identificador = '" + identificador + "'";
        ArrayList<Ensamblar_mueble> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean add(Ensamblar_mueble objeto) {
        String query = "INSERT INTO Ensamblar_mueble "
                + "(mueble_id, usuario_id, fecha, estado) "
                + "VALUES ("
                + "" + objeto.getMueble().getId() + ", "
                + "" + objeto.getUsuario().getId() + ", "
                + "'" + sdf.format(objeto.getFecha()) + "', "
                + "'Ensamblado' "
                + ")";
        return executeQuery(query);
    }

    public boolean edit(Ensamblar_mueble objeto) {
        String query = "UPDATE Ensamblar_mueble SET "
                + "mueble_id = " + objeto.getMueble().getId() + ", "
                + "usuario_id = " + objeto.getUsuario().getId() + ", "
                + "identificador = '" + objeto.getIdentificador() + "', "
                + "fecha = '" + sdf.format(objeto.getFecha()) + "', "
                + "estado = '" + objeto.getEstado() + "' "
                + "WHERE id = " + objeto.getId();
        return executeQuery(query);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Ensamblar_mueble WHERE id = " + id;
        return executeQuery(query);
    }

}
