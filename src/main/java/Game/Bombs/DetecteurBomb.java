package Game.Bombs;

import Game.Bomb;
import Game.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import Game.Score;

public class DetecteurBomb extends Bomb {
    private Player player;
    private double speed = 3;  // Speed at which the bomb follows the player
    private ImageView bombImageView;


    public DetecteurBomb(double startX, double startY, Player player) {
        super(startX, startY);
        this.player = player; // Pass in the player to the bomb
        // Initialize bomb appearance
        Image bombImage = new Image("/detecteurBomb.png"); // Use your bomb image path
        bombImageView = new ImageView(bombImage);
        bombImageView.setFitWidth(20);  // Adjust size as needed
        bombImageView.setFitHeight(20);

    }

    @Override
    public void move(double playerX, double playerY, Score score) {
        double bombX = this.getLayoutX();
        double bombY = this.getLayoutY();
        playerX = player.getLayoutX();
        playerY = player.getLayoutY();

        // Calculate the direction the bomb should move in
        double dx = playerX - bombX;
        double dy = playerY - bombY;

        // Normalize the direction to maintain a constant speed
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance != 0) {
            dx /= distance; // Normalize the x direction
            dy /= distance; // Normalize the y direction
        }

        // Move the bomb towards the player
        this.setLayoutX(bombX + dx * speed);
        this.setLayoutY(bombY + dy * speed);
    }

    @Override
    public boolean isOutOfBounds(double screenHeight) {
        return this.getLayoutY() > screenHeight; // Check if it went out of bounds
    }

    @Override
    public boolean collidesWith(Player player) {
        return this.getBoundsInParent().intersects(player.getBoundsInParent()); // Collision check
    }
}
