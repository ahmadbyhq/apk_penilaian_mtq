/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.*;
import java.sql.*;
import java.util.*;
import model.*;

/**
 *
 * @author baiha
 */
public class juriController {
    public List<Juri> getAllJuri() {
        List<Juri> juriList = new ArrayList<>();
        String query = "SELECT * FROM users WHERE role = 'juri'";

        try (
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
        ) {
            while (rs.next()) {
                Juri juri = new Juri();
                juri.setId(rs.getInt("id_user"));
                juri.setUsername(rs.getString("username"));
                juri.setPassword(rs.getString("password"));
                juri.setNama(rs.getString("nama"));
                juri.setRole(rs.getString("role"));
                juriList.add(juri);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juriList;
    }

    public boolean tambahJuri(Juri juri) {
        String query = "INSERT INTO users (username, password, nama, role) VALUES (?, ?, ?, 'juri')";

        try (
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
        ) {
            stmt.setString(1, juri.getUsername());
            stmt.setString(2, juri.getPassword());
            stmt.setString(3, juri.getNama());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateJuri(Juri juri) {
        try {
            Connection conn = dbConnection.getConnection();
            String query = "UPDATE users SET username = ?, password = ?, nama = ? WHERE id_user = ? AND role = 'juri'";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, juri.getUsername());
            stmt.setString(2, juri.getPassword());
            stmt.setString(3, juri.getNama());
            stmt.setInt(4, juri.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteJuri(int idJuri) {
        try {
            Connection conn = dbConnection.getConnection();
            String query = "DELETE FROM users WHERE id_user = ? AND role = 'juri'";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idJuri);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
