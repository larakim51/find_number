package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String roomId;
    private List<Player> players;
    private boolean gameStarted;

    public Room(String roomId) {
        this.roomId = roomId;
        this.players = new ArrayList<>();
        this.gameStarted = false;
    }

    public String getRoomId() {
        return roomId;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (players.size() < 3) {
            players.add(player);
        }
    }

    public boolean isFull() {
        return players.size() == 3;
    }

    public void startGame() {
        this.gameStarted = true;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }
}
