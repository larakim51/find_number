package view;

import model.GameSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JFrame {
    private GameSession gameSession;

    public HomeView(GameSession gameSession) {
        this.gameSession = gameSession;
        setTitle("Number Finding Game");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
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
    }

    private void startSinglePlayerGame() {
        // Khởi động chế độ chơi 1 người
        dispose();
        new GameView(gameSession).setVisible(true);
    }

    private void startMultiPlayerGame() {
        // Khởi động chế độ chơi nhiều người
        dispose();
        new MultiPlayerView(gameSession).setVisible(true);
    }

    private void showLeaderboard() {
        // Hiển thị bảng xếp hạng
        JOptionPane.showMessageDialog(this, "Leaderboard coming soon!");
    }

    public static void main(String[] args) {
        GameSession gameSession = new GameSession();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeView(gameSession).setVisible(true);
            }
        });
    }
}
