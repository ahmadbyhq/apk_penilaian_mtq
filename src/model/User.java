package model;

public class User {
    private int id;
    private String username;
    private String password;
    private String nama;
    private String role;

    public User(int id, String username, String password, String nama, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nama = nama;
        this.role = role;
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNama() {
        return nama;
    }

    public String getRole() {
        return role;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
