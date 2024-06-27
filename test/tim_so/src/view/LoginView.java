package view;

import database.DatabaseConnection;
import model.GameSession;
import model.MultiSession;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import controller.LoginController;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private CompletableFuture<Player> registrationFuture;
    private LoginController loginController;
    

    public LoginView() {
        setUndecorated(true); 
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginController = new LoginController();
        initUI();
    }

    private void initUI() {
        
        Font boldFont = new Font(Font.SANS_SERIF, Font.BOLD, 14);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Căn giữa tiêu đề
        titlePanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("Number Finding Game");
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        titlePanel.add(titleLabel);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Đăng nhập");
        registerButton = new JButton("Tạo tài khoản mới");

        JPanel mainContentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainContentPanel.add(new JLabel("Tên đăng nhập:", SwingConstants.RIGHT), gbc);

    gbc.gridx = 1;
    mainContentPanel.add(usernameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    mainContentPanel.add(new JLabel("Mật khẩu:", SwingConstants.RIGHT), gbc);

    gbc.gridx = 1;
    mainContentPanel.add(passwordField, gbc);

    // Panel chứa các nút
    JPanel buttonPanel = new JPanel(new FlowLayout()); // Căn giữa các nút
    loginButton.setFont(boldFont);
    buttonPanel.add(loginButton);

    registerButton.setFont(boldFont);
    buttonPanel.add(registerButton);

    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.gridwidth = 2; // Chiếm cả hai cột
    mainContentPanel.add(buttonPanel, gbc);

    JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Căn giữa label thông báo lỗi
    JLabel errorLabel = new JLabel("Sai tên đăng nhập hoặc mật khẩu");
    errorLabel.setForeground(Color.RED);
    errorLabel.setVisible(false);
    errorPanel.add(errorLabel);

    gbc.gridy = 3; 
    mainContentPanel.add(errorPanel, gbc);

    mainPanel.add(mainContentPanel, BorderLayout.CENTER); // Thêm mainContentPanel vào giữa mainPanel

    add(mainPanel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegisterView().setVisible(true);
            }
        });
        loginButton.addActionListener(e -> {
            loginUser();
            if (!checkLoginSuccess) { // Sử dụng biến checkLoginSuccess
                errorLabel.setVisible(true);
            } else {
                errorLabel.setVisible(false);
            }
        });

        registerButton.addActionListener(e -> {
            dispose();
            new RegisterView().setVisible(true);
        });
    }

    private boolean checkLoginSuccess = false;
    private MultiSession multiSession = new MultiSession();
    private int gameDuration = 300;

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username và mật khẩu không được để trống!");
            return;
        }

        boolean loginSuccess = loginController.checkLoginSuccess(username, password); // Gọi phương thức authenticate từ LoginController
        if (loginSuccess) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            Player player = new Player(username, password);
            dispose();

            SwingUtilities.invokeLater(() -> {
                int roomId = multiSession.createGame(player, gameDuration); // Lấy roomId
                GameSession gameSession = multiSession.getGameSession(roomId); // Lấy GameSession từ roomId
                gameSession.addPlayer(player);
                new HomeView(player, gameSession).setVisible(true);
            });
        } else {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập hoặc mật khẩu không đúng!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }

    public Player waitForRegistration() {
        registrationFuture = new CompletableFuture<>();
        return registrationFuture.join();
    }
} 