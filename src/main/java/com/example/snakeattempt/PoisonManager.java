package com.example.snakeattempt;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

import static com.example.snakeattempt.SnakeEngine.*;

public class PoisonManager {
    // Why no setters and getters? This game's constants are needed everywhere and shared between all classes.
    // This is a gradient of use:
    // PANE, POISON[], TILE_SIZE, TILE_COUNT and poisonType - Very High

    private final String[] poisonImages = {
            "/images/poison1G.png",
            "/images/poison2G.png",
            "/images/poison3G.png"
    };

    PoisonManager(){
        prepPoisonImage();

        animatePoison();

        PANE.getChildren().add(POISON[poisonType]);
    }

    public void prepPoisonImage(){
        // init image of POISON
        Image poisonImage = new Image(
                getClass().getResource(poisonImages[poisonType]).toExternalForm());
        POISON[poisonType] = new ImageView(poisonImage);

        // fitting POISON
        POISON[poisonType].setFitHeight(TILE_SIZE);
        POISON[poisonType].setFitWidth(TILE_SIZE);
        POISON[poisonType].setX(new Random(System.currentTimeMillis()).nextInt(1, TILE_COUNT - 1) * TILE_SIZE);
        POISON[poisonType].setY(
                (new Random(System.currentTimeMillis()).nextInt(PANEL_REALSTATE / TILE_SIZE, TILE_COUNT - 2) * TILE_SIZE)
        );

    }

    public void animatePoison(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(POISON[poisonType]);
        translateTransition.setToY(TILE_SIZE);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();
    }
}
