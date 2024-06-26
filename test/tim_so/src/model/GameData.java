package model;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class GameData {
    private int targetNumber; // Số cần tìm
    private Map<SocketChannel, PlayerData> playerData; // Thông tin của từng người chơi
    private boolean gameStarted; // Trạng thái trò chơi
    
    private int gameDuration;

    public GameData(int maxRounds) {
        this.playerData = new HashMap<>();
        this.gameStarted = false;
        this.targetNumber = new Random().nextInt(100) + 1; // Sinh số ngẫu nhiên từ 1 đến 100
    }
    public int getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(int targetNumber) {
        this.targetNumber = targetNumber;
    }

    // Getter cho playerData (trả về một bản sao để tránh sửa đổi trực tiếp)
    public Map<SocketChannel, PlayerData> getPlayerData() {
        return new HashMap<>(playerData); // Trả về bản sao
    }

    // Không cần setter cho playerData, vì bạn có thể thêm/xóa người chơi bằng các phương thức khác

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    public void addPlayer(SocketChannel channel) {
        playerData.put(channel, new PlayerData());
    }

    public void removePlayer(SocketChannel channel) {
        playerData.remove(channel);
    }

    public void startGame() {
        gameStarted = true;
        // Gửi thông báo bắt đầu trò chơi đến tất cả người chơi
    }

    public void nextRound() {
        if (roundNumber < maxRounds) {
            roundNumber++;
            targetNumber = new Random().nextInt(100) + 1;
            // Reset điểm số của người chơi trong vòng mới
            // Gửi thông báo bắt đầu vòng mới đến tất cả người chơi
        } else {
            endGame();
        }
    }

    public void endGame() {
        gameStarted = false;
        // Xử lý kết thúc trò chơi, thông báo người chiến thắng
    }

    // ... các phương thức khác để cập nhật điểm số, kiểm tra kết quả đoán, v.v.
}

class PlayerData {
    private int score; // Điểm số của người chơi

    public PlayerData() {
        this.score = 0;
    }

    // Các phương thức getter và setter cho score
    // ...
}
