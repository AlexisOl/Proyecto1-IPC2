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
import model.Devolucion;

public class DevolucionDao {

    Conexion conexion = new Conexion();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    Detalle_facturaDao detalle_facturaDao = new Detalle_facturaDao();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Devolucion toObject(ResultSet rs) {
        Devolucion objeto = null;
        try {
            objeto = new Devolucion();
            objeto.setId(rs.getInt(1));
            objeto.setFecha_devolucion(sdf.parse(rs.getString(2)));
            objeto.setPerdida(rs.getDouble(3));
            objeto.setDetalle_factura(detalle_facturaDao.search(rs.getInt(4)));
        } catch (Exception e) {
            Message.VALUE = e.getMessage();
            System.out.println("Excepcion: toObject Devolucion" + e.getMessage());
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
            System.out.println("Error in executeQuery Devolucion: " + e.getMessage());
        }
        return false;
    }

    private ArrayList<Devolucion> executeQueryWithResult(String query) {
        ArrayList<Devolucion> lista = new ArrayList<>();
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
            System.out.println("Error in executeQueryWithResult Devolucion" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Devolucion> list(String filter) {
        String query = "SELECT d.id, d.fecha_devolucion, d.perdida, "
                + "d.detalle_factura_id "
                + "FROM Devolucion d "
                + "INNER JOIN Detalle_factura df ON df.id = d.detalle_factura_id "
                + "INNER JOIN Ensamblar_mueble e ON e.id = df.ensamblar_mueble_id "
                + "WHERE e.identificador LIKE '" + filter + "%' "
                + "ORDER BY d.fecha_devolucion desc";
        return executeQueryWithResult(query);
    }

    public ArrayList<Devolucion> list(String nit, String inicio, String fin) {
        String query = "SELECT d.id, d.fecha_devolucion, d.perdida, "
                + "d.detalle_factura_id "
                + "FROM Devolucion d "
                + "INNER JOIN Detalle_factura df ON df.id = d.detalle_factura_id "
                + "INNER JOIN Factura f ON f.id = df.factura_id "
                + "INNER JOIN Cliente c ON c.id = f.cliente_id "
                + "WHERE c.nit = '" + nit + "' "
                + "AND d.fecha_devolucion BETWEEN '" + inicio + "' AND '" + fin + "' "
                + "ORDER BY d.fecha_devolucion asc";
        return executeQueryWithResult(query);
    }
    public ArrayList<Devolucion> listByUsuarioFecha(int usuario_id, String inicio, String fin) {
        String query = "SELECT d.id, d.fecha_devolucion, d.perdida, "
                + "d.detalle_factura_id "
                + "FROM Devolucion d "
                + "INNER JOIN Detalle_factura df ON df.id = d.detalle_factura_id "
                + "INNER JOIN Factura f ON f.id = df.factura_id "
                + "WHERE f.usuario_id = " + usuario_id + " "
                + "AND f.fecha BETWEEN '" + inicio + "' AND '" + fin + "' "
                + "ORDER BY d.fecha_devolucion asc";
        return executeQueryWithResult(query);
    }
    
    public ArrayList<Devolucion> listByFecha(String inicio, String fin) {
        String query = "SELECT * FROM Devolucion "
                + "WHERE fecha_devolucion BETWEEN '" + inicio + "' AND '" + fin + "' "
                + "ORDER BY fecha_devolucion asc";
        return executeQueryWithResult(query);
    }

    public Devolucion search(int id) {
        String query = "SELECT * FROM Devolucion "
                + "WHERE id = " + id;
        ArrayList<Devolucion> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean add(Devolucion objeto) {
        String query = "INSERT INTO Devolucion "
                + "(fecha_devolucion, perdida, detalle_factura_id)"
                + "VALUES ("
                + "'" + sdf.format(objeto.getFecha_devolucion()) + "', "
                + "" + objeto.getPerdida() + ", "
                + "" + objeto.getDetalle_factura().getId() + " "
                + ")";
        return executeQuery(query);
    }

    public boolean edit(Devolucion objeto) {
        String query = "UPDATE Devolucion SET "
                + "fecha_devolucion = '" + sdf.format(objeto.getFecha_devolucion()) + "', "
                + "perdida = " + objeto.getPerdida() + ", "
                + "detalle_factura_id = " + objeto.getDetalle_factura().getId() + " "
                + "WHERE id = " + objeto.getId();
        return executeQuery(query);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Devolucion WHERE id = " + id;
        return executeQuery(query);
    }

}
