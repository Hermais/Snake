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
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Snake extends Application {
    public static final int GAME_THEME = 1;
    private static final int HEIGHT = 400;
    private static final int WIDTH = HEIGHT;// Height MUST EQUAL Width.

    private static final int TILE_COUNT = 20; // 20 But 16 is recommended.
    private static final int TILE_SIZE = HEIGHT / TILE_COUNT;
    private static final int PANEL_REALSTATE = TILE_SIZE * 2;
    private static final Pane PANE = new Pane();
    private static Scene mainScene;


    private static ImageView mainBackground;
    private static ImageView upperPanel;
    private static final int FOOD_COUNT = 5;
    private static final int POISON_COUNT = 3;
    private static final ImageView[] FOOD = new ImageView[FOOD_COUNT];
    private static final ImageView[] POISON = new ImageView[POISON_COUNT];
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

            "/images/appleG.png",
            "/images/bananaG.png",
            "/images/peachG.png",
            "/images/grapesG.png",
            "/images/mushroomG.png",

            "/images/poison1G.png",
            "/images/poison2G.png",
            "/images/poison3G.png"
    };

    public static final double snakeSpeedTilesPerIncrement = TILE_SIZE;
    public static final int GAME_SPEED = 128;// Actually lower values give higher speeds.
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    public static ImageView snakeHead;
    private static int currentDirection = RIGHT;
    private static double snakeHeadX = TILE_SIZE * (TILE_COUNT / 2);
    private static double snakeHeadY = TILE_SIZE * (TILE_COUNT / 2);
    private static int foodType;
    private static int poisonType;
    private static int borderForSnake = 50;


    @Override
    public void start(Stage stage) throws IOException {
        StackPane mainPane = new StackPane(PANE);

        mainScene = new Scene(mainPane, HEIGHT, WIDTH);
        stage.setResizable(false);
        stage.setScene(mainScene);

        setBackground();
        foodType = randInt(FOOD_COUNT);
        poisonType = randInt(POISON_COUNT);
        placePoison(poisonType);
        placeFood(foodType);
        createTiles(false);

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

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(GAME_SPEED), e -> runSnake()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }

    //This function is only for visualization. For now.
    public void createTiles(boolean c) {
        if (c) {
            for (int i = 0; i < HEIGHT / TILE_SIZE; i++) {
                Line lY = new Line(i * TILE_SIZE, 0, i * TILE_SIZE, HEIGHT);
                Line lX = new Line(0, i * TILE_SIZE, WIDTH, i * TILE_SIZE);
                PANE.getChildren().add(lY);
                PANE.getChildren().add(lX);
            }
        }


    }

    public void setBackground() {
        if (GAME_THEME == 0) {
            //Grass
            Image grassImage = new Image(Objects.requireNonNull(getClass().getResource(imagesDirectories[5])).toExternalForm());
            mainBackground = new ImageView(grassImage);
            mainBackground.setFitHeight(HEIGHT);
            mainBackground.setFitWidth(WIDTH);
            PANE.getChildren().add(mainBackground);


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
            PANE.getChildren().add(upperPanel);
        } else if (GAME_THEME == 1) {
            Rectangle rec = new Rectangle();
            rec.setFill(Color.web("#568203"));
            rec.setHeight(HEIGHT);
            rec.setWidth(WIDTH);
            PANE.getChildren().add(rec);
            // Flat Grass
            for (int i = 0; i <= TILE_COUNT; i++) {
                for (int j = 0; j <= TILE_COUNT; j++) {

                    ImageView imageView = new ImageView(new Image(Objects.requireNonNull(
                            getClass().getResource((i + j) % 2 == 0 ? "/images/grassTile1.png" : "/images/grassTile2.png")).toExternalForm()));
                    imageView.setX(TILE_SIZE * i);
                    imageView.setY(TILE_SIZE * j);
                    imageView.setFitWidth(TILE_SIZE);
                    imageView.setFitHeight(TILE_SIZE);
                    PANE.getChildren().add(imageView);
                }

            }
        }


    }

    // ################################################

    public void snakeHead() {
        snakeHead = new ImageView(new Image(
                Objects.requireNonNull(getClass().getResource(
                        GAME_THEME == 0 ? imagesDirectories[7] : "/images/snakeHeadG.png")).toExternalForm()
        ));

        snakeHead.setX(snakeHeadX);
        snakeHead.setY(snakeHeadY);


        snakeHead.setFitHeight(TILE_SIZE);
        snakeHead.setFitWidth(TILE_SIZE);
        PANE.getChildren().add(snakeHead);
    }


    public void placeFood(int foodType) {
        //FOOD
        Image foodImage = new Image(Objects.requireNonNull(getClass().getResource(
                GAME_THEME == 0 ? imagesDirectories[foodType] : imagesDirectories[foodType + 13])).toExternalForm());
        FOOD[foodType] = new ImageView(foodImage);

        //FOOD adjusting
        FOOD[foodType].setFitHeight(TILE_SIZE);
        FOOD[foodType].setFitWidth(TILE_SIZE);
        FOOD[foodType].setX(new Random().nextInt(1, TILE_COUNT - 1) * TILE_SIZE);
        FOOD[foodType].setY(
                (new Random().nextInt(PANEL_REALSTATE / TILE_SIZE, TILE_COUNT - 2) * TILE_SIZE)
        );

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(FOOD[foodType]);
        translateTransition.setToY(TILE_SIZE / 1.0);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();

        checkFood_PoisonOverlapping();


        PANE.getChildren().add(FOOD[foodType]);


    }

    // poisonType indices are from 0 to 2
    // images for POISON indices are from 10 to 12
    public void placePoison(int poisonType) {
        // init image of POISON
        Image poisonImage = new Image(Objects.requireNonNull(
                getClass().getResource(GAME_THEME == 0 ? imagesDirectories[poisonType + 10] : imagesDirectories[poisonType
                        + 18])).toExternalForm());
        POISON[poisonType] = new ImageView(poisonImage);

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(POISON[poisonType]);
        translateTransition.setToY( TILE_SIZE / 1.0);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();
        // fitting POISON
        POISON[poisonType].setFitHeight(TILE_SIZE);
        POISON[poisonType].setFitWidth(TILE_SIZE);
        POISON[poisonType].setX(new Random().nextInt(1, TILE_COUNT - 1) * TILE_SIZE);
        POISON[poisonType].setY(
                (new Random().nextInt(PANEL_REALSTATE / TILE_SIZE, TILE_COUNT - 2) * TILE_SIZE)
                        - (double) TILE_SIZE / 1.1);



        PANE.getChildren().add(POISON[poisonType]);


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
        if (snakeHeadX > WIDTH - borderForSnake && currentDirection == RIGHT)
            snakeHeadX = -TILE_SIZE;
        if (snakeHeadX < borderForSnake / 2.0 && currentDirection == LEFT)
            snakeHeadX = WIDTH;
        if (snakeHeadY > HEIGHT - borderForSnake && currentDirection == DOWN)
            snakeHeadY = -TILE_SIZE;
        if (snakeHeadY < borderForSnake / 2.0 && currentDirection == UP)
            snakeHeadY = HEIGHT;

        System.out.println("snakeX: " + snakeHeadX);
        System.out.println("snakeY: " + snakeHeadY);
        System.out.println();
        System.out.println("foodX: " + FOOD[foodType].getX());
        System.out.println("foodY: " + FOOD[foodType].getY());

        if (snakeHeadX == FOOD[foodType].getX() && snakeHeadY - TILE_SIZE == FOOD[foodType].getY()) {
            // Collision with FOOD detected
            System.out.println("Food is eaten.");
            FOOD[foodType].setImage(null);
            foodType = randInt(FOOD_COUNT);
            System.out.println(imagesDirectories[foodType]);
            placeFood(foodType);


        }

        PANE.requestFocus();
    }

    public int randInt(int max) {
        return new Random().nextInt(0, max);
    }

    public void checkFood_PoisonOverlapping() {
        while (true) {
            if (FOOD[foodType].getX() == POISON[poisonType].getX() &&
                    FOOD[foodType].getY() == POISON[poisonType].getY()) {
                FOOD[foodType].setImage(null);
                foodType = randInt(FOOD_COUNT);
                placeFood(foodType);
            } else {
                System.out.println("Overlapping Fixed!");
                break;
            }
        }
    }

}