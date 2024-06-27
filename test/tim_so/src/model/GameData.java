package  model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameData {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numPlayers;
    private List<Integer> numbersToFind;
    private int gameDuration;
    private int luckyNumber;
    private int luckyNumberBonus = 5; 
    private Map<Player, PlayerSession> playerSessions = new HashMap<>();
    private int currentNumber;
    private int currentPlayerIndex = 0;

    public GameData(int roomId, int gameDuration) {
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
    public void addPlayerSession(Player player, String color) {
        PlayerSession playerSession = new PlayerSession(player, this, color);
        playerSessions.put(player, playerSession);
    }
    public void setPlayers(Map<Player, String> playerColors) {
        this.playerSessions = new HashMap<>();
        for (Map.Entry<Player, String> entry : playerColors.entrySet()) {
            Player player = entry.getKey();
            String color = entry.getValue();
            PlayerSession playerSession = new PlayerSession(player, this, color);
            this.playerSessions.put(player, playerSession);
        }
        this.numPlayers = playerSessions.size();
        this.luckyNumber = numbersToFind.get((int) (Math.random() * 100));
    }

    public boolean checkNumber(PlayerSession playerSession, int number) { // Sử dụng PlayerSession
        if (number == currentNumber) {
            playerSession.getFoundNumbers().add(number);
            if (number == luckyNumber) {
                playerSession.updateScore(luckyNumberBonus);
            }
            playerSession.updateScore(1);

            if (numbersToFind.isEmpty()) {
                end();
            } else {
                currentNumber = numbersToFind.remove(0);
                nextTurn();
            }
            return true; 
        } else {
            return false; 
        }
    }
    public boolean isGameOver() {
        return numbersToFind.isEmpty() || LocalDateTime.now().isAfter(endTime); // Kiểm tra hết số hoặc hết giờ
    }
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
    }

    public void end() {
        endTime = LocalDateTime.now();
        // Cập nhật kết quả vào database
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

    public Map<Player, PlayerSession> getPlayers() {
        return playerSessions;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }


    // ... (Thêm các setters nếu cần)

    public PlayerSession getCurrentPlayer() {
        return (PlayerSession) playerSessions.values().toArray()[currentPlayerIndex];
    }

    public void addPlayerSession(Player player, PlayerSession ps){
        playerSessions.put(player, ps);
    }
}
