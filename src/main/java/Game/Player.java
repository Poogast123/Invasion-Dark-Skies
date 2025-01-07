package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends Pane {
    private double count = 3; // Player's health (number of hearts)

    private ImageView playerImageView; // Player's main image
    private Shield shield;             // Shield instance

    public Player() {
        // Load the player image
        Image playerImage = new Image("/player.png"); // Update with the correct path to your image
        playerImageView = new ImageView(playerImage);

        // Set the player's image size
        playerImageView.setFitWidth(100);  // Set the width of the image
        playerImageView.setFitHeight(100); // Set the height of the image

        // Add the player image to the Player (Pane)
        this.getChildren().add(playerImageView);

        // Initialize the shield and add it to the Player (Pane)
        shield = new Shield();
        this.getChildren().add(shield);

        // Position the shield relative to the player
        updateShieldPosition();
    }

    // Method to move the player to the right
    public void moveRight(double distance) {
        setLayoutX(getLayoutX() + distance);
        updateShieldPosition(); // Update shield position after movement
    }

    // Method to move the player to the left
    public void moveLeft(double distance) {
        setLayoutX(getLayoutX() - distance);
        updateShieldPosition(); // Update shield position after movement
    }

    // Method to decrease health when the player loses health
    public void loseHealth() {
        if (count > 0) {
            count--; // Decrease health count
        }
    }

    // Check if the player is alive
    public boolean isAlive() {
        return count > 0;
    }

    // Getter for current health
    public double getHealth() {
        return count;
    }

    // Reset health to the initial value
    public void resetHealth() {
        count = 3; // Reset health to the initial value
    }

    // Activate the shield
    public void activateShield() {
        shield.activate();
    }

    // Update the shield's status
    public void updateShieldStatus() {
        shield.update();
    }

    // Check if the shield is active
    public boolean isShieldActive() {
        return shield.isActive();
    }

    // Update the position of the shield relative to the player
    private void updateShieldPosition() {
        shield.setTranslateX(-10); // Adjust the shield’s horizontal position
        shield.setTranslateY(-10); // Adjust the shield’s vertical position
    }

    public boolean isOnCooldown() {
        return shield.isOnCooldown();
    }
    public Shield getShield() {
        return shield;
    }

}
