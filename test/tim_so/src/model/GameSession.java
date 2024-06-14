package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSession {
    private List<Player> players;
    private List<Integer> numbers; // Danh sách chứa các số từ 1 đến 100
    private int currentTime; // Thời gian hiện tại của ván chơi
    private int currentPlayerIndex; // Chỉ số của người chơi hiện tại

    public GameSession() {
        this.players = new ArrayList<>();
        this.numbers = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            this.numbers.add(i);
        }
        Collections.shuffle(this.numbers); // Trộn các số
        this.currentTime = 0;
        this.currentPlayerIndex = 0;
    }

    // Getters và Setters
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public Player getCurrentPlayer() {
        if (players.size() > 0) {
            return players.get(currentPlayerIndex);
        } else {
            return null;
        }
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    // Các phương thức khác
    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
