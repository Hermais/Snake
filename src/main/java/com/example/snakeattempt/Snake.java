package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Arrays;


public class Snake {


        private double temp ;
        private int snakeInitBodySegments;

        private ImageView[] Snake;

        private double snakeSegSizeXY;

        private double initHeadX;
        private double initHeadY;

        public boolean putSnake;

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

    public void putSnake(Pane pane, int snakeInitBodySegments){
        temp = snakeSegSizeXY;

            for(int i = 0; i <= snakeInitBodySegments; i++) {
                Snake[i] = new ImageView(new Image(getClass().getResource(
                        i == 0 ? "/images/snakeHeadG.png" : "/images/snakeBodySegments.png").toExternalForm())
                );
                Snake[i].setFitHeight(snakeSegSizeXY);
                Snake[i].setFitWidth(snakeSegSizeXY);
                Snake[i].setX(initHeadX);
                Snake[i].setY(initHeadX + temp);

                temp += snakeSegSizeXY;
                pane.getChildren().add(Snake[i]);

            }


        }

        public void removeSnake(Pane pane, ImageView[] removerVar, int bodyPartsNow){
            for(int i = 0; i <= bodyPartsNow; i++) {

                removerVar[i].setImage(null);
                removerVar[i].setX(0);
                removerVar[i].setY(0);

            }


        }




    }

