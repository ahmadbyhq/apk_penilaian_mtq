package controller;

import java.sql.*;
import java.util.*;
import model.*;


public class database {
    private static final String URL = "jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq";
    private static final String USER = "u725894752_appmtqpbo";
    private static final String PASSWORD = "Appmtqpbo{07};";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static List<Peserta> getPesertaByLomba(int id_lomba) {
        List<Peserta> daftar = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM peserta WHERE id_lomba = ?")) {

            stmt.setInt(1, id_lomba);
            ResultSet rs = stmt.executeQuery();

            Lomba l = getLombaById(id_lomba);

            while (rs.next()) {
                String namaPeserta = rs.getString("nama");
                String asal = rs.getString("asal");
                Peserta p = new Peserta(namaPeserta, l, asal);
                daftar.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil peserta lomba: " + e.getMessage());
        }
        return daftar;
    }

    public static Lomba getLombaById(int id) {
        try {
            Connection conn = database.getConnection();
            String sql = "SELECT * FROM lomba WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Lomba(rs.getInt("id"), rs.getString("nama_lomba"));
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean hapusNilaiPeserta(String nama_peserta) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM nilai WHERE nama_peserta = ?")) {

            stmt.setString(1, nama_peserta);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menghapus nilai peserta: " + e.getMessage());
            return false;
        }
    }

    public static Nilai getNilaiPeserta(String nama_peserta) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM nilai WHERE nama_peserta = ?")) {
            stmt.setString(1, nama_peserta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Nilai(
                        rs.getInt("id_lomba"),
                        rs.getInt("id_juri"),
                        rs.getString("nama_peserta"),
                        rs.getDouble("tajwid"),
                        rs.getDouble("fashahah"),
                        rs.getDouble("adab"),
                        rs.getDouble("suara_lagu"),
                        rs.getDouble("penguasaan_juz")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
