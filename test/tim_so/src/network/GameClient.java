package network;
import java.io.*;
import java.net.*;

public class GameClient {
    private static final String SERVER_ADDRESS = "localhost"; // Địa chỉ server
    private static final int PORT = 12345; // Cổng kết nối

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Gửi thông tin đăng nhập hoặc yêu cầu tạo phòng
            // ...

            String message;
            while ((message = in.readLine()) != null) {
                // Xử lý message từ server
                // ...
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
