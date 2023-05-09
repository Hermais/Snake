package com.example.snakeattempt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.Timer;

public class HelloApplication extends Application {
    private static final int HEIGHT = 800;
    private static final int WIDTH = HEIGHT;// Height MUST EQUAL Width.

    private static final int TILE_COUNT =16; // 20 But 16 is recommended.
    private static final int TILE_SIZE = HEIGHT / TILE_COUNT;
    private static final int PANEL_REALSTATE = TILE_SIZE * 2;
    private static final Pane pane = new Pane();


    private static  ImageView mainBackground;
    private static ImageView upperPanel;
    private static ImageView[] food = new ImageView[5];
    private static String[] imagesDirectories = {"/images/apple.png",
            "/images/banana.png",
            "/images/peach.png",
            "/images/grapes.png",
            "/images/mushroom.png"
            };





    @Override
    public void start(Stage stage) throws IOException {
        StackPane mainPane = new StackPane(pane);
        // mainPane.setPrefHeight(HEIGHT);
        // mainPane.setPrefWidth(WIDTH);


        setBackground();
        placeFood(new Random().nextInt(0, 5));
        createTiles(false);

        Scene mainScene = new Scene(mainPane,  HEIGHT,WIDTH );
        stage.setResizable(false);
        stage.setScene(mainScene);
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
        Image grassImage = new Image("file:D:\\Java\\SnakeAttempt\\src\\images\\grass1.jpg");
        mainBackground = new ImageView(grassImage);
        mainBackground.setFitHeight(HEIGHT);
        mainBackground.setFitWidth(WIDTH);
        pane.getChildren().add(mainBackground);


        //Status Panel
        Image fabricImage = new Image("file:D:\\Java\\SnakeAttempt\\src\\images\\fabric0.jpg");
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

    public void snakeStatus(){

    }

    public void placeFood(int foodType){
        //apple
        Image appleImage = new Image(getClass().getResource(imagesDirectories[0]).toExternalForm());
        food[0] = new ImageView(appleImage);

        //banana
        Image bananaImage = new Image(getClass().getResource(imagesDirectories[1]).toExternalForm());
        food[1] = new ImageView(bananaImage);

        //peach
        Image peachImage = new Image(getClass().getResource(imagesDirectories[2]).toExternalForm());
        food[2] = new ImageView(peachImage);

        //grapes
        Image grapesImage = new Image(getClass().getResource(imagesDirectories[3]).toExternalForm());
        food[3] = new ImageView(grapesImage);

        //mushroom
        Image mushroomImage = new Image(getClass().getResource(imagesDirectories[4]).toExternalForm());
        food[4] = new ImageView(mushroomImage);







        food[foodType].setFitHeight(TILE_SIZE);
        food[foodType].setFitWidth(TILE_SIZE);
        food[foodType].setX(new Random().nextInt(0, TILE_COUNT ) * TILE_SIZE);
        food[foodType].setY(new Random().nextInt((int)(Math.ceil(PANEL_REALSTATE) / TILE_SIZE), TILE_COUNT ) * TILE_SIZE);
        pane.getChildren().add(food[foodType]);



    }

    public void initializeImages(){

    }
}