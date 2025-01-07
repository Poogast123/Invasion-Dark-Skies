package com.example.spacegame;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundManager {
    public void playSound(String soundFilePath) {
        try {
            Media sound = new Media(getClass().getResource(soundFilePath).toExternalForm());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        } catch (NullPointerException e) {
            System.err.println("Sound file not found: " + soundFilePath);
        }
    }
}
