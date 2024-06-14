package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.GameSession;
import model.Player;

public class GameView extends JFrame {
    private GameSession gameSession;
    private JButton[] numberButtons;
    private JLabel numberLabel;
    private JLabel scoreLabel;
    private int currentNumber;

    public GameView(GameSession gameSession) {
        this.gameSession = gameSession;
        setTitle("Number Finding Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(10, 10, 10, 10));

        numberButtons = new JButton[100];
        for (int i = 0; i < 100; i++) {
            numberButtons[i] = new JButton(String.valueOf(i + 1));
            numberButtons[i].addActionListener(e -> handleNumberClick(Integer.parseInt(((JButton) e.getSource()).getText())));
            panel.add(numberButtons[i]);
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        numberLabel = new JLabel(String.valueOf(getRandomNumber()));
        topPanel.add(numberLabel, BorderLayout.CENTER);

        JPanel scorePanel = new JPanel(new BorderLayout());
        scoreLabel = new JLabel("Score: 0");
        scorePanel.add(scoreLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(scorePanel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
    }

    private int getRandomNumber() {
        Random random = new Random();
        currentNumber = random.nextInt(100) + 1;
        return currentNumber;
    }

    private void handleNumberClick(int number) {
        if (number == currentNumber) {
            highlightNumber(number, Color.GREEN);
            gameSession.getPlayers().get(0).setScore(gameSession.getPlayers().get(0).getScore() + 1);
            updatePlayerScore(gameSession.getPlayers().get(0));
            resetNumber(number); // Reset the color of the button
            if (gameSession.getNumbers().isEmpty()) {
                showGameOverMessage("Congratulations! You've found all the numbers.");
            } else {
                updateNextNumber(getRandomNumber());
            }
        } else {
            highlightNumber(number, Color.RED);
            resetNumber(currentNumber); // Reset the color of the current number
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
}
