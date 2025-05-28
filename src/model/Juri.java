import java.util.*;

public class Juri extends User {
    public Juri(int id, String username, String password, String nama, String role) {
        super(id, username, password, nama, role);
    }

    @Override
    public void tampilPeran() {
        System.out.println("Saya Juri: " + nama);
    }

    public void beriNilai() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Masukkan Nama Peserta:");
        String namaPeserta = sc.nextLine();

        System.out.println("Pilih Lomba: \n1. MTQ Tartil \n2. MHQ 2 Juz");
        String namaLomba = sc.nextLine();
        Lomba lomba = Lomba.getLombaByName(namaLomba);

        if (lomba == null) {
            System.out.println("Lomba tidak ditemukan.");
            return;
        }

        System.out.println("Masukkan Nilai (0-100):");

        System.out.print("Tajwid: ");
        double tajwid = sc.nextDouble();
        System.out.print("Fashahah: ");
        double fashahah = sc.nextDouble();
        System.out.print("Adab: ");
        double adab = sc.nextDouble();
        System.out.print("Suara dan Lagu: ");
        double suaraLagu = sc.nextDouble();
        System.out.print("Penguasaan Juz: ");
        double penguasaanJuz = sc.nextDouble();

        Nilai.inputNilai(lomba.getId(), this.id, namaPeserta, tajwid, fashahah, adab, suaraLagu, penguasaanJuz);
    }
}
