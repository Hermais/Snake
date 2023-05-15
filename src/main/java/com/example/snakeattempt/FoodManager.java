package com.example.snakeattempt;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;

// import static com.example.snakeattempt.SnakeEngine.*;

public class FoodManager {
    // Why no setters and getters? This game's constants are needed everywhere and shared between all classes.
    // This is a gradient of use:
    // PANE, FOOD[], TILE_SIZE, TILE_COUNT and foodType - Very High
    private final String[] foodImages = {
            "/images/appleG.png",
            "/images/bananaG.png",
            "/images/peachG.png",
            "/images/grapesG.png",
            "/images/mushroomG.png"};

    private Pane pane;
    private int foodType;
    private ImageView[] FOOD;
    private int fitSize;
    private int upperYMargin;
    private int squareDivisionNum;

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public int getFoodType() {
        return foodType;
    }

    public void setFoodType(int foodType) {
        this.foodType = foodType;
    }

    public ImageView[] getFOOD() {
        return FOOD;
    }

    public void setFOOD(ImageView[] FOOD) {
        this.FOOD = FOOD;
    }

    public int getFitSize() {
        return fitSize;
    }

    public void setFitSize(int fitSize) {
        this.fitSize = fitSize;
    }

    public int getUpperYMargin() {
        return upperYMargin;
    }

    public void setUpperYMargin(int upperYMargin) {
        this.upperYMargin = upperYMargin;
    }

    public int getSquareDivisionNum() {
        return squareDivisionNum;
    }

    public void setSquareDivisionNum(int squareDivisionNum) {
        this.squareDivisionNum = squareDivisionNum;
    }

    FoodManager(){


    }

    public FoodManager(Pane pane, int foodType, ImageView[] FOOD, int fitSize, int upperYMargin, int squareDivisionNum) {
        this.pane = pane;
        this.foodType = foodType;
        this.FOOD = FOOD;
        this.fitSize = fitSize;
        this.upperYMargin = upperYMargin;
        this.squareDivisionNum = squareDivisionNum;
        prepFoodImg(foodType);
        animateFood();

    }

    public void prepFoodImg(int foodType){
        //FOOD Image
        Image foodImage = new Image(getClass().getResource(foodImages[foodType]
        ).toExternalForm());
        FOOD[foodType] = new ImageView(foodImage);

        //FOOD adjusting
        FOOD[foodType].setFitHeight(fitSize);
        FOOD[foodType].setFitWidth(fitSize);
        FOOD[foodType].setX(new Random(System.currentTimeMillis()).nextInt(1, squareDivisionNum - 1) * fitSize);
        FOOD[foodType].setY(
                (new Random(System.currentTimeMillis()).nextInt(upperYMargin / fitSize, squareDivisionNum - 2) * fitSize)
        );
        pane.getChildren().add(FOOD[foodType]);



    }

    public void animateFood(){
        // Just an animation
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(FOOD[foodType]);
        translateTransition.setToY(fitSize);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();

    }


}
