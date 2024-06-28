package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MultiSession {
    private String sessionId;
    private Map<Player, Integer> players;
    private Map<Player, String> playerColors;
    private static final String[] COLORS = {"RED", "BLUE", "GREEN"};
    private Player currentPlayer;
    private int currentNumber;
    private int[] numberList;
    private int numberIndex;
    private int timeLimit; // in seconds
    private boolean isLuckyNumberActive;
    private Random random;
    private Timer timer;
    private int remainingTime;
    private MultiSessionListener listener;

    public interface MultiSessionListener {
        void onTimeUpdate(String time);
        void onGameEnd();
        void onNumberUpdate(int number);
    }
    public MultiSession(MultiSessionListener listener) {
        this.sessionId = generateSessionId();
        this.players = new HashMap<>();
        this.playerColors = new HashMap<>();
        this.random = new Random();
        this.listener = listener;
        initializeGame();
    }

    public MultiSession() {
        this.sessionId = generateSessionId();
        this.players = new HashMap<>();
        this.playerColors = new HashMap<>();
        this.random = new Random();
        initializeGame();
    }

    private String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public String getSessionId() {
        return sessionId;
    }

    public void addPlayer(Player player) {
        if (players.size() < COLORS.length) {
            players.put(player, 0); // Initialize points to 0
            String color = COLORS[players.size()];
            player.setColor(color); // Assign color to player
            playerColors.put(player, color);
            if (players.size() == 1) {
                currentPlayer = player; // Set first player as current player
            }
        }
    }

    public void removePlayer(Player player) {
        players.remove(player);
        playerColors.remove(player);
    }

    public Map<Player, Integer> getPlayers() {
        return players;
    }

    public Map<Player, String> getPlayerColors() {
        return playerColors;
    }

    public void updatePoints(Player player, int points) {
        if (players.containsKey(player)) {
            players.put(player, players.get(player) + points);
        }
    }

    public String getPlayerColor(Player player) {
        return playerColors.get(player);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    private void initializeGame() {
        this.numberList = generateRandomNumbers();
        this.numberIndex = 0;
        this.currentNumber = numberList[numberIndex];
        this.timeLimit = 180; 
        this.remainingTime = timeLimit;
        this.isLuckyNumberActive = false;
        startTimer();
    }

    private int[] generateRandomNumbers() {
        int[] numbers = new int[100];
        for (int i = 0; i < 100; i++) {
            numbers[i] = i + 1;
        }
        // Shuffle numbers
        for (int i = 0; i < numbers.length; i++) {
            int randomIndex = random.nextInt(numbers.length);
            int temp = numbers[i];
            numbers[i] = numbers[randomIndex];
            numbers[randomIndex] = temp;
        }
        return numbers;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void findNextNumber() {
        if (numberIndex < numberList.length - 1) {
            numberIndex++;
            currentNumber = numberList[numberIndex];
            if (listener != null) {
                listener.onNumberUpdate(currentNumber);
            }
        } else {
            currentNumber = -1; // No more numbers
            if (listener != null) {
                listener.onGameEnd();
            }
        }
    }
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    remainingTime--;
                    int minutes = remainingTime / 60;
                    int seconds = remainingTime % 60;
                    String time = String.format("%02d:%02d", minutes, seconds);
                    if (listener != null) {
                        listener.onTimeUpdate(time);
                    }
                } else {
                    timer.cancel();
                    if (listener != null) {
                        listener.onGameEnd();
                    }
                }
            }
        }, 0, 1000);
    }

    public void activateLuckyNumber() {
        isLuckyNumberActive = true;
    }

    public boolean isLuckyNumber(int number) {
        return isLuckyNumberActive && number == currentNumber;
    }

    public void deactivateLuckyNumber() {
        isLuckyNumberActive = false;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
    public String getRemainingTime() {
        int minutes = remainingTime / 60;
        int seconds = remainingTime % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getLeaderboard() {
        StringBuilder leaderboard = new StringBuilder();
        players.entrySet().stream()
                .sorted(Map.Entry.<Player, Integer>comparingByValue().reversed())
                .forEach(entry -> leaderboard.append(entry.getKey().getUsername()).append(": ").append(entry.getValue()).append("\n"));
        return leaderboard.toString();
    }
    public void setListener(MultiSessionListener listener) {
        this.listener = listener;
    }
}
