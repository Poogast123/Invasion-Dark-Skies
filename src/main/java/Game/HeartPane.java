package Game;


import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;


public class HeartPane extends Pane {
    private ImageView[] hearts = new ImageView[3];

    public ImageView[] getHearts(){
        return this.hearts;
    }

    public void setHeart(String link,int i){
        this.hearts[i] = new ImageView(link);
        this.hearts[i].setFitWidth(30);
        this.hearts[i].setFitHeight(30);
        this.hearts[i].setLayoutX(i * 35 + 20);
        this.hearts[i].setLayoutY(10);
    }

    public ImageView getHeart(int i){
        return this.hearts[i];
    }

    // Method to update hearts display
    public void updateHearts(HeartPane hearts, Player player) {
        hearts.getChildren().clear();
        for (int i = 0; i < player.getHealth(); i++) {
            hearts.getChildren().add(hearts.getHeart(i));
        }
    }


}






