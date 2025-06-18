/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import config.dbConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;
import model.*;
/**
 *
 * @author baiha
 */
public class DaftarpesertaController {



    public List<Peserta> getSemuaPeserta() {
        List<Peserta> daftar = new ArrayList<>();

        try (Connection conn = dbConnection.getConnection()) {
            String sql = "SELECT p.id_peserta, p.nama_peserta, p.asal, l.id_lomba, l.nama_lomba, l.kuota " +
                        "FROM peserta p JOIN lomba l ON p.id_lomba = l.id_lomba ORDER BY p.id_peserta";

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Lomba lomba = new Lomba(rs.getInt("id_lomba"), rs.getString("nama_lomba"), rs.getInt("kuota"));
                Peserta peserta = new Peserta(
                    rs.getInt("id_peserta"),
                    rs.getString("nama_peserta"),
                    lomba,
                    rs.getString("asal")
                );

                daftar.add(peserta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daftar;
    }

    
    public void cariPeserta(JTable tabelPeserta, String keyword) {
        DefaultTableModel model = (DefaultTableModel) tabelPeserta.getModel();
        model.setRowCount(0);

        try (Connection conn = dbConnection.getConnection()) {
            String sql = "SELECT p.nama_peserta, p.asal, l.nama_lomba " +
                        "FROM peserta p " +
                        "JOIN lomba l ON p.id_lomba = l.id_lomba " +
                        "WHERE p.nama_peserta LIKE ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + keyword + "%");
            ResultSet rs = pst.executeQuery();

            int no = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                    no++,
                    rs.getString("nama_peserta"),
                    rs.getString("asal"),
                    rs.getString("nama_lomba")
                });
            }

            tabelPeserta.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void isiComboBoxLomba(JComboBox<String> comboBox) {
        try (Connection conn = dbConnection.getConnection()) {
            String sql = "SELECT nama_lomba FROM lomba";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            comboBox.removeAllItems();
            while (rs.next()) {
                comboBox.addItem(rs.getString("nama_lomba"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // public boolean tambahPeserta(Peserta peserta) {
    //     try (Connection conn = dbConnection.getConnection()) {
    //         String sql = "INSERT INTO peserta (nama_peserta, asal, id_lomba) VALUES (?, ?, ?)";
    //         PreparedStatement pst = conn.prepareStatement(sql);
    //         pst.setString(1, peserta.getNamaPeserta());
    //         pst.setString(2, peserta.getAsal());
    //         pst.setInt(3, peserta.getLomba().getIdLomba());

    //         int result = pst.executeUpdate();
    //         return result > 0;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public boolean tambahPeserta(Peserta peserta) {
        String sqlCount = "SELECT COUNT(*) FROM peserta WHERE id_lomba = ?";
        String sqlKuota = "SELECT kuota FROM lomba WHERE id_lomba = ?";
        String sqlInsert = "INSERT INTO peserta (nama_peserta, asal, id_lomba) VALUES (?, ?, ?)";

        try (Connection conn = dbConnection.getConnection()) {
            // Hitung jumlah peserta saat ini
            PreparedStatement pstCount = conn.prepareStatement(sqlCount);
            pstCount.setInt(1, peserta.getLomba().getIdLomba());
            ResultSet rsCount = pstCount.executeQuery();
            int jumlahPeserta = rsCount.next() ? rsCount.getInt(1) : 0;

            // Ambil kuota dari tabel lomba
            PreparedStatement pstKuota = conn.prepareStatement(sqlKuota);
            pstKuota.setInt(1, peserta.getLomba().getIdLomba());
            ResultSet rsKuota = pstKuota.executeQuery();
            int kuota = rsKuota.next() ? rsKuota.getInt(1) : 0;

            // Validasi
            if (jumlahPeserta >= kuota) {
                System.out.println("Peserta sudah melebihi kuota.");
                return false;
            }

            // Tambahkan peserta jika masih di bawah kuota
            PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
            pstInsert.setString(1, peserta.getNamaPeserta());
            pstInsert.setString(2, peserta.getAsal());
            pstInsert.setInt(3, peserta.getLomba().getIdLomba());

            int result = pstInsert.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean editPeserta(int idPeserta, Peserta pesertaBaru) {
        try (Connection conn = dbConnection.getConnection()) {
            String sql = "UPDATE peserta SET nama_peserta = ?, asal = ?, id_lomba = ? WHERE id_peserta = ?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, pesertaBaru.getNamaPeserta());
            pst.setString(2, pesertaBaru.getAsal());
            pst.setInt(3, pesertaBaru.getLomba().getIdLomba());
            pst.setInt(4, idPeserta);


            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deletePeserta(int idPeserta) {
        try (Connection conn = dbConnection.getConnection()) {
            String sql = "DELETE FROM peserta WHERE id_peserta = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, idPeserta);

            int result = pst.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    

    public Lomba getLombaByNama(String namaLomba) {
        try (Connection conn = dbConnection.getConnection()) {
            String sql = "SELECT * FROM lomba WHERE nama_lomba = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, namaLomba);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new Lomba(
                    rs.getInt("id_lomba"),
                    rs.getString("nama_lomba")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Lomba> getSemuaLomba() {
        List<Lomba> daftar = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection()) {
            String sql = "SELECT * FROM lomba";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                daftar.add(new Lomba(
                    rs.getInt("id_lomba"),
                    rs.getString("nama_lomba"),
                    rs.getInt("kuota")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftar;
    }

    // public boolean importPesertaDariCSV(File file) {
    //     try (BufferedReader br = new BufferedReader(new FileReader(file))) {
    //         Connection conn = dbConnection.getConnection();
    //         String sql = "INSERT INTO peserta (nama_peserta, asal, id_lomba) VALUES (?, ?, ?)";
    //         PreparedStatement pst = conn.prepareStatement(sql);

    //         String line;
    //         boolean isFirstLine = true;

    //         while ((line = br.readLine()) != null) {
    //             if (isFirstLine) {
    //                 isFirstLine = false;
    //                 continue;
    //             }

    //             String[] data = line.split(",");
    //             if (data.length >= 3) {
    //                 String nama = data[0].trim();
    //                 String asal = data[1].trim();
    //                 int idLomba = Integer.parseInt(data[2].trim());

    //                 pst.setString(1, nama);
    //                 pst.setString(2, asal);
    //                 pst.setInt(3, idLomba);
    //                 pst.addBatch();
    //             }
    //         }

    //         pst.executeBatch();
    //         return true;

    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }

    public String importPesertaDariCSV(File file) {
        List<String> barisGagal = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            Connection conn = dbConnection.getConnection();
            String sql = "INSERT INTO peserta (nama_peserta, asal, id_lomba) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            String line;
            boolean isFirstLine = true;
            int barisKe = 1;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    barisKe++;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length >= 3) {
                    String nama = data[0].trim();
                    String asal = data[1].trim();
                    int idLomba = Integer.parseInt(data[2].trim());

                    try {
                        pst.setString(1, nama);
                        pst.setString(2, asal);
                        pst.setInt(3, idLomba);
                        pst.executeUpdate();
                    } catch (SQLException e) {
                        barisGagal.add("Baris " + barisKe + " gagal: " + line);
                    }
                } else {
                    barisGagal.add("Baris " + barisKe + " tidak lengkap: " + line);
                }

                barisKe++;
            }

            if (barisGagal.isEmpty()) {
                return "SUKSES";
            } else {
                StringBuilder msg = new StringBuilder("Import selesai, tetapi beberapa baris gagal:\n");
                for (String gagal : barisGagal) {
                    msg.append(gagal).append("\n");
                }
                return msg.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }







    
}
