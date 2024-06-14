package controller;

import model.GameSession;
import view.GameView;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameController {
    private GameSession model;
    private GameView view;

    public GameController(GameSession model, GameView view) {
        this.model = model;
        this.view = view;
        initController();
    }

    private void initController() {
        for (JButton button : view.getNumberButtons()) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    int number = Integer.parseInt(clickedButton.getText());
                    handleNumberClick(number);
                }
            });
        }
    }

    private void handleNumberClick(int number) {
        List<Integer> numbers = model.getNumbers();
        
        if (numbers.contains(number)) {
            model.getNumbers().remove((Integer) number);
            view.highlightNumber(number, Color.GREEN);
        
            // Cập nhật điểm số của người chơi
            Player currentPlayer = model.getPlayers().get(0);
            currentPlayer.setScore(currentPlayer.getScore() + 1);
            view.updatePlayerScore(currentPlayer);
        
            // Kiểm tra kết thúc trò chơi
            if (model.getNumbers().isEmpty()) {
                view.showGameOverMessage("Congratulations! You've found all the numbers.");
                // Thực hiện các logic kết thúc trò chơi khác
            }
        } else {
            view.highlightNumber(number, Color.RED);
            // Có thể thêm logic để thông báo cho người chơi biết số đó không đúng
        }
        
    }
}
