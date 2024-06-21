package view;

import database.DatabaseConnection;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class RegisterView extends JFrame {
    private JTextField usernameField;
    private JTextField emaField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JButton loginButton;
    private CompletableFuture<Player> registrationFuture;

    public RegisterView() {
        setUndecorated(true);
        setSize(550, 350); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        emaField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JPasswordField confirmPasswordField = new JPasswordField(20);

        registerButton = new JButton("đăng ký");
        loginButton = new JButton("đăng nhập");

        JPanel mainContentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainContentPanel.add(new JLabel("Tên đăng ký:", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        mainContentPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainContentPanel.add(new JLabel("Email:", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        mainContentPanel.add(emaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainContentPanel.add(new JLabel("Mật khẩu:", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        mainContentPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3; // Hàng mới cho label "Nhập lại mật khẩu"
        mainContentPanel.add(new JLabel("Nhập lại mật khẩu:", SwingConstants.RIGHT), gbc);
        gbc.gridx = 1;
        mainContentPanel.add(confirmPasswordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout()); // Căn giữa các nút
        registerButton.setFont(boldFont);
        buttonPanel.add(registerButton);

        loginButton.setFont(boldFont);
        buttonPanel.add(loginButton);

        gbc.gridx = 0;
        gbc.gridy = 4; 
        gbc.gridwidth = 2; 
        mainContentPanel.add(buttonPanel, gbc);

        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Căn giữa label thông báo lỗi
        JLabel errorLabel = new JLabel("Sai tên đăng nhập hoặc mật khẩu");
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        errorPanel.add(errorLabel);

        gbc.gridy = 5; 
        mainContentPanel.add(errorPanel, gbc);

        mainPanel.add(mainContentPanel, BorderLayout.CENTER); // Thêm mainContentPanel vào giữa mainPanel

        add(mainPanel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (!password.equals(confirmPassword)) {
                    errorLabel.setText("Mật khẩu không khớp");
                    errorLabel.setVisible(true); // Hiển thị thông báo lỗi nếu mật khẩu không khớp
                } else if(password.isEmpty() || confirmPassword.isEmpty()){
                    errorLabel.setText("Mật khẩu không được để trống");
                    errorLabel.setVisible(true); // Hiển thị thông báo lỗi nếu mật khẩu trống
                } else {
                    registerUser(); 
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginView().setVisible(true);
            }
        });
    }

    private void registerUser() {
        String username = usernameField.getText();
        String email = emaField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, email);  // Thêm giá trị email vào câu truy vấn
            stmt.setString(3, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công!");
                dispose();
                new LoginView().setVisible(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại! Vui lòng thử lại.");
            if (registrationFuture != null) {
                registrationFuture.complete(null);
            }
        }
    }
    
        public Player waitForRegistration() {
            registrationFuture = new CompletableFuture<>();
            return registrationFuture.join();
        }
    
        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new RegisterView().setVisible(true);
                }
            });
        }
    }