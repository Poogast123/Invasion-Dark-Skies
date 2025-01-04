package com.example.spacegame;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Game{
    public void start(Stage stage) {
        Pane gamePane = new Pane();

        Scene gameScene = new Scene(gamePane);
        stage.setTitle("SpaceGame");
        stage.setScene(gameScene);
        stage.setMaximized(true);
        stage.show();
    }
}
