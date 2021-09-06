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
import model.Factura;

public class FacturaDao {

    Conexion conexion = new Conexion();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    Ensamblar_muebleDao ensamblar_muebleDao = new Ensamblar_muebleDao();
    ClienteDao clienteDao = new ClienteDao();
    UsuarioDao usuarioDao = new UsuarioDao();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private Factura toObject(ResultSet rs) {
        Factura objeto = null;
        try {
            objeto = new Factura();
            objeto.setId(rs.getInt(1));
            objeto.setCodigo(rs.getString(2));
            objeto.setCliente(clienteDao.search(rs.getInt(3)));
            objeto.setUsuario(usuarioDao.search(rs.getInt(4)));
            objeto.setFecha(sdf.parse(rs.getString(5)));
            objeto.setCosto(rs.getDouble(6));
        } catch (Exception e) {
            Message.VALUE = e.getMessage();
            System.out.println("Excepcion: toObject Factura" + e.getMessage());
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
            System.out.println("Error in executeQuery Factura: " + e.getMessage());
        }
        return false;
    }

    private ArrayList<Factura> executeQueryWithResult(String query) {
        ArrayList<Factura> lista = new ArrayList<>();
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
            System.out.println("Error in executeQueryWithResult Factura" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Factura> list(String filter, String date) {
        String query = "SELECT f.id, f.codigo, f.cliente_id, f.usuario_id,"
                + "f.fecha, f.costo "
                + "FROM Factura f "
                + "INNER JOIN Cliente c ON c.id = f.cliente_id "
                + "WHERE (f.codigo LIKE '" + filter + "%' OR c.nit LIKE '" + filter + "%') "
                + "AND f.fecha LIKE '" + date + "%' "
                + "ORDER BY f.fecha desc";
        return executeQueryWithResult(query);
    }

    public ArrayList<Factura> listByClienteRangoFecha(String nit, String inicio, String fin) {
        String query = "SELECT f.id, f.codigo, f.cliente_id, f.usuario_id,"
                + "f.fecha, f.costo "
                + "FROM Factura f "
                + "INNER JOIN Cliente c ON c.id = f.cliente_id "
                + "WHERE c.nit = '" + nit + "' "
                + "AND f.fecha BETWEEN '" + inicio + "' AND '" + fin + "' "
                + "ORDER BY f.fecha asc";
        return executeQueryWithResult(query);
    }

    public Factura search(int id) {
        String query = "SELECT * FROM Factura "
                + "WHERE id = " + id;
        ArrayList<Factura> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Factura searchByCodigo(String codigo) {
        String query = "SELECT * FROM Factura "
                + "WHERE codigo = '" + codigo + "'";
        ArrayList<Factura> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean add(Factura objeto) {
        String query = "INSERT INTO Factura "
                + "(codigo, cliente_id, usuario_id,"
                + "fecha, costo)"
                + "VALUES ("
                + "'" + objeto.getCodigo() + "', "
                + "" + objeto.getCliente().getId() + ", "
                + "" + objeto.getUsuario().getId() + ", "
                + "'" + sdf.format(objeto.getFecha()) + "', "
                + "" + objeto.getCosto() + " "
                + ")";
        return executeQuery(query);
    }

    public boolean edit(Factura objeto) {
        String query = "UPDATE Factura SET "
                + "codigo = '" + objeto.getCodigo() + "', "
                + "cliente_id = " + objeto.getCliente().getId() + ", "
                + "usuario_id = " + objeto.getUsuario().getId() + ", "
                + "fecha = '" + sdf.format(objeto.getFecha()) + "', "
                + "costo = " + objeto.getCosto() + " "
                + "WHERE Id = " + objeto.getId();
        return executeQuery(query);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Factura WHERE id = " + id;
        return executeQuery(query);
    }

}
