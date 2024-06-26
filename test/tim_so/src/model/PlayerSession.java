package model;
import java.nio.channels.SocketChannel;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

class PlayerSession {
    private SocketChannel channel;
    private String playerId;
    private Set<Integer> foundNumbers; // Số đã tìm được
    private int score; // Điểm số
    private boolean hasPriority; // Có quyền ưu tiên hay không
    private Instant priorityEndTime; // Thời điểm hết hiệu lực ưu tiên
    private Instant gameStartTime; // Thời gian bắt đầu chơi
    private 
    // Constructor
    public PlayerSession(SocketChannel channel, String playerId) {
        this.channel = channel;
        this.playerId = playerId;
        this.foundNumbers = new HashSet<>();
        this.score = 0;
        this.hasPriority = false;
    }

    public void handleGuess(int guess, GameData gameData) {
        if (gameData.isGameOver()) {
            // Trò chơi đã kết thúc, không xử lý đoán số nữa
            return;
        }

        if (guess == gameData.getTargetNumber()) {
            if (!foundNumbers.contains(guess)) { // Kiểm tra xem số này đã được tìm chưa
                foundNumbers.add(guess);
                increaseScore(1); // Tăng điểm khi tìm đúng số
                if (gameData.getRemainingNumbers().isEmpty()) {
                    // Nếu tất cả các số đã được tìm, kết thúc trò chơi
                    gameData.endGame();
                } else {
                    gameData.nextRound(); // Chuyển sang vòng tiếp theo
                }
            }
        } else {
            // Sai số, có thể trừ điểm hoặc xử lý theo luật chơi
        }
    }

    // Getters
    public SocketChannel getChannel() {
        return channel;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Set<Integer> getFoundNumbers() {
        return new HashSet<>(foundNumbers); // Trả về bản sao để tránh sửa đổi trực tiếp
    }

    public int getScore() {
        return score;
    }

    public boolean hasPriority() {
        return hasPriority && Instant.now().isBefore(priorityEndTime); // Kiểm tra cả cờ và thời gian
    }

    public Instant getPriorityEndTime() {
        return priorityEndTime;
    }

    public Instant getGameStartTime() {
        return gameStartTime;
    }

    // Setters
    public void setScore(int score) {
        this.score = score;
    }

    public void setHasPriority(boolean hasPriority, Instant priorityEndTime) {
        this.hasPriority = hasPriority;
        this.priorityEndTime = priorityEndTime;
    }

    public void addFoundNumber(int number) {
        foundNumbers.add(number);
    }

    public boolean hasFoundNumber(int number) {
        return foundNumbers.contains(number);
    }

    public void increaseScore(int points) {
        score += points;
    }

    public void activatePriority() {
        hasPriority = true;
        priorityEndTime = Instant.now().plusSeconds(3); // Ưu tiên trong 3 giây
    }

    public boolean isPriorityActive() {
        return hasPriority && Instant.now().isBefore(priorityEndTime);
    }

}
