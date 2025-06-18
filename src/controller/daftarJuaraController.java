/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import config.dbConnection;
import java.sql.*;
import java.util.*;
import model.Juara;
/**
 *
 * @author Chris Bimo
 */

public class daftarJuaraController {
    // public List<Juara> getDataJuara() throws SQLException {
    //     List<Juara> listJuara = new ArrayList<>();
    
    //     try (Connection conn = dbConnection.getConnection()) {
    //         String sqlPeserta = """
    //             SELECT p.id_peserta, p.nama_peserta, l.nama_lomba, l.id_lomba
    //             FROM peserta p
    //             JOIN lomba l ON p.id_lomba = l.id_lomba
    //             WHERE l.id_lomba <= 2
    //         """;
    
    //         try (PreparedStatement stmtPeserta = conn.prepareStatement(sqlPeserta);
    //              ResultSet rsPeserta = stmtPeserta.executeQuery()) {
    
    //             while (rsPeserta.next()) {
    //                 int idPeserta = rsPeserta.getInt("id_peserta");
    //                 String namaPeserta = rsPeserta.getString("nama_peserta");
    //                 String namaLomba = rsPeserta.getString("nama_lomba");
    
    //                 // Hitung total nilai
    //                 double totalSkor = 0;
    
    //                 String sqlNilai = """
    //                     SELECT a.presentase, n.skor
    //                     FROM nilai n
    //                     JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
    //                     WHERE n.id_peserta = ?
    //                 """;
    
    //                 try (PreparedStatement stmtNilai = conn.prepareStatement(sqlNilai)) {
    //                     stmtNilai.setInt(1, idPeserta);
    
    //                     try (ResultSet rsNilai = stmtNilai.executeQuery()) {
    //                         while (rsNilai.next()) {
    //                             double persen = rsNilai.getDouble("presentase");
    //                             int skor = rsNilai.getInt("skor");
    //                             totalSkor += (skor * persen) / 100.0;
    //                         }
    //                     }
    //                 }
    
    //                 // Tambahkan ke list
    //                 listJuara.add(new Juara(namaPeserta, namaLomba, totalSkor, 0)); // juara sementara 0
    //             }
    //         }
    
    //         // Urutkan descending by totalNilai
    //         listJuara.sort(Comparator.comparingDouble(Juara::getTotalNilai).reversed());
    
    //         // Set ranking Juara
    //         int juaraRank = 1;
    //         for (int i = 0; i < listJuara.size(); i++) {
    //             Juara j = listJuara.get(i);
    //             listJuara.set(i, new Juara(j.getNamaPeserta(), j.getNamaLomba(), j.getTotalNilai(), juaraRank));
    //             juaraRank++;
    //         }
    //     }
    
    //     return listJuara;
    // }
    
//  public List<Juara> getDataJuara() throws SQLException {
//         List<Juara> listJuara = new ArrayList<>();
    
//         try (Connection conn = dbConnection.getConnection()) {
//             String sqlPeserta = """
//                 SELECT p.id_peserta, p.nama_peserta, l.nama_lomba, l.id_lomba
//                 FROM peserta p
//                 JOIN lomba l ON p.id_lomba = l.id_lomba
//                 WHERE l.id_lomba <= 2
//             """;
    
//             try (PreparedStatement stmtPeserta = conn.prepareStatement(sqlPeserta);
//                     ResultSet rsPeserta = stmtPeserta.executeQuery()) {
    
//                 while (rsPeserta.next()) {
//                     int idPeserta = rsPeserta.getInt("id_peserta");
//                     String namaPeserta = rsPeserta.getString("nama_peserta");
//                     String namaLomba = rsPeserta.getString("nama_lomba");
    
//                     // Hitung total nilai
//                     double totalSkor = 0;
    
//                     String sqlNilai = """
//                         SELECT a.presentase, n.skor
//                         FROM nilai n
//                         JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
//                         WHERE n.id_peserta = ?
//                     """;
    
//                     try (PreparedStatement stmtNilai = conn.prepareStatement(sqlNilai)) {
//                         stmtNilai.setInt(1, idPeserta);
    
//                         try (ResultSet rsNilai = stmtNilai.executeQuery()) {
//                             while (rsNilai.next()) {
//                                 double persen = rsNilai.getDouble("presentase");
//                                 int skor = rsNilai.getInt("skor");
//                                 totalSkor += (skor * persen) / 100.0;
//                             }
//                         }
//                     }
    
//                     // Tambahkan ke list
//                     listJuara.add(new Juara(namaPeserta, namaLomba, totalSkor, 0)); // juara sementara 0
//                 }
//             }
    
//             // Urutkan descending by totalNilai
//             listJuara.sort(Comparator.comparingDouble(Juara::getTotalNilai).reversed());
    
//             // Set ranking Juara
//             int juaraRank = 1;
//             for (int i = 0; i < listJuara.size(); i++) {
//                 Juara j = listJuara.get(i);
//                 listJuara.set(i, new Juara(j.getNamaPeserta(), j.getNamaLomba(), j.getTotalNilai(), juaraRank));
//                 juaraRank++;
//             }
//         }
    
//         return listJuara;
//     }
public List<Juara> getJuaraPerLomba() {
        List<Juara> daftarJuara = new ArrayList<>();
        String sql =
            "SELECT * FROM ( " +
            "    SELECT nama_peserta, nama_lomba, " +
            "           ROUND(SUM(nilai_aspek), 2) AS total_nilai, " +
            "           ROW_NUMBER() OVER (PARTITION BY nama_lomba ORDER BY SUM(nilai_aspek) DESC) AS peringkat " +
            "    FROM ( " +
            "        SELECT p.nama_peserta, l.nama_lomba, " +
            "               (AVG(n.skor) * a.presentase / 100) AS nilai_aspek " +
            "        FROM peserta p " +
            "        JOIN lomba l ON p.id_lomba = l.id_lomba " +
            "        JOIN nilai n ON n.id_peserta = p.id_peserta " +
            "        JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek " +
            "        GROUP BY p.id_peserta, a.id_aspek, l.nama_lomba, p.nama_peserta " +
            "    ) AS sub " +
            "    GROUP BY nama_peserta, nama_lomba " +
            ") AS ranking " +
            "WHERE peringkat <= 3 " +
            "ORDER BY nama_lomba, peringkat";

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Juara juara = new Juara();
                juara.setNamaPeserta(rs.getString("nama_peserta"));
                juara.setNamaLomba(rs.getString("nama_lomba"));
                juara.setTotalNilai(rs.getDouble("total_nilai"));
                juara.setJuara(rs.getInt("peringkat"));
                daftarJuara.add(juara);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftarJuara;
    }

}
