package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Panitia extends User {
    public Panitia(int id, String username, String password, String nama, String role) {
        super(id, username, password, nama, role);
    }

    @Override
    public void tampilPeran() {
        System.out.println("Saya Panitia: ");
    }

    public void tambahJuri(String username, String password, String nama) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq",
                    "u725894752_appmtqpbo",
                    "Appmtqpbo{07};");
            String sql = "INSERT INTO users (username, password, nama, role) VALUES (?, ?, ?, 'juri')";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, nama);
            stmt.executeUpdate();
            System.out.println("Juri berhasil ditambahkan!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hapusJuri(String username) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq",
                    "u725894752_appmtqpbo",
                    "Appmtqpbo{07};");
            String sql = "DELETE FROM users WHERE username = ? AND role = 'juri'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Juri berhasil dihapus.");
            } else {
                System.out.println("Juri tidak ditemukan.");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editJuri(String usernameLama, String usernameBaru, String passwordBaru, String namaBaru) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq",
                    "u725894752_appmtqpbo",
                    "Appmtqpbo{07};"
            );
            String sql = "UPDATE users SET username = ?, password = ?, nama = ? WHERE username = ? AND role = 'juri'";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usernameBaru);
            stmt.setString(2, passwordBaru);
            stmt.setString(3, namaBaru);
            stmt.setString(4, usernameLama);
            int updated = stmt.executeUpdate();
            if (updated > 0) {
                System.out.println("Juri berhasil diedit.");
            } else {
                System.out.println("Juri tidak ditemukan.");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
