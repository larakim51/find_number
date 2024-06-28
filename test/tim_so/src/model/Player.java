package model;

import java.io.PrintWriter;

public class Player {
    private String username;
    private String password; // Mật khẩu cần được mã hóa khi lưu trữ
    private int score;  
    private String email;        
    private int ranking;       
    private int wins;          
    private int losses;        
    private int totalGames;
    private String color;
    private PrintWriter out;

    public Player(String username, String password, int score, String email, int ranking, int wins, int losses, int totalGames) {
        this.username = username;
        this.password = password; // Lưu ý: Cần mã hóa mật khẩu trước khi lưu trữ
        this.score = 0;
        this.email = email;
        this.ranking = ranking;
        this.wins = wins;
        this.losses = losses;
        this.totalGames = totalGames;
        this.out = out;
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password; 
        this.score = 0;
    }
    public Player(String username, PrintWriter out) {
        this.username = username;
        this.out = out;
        this.score = 0;
    }
    public Player(String username) {
        this.username = username;
        this.score = 0; // Initialize score to 0
    }

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

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getRanking() {
        return ranking;
    }
    
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
    
    public int getWins() {
        return wins;
    }
    
    public void setWins(int wins) {
        this.wins = wins;
    }
    
    public int getLosses() {
        return losses;
    }
    
    public void setLosses(int losses) {
        this.losses = losses;
    }
    
    public int getTotalGames() {
        return totalGames;
    }
    
    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }
    public String getPlayerName(){
        return username;
        }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void sendMessage(String message) {
        out.println(message);
    }
    // Các phương thức khác nếu cần
}
