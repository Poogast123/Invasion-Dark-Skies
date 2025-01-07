package com.example.spacegame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class GameOverController {

    @FXML
    private Button replayButton;

    @FXML
    private Button quitButton;

    public void setOnReplayAction(Runnable replayAction) {
        replayButton.setOnAction(event -> replayAction.run());
    }

    public void setOnQuitAction(Runnable quitAction) {
        quitButton.setOnAction(event -> quitAction.run());
    }
}
