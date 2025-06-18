/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Chris Bimo
 */
public class LombaItem {
    private int id_lomba;
    private String nama_lomba;

    public LombaItem(int id_lomba, String nama_lomba) {
        this.id_lomba = id_lomba;
        this.nama_lomba = nama_lomba;
    }

    public int getIdLomba() {
        return id_lomba;
    }

    @Override
    public String toString() {
        return nama_lomba; // biar yang tampil di comboBox adalah namaLomba
    }
}

