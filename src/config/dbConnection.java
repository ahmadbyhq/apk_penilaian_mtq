package config;

import java.sql.*;

public class dbConnection {
//    koneksi lokal
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/u725894752_app_mtq";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new SQLException("JDBC Driver tidak ditemukan: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
    
    // koneksi db ke hostinger
//    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//    private static final String URL = "jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq";
//    private static final String USERNAME = "u725894752_appmtqpbo";
//    private static final String PASSWORD = "Appmtqpbo{07};";
//
//    public static Connection getConnection() throws SQLException {
//        try {
//            Class.forName(JDBC_DRIVER);
//        } catch (ClassNotFoundException e) {
//            throw new SQLException("JDBC Driver tidak ditemukan: " + e.getMessage());
//        }
//        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
//    }
}