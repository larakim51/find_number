package controller; 

import database.DatabaseConnection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import network.GameClient;

public class RegisterController {

    private Socket socket;
    private ObjectOutputStream out;

    public RegisterController() {
        try {
            socket = new Socket("localhost", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleRegisterAction(String username, String email, String password, Runnable onSuccess, Runnable onFailure) {
        boolean registerSuccess = register(username, email, password);
        if (registerSuccess) {
            try {
                onSuccess.run();
                GameClient client = GameClient.getInstance();
                client.sendMessage("REGISTER_SUCCESS:" + username);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            onFailure.run();
        }
    }

    
    public boolean register(String username, String email, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO usersAcc (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            // Log the exception for debugging
            e.printStackTrace();
            return false;
        }
    }
}
