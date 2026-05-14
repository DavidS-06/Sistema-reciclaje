package com.almacen;

import javax.swing.*;
import java.sql.*;

public class ConexionPostgreSQL {

    /*
    private static final String URL = "jdbc:postgresql://db.duxaupbccprzlwvcxtwz.supabase.co:5432/postgres";
    private static final String USUARIO = "postgres";
    private static final String PASSWORD = "BotellaDePET";
     */
    
    //Prueba de conexion
    private static final String URL = "jdbc:postgresql://localhost:5432/espejopet";
    private static final String USUARIO = "davids_06";
    private static final String PASSWORD = "50374895664";

    // Método para obtener la conexión
    public static Connection getConexion() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("Conexión exitosa a PostgreSQL");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
        }
        return conexion;
    }
}
