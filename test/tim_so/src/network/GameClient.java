package network;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private static GameClient instance;

    public GameClient(String serverAddress, int serverPort) throws IOException {
        socket = new Socket(serverAddress, serverPort);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        instance = this;
    }

    public static GameClient getInstance() {
        return instance;
    }

    public void sendMessage(String message) {
        try {
            out.writeObject(message);
            out.flush();  // Ensure message is sent immediately
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startApplication() {
        try {
            String command = (String) in.readObject();
            if ("START_APPLICATION".equals(command)) {
                executeMainMethod("test.Main");

                while (true) {
                    // Do something or keep the connection alive
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void executeMainMethod(String className) {
        try {
            Class<?> cls = Class.forName(className);
            Method mainMethod = cls.getMethod("main", String[].class);
            String[] params = {}; // init params array
            mainMethod.invoke(null, (Object) params); // static method doesn't belong to any instance
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter server IP address: ");
        String serverAddress = scanner.nextLine();

        System.out.print("Enter server port: ");
        int serverPort = scanner.nextInt();

        try {
            GameClient client = new GameClient(serverAddress, serverPort);
            client.startApplication();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
