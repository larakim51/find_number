package controller;

import java.awt.Color;
import javax.swing.JOptionPane;
import model.MultiSession;
import model.Player;
import network.GameClient;
import view.HomeView;
import view.MultiPlayerView;

public class MultiPlayerController {
    private MultiSession multiSession;
    private MultiPlayerView view;
    private GameClient client;
    private int currentNumber;
    

    public MultiPlayerController(MultiSession multiSession, MultiPlayerView view) {
        this.multiSession = multiSession;
        this.view = view;
        startNewGame();
    }

    public void startNewGame() {
        currentNumber = multiSession.getCurrentNumber();
        view.updateNumberLabel(currentNumber);
    }

    public void handleBackButton() {
        int response = JOptionPane.showConfirmDialog(view, "Do you really want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(view, "Final scores:\n" + multiSession.getLeaderboard());
            view.dispose();
            HomeView.showHomeView();
        }
    }

    public void handleNumberButton(int number) {
        if (number == currentNumber) {
            Player player = multiSession.getCurrentPlayer();
            multiSession.updatePoints(player, 10);
            view.highlightNumberButton(number, getColorFromString(player.getColor()));
            multiSession.findNextNumber();
            currentNumber = multiSession.getCurrentNumber();
            view.updateNumberLabel(currentNumber);
        } else {
            view.highlightNumberButton(number, Color.WHITE); // Giữ màu trắng khi chọn sai
        }
    }

    private Color getColorFromString(String colorName) {
        switch (colorName.toUpperCase()) {
            case "RED":
                return Color.RED;
            case "BLUE":
                return Color.BLUE;
            case "GREEN":
                return Color.GREEN;
            default:
                return Color.BLACK;
        }
    }

    public void updateTimer(String time) {
        view.updateTimerLabel(time);
    }
}
