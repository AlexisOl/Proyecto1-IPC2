/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.sql.Connection;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexis
 */

public class Conexion {
private static final String USER ="AlexisOl";
private static final String PASSWORD = "AlexisOl";
private static final String URL = "jdbc:mysql://localhost:3306/db_muebleria";  
private final String database = "db_muebleria";

    public Connection getConnection() {

        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + database + "?serverTimezone=UTC",
                    USER,
                    PASSWORD
            );

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error." + ex.getMessage());
        }
        return conexion;
    }
}



