package com.example.spacegame;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import Game.Player;
import Game.Enemy;
import Game.Bullet;
import Game.Bomb;
import Game.Score;
import Game.HeartPane;
import Game.Bombs.DetecteurBomb;
import java.util.*;
import Game.BulletPool;
import Game.Shield;

public class Game {
    private final Set<KeyCode> pressedKeys = new HashSet<>();
    private BulletPool bulletPool = new BulletPool();
    private Player player;
    private long lastShotTime = 0;
    private final long shotCooldown = 500;
    private List<Enemy> enemies = new ArrayList<>();
    private List<DetecteurBomb> activeDetecteurBombs = new ArrayList<>();
    private int maxEnemies = 5;
    private long lastEnemySpawnTime = 0;
    private final long enemySpawnInterval = 1000;
    private List<Bomb> activeBombs = new ArrayList<>();
    private Score score;
    private double screenWidth;
    private double screenHeight;
    private BackGround BGset;
    private HeartPane heartPane;
    private GameOverOverlay gameover = new GameOverOverlay();
    private Game gameInstance = this;
    private BombManager bombManager;
    private AnimationTimer gameLoop;
    private Shield shield = new Shield();

    public void starter(Stage primaryStage) {
        Pane root = new Pane();

        screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        screenHeight = Screen.getPrimary().getVisualBounds().getHeight();

        // Background setup
        this.BGset = new BackGround(primaryStage);
        this.BGset.playMusic();
        this.BGset.SetBackGround(root);

        this.player = new Player();
        player.setLayoutX((screenWidth - 100) / 2);
        player.setLayoutY(screenHeight - 100);

        // Initialize hearts
        this.heartPane = new HeartPane();
        heartPane.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            heartPane.setHeart("heart.png", i);
            heartPane.getChildren().add(heartPane.getHeart(i));
        }

        // Initialize score display
        this.score = new Score();
        score.Initialize_score_display(root);

        root.getChildren().clear();
        root.getChildren().addAll(player, heartPane, score.getScoreText());

        Scene scene = new Scene(root, screenWidth, screenHeight);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Invasion: Dark Skies");
        primaryStage.setFullScreen(true);

        scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));

        List<Bullet> activeBullets = new ArrayList<>();
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Move player based on pressed keys
                if (pressedKeys.contains(KeyCode.RIGHT)) {
                    player.moveRight(10);
                }
                if (pressedKeys.contains(KeyCode.LEFT)) {
                    player.moveLeft(10);
                }

                if (pressedKeys.contains(KeyCode.SPACE)) { // Use SPACE to activate the shield
                    if (!player.isShieldActive() && !player.isOnCooldown()) {
                        player.activateShield();
                    }
                }

                // Shooting bullets
                if (pressedKeys.contains(KeyCode.S)) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastShotTime >= shotCooldown) {
                        Bullet bullet = bulletPool.getBullet(player.getLayoutX(), player.getLayoutY());
                        activeBullets.add(bullet);
                        root.getChildren().add(bullet);
                        lastShotTime = currentTime;
                    }
                }
                if (pressedKeys.contains(KeyCode.F)) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastShotTime >= shotCooldown) {
                        double bulletStartX = player.getLayoutX() + player.getWidth() - 10;
                        double bulletStartY = player.getLayoutY();

                        Bullet bullet = bulletPool.getBullet(bulletStartX, bulletStartY);
                        activeBullets.add(bullet);
                        root.getChildren().add(bullet);
                        lastShotTime = currentTime;
                    }
                }

                // Move active bullets and remove out-of-bound ones
                activeBullets.removeIf(bullet -> {
                    bullet.move();
                    if (bullet.isOutOfBounds(screenHeight)) {
                        root.getChildren().remove(bullet);
                        bulletPool.returnBullet(bullet);
                        return true;
                    }
                    return false;
                });

                // Check collisions between bullets and enemies
                List<Bullet> bulletsToRemove = new ArrayList<>();
                List<Enemy> enemiesToRemove = new ArrayList<>();
                for (Bullet bullet : activeBullets) {
                    for (Enemy enemy : enemies) {
                        if (bullet.collidesWith(enemy)) {
                            bulletsToRemove.add(bullet);
                            enemiesToRemove.add(enemy);
                            root.getChildren().removeAll(bullet, enemy);
                            bulletPool.returnBullet(bullet);
                            score.updateScore(1);

                            // Show explosion effect
                            ShowEplosion showexplosion = new ShowEplosion();
                            showexplosion.showExplosion(root, enemy.getX(), enemy.getY());

                            // Play explosion sound
                            SoundManager Explosion = new SoundManager();
                            Explosion.playSound("/explosionSE.mp3");
                        }
                    }
                }
                activeBullets.removeAll(bulletsToRemove);
                enemies.removeAll(enemiesToRemove);

                // Remove out-of-bounds enemies
                enemies.removeIf(enemy -> {
                    if (enemy.isOutOfBounds(screenHeight)) {
                        root.getChildren().remove(enemy);
                        return true;
                    }
                    return false;
                });

                List<Bomb> bombsToRemove = new ArrayList<>();

                for (Bomb bomb : activeBombs) {
                    if (bomb.collidesWith(player)) {
                        if (player.isShieldActive()) {
                            root.getChildren().remove(bomb);
                        } else {
                            player.loseHealth();
                            heartPane.updateHearts(heartPane, player);
                            root.getChildren().remove(bomb);

                            if (!player.isAlive()) {
                                gameInstance.getGameLoop().stop();
                                gameover.showGameOverOverlay(root, player, enemies, activeBombs,
                                        gameInstance.getBulletPool(), gameInstance.getScore(), gameInstance.getLastEnemySpawnTime(),
                                        heartPane, gameInstance);
                                BGset.stopMusic();
                            }
                        }
                        bombsToRemove.add(bomb); // Add bomb to removal list
                    }
                }
                // Remove bombs outside the loop to avoid concurrent modification
                activeBombs.removeAll(bombsToRemove);

                bombManager.handleBombs(activeBombs, root, player, heartPane, gameover, gameInstance, BGset, enemies,score);







                // Spawn new enemies
                long currentTime = System.currentTimeMillis();
                if (enemies.size() < maxEnemies && currentTime - lastEnemySpawnTime >= enemySpawnInterval) {
                    Enemy newEnemy = new Enemy(Math.random() * screenWidth, 0);
                    newEnemy.setLastBombTime(currentTime);
                    enemies.add(newEnemy);
                    root.getChildren().add(newEnemy);
                    lastEnemySpawnTime = currentTime;
                }
                player.updateShieldStatus();


                // Move enemies
                for (Enemy enemy : enemies) {
                    enemy.move(player.getLayoutX(),player.getLayoutY(),score.getScore());

                    if (currentTime - enemy.getLastBombTime() >= 2000) {
                        Bomb bomb = enemy.shootBomb(player.getLayoutX(), player.getLayoutY());
                        if (bomb != null) {
                            activeBombs.add(bomb);
                            root.getChildren().add(bomb);
                            enemy.setLastBombTime(currentTime);
                        }
                    }
                }
            }
        };

        gameLoop.start();
        primaryStage.show();

        if (!player.isAlive()) {
            gameLoop.stop();
            gameover.showGameOverOverlay(root, player, enemies, activeBombs, bulletPool, score, lastEnemySpawnTime, heartPane, this);
        }
    }

    public double getScreenHeight() {
        return screenHeight;
    }

    public AnimationTimer getGameLoop() {
        return this.gameLoop;
    }

    public long getLastEnemySpawnTime() {
        return this.lastEnemySpawnTime;
    }

    public Score getScore() {
        return this.score;
    }

    public BulletPool getBulletPool() {
        return this.bulletPool;
    }
}