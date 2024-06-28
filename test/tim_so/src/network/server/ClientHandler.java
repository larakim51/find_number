package network.server;

import model.Player;
import model.Room;
import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private GameServer server;
    private Socket socket;
    private Player player;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(GameServer server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
    try {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        String username = in.readLine();
        String roomId = in.readLine();

        player = new Player(username, out);
        Room room = server.getRoom(roomId);
        if (room == null) {
            room = server.createRoom(roomId);
        }
        room.addPlayer(player);

        if (room.isFull()) {
            server.startGame(room);
        } else {
            out.println("Waiting for more players...");
        }

        // Lắng nghe và xử lý các yêu cầu từ client trong khi trò chơi đang diễn ra
        String message;
        while ((message = in.readLine()) != null) {
            if (message.startsWith("SCORE:")) {
                int score = Integer.parseInt(message.substring(6));
                player.setScore(score);
                server.updateScore(room, player);
            }
        }

    } catch (IOException e) {
        e.printStackTrace();
    } finally {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}
