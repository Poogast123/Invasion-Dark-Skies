package Game;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class Score {
    private int score = 0;
    private Text scoreText = new Text();
    private double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();

    public void Initialize_score_display(Pane root) {
        // Initialize score display
        updateScoreText(); // Set initial text

        // Adjust layout based on screen size
        double margin = screenWidth * 0.02; // 2% margin from the edge
        scoreText.setLayoutX(screenWidth - margin - 250); // Adjust position dynamically
        scoreText.setLayoutY(40);

        // Adjust style for responsiveness
        scoreText.setStyle("-fx-font-size: " + (screenWidth * 0.02) + "px; " + // Dynamic font size based on screen width
                "-fx-fill: white; " +             // Set text color to white
                "-fx-font-weight: bold; " +       // Make the text bold
                "-fx-font-family: 'Exo'; " +      // Set the font family
                "-fx-stroke: black; " +           // Add a black outline to make the text stand out
                "-fx-stroke-width: 1; " +         // Set the width of the outline
                "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.7), 5, 0, 2, 2);"); // Add a shadow effect

        root.getChildren().add(scoreText);
    }

    public Text getScoreText() {
        return this.scoreText;
    }

    public void setScoreText(Text scoreText) {
        this.scoreText = scoreText;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        updateScoreText();
    }

    public void updateScore(int scoreAdd) {
        this.score += scoreAdd; // Update internal score
        updateScoreText();
    }

    public void reset() {
        this.score = 0;
        updateScoreText();
    }

    private void updateScoreText() {
        int i = 1;
        if(this.getScore() > 20){i++;}
        if(this.getScore() > 40){i++;}
        scoreText.setText("LVL : "+i+" " + "   " + "Score : " + this.score);



    }

}
