package it.unibo.objectmon;

import it.unibo.objectmon.controller.GameController;
import it.unibo.objectmon.model.GameModel;
import it.unibo.objectmon.view.GameView;

public class LaunchApp {
    public static void main(String[] args) {
        GameController gameController = new GameController();
        GameModel gameModel = new GameModel();
        GameView gameView = new GameView(gameController);
        gameController.setView(gameView);
        gameController.setModel(gameModel);
        gameController.startGame();
    }
}
