package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;
    private String username;
    private String roomId;

    public GameClient(String serverAddress, int serverPort, String username) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.username = username;
        this.roomId = generateRoomId();
        scanner = new Scanner(System.in);
    }

    private String generateRoomId() {
        // Tạo Room ID tự động, bạn có thể tùy chỉnh logic này
        return "Room-" + System.currentTimeMillis();
    }

    public void connect() {
        try {
            System.out.println("Connected to server");

            // Gửi thông tin người chơi đến server
            out.println(username);
            out.println(roomId);

            // Lắng nghe thông báo từ server
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Server: " + serverMessage);
                if (serverMessage.equals("Game is starting")) {
                    // Bắt đầu xử lý trò chơi
                    playGame();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playGame() {
        try {
            // Đây là nơi bạn sẽ thêm logic để chơi trò chơi
            // Ví dụ: đọc và gửi điểm về server
            while (true) {
                System.out.print("Enter the number you found: ");
                String number = scanner.nextLine();
                out.println("NUMBER:" + number);

                System.out.print("Enter your score: ");
                String score = scanner.nextLine();
                out.println("SCORE:" + score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sendScore(int score) {
        out.println("SCORE:" + score);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter server address: ");
        String serverAddress = scanner.nextLine();

        if (!serverAddress.equals("10.0.5.1")) {
            System.out.println("Invalid server address. Please enter 10.0.5.1.");
            return;
        }

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        try {
            GameClient client = new GameClient(serverAddress, 12345, username);
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
