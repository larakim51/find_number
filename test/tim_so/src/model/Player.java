package model;

public class Player {
    private String username;
    private String password; // Mật khẩu cần được mã hóa khi lưu trữ
    private int score;

    public Player(String username, String password) {
        this.username = username;
        this.password = password; // Bạn cần mã hóa mật khẩu trước khi lưu
        this.score = 0;
    }

    // Getters và Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password; // Nhớ mã hóa mật khẩu
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // Các phương thức khác nếu cần
}
