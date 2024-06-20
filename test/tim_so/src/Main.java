import model.GameSession;
import model.Player;
import view.HomeView;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo model, view và controller
        GameSession gameSession = new GameSession();

        LoginView loginView = new LoginView();
        loginView.setVisible(true);

        // Chờ đăng ký thành công
        Player player = loginView.waitForRegistration();
        if (player != null) {
            // Thêm người chơi vào model
            gameSession.addPlayer(player);

            // Chuyển sang trang chủ
            HomeView homeView = new HomeView(gameSession);
            homeView.setVisible(true);
        } else {
            // Đăng ký không thành công, không được thoát ứng dụng
        }

        /*
         * HomeView homeView = new HomeView(gameSession);
         * homeView.setVisible(true);
         */
    }
}
