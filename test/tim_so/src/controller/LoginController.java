package controller;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import network.GameClient;

public class LoginController {
    private GameClient client;

    public LoginController() {
        this.client = GameClient.getInstance(); // Đảm bảo rằng GameClient đã được khởi tạo trước đó
    }


    public void handleLoginAction(String username, String password, Runnable onSuccess, Runnable onFailure) {
        boolean loginSuccess = checkLoginSuccess(username, password);
        if (loginSuccess) {
            try {
                client.sendMessage("LOGIN_SUCCESS:" + username);
                onSuccess.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            onFailure.run();
        }
    }

    
    public boolean checkLoginSuccess(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM usersAcc WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Trả về true nếu tìm thấy user, ngược lại false
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
