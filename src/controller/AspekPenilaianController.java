/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.AspekPenilaian;

/**
 *
 * @author baiha
 */
public class AspekPenilaianController {

    // private int getTotalPresentaseByLomba(int idLomba, Integer excludeIdAspek) {
    //     int total = 0;
    //     String sql = "SELECT SUM(presentase) AS total FROM aspek_penilaian WHERE id_lomba = ?";
    //     if (excludeIdAspek != null) {
    //         sql += " AND id_aspek != ?";
    //     }

    //     try (Connection conn = dbConnection.getConnection();
    //         PreparedStatement pst = conn.prepareStatement(sql)) {

    //         pst.setInt(1, idLomba);
    //         if (excludeIdAspek != null) {
    //             pst.setInt(2, excludeIdAspek);
    //         }

    //         ResultSet rs = pst.executeQuery();
    //         if (rs.next()) {
    //             total = rs.getInt("total");
    //         }

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return total;
    // }

    
    public List<AspekPenilaian> getSemuaAspek() {
        List<AspekPenilaian> list = new ArrayList<>();

        String sql = "SELECT a.id_aspek, a.id_lomba, a.nama_aspek, l.nama_lomba, a.presentase " +
                    "FROM aspek_penilaian a JOIN lomba l ON a.id_lomba = l.id_lomba";

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                AspekPenilaian aspek = new AspekPenilaian(
                    rs.getInt("id_aspek"),
                    rs.getInt("id_lomba"),
                    rs.getString("nama_aspek"),
                    rs.getString("nama_lomba"),
                    rs.getInt("presentase")
                );
                list.add(aspek);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // public boolean tambahAspek(AspekPenilaian aspek) {
    //     String insertSql = "INSERT INTO aspek_penilaian (id_lomba, nama_aspek, presentase) VALUES (?, ?, ?)";

    //     try (Connection conn = dbConnection.getConnection()) {
    //         int total = getTotalPresentaseByLomba(aspek.getIdLomba(), null);
            
    //         if (total + aspek.getPresentase() > 100) {
    //             return false;
    //         }

    //         PreparedStatement insertStmt = conn.prepareStatement(insertSql);
    //         insertStmt.setInt(1, aspek.getIdLomba());
    //         insertStmt.setString(2, aspek.getNamaAspek());
    //         insertStmt.setInt(3, aspek.getPresentase());

    //         int rows = insertStmt.executeUpdate();
    //         return rows > 0;

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return false;
    // }

        public boolean tambahAspek(AspekPenilaian aspek) {
        String insertSql = "INSERT INTO aspek_penilaian (id_lomba, nama_aspek, presentase) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, aspek.getIdLomba());
            insertStmt.setString(2, aspek.getNamaAspek());
            insertStmt.setInt(3, aspek.getPresentase());

            int rows = insertStmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }




    // public boolean editAspek(AspekPenilaian aspek) {
    //     String cekSql = "SELECT SUM(presentase) as total FROM aspek_penilaian WHERE id_lomba = ? AND id_aspek != ?";
    //     String updateSql = "UPDATE aspek_penilaian SET nama_aspek = ?, presentase = ?, id_lomba = ? WHERE id_aspek = ?";

    //     try (Connection conn = dbConnection.getConnection()) {
    //         PreparedStatement cekStmt = conn.prepareStatement(cekSql);
    //         cekStmt.setInt(1, aspek.getIdLomba());
    //         cekStmt.setInt(2, aspek.getIdAspek());
    //         ResultSet rs = cekStmt.executeQuery();

    //         int total = 0;
    //         if (rs.next()) {
    //             total = rs.getInt("total");
    //         }

    //         if (total + aspek.getPresentase() > 100) {
    //             return false;
    //         }

    //         PreparedStatement updateStmt = conn.prepareStatement(updateSql);
    //         updateStmt.setString(1, aspek.getNamaAspek());
    //         updateStmt.setInt(2, aspek.getPresentase());
    //         updateStmt.setInt(3, aspek.getIdLomba());
    //         updateStmt.setInt(4, aspek.getIdAspek());


    //         return updateStmt.executeUpdate() > 0;

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }

    //     return false;
    // }

        public boolean editAspek(AspekPenilaian aspek) {
        String updateSql = "UPDATE aspek_penilaian SET nama_aspek = ?, presentase = ?, id_lomba = ? WHERE id_aspek = ?";

        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, aspek.getNamaAspek());
            updateStmt.setInt(2, aspek.getPresentase());
            updateStmt.setInt(3, aspek.getIdLomba());
            updateStmt.setInt(4, aspek.getIdAspek());


            return updateStmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



    public boolean hapusAspek(int idAspek) {
        String sql = "DELETE FROM aspek_penilaian WHERE id_aspek = ?";
        try (Connection conn = dbConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idAspek);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    
}
