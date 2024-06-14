import model.GameSession;
import model.Player;
import view.HomeView;
import view.RegisterView;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo model, view và controller
        GameSession gameSession = new GameSession();

        // Hiện màn hình đăng ký
        RegisterView registerView = new RegisterView();
        registerView.setVisible(true);

        // Chờ đăng ký thành công
        Player player = registerView.waitForRegistration();
        if (player != null) {
            // Thêm người chơi vào model
            gameSession.addPlayer(player);

            // Chuyển sang trang chủ
            HomeView homeView = new HomeView(gameSession);
            homeView.setVisible(true);
        } else {
            // Đăng ký không thành công, không được thoát ứng dụng
            //palm de thwnmdfjksf
            System.out.println("Hello Git Hub test");
        }
    }
}
