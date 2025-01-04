package com.example.spacegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaException;

import java.io.IOException;
import java.util.Objects;


public class HelloApplication extends Application implements GameStarter{
    private Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.mainStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane root = fxmlLoader.load();

        // Set background image using CSS
        root.setStyle("-fx-background-image: url('background.jpg'); " +
                "-fx-background-size: cover; " +
                "-fx-background-position: center;");

        // Background Music
        String musicFile = "/CityStomper.mp3"; // Path to the music file in resources
        Media media = new Media(Objects.requireNonNull(getClass().getResource(musicFile)).toExternalForm()); // Load the music
        MediaPlayer mediaPlayer = new MediaPlayer(media); // Create a MediaPlayer for the music
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set it to loop indefinitely
        mediaPlayer.play();

        // Monitor scene changes
        mainStage.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (oldScene != null) {
                // Stop music if we are leaving the scene where music is playing
                mediaPlayer.stop();
            }
        });


        Scene scene = new Scene(root);
        mainStage.setTitle("SpaceGame");
        mainStage.setScene(scene);
        mainStage.setMaximized(true);
        mainStage.show();

        // Pass the instance of this class to the controller
        MyFrame controller = fxmlLoader.getController();
        controller.setGameStarter(this);
    }





    public static void main(String[] args) {
        launch();
    }

    @Override
    public void startGame() {
        Game game = GameFactory.createGame();
        game.start(mainStage);

    }

}