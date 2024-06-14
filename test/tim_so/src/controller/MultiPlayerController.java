package controller;

import model.GameSession;
import model.Player;
import view.MultiPlayerView;

import java.util.List;

public class MultiPlayerController {
    private GameSession gameSession;
    private MultiPlayerView view;

    public MultiPlayerController(GameSession gameSession, MultiPlayerView view) {
        this.gameSession = gameSession;
        this.view = view;
    }

    public void makeGuess(String numberStr) {
        try {
            int number = Integer.parseInt(numberStr);
            if (gameSession.getNumbers().contains(number)) {
                // Xử lý đoán số
                gameSession.getNumbers().remove(Integer.valueOf(number));
                view.appendChatMessage(gameSession.getCurrentPlayer().getUsername() + " guessed the number " + number + " correctly!");
                updatePlayerList();
            } else {
                view.appendChatMessage(gameSession.getCurrentPlayer().getUsername() + " guessed the number " + number + " incorrectly.");
            }
        } catch (NumberFormatException e) {
            view.appendChatMessage("Invalid number format. Please enter a valid number.");
        }
    }

    public void sendMessage(String message) {
        view.appendChatMessage(gameSession.getCurrentPlayer().getUsername() + ": " + message);
    }

    private void updatePlayerList() {
        List<Player> players = gameSession.getPlayers();
        view.updatePlayerList(players);
    }
}
