package test;
import java.io.IOException;
import javax.swing.SwingUtilities;
import network.GameClient;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        
        try {
            // Khởi tạo GameClient trước khi gọi LoginController
            GameClient client = new GameClient("192.168.160.1", 12345);

            SwingUtilities.invokeLater(() -> {
                new LoginView().setVisible(true); // Chỉ hiển thị LoginView khi bắt đầu chương trình
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
