package com.example.spacegame;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import Game.Player;
import Game.Enemy;
import Game.Bomb;
import Game.Bullet;
import Game.BulletPool;

import java.io.IOException;
import java.util.List;
import Game.Score;
import Game.HeartPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GameOverOverlay {
    private double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();

    private double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();;

    // Method to create and display the Game Over overlay
    public void showGameOverOverlay(Pane root, Player player, List<Enemy> enemies, List<Bomb> activeBombs, BulletPool bulletPool, Score score, long lastEnemySpawnTime, HeartPane hearts, Game game) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game_over_overlay.fxml"));
            Pane overlay = loader.load();

            // Set overlay to fill the entire screen
            overlay.setPrefSize(screenWidth, screenHeight);
            overlay.setLayoutX(0);
            overlay.setLayoutY(0);

            GameOverController controller = loader.getController();
            controller.setOnReplayAction(() -> restartGame(root, overlay, player, enemies, activeBombs, bulletPool, score, lastEnemySpawnTime, hearts, game));
            controller.setOnQuitAction(() -> System.exit(0));

            root.getChildren().add(overlay);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading overlay: " + e.getMessage());
        }
    }


    // Method to restart the game
    private void restartGame(Pane root, Pane overlay, Player player, List<Enemy> enemies, List<Bomb> activeBombs, BulletPool bulletPool, Score score, long lastEnemySpawnTime, HeartPane hearts,Game game) {
        root.getChildren().remove(overlay);

        // Reset game state
        player.resetHealth();
        hearts.updateHearts(hearts, player);
        player.setLayoutX((screenWidth - 100) / 2);
        player.setLayoutY(screenHeight - 100);

        // Clear enemies, bombs, and bullets
        for (Enemy enemy : enemies) {
            root.getChildren().remove(enemy);
        }
        for (Bomb bomb : activeBombs) {
            root.getChildren().remove(bomb);
        }
        for (Bullet bullet : bulletPool.getAllActiveBullets()) {
            root.getChildren().remove(bullet);
        }
        enemies.clear();
        activeBombs.clear();
        bulletPool.clear();

        // Reset score
        score.reset(); // Use reset instead of updateScore(0)

        // Restart the game loop
        lastEnemySpawnTime = System.currentTimeMillis();

        // Call starter to restart the game
        game.starter((Stage) root.getScene().getWindow());
    }






}
