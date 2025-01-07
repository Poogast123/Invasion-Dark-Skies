package Game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Bullet extends Pane {
    private double speed = 7;
    private ImageView bulletImageView;

    public Bullet(double startX, double startY) {
        // Initialize the bullet's appearance with an image
        Image bulletImage = new Image("/bullet.png"); // Specify the image file path
        bulletImageView = new ImageView(bulletImage);

        // Set the size of the image if necessary (optional)
        bulletImageView.setFitWidth(10);  // Adjust width as needed
        bulletImageView.setFitHeight(20); // Adjust height as needed

        // Position the bullet at the given starting coordinates
        this.setLayoutX(startX);
        this.setLayoutY(startY);

        // Add the ImageView to the Pane (Bullet)
        this.getChildren().add(bulletImageView);
    }

    public void move() {
        this.setLayoutY(this.getLayoutY() - speed); // Move bullet upwards
    }

    public boolean isOutOfBounds(double screenHeight) {
        return this.getLayoutY() < 0;
    }

    public boolean collidesWith(Enemy enemy) {
        return this.getBoundsInParent().intersects(enemy.getBoundsInParent());
    }
}
