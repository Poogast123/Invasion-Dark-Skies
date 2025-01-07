package Game.Enemys;
import Game.Bomb;

import Game.Enemy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnemyLVL2 extends Enemy {
    private double speed = 3;
    private ImageView enemyImageView;
    private long lastBombTime; // Time of the last bomb in milliseconds
    private long bombCooldown = 2000; // Bomb cooldown (2 seconds)

    public EnemyLVL2(double startX, double startY) {
        super(startX, startY);
        // Set the enemy's image
        Image enemyImage = new Image("/enemyLVL2.png"); // Use your enemy image path
        enemyImageView = new ImageView(enemyImage);
        enemyImageView.setFitWidth(50);  // Adjust size as needed
        enemyImageView.setFitHeight(50);

        // Set the initial position of the enemy
        this.setLayoutX(startX);
        this.setLayoutY(startY);

        // Add the ImageView to the Pane
        this.getChildren().add(enemyImageView);
    }

    // Move the enemy downward
    public void move() {
        this.setLayoutY(this.getLayoutY() + speed);
    }

    // Check if the enemy is out of bounds
    public boolean isOutOfBounds(double screenHeight) {
        return this.getLayoutY() > screenHeight;
    }

    // Shoot bombs at intervals
    public Bomb shootBomb(double playerX, double playerY) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBombTime >= bombCooldown) {
            Bomb bomb = new Bomb(this.getLayoutX() + 20, this.getLayoutY() + 50); // Position bomb below the enemy
            lastBombTime = currentTime; // Update the last bomb time
            return bomb;
        }
        return null;
    }

    // Getter for the enemy's X position
    public double getX() {
        return this.getLayoutX();
    }

    // Getter for the enemy's Y position
    public double getY() {
        return this.getLayoutY();
    }

    public long getLastBombTime() {
        return lastBombTime;
    }

    public void setLastBombTime(long lastBombTime) {
        this.lastBombTime = lastBombTime;
    }
}
