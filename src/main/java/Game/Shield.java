package Game;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Shield extends Pane {
    private boolean active = false; // Whether the shield is active
    private long startTime = 0;     // Start time of the shield
    private final long duration = 5000; // Shield duration in milliseconds
    private boolean onCooldown = false;
    private final long cooldownTime = 10000; // 3 seconds cooldown

    private ImageView shieldImageView; // Shield's visual representation

    public Shield() {
        // Load the shield image
        Image shieldImage = new Image("/shield.png"); // Update with the correct path to your image
        shieldImageView = new ImageView(shieldImage);

        // Set the shield's size
        shieldImageView.setFitWidth(120); // Slightly larger than the player
        shieldImageView.setFitHeight(120);

        // Add the shield image to the Shield (Pane)
        this.getChildren().add(shieldImageView);

        // Initially, the shield is hidden
        this.setVisible(false);
    }

    // Activate the shield
    public void activate() {
        if (onCooldown) {
            return; // Do nothing if the shield is on cooldown
        }

        active = true;
        startTime = System.currentTimeMillis();
        this.setVisible(true);

        // Add a glowing effect to the shield
        DropShadow glow = new DropShadow();
        glow.setColor(Color.CYAN);
        glow.setRadius(20);
        shieldImageView.setEffect(glow);
    }

    // Update the shield's status
    public void update() {
        if (active && System.currentTimeMillis() - startTime >= duration) {
            deactivate();
            onCooldown = true; // Start cooldown
            startTime = System.currentTimeMillis(); // Reset start time for cooldown tracking
        }
        if (onCooldown && System.currentTimeMillis() - startTime >= cooldownTime) {
            onCooldown = false; // End cooldown
        }

    }

    // Deactivate the shield
    public void deactivate() {
        active = false;
        this.setVisible(false);
    }

    // Check if the shield is active
    public boolean isActive() {
        return active;
    }

    public boolean isOnCooldown() {
        return onCooldown;
    }
}
