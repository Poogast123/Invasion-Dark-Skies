package com.example.spacegame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ShowEplosion {

    // Method to show explosion at the specified position
    void showExplosion(Pane root, double x, double y) {
        // Load explosion image
        Image explosionImage = new Image(getClass().getResource("/explosion.gif").toExternalForm());
        ImageView explosion = new ImageView(explosionImage);

        // Set the position of the explosion
        explosion.setX(x - 100);
        explosion.setY(y - 50);

        // Add the explosion to the scene
        root.getChildren().add(explosion);

        // Remove the explosion after a delay (e.g., 500 ms)
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(500), event -> root.getChildren().remove(explosion))
        );
        timeline.setCycleCount(1);
        timeline.play();
    }
}
