/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.dbConnection;
import java.sql.*;
import model.User;
import model.Panitia;
import model.Juri;
/**
 *
 * @author baiha
 */

public class loginController {
    public static User login(String inputUsername, String inputPassword) {
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, inputUsername);
            stmt.setString(2, inputPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            int id = rs.getInt("id_user");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String nama = rs.getString("nama");
            String role = rs.getString("role");

            switch (role.toLowerCase()) {
                case "panitia":
                    return new Panitia(id, username, password, nama);
                case "juri":
                    return new Juri(id, username, password, nama);
                default:
                    System.out.println("Role tidak dikenali: " + role);
                    return null;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error saat login: " + e.getMessage());
        }
        return null;
    }
}
