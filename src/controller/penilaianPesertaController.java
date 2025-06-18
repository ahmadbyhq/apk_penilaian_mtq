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
    public List<LombaItem> getListLomba() {
        List<LombaItem> list = new ArrayList<>();
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT DISTINCT l.id_lomba, l.nama_lomba FROM nilai n JOIN lomba l ON n.id_lomba = l.id_lomba";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new LombaItem(rs.getInt("id_lomba"), rs.getString("nama_lomba")));
            }
            rs.close(); ps.close(); conn.close();

        } catch (SQLException e) {
            System.out.println("Gagal ambil lomba: " + e.getMessage());
        }
        return list;
    }
    
    public List<String> getNamaAspekByLomba(int idLomba) {
        List<String> aspekList = new ArrayList<>();
        try {
            Connection conn = dbConnection.getConnection();
            String sql = "SELECT nama_aspek FROM aspek_penilaian WHERE id_lomba = ? ORDER BY id_aspek";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idLomba);
            ResultSet rs = ps.executeQuery();
    
            while (rs.next()) {
                aspekList.add(rs.getString("nama_aspek"));
            }
    
            rs.close(); ps.close(); conn.close();
        } catch (SQLException e) {
            System.out.println("Gagal ambil aspek: " + e.getMessage());
        }
        return aspekList;
    }
    
}

