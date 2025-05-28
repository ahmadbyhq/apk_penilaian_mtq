import java.sql.*;

public class Lomba {
    private int id_lomba;
    private String nama_lomba;

    public Lomba(int id_lomba, String nama_lomba) {
        this.id_lomba = id_lomba;
        this.nama_lomba = nama_lomba;
    }

    public int getId() {
        return id_lomba;
    }

    public int getIdLomba() {
        return id_lomba;
    }

    public String getNamaLomba() {
        return nama_lomba;
    }

    public static Lomba getLombaByName(String nama_lomba) {
        try {
            Connection conn = database.getConnection();
            String sql = "SELECT * FROM lomba WHERE nama_lomba = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nama_lomba);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Lomba(rs.getInt("id_lomba"), rs.getString("nama_lomba"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
