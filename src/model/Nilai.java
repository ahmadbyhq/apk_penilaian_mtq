package model;
import java.sql.*;

public class Nilai {
    public Nilai(int id_lomba, int id_juri, String nama_peserta, double tajwid, double fashahah, double adab, double suaraLagu, double penguasaanJuz) {
    }

    public static void inputNilai(int id_lomba, int id_juri, String nama_peserta,
                                  double tajwid, double fashahah, double adab,
                                  double suaraLagu, double penguasaanJuz) {
            double total = tajwid * 0.2 + fashahah * 0.2 + adab * 0.2 + suaraLagu * 0.15 + penguasaanJuz * 0.25;
            try {
                Connection conn = database.getConnection();
                String sql = "INSERT INTO nilai (id_lomba, id_juri, nama_peserta, tajwid, fashahah, adab, suara_lagu, penguasaan_juz, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id_lomba);
                stmt.setInt(2, id_juri);
                stmt.setString(3, nama_peserta);
                stmt.setDouble(4, tajwid);
                stmt.setDouble(5, fashahah);
                stmt.setDouble(6, adab);
                stmt.setDouble(7, suaraLagu);
                stmt.setDouble(8, penguasaanJuz);
                stmt.setDouble(9, total);
                stmt.executeUpdate();
                conn.close();

                System.out.println("Nilai berhasil dimasukkan! Total: " + total);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
