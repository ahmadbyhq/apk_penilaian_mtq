/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Lomba;
import config.*;

/**
 *
 * @author baiha
 */
public class DaftarLombaController {
    

    
    public List<Lomba> getSemuaLomba() {
        List<Lomba> daftar = new ArrayList<>();
        String sql = "SELECT * FROM lomba";

        try (Connection conn = dbConnection.getConnection()) {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Lomba lomba = new Lomba(
                    rs.getInt("id_lomba"),
                    rs.getString("nama_lomba"),
                    rs.getInt("kuota")
                );
                daftar.add(lomba);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }

    public boolean tambahLomba(Lomba lomba) {
        String sql = "INSERT INTO lomba (nama_lomba, kuota) VALUES (?, ?)";

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, lomba.getNamaLomba());
            pst.setInt(2, lomba.getKuota());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editLomba(int idLomba, Lomba dataBaru) {
        String sql = "UPDATE lomba SET nama_lomba = ?, kuota = ? WHERE id_lomba = ?";

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, dataBaru.getNamaLomba());
            pst.setInt(2, dataBaru.getKuota());
            pst.setInt(3, idLomba);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusLomba(int idLomba) {
        String sql = "DELETE FROM lomba WHERE id_lomba = ?";

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, idLomba);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
