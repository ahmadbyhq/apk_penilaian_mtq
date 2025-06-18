package main;

import model.Juri;
import config.dbConnection;
import controller.*;
import model.*;
import java.util.*;

public class main {
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

        boolean loginBerhasil = loginController.logins(username, password,role);
        if (loginBerhasil) {
            System.out.println("Login berhasil sebagai " + role);

            if (role.equals("panitia")) {
                while (true) {
                    System.out.println("Menu Panitia:");
                    System.out.println("1. Tampilkan Juri");
                    System.out.println("2. Tambah Juri");
                    System.out.println("3. Hapus Juri");
                    System.out.println("4. Edit Juri");
                    System.out.println("5. Tampilkan Peserta");
                    System.out.println("6. Tambah Peserta");
                    System.out.println("7. Hapus Peserta");
                    System.out.println("8. Edit Peserta");
                    System.out.println("9. Logout");
                    int menu = sc.nextInt();
                    sc.nextLine();
                    switch(menu) {
                        case 1:
                            juriController.tampilJuri();
                            break;
                        case 2:
                            System.out.println("Username juri Baru:");
                            String u = sc.nextLine();
                            System.out.println("Password Juri Baru:");
                            String p = sc.nextLine();
                            System.out.println("Nama Juri Baru:");
                            String n = sc.nextLine();
                            Panitia User = new Panitia(0, username, password, "Juri", role);
                            User.tambahJuri(u, p, n);
                            break;

                        case 3:
                            System.out.println("Username Juri yang ingin dihapus:");
                            String usernameHapus = sc.nextLine();
                            Panitia userHapus = new Panitia(0, username, password, "Panitia", role);
                            userHapus.hapusJuri(usernameHapus);
                            break;

                        case 4:
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

                        case 5:
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

                        case 6:
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
                            Peserta pesertaBaru = new Peserta(idPeserta, namaPeserta, lombaPeserta, asalPeserta);
                            pesertaBaru.tambahPeserta(idPeserta, namaPeserta, asalPeserta, lombaPeserta.getIdLomba());
                            break;

                        case 7:
                            System.out.println("Nama Peserta yang ingin dihapus:");
                            String namaHapus = sc.nextLine();
                            Lomba dummyLomba = new Lomba(0, "", 0);
                            Peserta pesertaHapus = new Peserta(0, namaHapus, dummyLomba, "");
                            pesertaHapus.hapusPeserta();
                            break;

                        case 8:
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
                            Peserta pesertaEdit = new Peserta(idEdit, namaBaruPeserta, lombaBaru, asalBaru);
                            pesertaEdit.editPeserta(namaBaruPeserta, asalBaru, lombaBaru);
                            break;

                        case 9:
                            System.out.println("Logout...");
                            return;

                        default:
                            System.out.println("Menu tidak valid.");
                    }
                }
            } else {
                while (true) {
                    int id_juri = juriController.getIdJuri(username);
                    System.out.println("=== Juri ===");
                    System.out.println("Tentukan ID peserta yang ingin ditinjau");
                    int id_peserta = sc.nextInt();
                    sc.nextLine();
                    System.out.println("1. Tampilkan nilai peserta");
                    System.out.println("2. Tambah nilai peserta");
                    System.out.println("3. Edit nilai peserta");
                    System.out.println("4. Hapus nilai peserta");
                    System.out.println("5. Ganti ID peserta");
                    System.out.println("6. Rekap nilai peserta");
                    System.out.println("7. Logout");
                    System.out.print("Pilih menu: ");
                    int menu = sc.nextInt();
                    sc.nextLine();

                    switch(menu) {
                        case 1:
                            Juri.tampilkanNilai(id_peserta, id_juri);
                            break;
                        case 2:
                            Juri.beriNilai(id_peserta, id_juri);
                            break;
                        case 3:
                            Juri.editNilaiPeserta(id_peserta, id_juri);
                            break;
                        case 4:
                            Juri.hapusNilaiPeserta(id_peserta, id_juri);
                            break;
                        case 5:
                            Juri.gantiIdPeserta(id_juri, id_peserta);
                            break;
                        case 6:
                            Juri.rekapNilaiPeserta(id_peserta, id_juri);
                            break;
                        case 7:
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
