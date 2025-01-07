package com.example.spacegame;

import java.io.IOException;

public class MyFrame {
    private GameStarter gameStarter;

    public void setGameStarter(GameStarter gameStarter) {
        this.gameStarter = gameStarter;}

    public void onStartButtonClick() throws IOException {
      if (gameStarter != null) {
            gameStarter.startGame();
        }
    }
}