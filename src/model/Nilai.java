package model;

import controller.*;
import java.sql.*;


public class Nilai {
    private int id_nilai;
    private int id_peserta;
    private int id_juri;
    private int id_lomba;
    private int id_aspek;
    private double skor;


    public Nilai(int id_nilai, int id_peserta, int id_juri, int id_lomba, int id_aspek, double skor) {
        this.id_nilai = id_nilai;
        this.id_peserta = id_peserta;
        this.id_juri = id_juri;
        this.id_lomba = id_lomba;
        this.id_aspek = id_aspek;
        this.skor = skor;
    }

    // Getter & Setter
    public int getId_nilai() {
        return id_nilai;
    }

    public void setId_nilai(int id_nilai) {
        this.id_nilai = id_nilai;
    }

    public int getId_peserta() {
        return id_peserta;
    }

    public void setId_peserta(int id_peserta) {
        this.id_peserta = id_peserta;
    }

    public int getId_juri() {
        return id_juri;
    }

    public void setId_juri(int id_juri) {
        this.id_juri = id_juri;
    }

    public int getId_lomba() {
        return id_lomba;
    }

    public void setId_lomba(int id_lomba) {
        this.id_lomba = id_lomba;
    }

    public int getId_aspek() {
        return id_aspek;
    }

    public void setId_aspek(int id_aspek) {
        this.id_aspek = id_aspek;
    }

    public double getSkor() {
        return skor;
    }

    public void setSkor(double skor) {
        this.skor = skor;
    }
}