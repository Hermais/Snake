package com.example.snakeattempt;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

import static com.example.snakeattempt.SnakeEngine.*;

public class RuntimeOfSnake {
    RuntimeOfSnake(){

    }

    public static void runSnake() {



        // Anton:
        // Update snake direction
        switch (currentDirection) {
            case UP -> {

                for (int i = snakeBodyPartsCount; i >= 0; i--) {
                    moveY(true, i);
                    if (i != 0) {
                        bodyParts[i].setX(bodyParts[i - 1].getX());
                        bodyParts[i].setY(bodyParts[i - 1].getY());
                    }

                }
            }
            case DOWN -> {
                for (int i = snakeBodyPartsCount; i >= 0; i--) {
                    moveY(false, i);
                    if (i != 0) {
                        bodyParts[i].setX(bodyParts[i - 1].getX());
                        bodyParts[i].setY(bodyParts[i - 1].getY());
                    }
                }
            }
            case RIGHT -> {
                for (int i = snakeBodyPartsCount; i >= 0; i--) {
                    moveX(true, i);
                    if (i != 0) {
                        bodyParts[i].setX(bodyParts[i - 1].getX());
                        bodyParts[i].setY(bodyParts[i - 1].getY());
                    }

                }
            }
            case LEFT -> {
                for (int i = snakeBodyPartsCount; i >= 0; i--) {
                    moveX(false, i);
                    if (i != 0) {
                        bodyParts[i].setX(bodyParts[i - 1].getX());
                        bodyParts[i].setY(bodyParts[i - 1].getY());
                    }

                }

            }
            default -> {

            }
        }


        // Boundaries adjustment - We can either make it deadly, or make the snake come out the other way.
        if ((bodyParts[0].getX() > WIDTH - borderForSnake * 2 && currentDirection == RIGHT)
                || (bodyParts[0].getX() < borderForSnake && currentDirection == LEFT)
                || (bodyParts[0].getY() > HEIGHT - borderForSnake * 2 && currentDirection == DOWN)
                || (bodyParts[0].getY() < PANEL_REALSTATE + borderForSnake * 2 - TILE_SIZE && currentDirection == UP)
        )
            new GameOver();


        // Ahmed Salem:
        // Check if food item overlaps poison item.
        // To debug this, set TILES_COUNT to 10, and play for sometime to test whether food will be on
        // poison or not.
        // Anton:
        while (true) {
            if (FOOD[foodType].getX() == POISON[poisonType].getX() &&
                    FOOD[foodType].getY() == POISON[poisonType].getY()) {
                FOOD[foodType].setImage(null);
                foodType = randInt(FOOD_COUNT);
                new FoodManager(PANE, foodType, FOOD, TILE_SIZE, PANEL_REALSTATE, TILE_COUNT);
                // System.out.println("Overlapping detected!");
            } else {
                // System.out.println("No overlapping.");
                break;
            }
        }



        // Food detection
        if (bodyParts[0].getX() == FOOD[foodType].getX() && bodyParts[0].getY() - TILE_SIZE == FOOD[foodType].getY()) {
            // Collision with FOOD detected
            // System.out.println("Food is eaten.");
            // playEatSound(); to be implemented.
            FOOD[foodType].setImage(null);

            soundsManager.playEatSound(randInt(2));
            //to invert back to normal
            if(invertedCounter>0){invertedCounter--;}

            // Anton:
            // Add snake body segment
            for(int i=0;i<2;i++) {
                snakeBodyPartsCount++;


                if (!poisoned) {
                    bodyParts[snakeBodyPartsCount] = new ImageView(
                            new Image(RuntimeOfSnake.class.getResource(
                                    "/images/snakeBodySegments.png").toExternalForm()));
                } else {
                    bodyParts[snakeBodyPartsCount] = new ImageView(
                            new Image(RuntimeOfSnake.class.getResource(
                                    "/images/snakeBodySegmentsGreen.png").toExternalForm()));
                }


                bodyParts[snakeBodyPartsCount].setFitHeight(TILE_SIZE);
                bodyParts[snakeBodyPartsCount].setFitWidth(TILE_SIZE);
                bodyParts[snakeBodyPartsCount].setX(-WIDTH);
                PANE.getChildren().add(bodyParts[snakeBodyPartsCount]);
                if(!poisonous){break;}
            }


            //Score = snakeBodyPartsCount - initialSnakeBodyPartsCount;
            if(!poisonous)
                Score++;
            else
                Score += 2;
            //System.out.println("/////////////score" + Score);
            score.setText("Score = " + Score);

            foodType = randInt(FOOD_COUNT);
            new FoodManager(PANE, foodType, FOOD, TILE_SIZE, PANEL_REALSTATE, TILE_COUNT);
        }

        //Peter: Poison Detection
        // -1 EXCEPTION!!!!
        if (bodyParts[0].getX() == POISON[poisonType].getX() && bodyParts[0].getY() - TILE_SIZE == POISON[poisonType].getY()){
            System.out.println("Poison is consumed.");
            POISON[poisonType].setImage(null);

            soundsManager.playPoisonSound();

            if(snakeBodyPartsCount > 3 && !poisonous){
                for(int i=0; i<3; i++){
                    bodyParts[snakeBodyPartsCount - i].setImage(null);
                }
                snakeBodyPartsCount -= 3;

            }
            else if(snakeBodyPartsCount > 3 && poisonous && poisonType !=2){
                for(int i=0; i<3; i++){
                    bodyParts[snakeBodyPartsCount - i].setImage(null);
                }
                snakeBodyPartsCount -= 3;

            }
            else if(poisonous && poisonType ==2){
                Score+=4;

                snakeBodyPartsCount++;
                bodyParts[snakeBodyPartsCount] = new ImageView(
                        new Image(RuntimeOfSnake.class.getResource("/images/snakeBodySegmentsGreen.png").toExternalForm()));


                bodyParts[snakeBodyPartsCount].setFitHeight(TILE_SIZE);
                bodyParts[snakeBodyPartsCount].setFitWidth(TILE_SIZE);
                bodyParts[snakeBodyPartsCount].setX(-WIDTH);
                PANE.getChildren().add(bodyParts[snakeBodyPartsCount]);
            }
            else{
                new GameOver();
            }







            Score-=3;
            score.setText("Score = " + Score);

            if(snakeBodyPartsCount < 1){
                new GameOver();
            }

            if(poisonType == 0){
                poisonCounter++;
            }

            if(poisonCounter > 2 && !poisoned && !poisonous){
                //change the snake body segments color
                poisoned = true;
            }

            if(poisoned && !poisonous){
                snake.removeSnake(PANE, bodyParts,snakeBodyPartsCount);
                snake.putSnake(PANE, snakeBodyPartsCount-3);
                snakeBodyPartsCount = initialSnakeBodyPartsCount;
                poisonous = true;
            }
            if(poisonType == 1){
                invertedCounter += 2;
            }

            poisonType = randInt(POISON_COUNT);
            //poisonType =0;
            //if(poisonous == true){poisonType =2;}
            new PoisonManager(POISON, poisonType, TILE_SIZE, TILE_COUNT, PANEL_REALSTATE, PANE);



        }


        ///////////////
        int count=0;
        for (int i=1;i<=snakeBodyPartsCount;i++){
            if (bodyParts[0].getX() == bodyParts[i].getX() && bodyParts[0].getY()  == bodyParts[i].getY()) {
                System.out.println("Snake eat itself");

                for (; i <=snakeBodyPartsCount; i++) {
                    bodyParts[i].setImage(null);
                    count++;
                }
                snakeBodyPartsCount-=count;
                Score -= count;
                score.setText("Score = " + Score);
            }
        }




        PANE.requestFocus();
    }


    public static int randInt(int max) {
        return new Random(System.currentTimeMillis()).nextInt(0, max);
    }


    public static void moveY(boolean up, int i) {
        if (up) {
            bodyParts[i].setY(bodyParts[i].getY() - snakeSpeedTilesPerIncrement);
            bodyParts[i].setRotate(0);
        } else {
            bodyParts[i].setY(bodyParts[i].getY() + snakeSpeedTilesPerIncrement);
            bodyParts[i].setRotate(180);
        }

    }

    public static void moveX(boolean right, int i) {
        if (right) {
            bodyParts[i].setX(bodyParts[i].getX() + snakeSpeedTilesPerIncrement);
            bodyParts[i].setRotate(90);
        } else {
            bodyParts[i].setX(bodyParts[i].getX() - snakeSpeedTilesPerIncrement);
            bodyParts[i].setRotate(-90);
        }
    }
}
