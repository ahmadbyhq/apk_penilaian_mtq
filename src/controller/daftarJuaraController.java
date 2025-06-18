/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.dbConnection;
import java.sql.*;
import java.util.*;
import model.*;
/**
 *
 * @author Chris Bimo
 */
public class daftarJuaraController {
    
 public List<Juara> getDataJuara() throws SQLException {
        List<Juara> listJuara = new ArrayList<>();
    
        try (Connection conn = dbConnection.getConnection()) {
            String sqlPeserta = """
                SELECT p.id_peserta, p.nama_peserta, l.nama_lomba, l.id_lomba
                FROM peserta p
                JOIN lomba l ON p.id_lomba = l.id_lomba
                WHERE l.id_lomba <= 2
            """;
    
            try (PreparedStatement stmtPeserta = conn.prepareStatement(sqlPeserta);
                 ResultSet rsPeserta = stmtPeserta.executeQuery()) {
    
                while (rsPeserta.next()) {
                    int idPeserta = rsPeserta.getInt("id_peserta");
                    String namaPeserta = rsPeserta.getString("nama_peserta");
                    String namaLomba = rsPeserta.getString("nama_lomba");
    
                    // Hitung total nilai
                    double totalSkor = 0;
    
                    String sqlNilai = """
                        SELECT a.presentase, n.skor
                        FROM nilai n
                        JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
                        WHERE n.id_peserta = ?
                    """;
    
                    try (PreparedStatement stmtNilai = conn.prepareStatement(sqlNilai)) {
                        stmtNilai.setInt(1, idPeserta);
    
                        try (ResultSet rsNilai = stmtNilai.executeQuery()) {
                            while (rsNilai.next()) {
                                double persen = rsNilai.getDouble("presentase");
                                int skor = rsNilai.getInt("skor");
                                totalSkor += (skor * persen) / 100.0;
                            }
                        }
                    }
    
                    // Tambahkan ke list
                    listJuara.add(new Juara(namaPeserta, namaLomba, totalSkor, 0)); // juara sementara 0
                }
            }
    
            // Urutkan descending by totalNilai
            listJuara.sort(Comparator.comparingDouble(Juara::getTotalNilai).reversed());
    
            // Set ranking Juara
            int juaraRank = 1;
            for (int i = 0; i < listJuara.size(); i++) {
                Juara j = listJuara.get(i);
                listJuara.set(i, new Juara(j.getNamaPeserta(), j.getNamaLomba(), j.getTotalNilai(), juaraRank));
                juaraRank++;
            }
        }
    
        return listJuara;
    }


}
