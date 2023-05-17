package com.example.snakeattempt;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

// OBJECTIVE: Clean the code by making multiple classes.

public class SnakeEngine extends Application {
    public static final int HEIGHT = 800;
    public static final int WIDTH = HEIGHT;// Height MUST EQUAL Width.

    public static final int TILE_COUNT = 20; // 20 But 16 is recommended.
    public static final int TILE_SIZE = HEIGHT / TILE_COUNT;
    public static final int PANEL_REALSTATE = TILE_SIZE * 2;
    public static final Pane PANE = new Pane();
    public static final Pane PANE_2 = new Pane(PANE);


    public static Scene mainScene;


    public static final int FOOD_COUNT = 5;
    public static final int POISON_COUNT = 3;
    public static final ImageView[] FOOD = new ImageView[FOOD_COUNT];
    public static final ImageView[] POISON = new ImageView[POISON_COUNT];
    public static final String[] imagesDirectories = {"",
            "",
            "",
            "",
            "",
            "",
            "",

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

            "",
            "",
            "",

            "images/snakeBodySegments.png"
    };

    public static final String[] SOUND_DIRECTORIES = {
            "/sounds/eat1.mp3",
            "/sounds/eat2.mp3",
            "/sounds/eat3.mp3"

    };
    public static final Duration SOUND_DURATION = Duration.seconds(1); // Adjust the duration as needed


    public static final double snakeSpeedTilesPerIncrement = TILE_SIZE;
    public static final int GAME_SPEED = 120;// Actually lower values give higher speeds.
    public static final int borderForSnake = TILE_SIZE;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = 3;

    // public static ImageView snakeHead;
    public static int currentDirection = UP;
    public static final double initialSnakeHeadX = TILE_SIZE * (TILE_COUNT / 2.0);
    public static final double initialSnakeHeadY = TILE_SIZE * (TILE_COUNT / 2.0);
    public static int foodType;
    public static int poisonType;
    public static int snakeBodyPartsCount = 3;
    public static final ImageView[] bodyParts = new ImageView[TILE_COUNT * TILE_COUNT];
    public static final ImageView[] fence = new ImageView[TILE_COUNT];
//    public static ImageView mainMenuPanel;
//    public static ImageView mainMenuLogo;
//    public static ImageView gameOverPanel;
//    public static ImageView restart;

    public static final double menuSizeX = TILE_SIZE * 16;
    public static final double menuSizeY = TILE_SIZE * 8;
    public static final double logoSizeX = TILE_SIZE * 16;
    public static final double logoSizeY = TILE_SIZE * 6;

    public static final double FADE_DURATION = 25;
    public static final double menuBtnFitSize = 3 * TILE_SIZE;
    public static int blurValue = 20;
    public static Timeline timeline;
    public static Stage mainStage;
    public MainMenu mainMenu = new MainMenu( HEIGHT, WIDTH, menuSizeX,
            menuSizeY, PANEL_REALSTATE, logoSizeX, logoSizeY, PANE_2);

    public Snake snake = new Snake(snakeBodyPartsCount, bodyParts, TILE_SIZE, initialSnakeHeadX, initialSnakeHeadY);










    public int Score = 0;
    // Notes: Overlapping method does not work.

    @Override
    public void start(Stage stage) {
        SnakeEngine.mainStage = stage;
        initStart();

        mainStage.setResizable(false);
        mainStage.setScene(mainScene);
        mainStage.setTitle("Go: Snake!");
        mainStage.getIcons().add(new Image(getClass().getResource("/images/stageIcon.png").toExternalForm()));
        mainStage.show();


        mainMenu.getExitBtn().setOnMouseClicked(exit -> {
            mainMenu.getExitBtn().wobbleAnimation();
            PauseTransition delay = new PauseTransition(Duration.seconds(0.3));


            delay.setOnFinished(finished -> System.exit(0));

            delay.play();
        });



        mainMenu.getStartBtn().setOnMouseClicked(event -> {
            int temp = blurValue;
            Timeline fadingTimeline = new Timeline(new KeyFrame(Duration.millis(FADE_DURATION), e -> {
                mainMenu.getStartBtn().wobbleAnimation();
                PANE.setEffect(new GaussianBlur(blurValue--));
                mainMenu.getMainPanel().setOpacity(((double) (blurValue) / temp));
                mainMenu.getLogo().setOpacity(((double) (blurValue) / temp));
                mainMenu.getStartBtn().setOpacity(((double) (blurValue) / temp));
                mainMenu.getOptionBtn().setOpacity(((double) (blurValue) / temp));
                mainMenu.getExitBtn().setOpacity(((double) (blurValue) / temp));
            }));

            fadingTimeline.setCycleCount(temp);

            fadingTimeline.setOnFinished(finishEvent -> {
                PauseTransition delay = new PauseTransition(Duration.seconds(0.3)); // Adjust the delay duration as needed
                delay.setOnFinished(delayEvent -> {

                    PANE_2.getChildren().removeAll(mainMenu.getMainPanel(),mainMenu.getLogo());

                    mainMenu.getStartBtn().removeBtnFromCurrentPane(PANE_2);
                    mainMenu.getExitBtn().removeBtnFromCurrentPane(PANE_2);
                    mainMenu.getOptionBtn().removeBtnFromCurrentPane(PANE_2);



                });
                delay.play();
            });

            fadingTimeline.play();





            new FoodManager(PANE, foodType, FOOD, TILE_SIZE, PANEL_REALSTATE, TILE_COUNT);
            new PoisonManager(POISON, poisonType, TILE_SIZE, TILE_COUNT, PANEL_REALSTATE, PANE);

            snake.putSnake(PANE);

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


            timeline = new Timeline(new KeyFrame(Duration.millis(GAME_SPEED), e -> runSnake()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });
    }

    public void initStart() {
        mainScene = new Scene(PANE_2, HEIGHT, WIDTH);


        new DrawPanelBackground(PANE, HEIGHT, WIDTH, TILE_SIZE, PANEL_REALSTATE, TILE_COUNT);
        foodType = randInt(FOOD_COUNT);
        poisonType = randInt(POISON_COUNT);
        new DrawFences(fence, PANEL_REALSTATE, TILE_SIZE, TILE_COUNT, PANE, HEIGHT);
        createTiles(false);


        PANE.setEffect(new GaussianBlur(blurValue));

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






    // poisonType indices are from 0 to 2
    // images for POISON indices are from 10 to 12



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
        while (true) {
            if (FOOD[foodType].getX() == POISON[poisonType].getX() &&
                    FOOD[foodType].getY() == POISON[poisonType].getY()) {
                FOOD[foodType].setImage(null);
                foodType = randInt(FOOD_COUNT);
                new FoodManager(PANE, foodType, FOOD, TILE_SIZE, PANEL_REALSTATE, TILE_COUNT);
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
            snakeBodyPartsCount++;
            bodyParts[snakeBodyPartsCount] = new ImageView(
                    new Image(getClass().getResource(
                            "/images/snakeBodySegments.png").toExternalForm()));
            bodyParts[snakeBodyPartsCount].setFitHeight(TILE_SIZE);
            bodyParts[snakeBodyPartsCount].setFitWidth(TILE_SIZE);
            bodyParts[snakeBodyPartsCount].setX(-WIDTH);
            PANE.getChildren().add(bodyParts[snakeBodyPartsCount]);
            Score = snakeBodyPartsCount - 3;
            System.out.println("/////////////score" + Score);
            foodType = randInt(FOOD_COUNT);
            System.out.println(imagesDirectories[foodType]);
            new FoodManager(PANE, foodType, FOOD, TILE_SIZE, PANEL_REALSTATE, TILE_COUNT);
        }

        //Peter: Poison Detection
        if (bodyParts[0].getX() == POISON[poisonType].getX() && bodyParts[0].getY() - TILE_SIZE == POISON[poisonType].getY()){
            System.out.println("Poison is consumed.");
            POISON[poisonType].setImage(null);
            for(int i=0; i<3; i++){
                bodyParts[snakeBodyPartsCount - i].setImage(null);
            }
            snakeBodyPartsCount -= 3;
            // Snake dies if it's only a head.

            if(snakeBodyPartsCount < 1)
                new GameOver();

            poisonType = randInt(POISON_COUNT);
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
        }
        }


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








}

