package model;

import java.util.ArrayList;
import java.util.List;

public class MultiSession {
    private Player player;
    private String color;
    private List<Integer> foundNumbers;
    private int score;

    public MultiSession(Player player, GameData gameData, String color) {
        this.player = player;
        this.color = color;
        this.foundNumbers = new ArrayList<>();
        this.score = 0;
    }

    // Getters và setters
    public Player getPlayer() {
        return player;
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFoundNumbers(List<Integer> foundNumbers) {
        this.foundNumbers = foundNumbers; // Hoặc new ArrayList<>(foundNumbers)
    }

    public void setScore(int score) {
        this.score = score;
    }
   
    public void updateScore(int points) {
        score += points;
    }
}
