package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSession {
    private List<Player> players;
    private List<Integer> numbers; 
    
    private static final int MAX_NUMBERS_TO_SEND = 3; // Giới hạn số lượng số gửi
    private static final int NUMBERS_PER_LIST = 100;
    public GameSession() {
        this.players = new ArrayList<>();
        this.numbers = new ArrayList<>();
        for (int i=1 ; i <= NUMBERS_PER_LIST ; i++){
            this.numbers.add(i);
        }
    }
    public List<Integer> getNumbersToSend() {
        List<Integer> allNumbers = new ArrayList<>();
        for (int i = 1; i <= NUMBERS_PER_LIST; i++) {
            allNumbers.add(i);
        }
        Collections.shuffle(allNumbers);

     
        return allNumbers.subList(0, Math.min(MAX_NUMBERS_TO_SEND, allNumbers.size()));
    }

    public boolean isGameOver() {
        return numbers.isEmpty(); 
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