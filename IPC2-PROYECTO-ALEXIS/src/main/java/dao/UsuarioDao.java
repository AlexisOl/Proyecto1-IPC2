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
import model.Usuario;

public class UsuarioDao {

    Conexion conexion = new Conexion();
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;

    private Usuario toObject(ResultSet rs) {
        Usuario objeto = null;
        try {
            objeto = new Usuario();
            objeto.setId(rs.getInt(1));
            objeto.setUsername(rs.getString(2));
            objeto.setPassword(rs.getString(3));
            objeto.setTipo(rs.getInt(4));
            objeto.setEstado(rs.getString(5));
        } catch (SQLException e) {
            Message.VALUE = e.getMessage();
            System.out.println("Excepcion: toObject Usuario" + e.getMessage());
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
            System.out.println("Error in executeQuery Usuario: " + e.getMessage());
        }
        return false;
    }

    private ArrayList<Usuario> executeQueryWithResult(String query) {
        ArrayList<Usuario> lista = new ArrayList<>();
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
            System.out.println("Error in executeQueryWithResult Usuario" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<Usuario> list() {
        String query = "SELECT * FROM Usuario";
        return executeQueryWithResult(query);
    }

    public ArrayList<Usuario> list(int tipo) {
        String query = "SELECT * FROM Usuario "
                + "WHERE tipo = " + tipo + " "
                + "AND estado = 'Activo'";
        return executeQueryWithResult(query);
    }

    public Usuario getUsuarioMaxGanancia(String inicio, String fin) {
        String query = "SELECT u.id, u.username, u.password, u.tipo, u.estado\n"
                + "FROM Factura f\n"
                + "INNER JOIN usuario u ON u.id = f.usuario_id\n"
                + "WHERE f.fecha BETWEEN '" + inicio + "' AND '" + fin + "'\n"
                + "GROUP BY u.id\n"
                + "HAVING SUM(costo) = (\n"
                + "	SELECT MAX(t.costo_) \n"
                + "	FROM (\n"
                + "		SELECT SUM(f1.costo) AS costo_ \n"
                + "		FROM Factura f1 \n"
                + "        WHERE f1.fecha BETWEEN '" + inicio + "' AND '" + fin + "'\n"
                + "		GROUP BY f1.usuario_id ) t\n"
                + "	) ";
        ArrayList<Usuario> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Usuario getUsuarioMaxVenta(String inicio, String fin) {
        String query = "SELECT u.id, u.username, u.password, u.tipo, u.estado\n"
                + "FROM Factura f\n"
                + "INNER JOIN Usuario u ON u.id = f.usuario_id\n"
                + "WHERE f.fecha BETWEEN '" + inicio + "' AND '" + fin + "'\n"
                + "GROUP BY u.id\n"
                + "HAVING COUNT(f.id) = (\n"
                + "	SELECT MAX(t.cantidad) \n"
                + "	FROM (\n"
                + "		SELECT COUNT(f1.id) AS cantidad \n"
                + "		FROM Factura f1 \n"
                + "        WHERE f1.fecha BETWEEN '" + inicio + "' AND '" + fin + "'\n"
                + "		GROUP BY f1.usuario_id ) t\n"
                + "	)";
        ArrayList<Usuario> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Usuario search(int id) {
        String query = "SELECT * FROM Usuario "
                + "WHERE id = " + id;
        ArrayList<Usuario> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public Usuario searchByUsername(String username) {
        String query = "SELECT * FROM Usuario "
                + "WHERE username = '" + username + "'";
        ArrayList<Usuario> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }

    public boolean add(Usuario objeto) {
        String query = "INSERT INTO Usuario "
                + "(username, password, tipo, estado) "
                + "VALUES ("
                + "'" + objeto.getUsername() + "', "
                + "'" + objeto.getPassword() + "', "
                + "" + objeto.getTipo() + ", "
                + "'" + objeto.getEstado() + "' "
                + ")";
        return executeQuery(query);
    }

    public boolean edit(Usuario objeto) {
        String query = "UPDATE Usuario SET "
                + "username = '" + objeto.getUsername() + "', "
                + "password = '" + objeto.getPassword() + "', "
                + "tipo = " + objeto.getTipo() + ", "
                + "estado = '" + objeto.getEstado() + "' "
                + "WHERE id = " + objeto.getId();
        return executeQuery(query);
    }

    public boolean delete(int id) {
        String query = "DELETE FROM Usuario WHERE id = " + id;
        return executeQuery(query);
    }

    public boolean cancel(int id) {
        String query = "UPDATE Usuario SET estado = 'Cancelado' WHERE id = " + id;
        return executeQuery(query);
    }

    public Usuario login(String username, String password) {
        String query = "SELECT * FROM Usuario WHERE username = '" + username + "'"
                + " AND password = '" + password + "'";
        ArrayList<Usuario> lista = executeQueryWithResult(query);
        return lista.isEmpty() ? null : lista.get(0);
    }
}
