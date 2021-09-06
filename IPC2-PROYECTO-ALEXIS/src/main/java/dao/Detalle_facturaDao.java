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
import model.Detalle_factura;

public class Detalle_facturaDao {

    Conexion conexion = new Conexion();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    FacturaDao facturaDao = new FacturaDao();
    Ensamblar_muebleDao ensamblar_muebleDao = new Ensamblar_muebleDao();

    private Detalle_factura toObject(ResultSet rs) {
        Detalle_factura objeto = null;
        try {
            objeto = new Detalle_factura();
            objeto.setId(rs.getInt(1));
            objeto.setFactura(facturaDao.search(rs.getInt(2)));
            objeto.setEnsamblar_mueble(ensamblar_muebleDao.search(rs.getInt(3)));
        } catch (SQLException e) {
            Message.VALUE = e.getMessage();
            System.out.println("Excepcion: toObject Detalle_factura" + e.getMessage());
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
            System.out.println("Error in executeQuery Detalle_factura: " + e.getMessage());
        }
        return false;
    }

    private ArrayList<Detalle_factura> executeQueryWithResult(String query) {
        ArrayList<Detalle_factura> lista = new ArrayList<>();
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
            System.out.println("Error in executeQueryWithResult Detalle_factura" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Detalle_factura> list(String filter) {
        String query = "SELECT d.id, d.factura_id, d.ensamblar_mueble_id "
                + "FROM Detalle_factura d "
                + "INNER JOIN Factura f ON f.id = d.factura_id "
                + "WHERE f.codigo LIKE '" + filter + "%' ";
        return executeQueryWithResult(query);
    }

    public ArrayList<Detalle_factura> listByFactura(int factura_id) {
        String query = "SELECT * FROM Detalle_factura "
                + "WHERE factura_id = " + factura_id;
        return executeQueryWithResult(query);
    }

    public ArrayList<Detalle_factura> listByFecha(String fecha_inicio, String fecha_fin) {
        String query = "SELECT d.id, d.factura_id, d.ensamblar_mueble_id "
                + "FROM Detalle_factura d "
                + "INNER JOIN Factura f ON f.id = d.factura_id "
                + "WHERE f.fecha BETWEEN '" + fecha_inicio + "' AND '" + fecha_fin + "' "
                + "ORDER BY f.fecha asc";
        return executeQueryWithResult(query);
    }

    public ArrayList<Detalle_factura> listByFechaForGanancia(String fecha_inicio, String fecha_fin) {
        String query = "SELECT d.id, d.factura_id, d.ensamblar_mueble_id "
                + "FROM Detalle_factura d "
                + "LEFT JOIN Devolucion de ON d.id = de.detalle_factura_id "
                + "INNER JOIN Factura f ON f.id = d.factura_id "
                + "WHERE f.fecha BETWEEN '" + fecha_inicio + "' AND '" + fecha_fin + "' "
                + "AND de.id IS NULL "
                + "ORDER BY f.fecha asc";
        return executeQueryWithResult(query);
    }

    
    //----
    public ArrayList<Detalle_factura> listByFechaUsuario(int usuario_id, String fecha_inicio, String fecha_fin) {
        String query = "SELECT d.id, d.factura_id, d.ensamblar_mueble_id "
                + "FROM Detalle_factura d "
                + "INNER JOIN Factura f ON f.id = d.factura_id "
                + "WHERE f.fecha BETWEEN '" + fecha_inicio + "' AND '" + fecha_fin + "' "
                + "AND usuario_id = " + usuario_id + " "
                + "ORDER BY f.fecha asc";
        return executeQueryWithResult(query);
    }

    public ArrayList<Detalle_factura> listByFechaMueble(int mueble_id, String fecha_inicio, String fecha_fin) {
        String query = "SELECT d.id, d.factura_id, d.ensamblar_mueble_id "
                + "FROM Detalle_factura d "
                + "INNER JOIN Factura f ON f.id = d.factura_id "
                + "INNER JOIN Ensamblar_mueble em ON em.id = d.ensamblar_mueble_id "
                + "WHERE f.fecha BETWEEN '" + fecha_inicio + "' AND '" + fecha_fin + "' "
                + "AND em.mueble_id = " + mueble_id + " "
                + "ORDER BY f.fecha asc";
        return executeQueryWithResult(query);
    }

    public double getGananciaByFecha(String fecha_inicio, String fecha_fin) {
        String query = "SELECT d.id, d.factura_id, d.ensamblar_mueble_id "
                + "FROM Detalle_factura d "
                + "LEFT JOIN Devolucion de ON d.id = de.detalle_factura_id "
                + "INNER JOIN Factura f ON f.id = d.factura_id "
                + "WHERE f.fecha BETWEEN '" + fecha_inicio + "' AND '" + fecha_fin + "' "
                + "AND de.id IS NULL";
        String codigo = "";
        double ganancia = 0;
        ArrayList<Detalle_factura> lista = executeQueryWithResult(query);
        for (Detalle_factura detalle_factura : lista) {
            if (!detalle_factura.getFactura().getCodigo().equals(codigo)) {
                codigo = detalle_factura.getFactura().getCodigo();
                ganancia += detalle_factura.getFactura().getCosto();
                
            }
        }
        return ganancia;
    }

    public Detalle_factura search(int id) {
        String query = "SELECT * FROM Detalle_factura "
                + "WHERE id = " + id;
        ArrayList<Detalle_factura> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Detalle_factura search(String codigo_factura, String identificador_mueble) {
        String query = "SELECT df.id, df.factura_id, df.ensamblar_mueble_id "
                + "FROM Detalle_factura df "
                + "INNER JOIN Factura f ON f.id = df.factura_id "
                + "INNER JOIN Ensamblar_mueble em ON em.id = df.ensamblar_mueble_id "
                + "WHERE f.codigo = '" + codigo_factura + "' "
                + "AND em.identificador = '" + identificador_mueble + "'";
        ArrayList<Detalle_factura> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean devolucionValid(int id, String fecha) {
        String query = "SELECT df.id, df.factura_id, df.ensamblar_mueble_id "
                + "FROM Detalle_factura df "
                + "INNER JOIN Factura f ON f.factura_id = df.factura_id "
                + "WHERE date_add(f.fecha,interval 7 day) >= '" + fecha + "' "
                + "AND df.id = " + id;
        return executeQueryWithResult(query).isEmpty();
    }

    public boolean add(Detalle_factura objeto) {
        String query = "INSERT INTO Detalle_factura "
                + "(factura_id, ensamblar_mueble_id)"
                + "VALUES ("
                + "" + objeto.getFactura().getId() + ", "
                + "" + objeto.getEnsamblar_mueble().getId() + " "
                + ")";
        return executeQuery(query);
    }

    public boolean edit(Detalle_factura objeto) {
        String query = "UPDATE Detalle_factura SET "
                + "factura_id = " + objeto.getFactura().getId() + ", "
                + "ensamblar_mueble_id = " + objeto.getEnsamblar_mueble().getId() + " "
                + "WHERE id = " + objeto.getId();
        return executeQuery(query);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Detalle_factura WHERE id = " + id;
        return executeQuery(query);
    }

}