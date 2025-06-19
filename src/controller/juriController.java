/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.dbConnection;
import model.Juri;
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
    
       public static void tampilJuri() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq", "u725894752_appmtqpbo", "Appmtqpbo{07};");
            String sql = "SELECT id_user, username, password, nama FROM users WHERE role = 'juri'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Daftar Juri:");
            System.out.println("ID\tUsername\tPassword\tNama");

            while(rs.next()) {
                int id = rs.getInt("id_user");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String nama = rs.getString("nama");
                System.out.println("" + id + "\t" + username + "\t" + password + "\t" + nama);
            }

            conn.close();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

    }
       
       public static int getIdJuri(String username) {
        int id_juri = -1;

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq", "u725894752_appmtqpbo", "Appmtqpbo{07};");

            try {
                String query = "SELECT id_user FROM users WHERE username = ? AND role = 'juri'";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    id_juri = rs.getInt("id_user");
                }
            } catch (Throwable var7) {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }
                }

                throw var7;
            }

            if (conn != null) {
                conn.close();
            }
        } catch (SQLException var8) {
            var8.printStackTrace();
        }

        return id_juri;
    }
}
