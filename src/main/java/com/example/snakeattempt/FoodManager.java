package com.example.snakeattempt;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

import static com.example.snakeattempt.SnakeEngine.*;

public class FoodManager {
    // Why no setters and getters? This game's constants are needed everywhere and shared between all classes.
    // This is a gradient of use:
    // PANE, FOOD[], TILE_SIZE, TILE_COUNT and foodType - Very High
    private final String[] foodImages = {"/images/appleG.png",
            "/images/bananaG.png",
            "/images/peachG.png",
            "/images/grapesG.png",
            "/images/mushroomG.png"};

    FoodManager(){
        prepFoodImg();

        animateFood();

        PANE.getChildren().add(FOOD[foodType]);

    }

    public void prepFoodImg(){
        //FOOD Image
        Image foodImage = new Image(getClass().getResource(foodImages[foodType]
        ).toExternalForm());
        FOOD[foodType] = new ImageView(foodImage);

        //FOOD adjusting
        FOOD[foodType].setFitHeight(TILE_SIZE);
        FOOD[foodType].setFitWidth(TILE_SIZE);
        FOOD[foodType].setX(new Random(System.currentTimeMillis()).nextInt(1, TILE_COUNT - 1) * TILE_SIZE);
        FOOD[foodType].setY(
                (new Random(System.currentTimeMillis()).nextInt(PANEL_REALSTATE / TILE_SIZE, TILE_COUNT - 2) * TILE_SIZE)
        );

    }

    public void animateFood(){
        // Just an animation
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(FOOD[foodType]);
        translateTransition.setToY(TILE_SIZE);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();

    }


}