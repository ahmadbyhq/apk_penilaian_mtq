/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import config.dbConnection;
import config.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Chris Bimo
 */
public class rekapNilaiController {

    private double totalNilai;
    
    public class RekapNilaiData {
    public String nama_peserta;
    public String nama_lomba;
    public double totalNilai;

    public RekapNilaiData(String nama_peserta, String nama_lomba, double totalNilai) {
        this.nama_peserta = nama_peserta;
        this.nama_lomba = nama_lomba;
        this.totalNilai = totalNilai;
    }
    
    public String getNama_peserta(){
        return nama_peserta;
    }
    
    public String getNama_lomba(){
        return nama_lomba;
    }
    
    public double getTotal_Nilai(){
        return totalNilai;
    }

    }
public RekapNilaiData getRekapNilai(int id_peserta, int id_juri) {
    double totalSkor = 0;
    double totalPersen = 0;
    String nama_peserta = "";
    String nama_lomba = "";

    try (Connection conn = dbConnection.getConnection()) {
        // Ambil nama peserta & lomba
        String infoSql = """
            SELECT p.nama_peserta, l.nama_lomba
            FROM peserta p 
            JOIN nilai n ON p.id_peserta = n.id_peserta 
            JOIN lomba l ON n.id_lomba = l.id_lomba 
            WHERE p.id_peserta = ? LIMIT 1
        """;
        PreparedStatement infoStmt = conn.prepareStatement(infoSql);
        infoStmt.setInt(1, id_peserta);
        ResultSet infoRs = infoStmt.executeQuery();
        if (infoRs.next()) {
            nama_peserta = infoRs.getString("nama_peserta");
            nama_lomba = infoRs.getString("nama_lomba");
        }

        // Hitung total nilai
        // String sql = """
        //     SELECT a.presentase, n.skor
        //     FROM nilai n
        //     JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
        //     WHERE n.id_peserta = ? 
        // """;

        String sql = """
            SELECT a.presentase, AVG(n.skor) AS rata_skor
            FROM nilai n
            JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
            WHERE n.id_peserta = ?
            GROUP BY a.id_aspek, a.presentase
        """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_peserta);
        ResultSet rs = stmt.executeQuery();

        // while (rs.next()) {
        //     double persen = rs.getDouble("presentase");
        //     int skor = rs.getInt("skor");
        //     totalSkor += (skor * persen) / 100.0;
        //     totalPersen += persen;
        // }

        while (rs.next()) {
            double persen = rs.getDouble("presentase");
            double rataSkor = rs.getDouble("rata_skor");
            totalSkor += (rataSkor * persen) / 100.0;
        }



    } catch (Exception e) {
        e.printStackTrace();
    }

    return new RekapNilaiData(nama_peserta, nama_lomba, totalSkor);
}

public static List<Integer> getAllPesertaIDs(){
    List<Integer> ids = new ArrayList<>();
    try (Connection conn = dbConnection.getConnection()){
        String sql = "SELECT id_peserta FROM peserta";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            ids.add(rs.getInt("id_peserta"));
        }
    } catch (Exception e){
        e.printStackTrace();
    }
    return ids;
}

public static String getNamaPeserta(int id_peserta) {
    String nama = "";
    try (Connection conn = dbConnection.getConnection()) {
        String sql = "SELECT nama_peserta FROM peserta WHERE id_peserta = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_peserta);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            nama = rs.getString("nama_peserta");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return nama;
}

public static String getNamaLombaByPeserta(int id_peserta) {
    String namaLomba = "";
    try (Connection conn = dbConnection.getConnection()) {
        String sql = """
            SELECT l.nama_lomba
            FROM peserta p
            JOIN lomba l ON p.id_lomba = l.id_lomba
            WHERE p.id_peserta = ?
        """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id_peserta);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            namaLomba = rs.getString("nama_lomba");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return namaLomba;
}

public double getTotalNilai() {
    return this.totalNilai;
}

public List<RekapNilaiData> searchRekapNilai(String keyword){
    List<RekapNilaiData> result = new ArrayList<>();
    keyword = "%" + keyword + "%";
    
    try (Connection conn = dbConnection.getConnection()){
        String sql = """
                     SELECT p.nama_peserta, l.nama_lomba,
                     COALESCE(SUM(n.skor * a.presentase / 100), 0) AS total_nilai
                     FROM peserta p
                     JOIN lomba l ON p.id_lomba = l.id_lomba
                     LEFT JOIN nilai n ON p.id_peserta = n.id_peserta
                     LEFT JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
                     WHERE p.nama_peserta LIKE ? OR l.nama_lomba LIKE ?
                     GROUP BY p.id_peserta
                     """;
        
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, keyword);
        stmt.setString(2, keyword);
        
        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            String nama_peserta = rs.getString("nama_peserta");
            String nama_lomba = rs.getString("nama_lomba");
            double total = rs.getDouble("total_nilai");
            result.add(new RekapNilaiData(nama_peserta, nama_lomba, total));
        }
    } catch (Exception e){
        e.printStackTrace();
    }
    return result;
}
}