package com.example.snakeattempt;

import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Snake extends Application {
    private static final int HEIGHT = 800;
    private static final int WIDTH = HEIGHT;// Height MUST EQUAL Width.

    private static final int TILE_COUNT =16; // 20 But 16 is recommended.
    private static final int TILE_SIZE = HEIGHT / TILE_COUNT;
    private static final int PANEL_REALSTATE = TILE_SIZE * 2;
    private static final Pane pane = new Pane();
    private static Scene mainScene;


    private static  ImageView mainBackground;
    private static ImageView upperPanel;
    private static final ImageView[] food = new ImageView[5];
    private static final String[] imagesDirectories = {"/images/apple.png",
            "/images/banana.png",
            "/images/peach.png",
            "/images/grapes.png",
            "/images/mushroom.png",
            "/images/grass1.jpg",
            "/images/fabric0.jpg",

            "/images/snakeHead.png"
            };

    private static final double snakeSpeed = 6;
    private static KeyCode currentDirection = KeyCode.RIGHT;
    private static int snakeHeadX = TILE_SIZE * 5;
    private static int snakeHeadY = TILE_SIZE * 10;


    @Override
    public void start(Stage stage) throws IOException {
        StackPane mainPane = new StackPane(pane);

        mainScene = new Scene(mainPane,  HEIGHT,WIDTH );
        stage.setResizable(false);
        stage.setScene(mainScene);

        setBackground();
        placeFood(new Random().nextInt(0, 5));
        createTiles(true);

        snakeHead();





        stage.setTitle("Hello!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    //This function is only for visualization. For now.
    public void createTiles(boolean c){
        if(c){
            for(int i = 0; i < HEIGHT/TILE_SIZE; i++){
                Line lY = new Line(i * TILE_SIZE, 0, i * TILE_SIZE, HEIGHT);
                Line lX = new Line(0,i * TILE_SIZE , WIDTH,i * TILE_SIZE);
                pane.getChildren().add(lY);
                pane.getChildren().add(lX);
            }
        }

        
    }

    public void setBackground(){
        Image grassImage = new Image(Objects.requireNonNull(getClass().getResource(imagesDirectories[5])).toExternalForm());
        mainBackground = new ImageView(grassImage);
        mainBackground.setFitHeight(HEIGHT);
        mainBackground.setFitWidth(WIDTH);
        pane.getChildren().add(mainBackground);


        //Status Panel
        Image fabricImage = new Image(Objects.requireNonNull(getClass().getResource(imagesDirectories[6])).toExternalForm());
        Rectangle mask = new Rectangle(HEIGHT, WIDTH);
        mask.setArcHeight(TILE_SIZE);
        mask.setArcWidth(TILE_SIZE);
        upperPanel = new ImageView(fabricImage);
        upperPanel.setFitHeight(HEIGHT);
        upperPanel.setFitWidth(WIDTH);
        upperPanel.setY(PANEL_REALSTATE - HEIGHT);
        mask.setY(upperPanel.getY());
        upperPanel.setClip(mask);
        pane.getChildren().add(upperPanel);



    }

    // ################################################

    public void snakeHead() {
        ImageView snakeHead = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource(imagesDirectories[7])).toExternalForm()
        ));

        snakeHead.setX(snakeHeadX);
        snakeHead.setY(snakeHeadY);

        // init ?????????????????????
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(snakeHead);
        translateTransition.setToY(50);
        translateTransition.setDuration(Duration.millis(1000));
        translateTransition.setCycleCount(Timeline.INDEFINITE);
        translateTransition.setAutoReverse(false);
        translateTransition.play();




        snakeHead.setFitHeight(TILE_SIZE);
        snakeHead.setFitWidth(TILE_SIZE);
        pane.getChildren().add(snakeHead);
    }


    public void placeFood(int foodType){
        //food
        Image foodImage = new Image(Objects.requireNonNull(getClass().getResource(imagesDirectories[foodType])).toExternalForm());
        food[foodType] = new ImageView(foodImage);

        //food adjusting
        food[foodType].setFitHeight(TILE_SIZE);
        food[foodType].setFitWidth(TILE_SIZE);
        food[foodType].setX(new Random().nextInt(0, TILE_COUNT ) * TILE_SIZE);
        food[foodType].setY(
                (new Random().nextInt(PANEL_REALSTATE / TILE_SIZE, TILE_COUNT ) * TILE_SIZE)
                - (double)TILE_SIZE / 1.1);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(food[foodType]);
        translateTransition.setToY((double) TILE_SIZE / 1.1);
        translateTransition.setDuration(Duration.millis(700));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();




        pane.getChildren().add(food[foodType]);



    }


}