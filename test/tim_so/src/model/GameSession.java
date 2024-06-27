package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import view.GameView;

public class GameSession extends Observable{
    private int id;
    private List<Player> players;
    private GameData gameData;
    private boolean started;
    private LocalDateTime startTime;
    private Map<Player, String> playerColors = new HashMap<>(); // Lưu trữ màu sắc của mỗi người chơi
    private int currentColorIndex = 0; // Theo dõi màu sắc tiếp theo cần gán
    private final List<String> availableColors = List.of("red", "blue", "green"); // Danh sách màu có thể chọn
    private List<GameView> observers = new ArrayList<>();

    public GameSession(int id, GameData gameData) {
        this.id = id;
        this.players = new ArrayList<>();
        this.gameData = gameData;
        this.started = false;
    }
    public void addObserver(GameView observer) {
        observers.add(observer);
    }

    private void notifyObservers(Player player, int number, boolean found) {
        for (GameView observer : observers) {
            observer.update(player, number, found);
        }
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
        PlayerSession playerSession = gameData.getPlayers().get(player);
        if (playerSession != null) {
            boolean isCorrect = gameData.checkNumber(playerSession, number);

            if (isCorrect) {
                setChanged(); // Đánh dấu trạng thái đã thay đổi
                notifyObservers(new int[]{number, playerSession.getScore()}); // Thông báo kết quả
            } else {
                setChanged();
                notifyObservers(new int[]{number, -1});
            }

            if (gameData.isGameOver()) {
                endGame();
                setChanged();
                notifyObservers("GAME_OVER"); // Thông báo kết thúc game
            }

            return isCorrect;
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
