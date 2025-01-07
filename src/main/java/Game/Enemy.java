package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Enemy extends Pane {
    private double speed = 2;
    private ImageView enemyImageView;
    private long lastBombTime; // Time of the last bomb in milliseconds
    private long bombCooldown = 3000; // Bomb cooldown (3 seconds)

    private boolean homing = false; // Flag for homing behavior
    private boolean homingCompleted = false; // Indicates if homing is completed
    private long homingStartTime = 0; // Start time of homing mode
    private final long homingDuration = 7000; // Duration of homing mode in milliseconds

    public Enemy(double startX, double startY) {
        // Set the enemy's image
        Image enemyImage = new Image("/ufo.png"); // Use your enemy image path
        enemyImageView = new ImageView(enemyImage);
        enemyImageView.setFitWidth(50);  // Adjust size as needed
        enemyImageView.setFitHeight(50);

        // Set the initial position of the enemy
        this.setLayoutX(startX);
        this.setLayoutY(startY);

        // Add the ImageView to the Pane
        this.getChildren().add(enemyImageView);
    }

    // Move the enemy
    public void move(double playerX, double playerY, int score) {
        if (score > 20 && !homingCompleted) {
            if (!homing) {
                // Enter homing mode
                homing = true;
                homingStartTime = System.currentTimeMillis();
            }

            // Check if homing mode is still active
            if (System.currentTimeMillis() - homingStartTime <= homingDuration) {
                moveTowardsPlayer(playerX, playerY);
            } else {
                // Exit homing mode and mark it as completed
                homing = false;
                homingCompleted = true;
                moveDownward();
            }
        } else {
            // Default downward movement after homing is completed
            moveDownward();
        }
    }

    // Move toward the player
    private void moveTowardsPlayer(double playerX, double playerY) {
        double dx = playerX - this.getLayoutX();
        double dy = playerY - this.getLayoutY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > 0) {
            this.setLayoutX(this.getLayoutX() + speed * (dx / distance));
            this.setLayoutY(this.getLayoutY() + speed * (dy / distance));
        }
    }

    // Default downward movement
    private void moveDownward() {
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
