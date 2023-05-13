package com.example.snakeattempt;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventType;
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

// OBJECTIVE: Clean the code by making multiple classes.

public class Snake extends Application {
    public static final int GAME_THEME = 1;
    private static final int HEIGHT = 800;
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
            "/images/poison3G.png",

            "images/snakeBodySegments.png"
    };

    private static final String[] SOUND_DIRECTORIES = {
            "/sounds/eat1.mp3",
            "/sounds/eat2.mp3",
            "/sounds/eat3.mp3"

    };
    private static final Duration SOUND_DURATION = Duration.seconds(1); // Adjust the duration as needed


    private static final double snakeSpeedTilesPerIncrement = TILE_SIZE;
    private static final int GAME_SPEED = 128;// Actually lower values give higher speeds.
    private static final int borderForSnake = TILE_SIZE;
    private static final int UP = 0;
    private static final int DOWN = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;

    // public static ImageView snakeHead;
    private static int currentDirection = UP;
    private static double initialSnakeHeadX = TILE_SIZE * (TILE_COUNT / 2);
    private static double initialSnakeHeadY = TILE_SIZE * (TILE_COUNT / 2);
    private static int foodType;
    private static int poisonType;
    private  int snakeBodyPartsCount = 3;
    private static ImageView[] bodyParts = new ImageView[TILE_COUNT * TILE_COUNT];
    private static ImageView[] fence = new ImageView[TILE_COUNT];

    private static int testChange = 0;
    public int Score=0;

    // Notes: Overlapping method does not work.

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
        drawFence();
        createTiles(false);

        initSnakeBody();

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

        stage.setTitle("Snake");
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

        //Status Panel
        Image fabricImage = new Image(Objects.requireNonNull(getClass().getResource(
                GAME_THEME == 0 ? imagesDirectories[6] : "/images/panelG.png")).toExternalForm());
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


    }

    public void drawFence() {
        for (int i = PANEL_REALSTATE / TILE_SIZE; i < TILE_COUNT; i++) {
            // Draw the upper fence
            if (i == PANEL_REALSTATE / TILE_SIZE) {
                for (int k = 0; k < TILE_COUNT; k++) {
                    fence[k] = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(
                            "/images/fenceX.png")).toExternalForm()));
                    fence[k].setFitHeight(TILE_SIZE);
                    fence[k].setFitWidth(TILE_SIZE);
                    fence[k].setX(TILE_SIZE * k);
                    fence[k].setY(PANEL_REALSTATE);
                    PANE.getChildren().add(fence[k]);

                }

            }
            // Draw the lower fence
            if (i == TILE_COUNT - 1) {
                for (int k = 0; k < TILE_COUNT; k++) {
                    fence[k] = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(
                            "/images/fenceX.png")).toExternalForm()));
                    fence[k].setFitHeight(TILE_SIZE);
                    fence[k].setFitWidth(TILE_SIZE);
                    fence[k].setX(TILE_SIZE * k);
                    fence[k].setY(HEIGHT - TILE_SIZE);
                    PANE.getChildren().add(fence[k]);


                }

            }

            // Peter: Complete the fence.

        }

    }

    public void initSnakeBody() {
        double temp = TILE_SIZE;


        // If we failed to make the code work as a snake with a head, we will remove the head and
        // replace it with body.
        for (int i = 0; i <= snakeBodyPartsCount; i++) {
            bodyParts[i] = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(
                    i == 0 ? "/images/snakeHeadG.png" : "/images/snakeBodySegments.png")).toExternalForm())
            );
            bodyParts[i].setFitHeight(TILE_SIZE);
            bodyParts[i].setFitWidth(TILE_SIZE);
            bodyParts[i].setX(initialSnakeHeadX);
            bodyParts[i].setY(initialSnakeHeadY + temp);
            PANE.getChildren().add(bodyParts[i]);
            temp += TILE_SIZE;
        }
    }


    public void placeFood(int foodType) {
        //FOOD
        Image foodImage = new Image(Objects.requireNonNull(getClass().getResource(
                GAME_THEME == 0 ? imagesDirectories[foodType] : imagesDirectories[foodType + 13])).toExternalForm());
        FOOD[foodType] = new ImageView(foodImage);

        //FOOD adjusting
        FOOD[foodType].setFitHeight(TILE_SIZE);
        FOOD[foodType].setFitWidth(TILE_SIZE);
        FOOD[foodType].setX(new Random(System.currentTimeMillis()).nextInt(1, TILE_COUNT - 1) * TILE_SIZE);
        FOOD[foodType].setY(
                (new Random(System.currentTimeMillis()).nextInt(PANEL_REALSTATE / TILE_SIZE, TILE_COUNT - 2) * TILE_SIZE)
        );

        // Just an animation
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(FOOD[foodType]);
        translateTransition.setToY(TILE_SIZE / 1.0);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();


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
        translateTransition.setToY(TILE_SIZE / 1.0);
        translateTransition.setDuration(Duration.millis(300));
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.play();
        // fitting POISON
        POISON[poisonType].setFitHeight(TILE_SIZE);
        POISON[poisonType].setFitWidth(TILE_SIZE);
        POISON[poisonType].setX(new Random(System.currentTimeMillis()).nextInt(1, TILE_COUNT - 1) * TILE_SIZE);
        POISON[poisonType].setY(
                (new Random(System.currentTimeMillis()).nextInt(PANEL_REALSTATE / TILE_SIZE, TILE_COUNT - 2) * TILE_SIZE)
                        - (double) TILE_SIZE / 1.1);


        PANE.getChildren().add(POISON[poisonType]);


    }


    public void runSnake() {


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
                    if (i !=0) {
                        bodyParts[i].setX(bodyParts[i - 1].getX());
                        bodyParts[i].setY(bodyParts[i - 1].getY());
                    }
                }
            }
            case RIGHT -> {
                for (int i = snakeBodyPartsCount; i >= 0; i--) {
                    moveX(true, i);
                    if (i !=0) {
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
        if ((bodyParts[0].getX() > WIDTH-borderForSnake*2 && currentDirection == RIGHT)
                || (bodyParts[0].getX() < borderForSnake && currentDirection == LEFT)
                || (bodyParts[0].getY() > HEIGHT - borderForSnake*2 && currentDirection == DOWN)
                || (bodyParts[0].getY() < PANEL_REALSTATE+borderForSnake*2 - TILE_SIZE && currentDirection == UP)
        )
            gameOver();


        // Ahmed Salem:
        // Check if food item overlaps poison item.
        // To debug this, set TILES_COUNT to 10, and play for sometime to test whether food will be on
        // poison or not.
        while (true) {
            if (FOOD[foodType].getX() == POISON[poisonType].getX() &&
                    FOOD[foodType].getY() == POISON[poisonType].getY()) {
                FOOD[foodType].setImage(null);
                foodType = randInt(FOOD_COUNT);
                placeFood(foodType);
                System.out.println("Overlapping detected!");
            } else {
                System.out.println("No overlapping.");
                break;
            }
        }

        System.out.println("snakeX: " + initialSnakeHeadX);
        System.out.println("snakeY: " + initialSnakeHeadY);
        System.out.println();
        System.out.println("foodX: " + FOOD[foodType].getX());
        System.out.println("foodY: " + FOOD[foodType].getY());

        // Food detection
        if (bodyParts[0].getX() == FOOD[foodType].getX() && bodyParts[0].getY() - TILE_SIZE == FOOD[foodType].getY()) {
            // Collision with FOOD detected
            System.out.println("Food is eaten.");
            // playEatSound(); to be implemented.
            FOOD[foodType].setImage(null);

            // Anton:
            // Add snake body segment
bodyParts[snakeBodyPartsCount+1]= new ImageView(new Image(Objects.requireNonNull(getClass().getResource( "/images/snakeBodySegments.png")).toExternalForm()));
            bodyParts[snakeBodyPartsCount+1].setFitHeight(TILE_SIZE);
            bodyParts[snakeBodyPartsCount+1].setFitWidth(TILE_SIZE);
            PANE.getChildren().add(bodyParts[snakeBodyPartsCount+1]);
            snakeBodyPartsCount++;
            Score=snakeBodyPartsCount-3;
            System.out.println("///////////////////Score"+Score);

            foodType = randInt(FOOD_COUNT);
            System.out.println(imagesDirectories[foodType]);
            placeFood(foodType);

        }

        // Mohamed: Poison Detection


        PANE.requestFocus();
    }

    public int randInt(int max) {
        return new Random(System.currentTimeMillis()).nextInt(0, max);
    }


    public void moveY(boolean up, int i) {
        if (up) {
            bodyParts[i].setY(bodyParts[i].getY() - snakeSpeedTilesPerIncrement);
            bodyParts[i].setRotate(0);
        } else {
            bodyParts[i].setY(bodyParts[i].getY() + snakeSpeedTilesPerIncrement);
            bodyParts[i].setRotate(180);
        }

    }

    public void moveX(boolean right, int i) {
        if (right) {
            bodyParts[i].setX(bodyParts[i].getX() + snakeSpeedTilesPerIncrement);
            bodyParts[i].setRotate(90);
        } else {
            bodyParts[i].setX(bodyParts[i].getX() - snakeSpeedTilesPerIncrement);
            bodyParts[i].setRotate(-90);
        }
    }


    public void gameOver() {
        // Peter:
        System.exit(0);
    }

}