package view;

import controller.MultiPlayerController;
import model.GameSession;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MultiPlayerView extends JFrame {
    private GameSession gameSession;
    private MultiPlayerController controller;

    private JPanel playerPanel;
    private JTextField numberField;
    private JButton guessButton;
    private JTextArea chatArea;
    private JTextField chatField;
    private JButton sendButton;

    public MultiPlayerView(GameSession gameSession) {
        this.gameSession = gameSession;
        setTitle("Number Finding Game - Multiplayer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Khởi tạo MultiPlayerController
        controller = new MultiPlayerController(gameSession, this);

        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Bảng hiển thị người chơi
        playerPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JScrollPane playerScrollPane = new JScrollPane(playerPanel);
        playerScrollPane.setPreferredSize(new Dimension(200, 0));

        // Khu vực nhập số và đoán
        JPanel guessPanel = new JPanel(new BorderLayout(10, 10));
        numberField = new JTextField();
        guessButton = new JButton("Guess");
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.makeGuess(numberField.getText());
                numberField.setText("");
            }
        });
        guessPanel.add(numberField, BorderLayout.CENTER);
        guessPanel.add(guessButton, BorderLayout.EAST);

        // Khu vực chat
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.sendMessage(chatField.getText());
                chatField.setText("");
            }
        });
        JPanel chatPanel = new JPanel(new BorderLayout(10, 10));
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);
        chatPanel.add(chatField, BorderLayout.WEST);
        chatPanel.add(sendButton, BorderLayout.EAST);

        mainPanel.add(playerScrollPane, BorderLayout.WEST);
        mainPanel.add(guessPanel, BorderLayout.NORTH);
        mainPanel.add(chatPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    public void updatePlayerList(List<Player> players) {
        playerPanel.removeAll();
        for (Player player : players) {
            JLabel playerLabel = new JLabel(player.getUsername());
            playerPanel.add(playerLabel);
        }
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    public void appendChatMessage(String message) {
        chatArea.append(message + "\n");
    }
}
