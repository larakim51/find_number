package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSession {
    private List<Player> players;
    private List<Integer> numbers; // Danh sách chứa các số từ 1 đến 100

    public GameSession() {
        this.players = new ArrayList<>();
        this.numbers = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            this.numbers.add(i);
        }
        Collections.shuffle(this.numbers); // Trộn các số
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

    // Các phương thức khác
    public void addPlayer(Player player) {
        this.players.add(player);
    }
    
}