package com.example.snakeattempt;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
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
    private static final int foodCount = 5;
    private static final int poisonCount = 3;
    private static final ImageView[] food = new ImageView[foodCount];
    private static final ImageView[] poison = new ImageView[poisonCount];
    private static final String[] imagesDirectories = {"/images/apple.png",
            "/images/banana.png",
            "/images/peach.png",
            "/images/grapes.png",
            "/images/mushroom.png",
            "/images/grass1.jpg",
            "/images/fabric0.jpg",

            "/images/snakeHead.png",
            "",
            "",

            "/images/poison1.png",
            "/images/poison2.png",
            "/images/poison3.png",

            };

    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int RIGHT =2;
    private static final int LEFT = 3;
    public static ImageView snakeHead;
    public static final double snakeSpeedTilesPerIncrement =TILE_SIZE / 50.0 ;
    public static final int gameSpeed =5;
    private static int currentDirection = RIGHT;
    private static double snakeHeadX = TILE_SIZE * 5;
    private static double snakeHeadY = TILE_SIZE * 10;
    private static int foodType ;
    private static int poisonType ;
    private static int borderForSnake = 50;


    @Override
    public void start(Stage stage) throws IOException {
        StackPane mainPane = new StackPane(pane);

        mainScene = new Scene(mainPane, HEIGHT, WIDTH);
        stage.setResizable(false);
        stage.setScene(mainScene);

        setBackground();
        foodType = randInt(foodCount);
        poisonType = randInt(poisonCount);
        placeFood(foodType);
        placePoison(poisonType);
        createTiles(true);

        snakeHead();

        // Set up key press event handling
        mainScene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
                if (currentDirection != DOWN)
                    currentDirection = UP;
            } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {
                if (currentDirection != UP)
                    currentDirection = DOWN;
            } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
                if (currentDirection != LEFT)
                    currentDirection = RIGHT;
            } else if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
                if (currentDirection != RIGHT)
                    currentDirection = LEFT;
            }
        });

        stage.setTitle("Hello!");
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(gameSpeed), e -> runSnake()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
        snakeHead = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource(imagesDirectories[7])).toExternalForm()
        ));

        snakeHead.setX(snakeHeadX);
        snakeHead.setY(snakeHeadY);



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
                );

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(food[foodType]);
        translateTransition.setToY( TILE_SIZE / 1.0 );
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();




        pane.getChildren().add(food[foodType]);



    }

    // poisonType indices are from 0 to 2
    // images for poison indices are from 10 to 12
    public void placePoison(int poisonType){
        // init image of poison
        Image poisonImage = new Image(Objects.requireNonNull(
                getClass().getResource(imagesDirectories[poisonType + 10])).toExternalForm());
        poison[poisonType] = new ImageView(poisonImage);

        // fitting poison
        poison[poisonType].setFitHeight(TILE_SIZE );
        poison[poisonType].setFitWidth(TILE_SIZE );
        poison[poisonType].setX(new Random().nextInt(0, TILE_COUNT ) * TILE_SIZE);
        poison[poisonType].setY(
                (new Random().nextInt(PANEL_REALSTATE / TILE_SIZE, TILE_COUNT ) * TILE_SIZE)
                        - (double)TILE_SIZE / 1.1);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(poison[poisonType]);
        translateTransition.setToY((double) TILE_SIZE / 1.1);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();




        pane.getChildren().add(poison[poisonType]);






    }



    public void runSnake() {
        // Update snake direction
        switch (currentDirection) {
            case UP -> {
                snakeHead.setRotate(0);
                snakeHeadY -= snakeSpeedTilesPerIncrement;
            }
            case DOWN -> {
                snakeHead.setRotate(180);
                snakeHeadY += snakeSpeedTilesPerIncrement;
            }
            case RIGHT -> {
                snakeHead.setRotate(90);
                snakeHeadX += snakeSpeedTilesPerIncrement;
            }
            case LEFT -> {
                snakeHead.setRotate(-90);
                snakeHeadX -= snakeSpeedTilesPerIncrement;

            }
            default -> {

            }
        }

        // Update snake head position
        snakeHead.setX(snakeHeadX);
        snakeHead.setY(snakeHeadY);

        // Boundaries adjustment - We can either make it deadly, or make the snake come out the other way.
        if(snakeHeadX > WIDTH - borderForSnake && currentDirection == RIGHT)
            snakeHeadX = -TILE_SIZE;
        if(snakeHeadX < borderForSnake/2.0 && currentDirection == LEFT)
            snakeHeadX = WIDTH;
        if(snakeHeadY > HEIGHT - borderForSnake && currentDirection == DOWN)
            snakeHeadY = -TILE_SIZE;
        if(snakeHeadY < borderForSnake/2.0 && currentDirection == UP)
            snakeHeadY = HEIGHT;

        System.out.println("snakeX: " + snakeHeadX);
        System.out.println("snakeY: " + snakeHeadY);
        System.out.println();
        System.out.println("foodX: "+ food[foodType].getX());
        System.out.println("foodY: "+ food[foodType].getY());

        if (Math.abs(snakeHeadX - food[foodType].getX()) <= TILE_SIZE/1.5 &&
                Math.abs(snakeHeadY - food[foodType].getY()) <= TILE_SIZE/1.5) {
            // Collision with food detected
            food[foodType].setImage(null);
            foodType = randInt(foodCount);
            System.out.println(imagesDirectories[foodType]);
            placeFood(foodType);



//        if((Math.abs(snakeHeadX )-Math.abs( food[foodType].getX()) < Math.abs(TILE_SIZE)) &&
//                ( Math.abs( snakeHeadY )-Math.abs( food[foodType].getY()) < Math.abs(TILE_SIZE))){
//            food[foodType].setImage(null);
//            foodType = randInt(foodCount);
//            System.out.println(imagesDirectories[foodType]);
//            placeFood(foodType);
//            boolean isDup=true;
//            while(isDup){
//                placeFood(foodType);
//                isDup = (Math.abs( food[foodType].getX() ) - Math.abs( poison[poisonType].getX()) < Math.abs(TILE_SIZE))&&
//                        (Math.abs( food[foodType].getY() ) - Math.abs( poison[poisonType].getY()) < Math.abs(TILE_SIZE));
//            }
        }

        pane.requestFocus();
    }
    public int randInt(int max){
        return new Random().nextInt(0, max);
    }

}