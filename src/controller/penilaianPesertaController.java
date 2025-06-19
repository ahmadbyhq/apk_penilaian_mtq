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
public class penilaianPesertaController {
    // public List<LombaItem> getListLomba() {
    //     List<LombaItem> list = new ArrayList<>();
    //     try {
    //         Connection conn = dbConnection.getConnection();
    //         String sql = "SELECT DISTINCT l.id_lomba, l.nama_lomba FROM nilai n JOIN lomba l ON n.id_lomba = l.id_lomba";
    //         PreparedStatement ps = conn.prepareStatement(sql);
    //         ResultSet rs = ps.executeQuery();

    //         while (rs.next()) {
    //             list.add(new LombaItem(rs.getInt("id_lomba"), rs.getString("nama_lomba")));
    //         }
    //         rs.close(); ps.close(); conn.close();

    //     } catch (SQLException e) {
    //         System.out.println("Gagal ambil lomba: " + e.getMessage());
    //     }
    //     return list;
    // }
    
    // public List<String> getNamaAspekByLomba(int idLomba) {
    //     List<String> aspekList = new ArrayList<>();
    //     try {
    //         Connection conn = dbConnection.getConnection();
    //         String sql = "SELECT nama_aspek FROM aspek_penilaian WHERE id_lomba = ? ORDER BY id_aspek";
    //         PreparedStatement ps = conn.prepareStatement(sql);
    //         ps.setInt(1, idLomba);
    //         ResultSet rs = ps.executeQuery();
    
    //         while (rs.next()) {
    //             aspekList.add(rs.getString("nama_aspek"));
    //         }
    
    //         rs.close(); ps.close(); conn.close();
    //     } catch (SQLException e) {
    //         System.out.println("Gagal ambil aspek: " + e.getMessage());
    //     }
    //     return aspekList;
    // }

     public ArrayList<Lomba> getDaftarLomba() {
        ArrayList<Lomba> list = new ArrayList<>();
        String query = "SELECT * FROM lomba";

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(new Lomba(
                        rs.getInt("id_lomba"),
                        rs.getString("nama_lomba"),
                        rs.getInt("kuota")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Peserta> getPesertaBerdasarkanLomba(int idLomba) {
        ArrayList<Peserta> list = new ArrayList<>();
        String query = "SELECT * FROM peserta WHERE id_lomba = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idLomba);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Peserta(
                        rs.getInt("id_peserta"),
                        rs.getString("nama_peserta"),
                        new Lomba(idLomba, "", 0),
                        rs.getString("asal")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<AspekPenilaian> getAspekBerdasarkanLomba(int idLomba) {
        ArrayList<AspekPenilaian> list = new ArrayList<>();
        String query = "SELECT * FROM aspek_penilaian WHERE id_lomba = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idLomba);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new AspekPenilaian(
                        rs.getInt("id_aspek"),
                        rs.getInt("id_lomba"),
                        rs.getString("nama_aspek"),
                        "", // nama_lomba optional
                        rs.getInt("presentase")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean simpanNilai(int idPeserta, int idJuri, int idLomba, int idAspek, int skor) {
        String query = "INSERT INTO nilai (id_peserta, id_juri, id_lomba, id_aspek, skor) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idPeserta);
            ps.setInt(2, idJuri);
            ps.setInt(3, idLomba);
            ps.setInt(4, idAspek);
            ps.setInt(5, skor);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> getPenilaianByJuriDanLomba(int idJuri, int idLomba) {
        List<Object[]> dataList = new ArrayList<>();
        String sql = """
            SELECT p.nama_peserta, p.asal, l.nama_lomba, a.nama_aspek, n.skor
            FROM nilai n
            JOIN peserta p ON n.id_peserta = p.id_peserta
            JOIN lomba l ON n.id_lomba = l.id_lomba
            JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
            WHERE n.id_juri = ? AND n.id_lomba = ?
        """;

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idJuri);
            stmt.setInt(2, idLomba);

            try (ResultSet rs = stmt.executeQuery()) {
                int no = 1;
                while (rs.next()) {
                    dataList.add(new Object[]{
                        no++,
                        rs.getString("nama_peserta"),
                        rs.getString("asal"),
                        rs.getString("nama_lomba"),
                        rs.getString("nama_aspek"),
                        rs.getInt("skor")
                    });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public boolean updateSkor(String namaPeserta, String namaLomba, String namaAspek, int skorBaru) {
        String sql = """
            UPDATE nilai n
            JOIN peserta p ON n.id_peserta = p.id_peserta
            JOIN lomba l ON n.id_lomba = l.id_lomba
            JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
            SET n.skor = ?
            WHERE p.nama_peserta = ? AND l.nama_lomba = ? AND a.nama_aspek = ?
        """;

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, skorBaru);
            stmt.setString(2, namaPeserta);
            stmt.setString(3, namaLomba);
            stmt.setString(4, namaAspek);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal update skor: " + e.getMessage());
            return false;
        }
    }






    
}

