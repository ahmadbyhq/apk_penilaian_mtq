package config;

import java.sql.*;

public class dbConnection {
    
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq";
    private static final String USERNAME = "u725894752_appmtqpbo";
    private static final String PASSWORD = "Appmtqpbo{07};";

    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//                System.out.println("Koneksi database berhasil.");
            } catch (ClassNotFoundException e) {
                System.err.println("JDBC Driver tidak ditemukan: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Gagal koneksi ke database: " + e.getMessage());
            }
        }
        return conn;
    }
}