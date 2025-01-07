package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Bomb extends Pane {
    private double speed = 3;
    private ImageView bombImageView;
    private boolean homing = false;
    private boolean homingCompleted = false; // New flag to track if homing is finished
    private long homingStartTime = 0;

    public Bomb(double startX, double startY) {
        // Initialize bomb appearance
        Image bombImage = new Image(getClass().getResourceAsStream("/detecteurBomb.png")); // Ensure correct image loading
        bombImageView = new ImageView(bombImage);
        bombImageView.setFitWidth(20);  // Adjust size as needed
        bombImageView.setFitHeight(20);

        // Set the initial position of the bomb
        this.setLayoutX(startX);
        this.setLayoutY(startY);

        // Add the ImageView to the Pane
        this.getChildren().add(bombImageView);
    }

    // Move the bomb
    public void move(double playerX, double playerY, Score score) {
        if (score.getScore() > 40  && !homingCompleted) {

            if (!homing) {
                homing = true;
                homingStartTime = System.currentTimeMillis();
            }

            // If homing for less than 1 second, move toward the player
            if (System.currentTimeMillis() - homingStartTime <= 1000) {
                double dx = playerX - this.getLayoutX();
                double dy = playerY - this.getLayoutY();
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance > 0) {
                    this.setLayoutX(this.getLayoutX() + speed * (dx / distance));
                    this.setLayoutY(this.getLayoutY() + speed * (dy / distance));
                }
            } else {
                // Stop homing after 1 second and set homing as completed
                homing = false;
                homingCompleted = true;
                moveDownward();
            }

        }
        else {
            // Default movement if score is <= 5 or homing is completed
            moveDownward();
        }
    }

    private void moveDownward() {
        this.setLayoutY(this.getLayoutY() + speed);
    }

    // Check if the bomb is out of bounds
    public boolean isOutOfBounds(double screenHeight) {
        return this.getLayoutY() > screenHeight;
    }

    public boolean collidesWith(Player player) {
        // Check if the shield is active and the bomb collides with it
        if (player.isShieldActive() && this.collidesWithShild(player.getShield())) {
            return false; // Bomb is blocked by the shield, no damage to the player
        }

        // If the shield is not active, check collision with the player
        return this.getBoundsInParent().intersects(player.getBoundsInParent());
    }



    public boolean collidesWithShild(Shield shield) {
        return this.getBoundsInParent().intersects(shield.getBoundsInParent());
    }
}
