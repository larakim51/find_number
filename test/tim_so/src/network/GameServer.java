package network;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer implements ClientHandlerDelegate {
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    public GameServer(int port, int poolSize) throws IOException {
        serverSocket = new ServerSocket(port);
        executorService = Executors.newFixedThreadPool(poolSize);
        InetAddress ip = InetAddress.getLocalHost();
        System.out.println("Server is listening on port " + port + ", IP: " + ip.getHostAddress());
    }


    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientHandler(clientSocket, this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loginSuccess(String username) {
        System.out.println("User logged in: " + username);
       
    }

    @Override
    public void gameFinished() {
        System.out.println("Game finished.");
       
    }

    @Override
    public void clientDisconnected() {
        System.out.println("Client disconnected.");
        
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private boolean running = true;
        private ClientHandlerDelegate delegate;

        public ClientHandler(Socket socket, ClientHandlerDelegate delegate) throws IOException {
            this.clientSocket = socket;
            this.delegate = delegate;
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }

        public void run() {
            try {
                // Gửi yêu cầu bắt đầu ứng dụng cho client
                out.writeObject("START_APPLICATION");

                // Nhận kết quả từ client sau khi chơi xong
                while (running) {
                    try {
                        String message = (String) in.readObject();
                        System.out.println("Received message from client: " + message);

                        /* // Xử lý thông điệp từ client
                        if (message.startsWith("LOGIN_SUCCESS:")) {
                            String username = message.substring("LOGIN_SUCCESS:".length()).trim();
                            delegate.loginSuccess(username);
                        } else if (message.equals("FINISHED_GAME")) {
                            delegate.gameFinished();
                            // Quyết định khi nào cần đóng kết nối
                            running = false;
                        } */
                    } catch (EOFException e) {
                        delegate.clientDisconnected();
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
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

    public static void main(String[] args) {
        try {
            int port = 12345;
            int poolSize = 10; // Số lượng luồng trong thread pool
            GameServer server = new GameServer(port, poolSize);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
