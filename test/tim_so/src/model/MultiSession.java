package model;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MultiSession {
    
    private Map<Integer, GameSession> gameSessions = new HashMap<>();
    
    public synchronized int createGame(Player player, int gameDuration) {
        int roomId = generateRoomId(); // Đảm bảo generateRoomId trả về int
        GameData gameData = new GameData(roomId, gameDuration);
        GameSession gameSession = new GameSession(roomId, gameData);
        gameSession.addPlayer(player);
        gameSessions.put(roomId, gameSession);
        return roomId;
    }

    public GameSession getGameSession(int roomId) {
        return gameSessions.get(roomId);
    }

    private int generateRoomId() {
        Random random = new Random();
        return random.nextInt(10000); 
    }
}
