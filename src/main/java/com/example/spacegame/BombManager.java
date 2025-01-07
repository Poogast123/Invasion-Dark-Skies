package com.example.spacegame;

import javafx.scene.layout.Pane;
import Game.Player;
import Game.HeartPane;
import Game.Bomb;
import Game.Enemy;
import javafx.stage.Screen;
import Game.Score;


import java.util.List;

public class BombManager {
    private static double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();


    // Method to move all bombs and check for collisions
    public static void handleBombs(List<Bomb> activeBombs, Pane root, Player player, HeartPane heartPane, GameOverOverlay gameover, Game gameInstance, BackGround BGset, List<Enemy> enemy, Score score) {
        activeBombs.removeIf(bomb -> {
            if (bomb != null) {
                bomb.move(player.getLayoutX(), player.getLayoutY(), score);
            }

            // Check if the bomb is out of bounds
            if (bomb.isOutOfBounds(gameInstance.getScreenHeight())) {
                root.getChildren().remove(bomb);
                return true;
            }

            // Check collision with the player or shield
            if (bomb.collidesWith(player)) {
                if (player.isShieldActive()) {
                    // Shield blocks the bomb
                    root.getChildren().remove(bomb);
                    return true; // Remove bomb after collision with shield
                } else {
                    // Bomb hits the player, reduce health
                    player.loseHealth();
                    heartPane.updateHearts(heartPane, player);
                    root.getChildren().remove(bomb);
                    if (!player.isAlive()) {
                        gameInstance.getGameLoop().stop();
                        gameover.showGameOverOverlay(root, player, enemy, activeBombs,
                                gameInstance.getBulletPool(), gameInstance.getScore(), gameInstance.getLastEnemySpawnTime(),
                                heartPane, gameInstance);
                        BGset.stopMusic();
                    }
                    return true; // Remove bomb after hitting player
                }
            }

            return false;
        });
    }
}
