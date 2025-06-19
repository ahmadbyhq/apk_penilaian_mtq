/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author baiha
 */
public class AspekPenilaian {
    
    private int id_aspek;
    private int id_lomba;
    private String nama_aspek;
    private String nama_lomba;
    private int presentase;
    
    public AspekPenilaian(int id_aspek, int id_lomba, String nama_aspek, String nama_lomba, int presentase) {
        this.id_aspek = id_aspek;
        this.id_lomba = id_lomba;
        this.nama_aspek = nama_aspek;
        this.nama_lomba = nama_lomba;
        this.presentase = presentase;
    }

    public int getIdAspek() {
    return id_aspek;
}

    public void setIdAspek(int id_aspek) {
        this.id_aspek = id_aspek;
    }

    public int getIdLomba() {
        return id_lomba;
    }

    public void setIdLomba(int id_lomba) {
        this.id_lomba = id_lomba;
    }

    public String getNamaAspek() {
        return nama_aspek;
    }

    public void setNamaAspek(String nama_aspek) {
        this.nama_aspek = nama_aspek;
    }

    public String getNamaLomba() {
    return nama_lomba;
}

    public void setNamaLomba(String nama_lomba) {
        this.nama_lomba = nama_lomba;
    }

    public int getPresentase() {
        return presentase;
    }

    public void setPresentase(int presentase) {
        this.presentase = presentase;
    }

    @Override
    public String toString() {
        return nama_aspek + " (" + presentase + "%)";
    }



    
    
}
