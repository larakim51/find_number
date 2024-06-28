package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import model.Player;
import model.Room;

public class GameServer {
    private ServerSocket serverSocket;
    private Map<String, Room> rooms; // Key: roomId, Value: Room

    public GameServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            rooms = new HashMap<>();
            System.out.println("Server is running on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(this, clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized Room createRoom(String roomId) {
        Room room = new Room(roomId);
        rooms.put(roomId, room);
        return room;
    }

    public synchronized Room getRoom(String roomId) {
        return rooms.get(roomId);
    }

    public synchronized void startGame(Room room) {
        room.startGame();
        for (Player player : room.getPlayers()) {
            player.sendMessage("Game is starting");
        }
    }
    public synchronized void updateScore(Room room, Player player) {
        // Cập nhật điểm cho người chơi trong phòng
        System.out.println("Updated score for " + player.getUsername() + ": " + player.getScore());
        // Bạn có thể thêm mã để lưu thông tin điểm vào cơ sở dữ liệu hoặc file tại đây
    }
    
    public static void main(String[] args) {
        int port = 12345; // Port to listen on
        new GameServer(port);
    }
}
