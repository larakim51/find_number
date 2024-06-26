package  model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameData {
    private int id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numPlayers;
    private List<Integer> numbersToFind;
    private int gameDuration;
    private int luckyNumber;
    private int luckyNumberBonus = 5; 
    private List<PlayerSession> players;
    private int currentNumber;
    private int currentPlayerIndex;

    public GameData(int gameSessionId) {
        // Lấy thông tin từ cơ sở dữ liệu dựa trên gameSessionId
        this.id = gameSessionId;
        this.startTime = LocalDateTime.now();
        this.numbersToFind = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            numbersToFind.add(i);
        }
        Collections.shuffle(numbersToFind); 
        this.currentNumber = numbersToFind.remove(0); 
    }

    public void start() {
        // Khởi tạo các PlayerSession từ database
    }

    public boolean checkNumber(PlayerSession player, int number) {
        if (number == currentNumber) {
            player.getFoundNumbers().add(number);
            if (number == luckyNumber) {
                player.updateScore(luckyNumberBonus);
            }
            player.updateScore(1);

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

    public List<PlayerSession> getPlayers() {
        return players;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    // ... (Thêm các setters nếu cần)

    public PlayerSession getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }
}
