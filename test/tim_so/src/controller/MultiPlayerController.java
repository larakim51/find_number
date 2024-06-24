package controller;

import model.GameSession;
import view.MultiPlayerView;

public class MultiPlayerController {
    private GameSession gameSession;
    private MultiPlayerView view;

    public MultiPlayerController(GameSession gameSession, MultiPlayerView view) {
        this.gameSession = gameSession;
        this.view = view;
    }


}
