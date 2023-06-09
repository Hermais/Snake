package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Arrays;

import static com.example.snakeattempt.SnakeEngine.poisoned;


public class Snake {


        private double temp ;
        private int snakeInitBodySegments;

        private ImageView[] Snake;

        private double snakeSegSizeXY;

        private double initHeadX;
        private double initHeadY;


    public double getSnakeSegSizeXY() {
        return snakeSegSizeXY;
    }

    public void setSnakeSegSizeXY(double snakeSegSizeXY) {
        this.snakeSegSizeXY = snakeSegSizeXY;
    }

    public int getSnakeInitBodySegments() {
        return snakeInitBodySegments;
    }

    public void setSnakeInitBodySegments(int snakeInitBodySegments) {
        this.snakeInitBodySegments = snakeInitBodySegments;
    }

    public ImageView[] getSnake() {
        return Snake;
    }

    public void setSnake(ImageView[] snake) {
        Snake = snake;
    }

    Snake(){

        }

    public Snake(int snakeInitBodySegments, ImageView[] snake, double snakeSegSizeXY, double initHeadX, double initHeadY) {
        Snake = snake;
        this.snakeInitBodySegments = snakeInitBodySegments;
        this.snakeSegSizeXY = snakeSegSizeXY;
        this.initHeadX = initHeadX;
        this.initHeadY = initHeadY;


    }


    public void putSnake(Pane pane, int initialSnakeBodyPartsCount){
        temp = snakeSegSizeXY;


            for(int i = 0; i <= snakeInitBodySegments; i++) {
                if (poisoned==false) {
                    Snake[i] = new ImageView(new Image(getClass().getResource(
                            i == 0 ? "/images/snakeHeadG.png" : "/images/snakeBodySegments.png").toExternalForm())
                    );
                }
                else if(poisoned ==true){
                    Snake[i] = new ImageView(new Image(getClass().getResource(
                            i == 0 ? "/images/snakeHeadGreenG.png" : "/images/snakeBodySegmentsGreen.png").toExternalForm())
                    );
                }
                    Snake[i].setFitHeight(snakeSegSizeXY);
                    Snake[i].setFitWidth(snakeSegSizeXY);
                    Snake[i].setX(initHeadX);
                    Snake[i].setY(initHeadX + temp);

                    temp += snakeSegSizeXY;
                    pane.getChildren().add(Snake[i]);


            }


        }

        public void removeSnake(Pane pane, ImageView[] removerVar, int snakeBodyPartsCount){
            for(int i = 0; i <= snakeBodyPartsCount; i++) {

                pane.getChildren().remove(removerVar[i]);

            }


        }




    }

