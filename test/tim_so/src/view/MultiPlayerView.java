package view;

import controller.MultiPlayerController;
import java.awt.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.MultiSession;
import model.Player;

public class MultiPlayerView extends JFrame implements MultiSession.MultiSessionListener {
    private MultiSession multiSession;
    private MultiPlayerController controller;
    private Player player;
    private JButton[] numberButtons;

    private JPanel mainPanel;
    private JLabel findLabel;
    private JLabel numberLabel;
    private JLabel timerLabel;
    private JLabel scoreLabel;
    private JPanel userListPanel;

    public MultiPlayerView(Player player, MultiSession multiSession) {
        this.player = player;
        this.multiSession = multiSession;
        setTitle("Number Finding Game - Multiplayer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Khởi tạo MultiPlayerController
        controller = new MultiPlayerController(multiSession, this);
        initUI();
        multiSession.setListener(this);
        updateUserList();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        GridBagConstraints gbc = new GridBagConstraints();

        // Nút "Quay lại"
        ImageIcon backIcon = new ImageIcon(GameView.class.getResource("/images/backIcon.png"));
        Image image = backIcon.getImage();
        Image newimg = image.getScaledInstance(35, 35, java.awt.Image.SCALE_AREA_AVERAGING);
        backIcon = new ImageIcon(newimg);
        JButton homeButton = new JButton(backIcon);
        homeButton.setPreferredSize(new Dimension(35, 35));
        homeButton.setBorder(null); // Loại bỏ viền
        homeButton.setBorderPainted(false); // Loại bỏ viền vẽ
        homeButton.setContentAreaFilled(false); // Đảm bảo nền trong suốt
        homeButton.addActionListener(e -> controller.handleBackButton());
        // Thành phần giữa: "Find:" và số cần tìm
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Căn giữa
        findLabel = new JLabel("Find:");
        numberLabel = new JLabel(String.valueOf(multiSession.getCurrentNumber()), JLabel.CENTER); // Lấy số từ multiSession
        centerPanel.add(findLabel);
        centerPanel.add(numberLabel);

        timerLabel = new JLabel("3:00"); // Bắt đầu từ 3 phút
       
        gbc.gridx = 0; // Cột 0
        gbc.gridy = 0; // Hàng 0
        gbc.weightx = 0.2; // Chiếm 20% chiều rộng
        topPanel.add(homeButton, gbc);

        gbc.gridx = 1; // Cột 1
        gbc.gridy = 0; 
        gbc.weightx = 0.6; // Chiếm 60% chiều rộng
        topPanel.add(centerPanel, gbc);

        // Thời gian đếm ngược (bên phải)
        gbc.gridx = 2; // Cột 2
        gbc.gridy = 0; 
        gbc.weightx = 0.2; // Chiếm 20% chiều rộng
        topPanel.add(timerLabel, gbc);

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.weighty = 1.0; 
        gbcMain.insets = new Insets(0, 0, 0, 0);

        // Left Panel (chứa button grid)
        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new GridLayout(10, 10, 10, 10));
        numberButtons = new JButton[100];
        for (int i = 0; i < 100; i++) {
            numberButtons[i] = new JButton(String.valueOf(i + 1));
            final int number = i + 1;
            numberButtons[i].setBackground(Color.WHITE);
            numberButtons[i].addActionListener(e -> controller.handleNumberButton(number));
            buttonPanel.add(numberButtons[i]);
        }
        leftPanel.add(buttonPanel, gbcLeft);

        // Right Panel (chứa label "List user" và danh sách người chơi)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.gridx = 0;
        gbcRight.gridy = 0;
        gbcRight.anchor = GridBagConstraints.CENTER;
        rightPanel.add(new JLabel("List user"), gbcRight);

        gbcRight.gridy = 1;
        userListPanel = new JPanel(new GridLayout(0, 1)); // Panel để chứa danh sách người chơi
        rightPanel.add(userListPanel, gbcRight);

        gbcRight.gridy = 1;
        userListPanel = new JPanel(new GridLayout(0, 1));
        rightPanel.add(userListPanel, gbcRight);

        // Thêm các panel vào mainPanel
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 0.7; 
        mainPanel.add(leftPanel, gbcMain);

        gbcMain.gridx = 1;
        gbcMain.gridy = 0;
        gbcMain.weightx = 0.3;
        mainPanel.add(rightPanel, gbcMain); 
        
        // Thêm các panel vào frame chính
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER); 

       

        // Update the initial values
        updateNumberLabel(multiSession.getCurrentNumber());
        updateTimerLabel(multiSession.getRemainingTime());
        updateUserList(); 
    }
    private void handleNumberClick(int number) {
        controller.handleNumberButton(number);
    }

    private void returnToHomeView() {
        controller.handleBackButton();
    }

    public void updateNumberLabel(int number) {
        if (numberLabel != null) {
            numberLabel.setText(String.valueOf(number));
        }
    }

    public void updateTimerLabel(String time) {
        if (timerLabel != null) {
            timerLabel.setText("Time: " + time);
        }
    }

    public void highlightNumberButton(int number, Color color) {
        if (numberButtons != null && number > 0 && number <= numberButtons.length) {
            numberButtons[number - 1].setBackground(color);
        }
    }
    public void updateUserList() {
        userListPanel.removeAll();
        for (Map.Entry<Player, Integer> entry : multiSession.getPlayers().entrySet()) {
            Player player = entry.getKey();
            int score = entry.getValue();
            JLabel playerLabel = new JLabel(player.getUsername() + ": " + score);
            userListPanel.add(playerLabel);
        }
        userListPanel.revalidate();
        userListPanel.repaint();
    }

    @Override
    public void onTimeUpdate(String time) {
        updateTimerLabel(time);
    }

    @Override
    public void onGameEnd() {
        JOptionPane.showMessageDialog(this, "Game over! Final scores:\n" + multiSession.getLeaderboard());
        returnToHomeView();
    }

    @Override
    public void onNumberUpdate(int number) {
        updateNumberLabel(number);
    }
    
}
