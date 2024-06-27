package model;

public class Player {
    private String username;
    private String password; // Mật khẩu cần được mã hóa khi lưu trữ
    private int score;  
    private String email;        
    private int ranking;       
    private int wins;          
    private int losses;        
    private int totalGames;
    private GameSession currentGame;

    public Player(String username, String password, int score, String email, int ranking, int wins, int losses, int totalGames) {
        this.username = username;
        this.password = password; // Lưu ý: Cần mã hóa mật khẩu trước khi lưu trữ
        this.score = score;
        this.email = email;
        this.ranking = ranking;
        this.wins = wins;
        this.losses = losses;
        this.totalGames = totalGames;
    }

    public Player(String username, String password) {
        this.username = username;
        this.password = password; // Bạn cần mã hóa mật khẩu trước khi lưu
        this.score = 0;
    }

    // Getters và Setters
    public GameSession getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(GameSession currentGame) {
        this.currentGame = currentGame;
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
    // Các phương thức khác nếu cần
}
