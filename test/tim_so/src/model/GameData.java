package  model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class GameData extends Observable{
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numPlayers;
    private List<Integer> numbersToFind;
    private int gameDuration;
    private int luckyNumber;
    private int luckyNumberBonus = 5; 
    private Map<Player, MultiSession> playerSessions = new HashMap<>();
    private int currentNumber;
    private int currentPlayerIndex = 0;
    private boolean gameOver;
    

    public GameData(int roomId, int gameDuration) {
        this.gameOver = false;
        this.id = roomId;
        this.startTime = LocalDateTime.now();
        this.numbersToFind = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            numbersToFind.add(i);
        }
        Collections.shuffle(numbersToFind); 
        this.currentNumber = numbersToFind.remove(0); 
        this.gameDuration = gameDuration;
        this.endTime = startTime.plusSeconds(gameDuration);
    }

    public void start() {
        // Khởi tạo các PlayerSession từ database
    }

    public boolean isGameOver() {
        return numbersToFind.isEmpty() || LocalDateTime.now().isAfter(endTime); // Kiểm tra hết số hoặc hết giờ
    }
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
    }

    public void endGame() {
        endTime = LocalDateTime.now();
        setChanged();
        notifyObservers("GAME_OVER"); // Thông báo kết thúc game cho GameView
    }
    // Getters và setters

    public int getId() {
        return id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public List<Integer> getNumbersToFind() {
        return numbersToFind;
    }

    public int getGameDuration() {
        return gameDuration;
    }

    public int getLuckyNumber() {
        return luckyNumber;
    }

    public int getLuckyNumberBonus() {
        return luckyNumberBonus;
    }

    public Map<Player, MultiSession> getPlayers() {
        return playerSessions;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }


    // ... (Thêm các setters nếu cần)

    public MultiSession getCurrentPlayer() {
        return (MultiSession) playerSessions.values().toArray()[currentPlayerIndex];
    }

    public void addPlayerSession(Player player, MultiSession ps){
        playerSessions.put(player, ps);
    }
}
