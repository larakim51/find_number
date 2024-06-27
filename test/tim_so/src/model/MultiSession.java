package model;
import java.util.ArrayList;
import java.util.List;
import model.Player;

public class MultiSession {
    private Player player; // Sử dụng Player thay vì User
    private GameData game;
    private String color;
    private List<Integer> foundNumbers;
    private int score;

    public MultiSession(Player player, GameData game, String color) {
        this.player = player;
        this.game = game;
        this.color = color;
        this.foundNumbers = new ArrayList<>();
        this.score = 0;
    }

    public void findNumber(int number) {
        if (game.checkNumber(this, number)) {
            // Xử lý khi tìm đúng số (ví dụ: gửi thông báo đến client)
        }
    }

    public void updateScore(int points) {
        this.score += points;
    }

    // Getters và setters

    public Player getPlayer() { 
        return player; 
    }

    public GameData getGame() {
        return game;
    }

    public String getColor() {
        return color;
    }

    public List<Integer> getFoundNumbers() {
        return foundNumbers;
    }

    public int getScore() {
        return score;
    }
}
