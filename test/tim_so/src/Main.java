import javax.swing.SwingUtilities;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true); // Chỉ hiển thị LoginView khi bắt đầu chương trình
        });
    }
}
