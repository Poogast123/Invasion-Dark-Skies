package com.example.spacegame;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.Objects;

public class BackGround {
    private final Stage stage;
    private MediaPlayer mediaPlayer;


    public BackGround(Stage stage) {
        this.stage = stage;
    }

    // Method to create and return the MediaPlayer for the background music
    public void playMusic() {
        String musicFile = "/backgroundGame.mp3"; // Path to the music file in resources
        Media media = new Media(Objects.requireNonNull(getClass().getResource(musicFile)).toExternalForm()); // Load the music
        this.mediaPlayer = new MediaPlayer(media); // Initialize the MediaPlayer
        this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set it to loop indefinitely
        this.mediaPlayer.play(); // Start playing the music
    }
    public void SetBackGround(Pane root){
        // Set background image using CSS
        root.setStyle("-fx-background-image: url('spaceBG.jpg'); " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;");

    }
    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the current music
            mediaPlayer.dispose(); // Release resources
        }
    }
}
