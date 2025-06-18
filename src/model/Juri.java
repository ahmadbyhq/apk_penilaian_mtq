package model;

import config.dbConnection;
import java.util.*;
import java.sql.*;
import model.User;

public class Juri extends User {

    public Juri() {
        super();
    }

    public Juri(int id, String username, String password, String nama, String role) {
        super(id, username, password, nama, role);
    }

    public static void beriNilai(int id_peserta, int id_juri) {
        try (Connection conn = dbConnection.getConnection()) {
            // 1. Ambil id_lomba dari peserta
            String sqlPeserta = "SELECT id_lomba FROM peserta WHERE id_peserta = ?";
            PreparedStatement stmtPeserta = conn.prepareStatement(sqlPeserta);
            stmtPeserta.setInt(1, id_peserta);
            ResultSet rsPeserta = stmtPeserta.executeQuery();

            if (!rsPeserta.next()) {
                System.out.println("Peserta tidak ditemukan.");
                return;
            }

            int id_lomba = rsPeserta.getInt("id_lomba");

            // 2. Ambil semua aspek untuk lomba tersebut
            String sqlAspek = "SELECT id_aspek, nama_aspek FROM aspek_penilaian WHERE id_lomba = ?";
            PreparedStatement stmtAspek = conn.prepareStatement(sqlAspek);
            stmtAspek.setInt(1, id_lomba);
            ResultSet rsAspek = stmtAspek.executeQuery();

            Scanner input = new Scanner(System.in);

            // 3. Loop tiap aspek → ambil nilai & simpan ke DB
            while (rsAspek.next()) {
                int id_aspek = rsAspek.getInt("id_aspek");
                String nama_aspek = rsAspek.getString("nama_aspek");

                System.out.print("Masukkan nilai untuk aspek " + nama_aspek + " (1-100): ");
                int nilai = input.nextInt();

                String insertNilai = "INSERT INTO nilai (id_peserta, id_juri, id_lomba, id_aspek, skor) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmtNilai = conn.prepareStatement(insertNilai);
                stmtNilai.setInt(1, id_peserta);
                stmtNilai.setInt(2, id_juri);
                stmtNilai.setInt(3, id_lomba);
                stmtNilai.setInt(4, id_aspek);
                stmtNilai.setInt(5, nilai);
                stmtNilai.executeUpdate();
            }

            System.out.println("Nilai berhasil disimpan.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tampilkanNilai(int id_peserta, int id_juri) {
        try (Connection conn = dbConnection.getConnection()) {

            String sql = """
            SELECT 
                p.nama_peserta,
                l.nama_lomba,
                a.nama_aspek,
                n.skor,
                u.nama AS nama_juri
            FROM nilai n
            JOIN peserta p ON n.id_peserta = p.id_peserta
            JOIN lomba l ON n.id_lomba = l.id_lomba
            JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
            JOIN users u ON n.id_juri = u.id_user
            WHERE n.id_peserta = ? AND n.id_juri = ?
        """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_peserta);
            stmt.setInt(2, id_juri);

            ResultSet rs = stmt.executeQuery();

            System.out.println("\n===== Daftar Nilai Peserta =====");
            while (rs.next()) {
                String namaPeserta = rs.getString("nama_peserta");
                String namaLomba = rs.getString("nama_lomba");
                String namaAspek = rs.getString("nama_aspek");
                int skor = rs.getInt("skor");
                String namaJuri = rs.getString("nama_juri");

                System.out.printf("Peserta: %s | Lomba: %s | Aspek: %s | Skor: %d | Juri: %s\n",
                        namaPeserta, namaLomba, namaAspek, skor, namaJuri);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editNilaiPeserta(int id_peserta, int id_juri) {
        try (Connection conn = dbConnection.getConnection()) {
            // 1. Ambil nilai yang pernah diberikan oleh juri tersebut untuk peserta ini
            String query = "SELECT n.id_nilai, a.nama_aspek, n.skor " +
                    "FROM nilai n " +
                    "JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek " +
                    "JOIN users u ON n.id_juri = u.id_user " +
                    "WHERE n.id_peserta = ? AND n.id_juri = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_peserta);
            stmt.setInt(2, id_juri);
            ResultSet rs = stmt.executeQuery();

            Scanner input = new Scanner(System.in);
            while (rs.next()) {
                int id_nilai = rs.getInt("id_nilai");
                String aspek = rs.getString("nama_aspek");
                double skorLama = rs.getDouble("skor");

                System.out.printf("Nilai untuk aspek %s sebelumnya: %.2f. Masukkan nilai baru: ", aspek, skorLama);
                double skorBaru = input.nextDouble();

                // 2. Update skor
                String update = "UPDATE nilai SET skor = ? WHERE id_nilai = ?";
                PreparedStatement updateStmt = conn.prepareStatement(update);
                updateStmt.setDouble(1, skorBaru);
                updateStmt.setInt(2, id_nilai);
                updateStmt.executeUpdate();
            }

            System.out.println("Nilai berhasil diperbarui.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hapusNilaiPeserta(int id_peserta, int id_juri) {
        try (Connection conn = dbConnection.getConnection()) {

            // Cek dulu apakah nilai yang dimaksud ada
            String cekQuery = "SELECT COUNT(*) AS total FROM nilai WHERE id_peserta = ? AND id_juri = ?";
            PreparedStatement cekStmt = conn.prepareStatement(cekQuery);
            cekStmt.setInt(1, id_peserta);
            cekStmt.setInt(2, id_juri);
            ResultSet rs = cekStmt.executeQuery();

            if (rs.next() && rs.getInt("total") == 0) {
                System.out.println("Tidak ditemukan nilai dari juri ini untuk peserta tersebut.");
                return;
            }

            // Hapus semua nilai
            String deleteQuery = "DELETE FROM nilai WHERE id_peserta = ? AND id_juri = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, id_peserta);
            deleteStmt.setInt(2, id_juri);

            int rowsAffected = deleteStmt.executeUpdate();
            System.out.println(rowsAffected + " nilai berhasil dihapus.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void gantiIdPeserta(int id_juri, int id_peserta) {
        Scanner input = new Scanner(System.in);

        try {
            System.out.println("=== Mengarahkan untuk mengganti ID peserta yang sedang ditinjau ===");

        } catch (Exception e) {
            System.out.println("Input tidak valid. Silakan masukkan angka.");
            gantiIdPeserta(id_juri, id_peserta); // retry kalau gagal
        }
    }

    public static void rekapNilaiPeserta(int id_peserta, int id_juri) {
        try (Connection conn = dbConnection.getConnection()) {
            String sql = """
            SELECT 
                a.nama_aspek,
                a.presentase,
                n.skor
            FROM nilai n
            JOIN aspek_penilaian a ON n.id_aspek = a.id_aspek
            WHERE n.id_peserta = ? AND n.id_juri = ?
        """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id_peserta);
            stmt.setInt(2, id_juri);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Masuk query...");

            double totalSkor = 0;
            double totalPersen = 0;

            System.out.println("\n===== Rekap Nilai Peserta =====");
            while (rs.next()) {
                String aspek = rs.getString("nama_aspek");
                double persen = rs.getDouble("presentase"); // ini yang jadi bobot aspek
                int skor = rs.getInt("skor");

                double kontribusi = (skor * persen) / 100.0;
                totalSkor += kontribusi;
                totalPersen += persen;

                System.out.printf("Aspek: %-25s | Skor: %3d | Bobot: %4.1f%% | Kontribusi: %.2f\n",
                        aspek, skor, persen, kontribusi);
            }

            System.out.println("---------------------------------------------------------------");
            System.out.printf("Total Skor Akhir (terbobot): %.2f dari maksimal %.1f\n", totalSkor, totalPersen);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tampilPeran() {
        System.out.println("Saya Juri: " + nama);
    }
}

