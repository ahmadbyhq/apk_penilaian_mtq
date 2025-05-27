package main;

import config.dbConnection;
import java.sql.*;

public class main {
    public static void main(String[] args) {
        Connection conn = dbConnection.getConnection();

        if (conn != null) {
            System.out.println("Berhasil terhubung ke database!");
            try {
                Statement stmt = conn.createStatement();

                String query = "SELECT * FROM peserta";
                ResultSet rs = stmt.executeQuery(query);

                System.out.println("Data Peserta:");
                System.out.println("ID | Nama Peserta | Asal | ID Lomba");
                System.out.println("--------------------------------------");

                while (rs.next()) {
                    int idPeserta = rs.getInt("id_peserta");
                    String nama = rs.getString("nama_peserta");
                    String asal = rs.getString("asal");
                    int idLomba = rs.getInt("id_lomba");

                    System.out.printf("%d | %s | %s | %d\n", idPeserta, nama, asal, idLomba);
                }

                rs.close();
                stmt.close();
                conn.close();

            } catch (SQLException e) {
                System.out.println("Gagal mengambil data dari database.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Koneksi ke database gagal.");
        }
    }
}
