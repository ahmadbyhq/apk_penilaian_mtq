package model;

import java.sql.*;
public class Peserta {
    private int id_peserta;
    private String nama_peserta;
    private Lomba id_lomba;
    private String asal;


    public Peserta(int id_peserta, String nama_peserta, Lomba id_lomba, String asal){
        this.id_peserta = id_peserta;
        this.nama_peserta = nama_peserta;
        this.id_lomba = id_lomba;
        this.asal = asal;
    }


    public Peserta(String nama_peserta, Lomba id_lomba, String asal) {
        if (id_lomba == null) {
            throw new IllegalArgumentException("Lomba tidak boleh kosong.");
        }
        // this.id_peserta = id_peserta;
        this.nama_peserta = nama_peserta;
        this.id_lomba = id_lomba;
        this.asal = asal;
    }


    public int getIdPeserta() {
        return id_peserta;
    }

    public String getNamaPeserta() {
        return nama_peserta;
    }
    public Lomba getLomba() {
        return id_lomba;
    }
    public String getAsal() {
        return asal;
    }

    public void setIdPeserta(int id_peserta) {
        this.id_peserta = id_peserta;
    }

    public void setNamaPeserta(String nama_peserta) {
        this.nama_peserta = nama_peserta;
    }

    public void setLomba(Lomba id_lomba) {
        this.id_lomba = id_lomba;
    }

    public void setAsal(String asal) {
        this.asal = asal;
    }



    @Override
    public String toString() {
        return nama_peserta + " - " + asal;
    }

    // public void infoPeserta() {
    //     System.out.println("=== Data Peserta ===");
    //     System.out.println("Nama    : " + nama_peserta);
    //     System.out.println("Asal    : " + asal);
    //     System.out.println("Lomba   : " + id_lomba.getNamaLomba());
    // }

    // @Override
    // public String toString() {
    //     return String.format("Peserta[nama=%s, asal=%s, lomba=%s]",
    //             nama_peserta, asal, id_lomba.getNamaLomba());
    // }

    public void tambahPeserta(int id_peserta, String nama_peserta, String asal, int id_lomba){
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq",
                    "u725894752_appmtqpbo",
                    "Appmtqpbo{07};");
            String sql = "INSERT INTO peserta (id_peserta, nama_peserta, asal, id_lomba) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_peserta);
            stmt.setString(2, nama_peserta);
            stmt.setString(3, asal);
            stmt.setInt(4, id_lomba);
            stmt.executeUpdate();
            System.out.println("Peserta berhasil ditambahkan!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hapusPeserta() {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq",
                    "u725894752_appmtqpbo",
                    "Appmtqpbo{07};");
            String sql = "DELETE FROM peserta WHERE nama_peserta = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, this.nama_peserta);
            int affected = stmt.executeUpdate();
            if (affected > 0) {
                System.out.println("Akun peserta berhasil dihapus.");
            } else {
                System.out.println("Akun tidak ditemukan atau bukan peserta.");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editPeserta(String namaBaru, String rumahBaru, Lomba lombaBaru) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://auth-db1410.hstgr.io:3306/u725894752_app_mtq",
                    "u725894752_appmtqpbo",
                    "Appmtqpbo{07};");
            String sql = "UPDATE peserta SET nama_peserta = ?, asal = ?, id_lomba = ? WHERE id_peserta = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, namaBaru);
            stmt.setString(2, rumahBaru);
            stmt.setInt(3, lombaBaru.getIdLomba());
            stmt.setInt(4, this.id_peserta);
            int updated = stmt.executeUpdate();
            if (updated > 0) {
                System.out.println("Profil peserta berhasil diupdate.");
                this.nama_peserta = namaBaru;
                this.asal = rumahBaru;
                this.id_lomba = lombaBaru;
            } else {
                System.out.println("Gagal update, peserta tidak ditemukan atau invalid.");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
