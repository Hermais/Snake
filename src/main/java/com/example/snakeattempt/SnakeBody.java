package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.snakeattempt.SnakeEngine.*;

public class SnakeBody {

        private double temp = TILE_SIZE;

        SnakeBody(){
            // If we failed to make the code work as a snake with a head, we will remove the head and
            // replace it with body.
            for(int i = 0; i <= snakeBodyPartsCount; i++) {
                bodyParts[i] = new ImageView(new Image(getClass().getResource(
                        i == 0 ? "/images/snakeHeadG.png" : "/images/snakeBodySegments.png").toExternalForm())
                );
                bodyParts[i].setFitHeight(TILE_SIZE);
                bodyParts[i].setFitWidth(TILE_SIZE);
                bodyParts[i].setX(initialSnakeHeadX);
                bodyParts[i].setY(initialSnakeHeadY + temp);
                PANE.getChildren().add(bodyParts[i]);
                temp += TILE_SIZE;
            }

        }


    }

