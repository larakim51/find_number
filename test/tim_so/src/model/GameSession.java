package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSession {
    private int id;
    private List<Player> players;
    private GameData gameData;
    private boolean started;
    private LocalDateTime startTime;
    private Map<Player, String> playerColors = new HashMap<>(); // Lưu trữ màu sắc của mỗi người chơi
    private int currentColorIndex = 0; // Theo dõi màu sắc tiếp theo cần gán
    private final List<String> availableColors = List.of("red", "blue", "green"); // Danh sách màu có thể chọn

    public GameSession(int id, GameData gameData) {
        this.id = id;
        this.players = new ArrayList<>();
        this.gameData = gameData;
        this.started = false;
    }

    private String assignColor() {
        String color = availableColors.get(currentColorIndex);
        currentColorIndex = (currentColorIndex + 1) % availableColors.size();
        return color;
    }

    public void addPlayer(Player player) {
        if (!isFull()) {
            players.add(player);
            player.setCurrentGame(this);
            String color = assignColor();
            playerColors.put(player, color);
            gameData.addPlayerSession(player, color);
        } else {
            // Thông báo phòng đã đầy
            // ... (code xử lý thông báo lỗi)
        }
    }

    public void startGame(){
        if (isFull()) {
            started = true;
            startTime = LocalDateTime.now();
            gameData.setPlayers(playerColors);
            gameData.start();
        } else {
            // Thông báo chưa đủ người chơi
            // ... (code xử lý thông báo lỗi)
        }
    }

    public boolean checkNumber(Player player, int number) {
        PlayerSession playerSession = gameData.getPlayers().get(player); // Lấy PlayerSession từ gameData
        if (playerSession != null && gameData.checkNumber(playerSession, number)) {
            // ... (xử lý khi tìm đúng số)
        }
        return false;
    }

    private void endGame() {
        // ... (code xử lý kết thúc game)
    }

    public int getId() {
        return id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameData getGameData() {
        return gameData;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isFull() {
        return players.size() >= gameData.getNumPlayers();
    }

    public boolean isGameOver() {
        return gameData.isGameOver();
    }

    public String getPlayerColor(Player player) {
        return playerColors.get(player);
    }
}
