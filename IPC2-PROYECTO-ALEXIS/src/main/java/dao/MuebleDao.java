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
import model.Mueble;

public class MuebleDao {

    Conexion conexion = new Conexion();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    private Mueble toObject(ResultSet rs) {
        Mueble objeto = null;
        try {
            objeto = new Mueble();
            objeto.setId(rs.getInt(1));
            objeto.setNombre(rs.getString(2));
            objeto.setPrecio(rs.getDouble(3));
        } catch (SQLException e) {
            Message.VALUE = e.getMessage();
            System.out.println("Excepcion: toObject Mueble" + e.getMessage());
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
            System.out.println("Error in executeQuery Mueble: " + e.getMessage());
        }
        return false;
    }

    private ArrayList<Mueble> executeQueryWithResult(String query) {
        ArrayList<Mueble> lista = new ArrayList<>();
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
            System.out.println("Error in executeQueryWithResult Mueble" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Mueble> list(String filter) {
        String query = "SELECT * FROM Mueble "
                + "WHERE nombre LIKE '" + filter + "%' "
                + "ORDER BY nombre asc";
        return executeQueryWithResult(query);
    }

    public Mueble getMasVendido(String inicio, String fin) {
        String query = "SELECT m.id, m.nombre, m.precio\n"
                + "FROM Factura f\n"
                + "INNER JOIN Detalle_factura df ON f.id = df.factura_id\n"
                + "INNER JOIN Ensamblar_mueble em ON em.id = df.ensamblar_mueble_id\n"
                + "INNER JOIN Mueble m ON m.id = em.mueble_id\n"
                + "WHERE f.fecha BETWEEN '" + inicio + "' AND '" + fin + "'\n"
                + "GROUP BY m.id\n"
                + "HAVING COUNT(m.id) = (\n"
                + "	SELECT MAX(t.cantidad) \n"
                + "	FROM (\n"
                + "		SELECT COUNT(m1.id) AS cantidad \n"
                + "		FROM Factura f1 \n"
                + "        INNER JOIN Detalle_factura df1 ON f1.id = df1.factura_id\n"
                + "		INNER JOIN Ensamblar_mueble em1 ON em1.id = df1.ensamblar_mueble_id\n"
                + "		INNER JOIN Mueble m1 ON m1.id = em1.mueble_id\n"
                + "        WHERE f1.fecha BETWEEN '" + inicio + "' AND '" + fin + "'\n"
                + "		GROUP BY f1.usuario_id ) t\n"
                + "	)";
        ArrayList<Mueble> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }
    
    public Mueble getMenosVendido(String inicio, String fin) {
        String query = "SELECT m.id, m.nombre, m.precio\n"
                + "FROM Factura f\n"
                + "INNER JOIN Detalle_factura df ON f.id = df.factura_id\n"
                + "INNER JOIN Ensamblar_mueble em ON em.id = df.ensamblar_mueble_id\n"
                + "INNER JOIN Mueble m ON m.id = em.mueble_id\n"
                + "WHERE f.fecha BETWEEN '" + inicio + "' AND '" + fin + "'\n"
                + "GROUP BY m.id\n"
                + "HAVING COUNT(m.id) = (\n"
                + "	SELECT MIN(t.cantidad) \n"
                + "	FROM (\n"
                + "		SELECT COUNT(m1.id) AS cantidad \n"
                + "		FROM Factura f1 \n"
                + "        INNER JOIN Detalle_factura df1 ON f1.id = df1.factura_id\n"
                + "		INNER JOIN Ensamblar_mueble em1 ON em1.id = df1.ensamblar_mueble_id\n"
                + "		INNER JOIN Mueble m1 ON m1.id = em1.mueble_id\n"
                + "        WHERE f1.fecha BETWEEN '" + inicio + "' AND '" + fin + "'\n"
                + "		GROUP BY f1.usuario_id ) t\n"
                + "	)";
        ArrayList<Mueble> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Mueble search(int id) {
        String query = "SELECT * FROM Mueble "
                + "WHERE id = " + id;
        ArrayList<Mueble> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Mueble searchByNombre(String nombre) {
        String query = "SELECT * FROM Mueble "
                + "WHERE nombre = '" + nombre + "'";
        ArrayList<Mueble> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean add(Mueble objeto) {
        String query = "INSERT INTO Mueble "
                + "(nombre, precio)"
                + "VALUES ("
                + "'" + objeto.getNombre() + "', "
                + "" + objeto.getPrecio() + " "
                + ")";
        return executeQuery(query);
    }

    public boolean edit(Mueble objeto) {
        String query = "UPDATE Mueble SET "
                + "nombre = '" + objeto.getNombre() + "', "
                + "precio = " + objeto.getPrecio() + " "
                + "WHERE id = " + objeto.getId();
        return executeQuery(query);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Mueble WHERE id = " + id;
        return executeQuery(query);
    }

}
