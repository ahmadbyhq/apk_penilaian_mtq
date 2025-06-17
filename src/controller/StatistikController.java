/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;


import config.dbConnection;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author baiha
 */
public class StatistikController {


    public void tampilkanStatistik(JLabel totalPesertatxt, JLabel totalJuritxt, JLabel totalLombatxt) {
        try (Connection conn = dbConnection.getConnection()) {
            // Total Peserta
            String sqlPeserta = "SELECT COUNT(*) AS total FROM peserta";
            PreparedStatement pstPeserta = conn.prepareStatement(sqlPeserta);
            ResultSet rsPeserta = pstPeserta.executeQuery();
            if (rsPeserta.next()) {
                totalPesertatxt.setText(rsPeserta.getString("total"));
            }

            // Total Juri
            String sqlJuri = "SELECT COUNT(*) AS total FROM users WHERE role = 'juri'";
            PreparedStatement pstJuri = conn.prepareStatement(sqlJuri);
            ResultSet rsJuri = pstJuri.executeQuery();
            if (rsJuri.next()) {
                totalJuritxt.setText(rsJuri.getString("total"));
            }

            // Total Lomba
            String sqlLomba = "SELECT COUNT(*) AS total FROM lomba";
            PreparedStatement pstLomba = conn.prepareStatement(sqlLomba);
            ResultSet rsLomba = pstLomba.executeQuery();
            if (rsLomba.next()) {
                totalLombatxt.setText(rsLomba.getString("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tampilkanTabelStatistikPeserta(JTable tabelStatistikLomba) {
        DefaultTableModel model = (DefaultTableModel) tabelStatistikLomba.getModel();
        model.setRowCount(0);

        String query = """
            SELECT l.nama_lomba, COUNT(p.id_peserta) AS total_peserta, l.kuota
            FROM lomba l
            LEFT JOIN peserta p ON l.id_lomba = p.id_lomba
            GROUP BY l.id_lomba, l.nama_lomba, l.kuota
            ORDER BY l.nama_lomba
        """;

        try (Connection conn = dbConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String namaLomba = rs.getString("nama_lomba");
                int totalPeserta = rs.getInt("total_peserta");
                int kuota = rs.getInt("kuota");

                model.addRow(new Object[]{namaLomba, totalPeserta, kuota});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
