package view;

import model.GameSession;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JFrame {
    private GameSession gameSession;
    private static HomeView instance;
    private Player player;

    public HomeView(Player player, GameSession gameSession) {
        this.player = player; 
        this.gameSession = gameSession;
        instance = this; 
        setUndecorated(true); 
        setTitle("Number Finding Game");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    public static void showHomeView() { // Phương thức static để hiển thị HomeView
        if (instance != null) {
            instance.setVisible(true);
            instance.toFront(); // Đưa HomeView lên trên cùng
        }
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

        JPanel panel = new JPanel(new GridLayout(4, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton singlePlayerButton = new JButton("Single Player");
        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSinglePlayerGame();
            }
        });

        JButton multiPlayerButton = new JButton("Multi Player");
        multiPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMultiPlayerGame();
            }
        });

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLeaderboard();
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(singlePlayerButton);
        panel.add(multiPlayerButton);
        panel.add(leaderboardButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);
        add(mainPanel,BorderLayout.NORTH);
    }

    private void startSinglePlayerGame() {
        // Khởi động chế độ chơi 1 người
        dispose();
        new GameView(player, gameSession).setVisible(true);
    }

    private void startMultiPlayerGame() {
        // Khởi động chế độ chơi nhiều người
        dispose();
        new MultiPlayerView(player, gameSession).setVisible(true);
    }

    private void showLeaderboard() {
        // Hiển thị bảng xếp hạng
        JOptionPane.showMessageDialog(this, "Leaderboard coming soon!");
    }

    public static void main(String[] args) {
        GameSession gameSession = multiSession.createGame(player, gameDuration);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeView(gameSession).setVisible(true);
            }
        });
    }
}
