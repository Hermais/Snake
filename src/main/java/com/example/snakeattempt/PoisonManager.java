package com.example.snakeattempt;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;


public class PoisonManager {
    // Why no setters and getters? This game's constants are needed everywhere and shared between all classes.
    // This is a gradient of use:
    // PANE, POISON[], fitSize, squareDivisionNum and poisonType - Very High

    private final String[] poisonImages = {
            "/images/poison1G.png",
            "/images/poison2G.png",
            "/images/poison3G.png"
    };
    
    private ImageView[] POISON;
    private int poisonType;
    private int fitSize;
    private int squareDivisionNum;
    private int upperYMargin;
    private Pane pane;

    public ImageView[] getPOISON() {
        return POISON;
    }

    public void setPOISON(ImageView[] POISON) {
        this.POISON = POISON;
    }

    public int getPoisonType() {
        return poisonType;
    }

    public void setPoisonType(int poisonType) {
        this.poisonType = poisonType;
    }

    public int getFitSize() {
        return fitSize;
    }

    public void setFitSize(int fitSize) {
        this.fitSize = fitSize;
    }

    public int getSquareDivisionNum() {
        return squareDivisionNum;
    }

    public void setSquareDivisionNum(int squareDivisionNum) {
        this.squareDivisionNum = squareDivisionNum;
    }

    public int getUpperYMargin() {
        return upperYMargin;
    }

    public void setUpperYMargin(int upperYMargin) {
        this.upperYMargin = upperYMargin;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    PoisonManager(){


    }

    public PoisonManager(ImageView[] POISON, int poisonType, int fitSize, int squareDivisionNum, int upperYMargin, Pane pane) {
        this.POISON = POISON;
        this.poisonType = poisonType;
        this.fitSize = fitSize;
        this.squareDivisionNum = squareDivisionNum;
        this.upperYMargin = upperYMargin;
        this.pane = pane;
        prepPoisonImage(poisonType);

        animatePoison();
    }

    public void prepPoisonImage(int poisonType){
        // init image of POISON
        Image poisonImage = new Image(
                getClass().getResource(poisonImages[poisonType]).toExternalForm());
        POISON[poisonType] = new ImageView(poisonImage);

        // fitting POISON
        POISON[poisonType].setFitHeight(fitSize);
        POISON[poisonType].setFitWidth(fitSize);
        POISON[poisonType].setX(new Random(System.currentTimeMillis()).nextInt(1, squareDivisionNum - 1) * fitSize);
        POISON[poisonType].setY(
                (new Random(System.currentTimeMillis()).nextInt(upperYMargin / fitSize, squareDivisionNum - 2) * fitSize)
        );

        pane.getChildren().add(POISON[poisonType]);


    }

    public void animatePoison(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(POISON[poisonType]);
        translateTransition.setToY(fitSize);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();
    }
}
