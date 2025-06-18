package model;

import controller.*;
import java.sql.*;

public class Lomba {
    private int id_lomba;
    private String nama_lomba;
    private int kuota;

    public Lomba(int id_lomba, String nama_lomba, int kuota) {
        this.id_lomba = id_lomba;
        this.nama_lomba = nama_lomba;
        this.kuota = kuota;
    }

    public Lomba(int id_lomba, String nama_lomba) {
        this.id_lomba = id_lomba;
        this.nama_lomba = nama_lomba;
    }

    


    //  getter
    public int getId() {
        return id_lomba;
    }

    public int getIdLomba() {
        return id_lomba;
    }

    public int getKuota() {
        return kuota;
    }

    public String getNamaLomba() {
        return nama_lomba;
    }

    // setter
    public void setIdLomba(int id_lomba) {
        this.id_lomba = id_lomba;
    }

    public void setNamaLomba(String nama_lomba) {
        this.nama_lomba = nama_lomba;
    }

    public void setKuota(int kuota) {
        this.kuota = kuota;
    }

    public static Lomba getLombaByName(String nama_lomba) {
        try {
            Connection conn = database.getConnection();
            String sql = "SELECT * FROM lomba WHERE nama_lomba = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nama_lomba);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Lomba(rs.getInt("id_lomba"), rs.getString("nama_lomba"), rs.getInt("kuota"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}