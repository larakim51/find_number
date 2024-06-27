package network;
import java.io.*;
import java.net.*;
import java.util.*;
import model.GameData;
import model.GameSession;
import model.MultiSession;
import model.Player;

public class GameServer {
    private static final int PORT = 12345; // Cổng lắng nghe
    private static final Map<String, GameData> games = new HashMap<>(); // Lưu trữ các ván chơi

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server đang lắng nghe trên cổng " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client kết nối: " + clientSocket);

                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ... (Các phương thức xử lý đăng nhập, tạo phòng, vào phòng, xử lý dữ liệu từ client, ...)
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private Player player;
    private MultiSession currentGame;
    
    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                String[] parts = message.split(" ");
                String command = parts[0];

                if (command.equals("LOGIN")) {
                    // Xử lý đăng nhập
                } else if (command.equals("CREATE_ROOM")) {
                    String roomId = parts[1];
                    currentGame = new GameSession(roomId);
                    GameServer.gameSessions.put(roomId, currentGame);
                    out.println("ROOM_CREATED " + roomId);
                } else if (command.equals("JOIN_ROOM")) {
                    String roomId = parts[1];
                    currentGame = GameServer.gameSessions.get(roomId);
                    if (currentGame != null && !currentGame.isStarted()) {
                        currentGame.addPlayer(player);
                        out.println("JOINED_ROOM " + roomId);
                        if (currentGame.isFull()) {
                            currentGame.startGame();
                        }
                    } else {
                        out.println("ROOM_NOT_FOUND_OR_FULL");
                    }
                } else if (command.equals("FIND_NUMBER")) {
                    if (currentGame != null && currentGame.isStarted()) {
                        int number = Integer.parseInt(parts[1]);
                        currentGame.checkNumber(player, number);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
