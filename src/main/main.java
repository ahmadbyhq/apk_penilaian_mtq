package main;

import config.dbConnection;
import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Pilih User Anda:");
        System.out.println("1. Juri");
        System.out.println("2. Panitia");
        int peran = sc.nextInt();
        sc.nextLine();

        String role = (peran == 1) ? "juri" : "panitia";

        System.out.println("Masukkan Username:");
        String username = sc.nextLine();
        System.out.println("Masukkan Password:");
        String password = sc.nextLine();

        boolean loginBerhasil = AuthService.login(username, password, role);
        if (loginBerhasil) {
            System.out.println("Login berhasil sebagai " + role);

            if (role.equals("panitia")) {
                while (true) {
                    System.out.println("Menu Panitia:");
                    System.out.println("1. Tambah Juri");
                    System.out.println("2. Hapus Juri");
                    System.out.println("3. Edit Juri");
                    System.out.println("4. Tampilkan Peserta");
                    System.out.println("5. Tambah Peserta");
                    System.out.println("6. Hapus Peserta");
                    System.out.println("7. Edit Peserta");
                    System.out.println("8. Logout");
                    int menu = sc.nextInt();
                    sc.nextLine();

                    switch (menu) {
                        case 1:
                            System.out.println("Username juri Baru:");
                            String u = sc.nextLine();
                            System.out.println("Password Juri Baru:");
                            String p = sc.nextLine();
                            System.out.println("Nama Juri Baru:");
                            String n = sc.nextLine();
                            Panitia User = new Panitia(0, username, password, "Juri", role);
                            User.tambahJuri(u, p, n);
                            break;

                        case 2:
                            System.out.println("Username Juri yang ingin dihapus:");
                            String usernameHapus = sc.nextLine();
                            Panitia userHapus = new Panitia(0, username, password, "Panitia", role);
                            userHapus.hapusJuri(usernameHapus);
                            break;

                        case 3:
                            System.out.println("Username juri yang ingin diedit:");
                            String usernameLama = sc.nextLine();
                            System.out.println("Username baru:");
                            String usernameBaru = sc.nextLine();
                            System.out.println("Password baru:");
                            String passwordBaru = sc.nextLine();
                            System.out.println("Nama baru:");
                            String namaBaru = sc.nextLine();

                            Panitia userEdit = new Panitia(0, username, password, "Panitia", role);
                            userEdit.editJuri(usernameLama, usernameBaru, passwordBaru, namaBaru);
                            break;

                        case 4:
                            System.out.println("Daftar Lomba Tersedia:");
                            System.out.println("1. MTQ Tartil ");
                            System.out.println("2. MHQ 2 Juz ");
                            System.out.print("Pilih nomor lomba: ");
                            int pilihLomba = sc.nextInt();
                            sc.nextLine();
                            Lomba l = null;
                            if (pilihLomba == 1) {
                                l = Lomba.getLombaByName("MTQ Tartil");
                            } else if (pilihLomba == 2) {
                                l = Lomba.getLombaByName("MHQ 2 Juz");
                            }
                            if (l == null) {
                                System.out.println("Lomba tidak ditemukan.");
                                break;
                            }

                            List<Peserta> peserta = database.getPesertaByLomba(l.getId());
                            if (peserta.isEmpty()) {
                                System.out.println("Belum ada peserta di lomba ini.");
                            } else {
                                for (Peserta pe : peserta) {
                                    pe.infoPeserta();
                                }
                            }
                            break;
                        case 5:
                            System.out.println("Tambah Peserta Baru:");
                            System.out.println("ID Peserta:");
                            int idPeserta = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Nama Peserta:");
                            String namaPeserta = sc.nextLine();
                            System.out.println("Asal Peserta:");
                            String asalPeserta = sc.nextLine();
                            System.out.println("Daftar Lomba:");
                            System.out.println("1. MTQ Tartil ");
                            System.out.println("2. MHQ 2 Juz ");
                            System.out.print("Pilih nomor lomba: ");
                            int nomorLomba = sc.nextInt();
                            sc.nextLine();
                            Lomba lombaPeserta = null;
                            if (nomorLomba == 1) {
                                lombaPeserta = Lomba.getLombaByName("MTQ Tartil");
                            } else if (nomorLomba == 2) {
                                lombaPeserta = Lomba.getLombaByName("MHQ 2 Juz");
                            }
                            if (lombaPeserta == null) {
                                System.out.println("Lomba tidak valid.");
                                break;
                            }
                            Peserta pesertaBaru = new Peserta(namaPeserta, lombaPeserta, asalPeserta);
                            pesertaBaru.tambahPeserta(idPeserta, namaPeserta, asalPeserta, lombaPeserta.getIdLomba());
                            break;
                        case 6:
                            System.out.println("Nama Peserta yang ingin dihapus:");
                            String namaHapus = sc.nextLine();
                            Lomba dummyLomba = new Lomba(0, "");
                            Peserta pesertaHapus = new Peserta(namaHapus, dummyLomba, "");
                            pesertaHapus.hapusPeserta();
                            break;
                        case 7:
                            System.out.println("ID Peserta yang ingin diedit:");
                            int idEdit = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Nama baru:");
                            String namaBaruPeserta = sc.nextLine();
                            System.out.println("Asal baru:");
                            String asalBaru = sc.nextLine();
                            System.out.println("Daftar Lomba:");
                            System.out.println("1. MTQ Tartil ");
                            System.out.println("2. MHQ 2 Juz ");
                            System.out.print("Pilih nomor lomba: ");
                            int nomorBaru = sc.nextInt();
                            sc.nextLine();
                            Lomba lombaBaru = null;
                            if (nomorBaru == 1) {
                                lombaBaru = Lomba.getLombaByName("MTQ Tartil");
                            } else if (nomorBaru == 2) {
                                lombaBaru = Lomba.getLombaByName("MHQ 2 Juz");
                            }
                            if (lombaBaru == null) {
                                System.out.println("Lomba tidak valid.");
                                break;
                            }
                            Peserta pesertaEdit = new Peserta(namaBaruPeserta, lombaBaru, asalBaru);
                            pesertaEdit.editPeserta(namaBaruPeserta, asalBaru, lombaBaru);
                            break;
                        case 8:
                            System.out.println("Logout...");
                            return;

                        default:
                            System.out.println("Menu tidak valid.");
                    }
                }
            } else {
                Lomba lombaDipilih = null;
                while (true) {
                    System.out.println("Menu Juri");
                    System.out.println("1. Tentukan lomba yang ingin dinilai");
                    System.out.println("2. Hapus nilai peserta");
                    System.out.println("3. Logout");
                    System.out.print("Pilih menu: ");
                    int menu = sc.nextInt();
                    sc.nextLine();

                    switch (menu) {
                        case 1:
                            System.out.println("Daftar Lomba Tersedia:");
                            System.out.println("1. MTQ Tartil ");
                            System.out.println("2. MHQ 2 Juz ");
                            System.out.println("Masukkan nama lomba: ");
                            String lomba = sc.nextLine();
                            if (Objects.equals(lomba, "MTQ Tartil")) {
                                lombaDipilih = Lomba.getLombaByName("MTQ Tartil");
                            } else if (Objects.equals(lomba, "MHQ 2 Juz")) {
                                lombaDipilih = Lomba.getLombaByName("MHQ 2 Juz");
                            } else {
                                System.out.println("Pilihan tidak valid.");
                                break;
                            }
                            if (lombaDipilih != null) {
                                System.out.println("Lomba \"" + lombaDipilih.getNamaLomba() + "\" dipilih.");
                            } else {
                                System.out.println("Lomba tidak ditemukan di database.");
                            }
                            break;

                        case 2:
                            if (lombaDipilih == null) {
                                System.out.println("Pilih dulu lomba yang ingin dinilai (menu 1).");
                                break;
                            }
                            List<Peserta> pesertaLomba = database.getPesertaByLomba(lombaDipilih.getId());
                            if (pesertaLomba.isEmpty()) {
                                System.out.println("Tidak ada peserta pada lomba ini.");
                                break;
                            }

                            System.out.println("Pilih peserta yang ingin dihapus nilainya:");
                            for (int i = 0; i < pesertaLomba.size(); i++) {
                                System.out.println((i + 1) + ". " + pesertaLomba.get(i).getNamaPeserta());
                            }

                            int pilihHapus = sc.nextInt();
                            sc.nextLine();

                            if (pilihHapus >= 1 && pilihHapus <= pesertaLomba.size()) {
                                Peserta p = pesertaLomba.get(pilihHapus - 1);
                                boolean sukses = database.hapusNilaiPeserta(p.getNamaPeserta());
                                if (sukses) {
                                    System.out.println("Nilai peserta berhasil dihapus.");
                                } else {
                                    System.out.println("Gagal menghapus nilai peserta.");
                                }
                            } else {
                                System.out.println("Pilihan tidak valid.");
                            }
                            break;

                        case 3:
                            System.out.println("Logout...");
                            return;

                        default:
                            System.out.println("Menu tidak valid.");
                    }
                }
            }
        } else {
            System.out.println("Login gagal");
        }
    }
}
