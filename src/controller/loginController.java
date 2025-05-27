/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.dbConnection;
import java.sql.*;
import model.User;
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
                return new User (
                    rs.getInt("id_user"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nama"),
                    rs.getString("role")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error saat login: " + e.getMessage());
        }
        return null;
    }
}
