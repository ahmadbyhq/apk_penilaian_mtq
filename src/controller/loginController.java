/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.dbConnection;
import java.sql.*;
import model.Juri;
import model.Panitia;
import model.User;
/**
 *
 * @author baiha
 */

public class loginController {
    // public static User login(String inputUsername, String inputPassword) {
    //     try {
    //         Connection conn = dbConnection.getConnection();
    //         String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
    //         PreparedStatement stmt = conn.prepareStatement(sql);
    //         stmt.setString(1, inputUsername);
    //         stmt.setString(2, inputPassword);

    //         ResultSet rs = stmt.executeQuery();

    //         if (rs.next()) {
    //         int id = rs.getInt("id_user");
    //         String username = rs.getString("username");
    //         String password = rs.getString("password");
    //         String nama = rs.getString("nama");
    //         String role = rs.getString("role");

    //         switch (role.toLowerCase()) {
    //             case "panitia":
    //                 return new Panitia(id, username, password, nama, role);
    //             case "juri":
    //                 return new Juri(id, username, password, nama, role);
    //             default:
    //                 System.out.println("Role tidak dikenali: " + role);
    //                 return null;
    //             }
    //         }

    //     } catch (SQLException e) {
    //         System.err.println("Error saat login: " + e.getMessage());
    //     }
    //     return null;
    // }

    public static User login(String inputUsername, String inputPassword) {
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

    try (
        Connection conn = dbConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {
        stmt.setString(1, inputUsername);
        stmt.setString(2, inputPassword);

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int id = rs.getInt("id_user");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String nama = rs.getString("nama");
                String role = rs.getString("role");

                switch (role.toLowerCase()) {
                    case "panitia":
                        return new Panitia(id, username, password, nama, role);
                    case "juri":
                        return new Juri(id, username, password, nama, role);
                    default:
                        System.out.println("Role tidak dikenali: " + role);
                        return null;
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Error saat login: " + e.getMessage());
    }

    return null;
    }

        public static boolean logins(String username, String password, String role) {
        boolean berhasil = false;

        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            berhasil = rs.next();
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return berhasil;
    }
}
