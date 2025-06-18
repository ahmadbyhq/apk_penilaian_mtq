/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Chris Bimo
 */
public class Juara {
    private String nama_peserta;
    private String nama_lomba;
    private double totalNilai;
    private int juara;

    public Juara(String nama_peserta, String nama_lomba, double totalNilai, int juara) {
        this.nama_peserta = nama_peserta;
        this.nama_lomba = nama_lomba;
        this.totalNilai = totalNilai;
        this.juara = juara;
    }

    public String getNamaPeserta() {
        return nama_peserta;
    }

    public String getNamaLomba() {
        return nama_lomba;
    }

    public double getTotalNilai() {
        return totalNilai;
    }

    public int getJuara() {
        return juara;
    }
}
