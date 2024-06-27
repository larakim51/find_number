package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import model.GameSession;
import model.Player;

public class GameView extends JFrame implements Observer  {
    private GameSession gameSession;
    private JButton[] numberButtons;
    private JLabel numberLabel;
    private JLabel scoreLabel;
    private HomeView homeView;
    private Player player;

    public GameView(HomeView homeview,GameSession gameSession) {
        this.homeView = homeview;
        this.gameSession = gameSession;
        setUndecorated(true); 
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }
    public GameView(Player player,HomeView homeview,GameSession gameSession) {
        this.homeView = homeview;
        this.gameSession = gameSession;
        this.player = player;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.initUI();
        this.setVisible(true);
    }
    public Player getPlayer() {
        return player;
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
        
        // Header panel (chứa tên người dùng, nút Quay lại, và nút Setting)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Thêm padding xung quanh

        ImageIcon backIcon = new ImageIcon(GameView.class.getResource("/images/backIcon.png"));
        Image image = backIcon.getImage();
        Image newimg = image.getScaledInstance(35, 35, java.awt.Image.SCALE_AREA_AVERAGING);
        backIcon = new ImageIcon(newimg);

        JButton homeButton = new JButton(backIcon);
        homeButton.setPreferredSize(new Dimension(35, 35));
        homeButton.setBorder(null); // Loại bỏ viền
        homeButton.setBorderPainted(false); // Loại bỏ viền vẽ
        homeButton.setContentAreaFilled(false); // Đảm bảo nền trong suốt

        headerPanel.add(homeButton, BorderLayout.WEST);
        headerPanel.setBackground(new Color(238, 238, 238));

        // Nút "Setting"
        JButton settingButton = new JButton("Setting");
        headerPanel.add(settingButton, BorderLayout.EAST);

        // Button grid (vùng chọn số)
        JPanel buttonPanel = new JPanel(new GridLayout(10, 10, 10, 10));
        numberButtons = new JButton[100];
        for (int i = 0; i < 100; i++) {
            numberButtons[i] = new JButton(String.valueOf(i + 1));
            numberButtons[i].addActionListener(e -> handleNumberClick(Integer.parseInt(((JButton) e.getSource()).getText())));
            buttonPanel.add(numberButtons[i]);
        }
        
        // Top panel (hiển thị số và điểm)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Sử dụng FlowLayout để căn giữa
        // Label cho chữ "Find:"
        JLabel findLabel = new JLabel("Find:", JLabel.LEFT); // Căn lề trái
        findLabel.setBorder(new EmptyBorder(10, 20, 10, 0));  // Thêm khoảng cách với numberLabel
        topPanel.add(findLabel, BorderLayout.WEST);
        // Number label (hiển thị số)
        numberLabel = new JLabel();
        int currentNumber = gameSession.getGameData().getCurrentNumber();
        numberLabel.setText(String.valueOf(currentNumber));
        topPanel.add(numberLabel); 
        // Score label (hiển thị điểm)
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Thêm padding xung quanh
        topPanel.add(scoreLabel);

        // Đặt kích thước mong muốn cho topPanel
        topPanel.setPreferredSize(new Dimension(400, 60)); // Ví dụ: rộng 500px, cao 60px

        // Tạo JPanel mới để nhóm headerPanel và topPanel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout để sắp xếp theo chiều dọc

        // Thêm headerPanel và topPanel vào northPanel
        northPanel.add(headerPanel);
        northPanel.add(topPanel);

        // Main layout (sắp xếp các thành phần chính)
        add(northPanel, BorderLayout.NORTH); 
        add(buttonPanel, BorderLayout.CENTER); 
        homeButton.addActionListener(e -> {
            // Gọi phương thức để chuyển về homeview
            returnToHomeView(); 
        });
    }
    
    private void handleNumberClick(int number) {
        Player currentPlayer = gameSession.getPlayers().get(0);
        if (gameSession != null && !gameSession.getPlayers().isEmpty() && gameSession.checkNumber(currentPlayer, number)) {
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
            currentPlayer.setScore(currentPlayer.getScore() + 1);
            updatePlayerScore(currentPlayer);

            int newCurrentNumber = gameSession.getGameData().getCurrentNumber();
            updateNextNumber(newCurrentNumber); 

            // Reset tất cả các nút đã chọn sai trước đó và số hiện tại
            for (int i = 0; i < numberButtons.length; i++) {
                if (numberButtons[i].getBackground() == Color.RED || i == newCurrentNumber - 1) {
                    resetNumber(i + 1);
                }
            }

        } else{ 
            highlightNumber(number, Color.RED);
        }
    }

    public void updatePlayerScore(Player player) {
        scoreLabel.setText("Score: " + player.getScore());
    }

    public void showGameOverMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public JButton[] getNumberButtons() {
        return numberButtons;
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
    
    public void YourClassName(HomeView homeview) { // Constructor mới
        this.homeView = homeview;
        initUI(); 
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

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
