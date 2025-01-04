package com.example.spacegame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MyFrame {
    private GameStarter gameStarter;

    public void setGameStarter(GameStarter gameStarter) {
        this.gameStarter = gameStarter;}

    public void onStartButtonClick() {
      if (gameStarter != null) {
            gameStarter.startGame();
        }
    }
}