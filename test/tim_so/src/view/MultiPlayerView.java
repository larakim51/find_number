package view;

import controller.MultiPlayerController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import model.GameSession;
import model.Player;

public class MultiPlayerView extends JFrame {
    private GameSession gameSession;
    private MultiPlayerController controller;
    private int currentNumber;
    private JButton[] numberButtons;
    private HomeView homeView;
    private Player player;

    private JPanel mainPanel;
    private JLabel findLabel;
    private JLabel numberLabel;
    private JLabel timerLabel;

    public MultiPlayerView(HomeView homeview,GameSession gameSession) {
        this.homeView = homeview;
        this.gameSession = gameSession;
        setTitle("Number Finding Game - Multiplayer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Khởi tạo MultiPlayerController
        controller = new MultiPlayerController(gameSession, this);
        initUI();
    }
    public MultiPlayerView(Player player, GameSession gameSession) {
        this.player = player;
        this.gameSession = gameSession;
        // ... (khởi tạo các thành phần khác của giao diện)
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        //Nút "Quay lại"
        ImageIcon backIcon = new ImageIcon(GameView.class.getResource("/images/backIcon.png"));
        Image image = backIcon.getImage();
        Image newimg = image.getScaledInstance(35, 35, java.awt.Image.SCALE_AREA_AVERAGING);
        backIcon = new ImageIcon(newimg);
        JButton homeButton = new JButton(backIcon);
        homeButton.setPreferredSize(new Dimension(35, 35));
        homeButton.setBorder(null); // Loại bỏ viền
        homeButton.setBorderPainted(false); // Loại bỏ viền vẽ
        homeButton.setContentAreaFilled(false); // Đảm bảo nền trong suốt


        // Thành phần giữa: "Find:" và số cần tìm
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Căn giữa
        findLabel = new JLabel("Find:");
        numberLabel = new JLabel(String.valueOf(getRandomNumber()), JLabel.CENTER); // Lấy số từ gameSession
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

    // Left Panel (chứa label "Hello")
    JPanel leftPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbcLeft = new GridBagConstraints();
    gbcLeft.gridx = 0;
    gbcLeft.gridy = 0;
    gbcLeft.anchor = GridBagConstraints.CENTER;
    JPanel buttonPanel = new JPanel(new GridLayout(10, 10, 10, 10));
        numberButtons = new JButton[100];
        for (int i = 0; i < 100; i++) {
            numberButtons[i] = new JButton(String.valueOf(i + 1));
            numberButtons[i].addActionListener(e -> handleNumberClick(Integer.parseInt(((JButton) e.getSource()).getText())));
            buttonPanel.add(numberButtons[i]);
        }
    leftPanel.add(buttonPanel,gbcLeft);

    // Right Panel (chứa label "List user")
    JPanel rightPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbcRight = new GridBagConstraints();
    gbcRight.gridx = 0;
    gbcRight.gridy = 0;
    gbcRight.anchor = GridBagConstraints.CENTER;
    rightPanel.add(new JLabel("List user"), gbcRight);
    int n=0;
    JPanel listPanel = new JPanel(new GridLayout(1,n));
    for (int i = 0; i < 5; i++) {
        
    }
    

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

        homeButton.addActionListener(e -> {
            // Gọi phương thức để chuyển về homeview
            returnToHomeView(); 
        });
    }

    private int getRandomNumber() {
        Random random = new Random();
        currentNumber = random.nextInt(100) + 1;
        return currentNumber;
    }
    private void returnToHomeView() {
        if (homeView != null) { // Kiểm tra xem homeView có null không
            this.dispose();     // Đóng GameView
            homeView.setVisible(true); // Hiển thị lại HomeView
            homeView.toFront(); // Đưa HomeView lên trên cùng
        } else {
            System.err.println("Lỗi: homeView chưa được khởi tạo!");
            // Xử lý lỗi ở đây, ví dụ: hiển thị thông báo lỗi hoặc thoát chương trình
        }
    }


    private void handleNumberClick(int number) {

        if (number == currentNumber) {
            new Thread(() -> {
                try {
                    for (int i = 0; i < 5; i++) { // Nhấp nháy 5 lần
                        highlightNumber(number, Color.green); // Màu vàng
                        Thread.sleep(200); // Dừng 200ms
                        resetNumber(number);
                        Thread.sleep(200);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

            gameSession.getPlayers().get(0).setScore(gameSession.getPlayers().get(0).getScore() + 1);
/*             updatePlayerScore(gameSession.getPlayers().get(0));
 */            resetNumber(number); 
    
            // Reset tất cả các nút đã chọn sai trước đó
            for (int i = 0; i < numberButtons.length; i++) {
                if (i != number - 1 && numberButtons[i].getBackground() == Color.RED) {
                    resetNumber(i + 1); // Reset màu của nút
                }
            }
    
            
        } else {
            highlightNumber(number, Color.RED);
            resetNumber(currentNumber); 
        }
    }

    public void highlightNumber(int number, Color color) {
        numberButtons[number - 1].setBackground(color);
        numberButtons[number - 1].setOpaque(true);
        numberButtons[number - 1].setBorderPainted(false);
    }
    
    public void resetNumber(int number) {
        numberButtons[number - 1].setBackground(null);
        numberButtons[number - 1].setOpaque(false);
        numberButtons[number - 1].setBorderPainted(true);
    }

    public void updateNextNumber(int number) {
        numberLabel.setText(String.valueOf(number));
    }

    public void showGameOverMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}