package com.example.snakeattempt;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;
import java.util.Random;

// OBJECTIVE: Clean the code by making multiple classes.

public class SnakeEngine extends Application {
    public static final int HEIGHT = 800;
    public static final int WIDTH = HEIGHT;// Height MUST EQUAL Width.

    public static final int TILE_COUNT = 16; // 20 But 16 is recommended.
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
    public static ImageView menu;
    public static final double menuSizeX = TILE_SIZE * 16;
    public static final double menuSizeY = TILE_SIZE * 8;
    public static ImageView logo;
    public static final double logoSizeX = TILE_SIZE * 16;
    public static final double logoSizeY = TILE_SIZE * 6;
    public static final double FADE_DURATION = 25;
    public static int blurValue = 20;


    public int Score = 0;
    // Notes: Overlapping method does not work.

    @Override
    public void start(Stage stage) {
        initStart();

        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.setTitle("Go: Snake!");
        stage.getIcons().add(new Image(getClass().getResource("/images/stageIcon.png").toExternalForm()));
        stage.show();

        menu.setOnMouseClicked(event -> {
            //PANE.setEffect(null);
            int temp = blurValue;
            Timeline fadingTimeline = new Timeline(new KeyFrame(Duration.millis(FADE_DURATION), e -> {
                PANE.setEffect(new GaussianBlur(blurValue--));
                menu.setOpacity(((double) (blurValue) / temp));
                logo.setOpacity(((double) (blurValue) / temp));
            }));

            fadingTimeline.setCycleCount(temp);

            fadingTimeline.setOnFinished(finishEvent -> {
                PauseTransition delay = new PauseTransition(Duration.seconds(1)); // Adjust the delay duration as needed
                delay.setOnFinished(delayEvent -> {
                    PANE_2.getChildren().remove(menu);
                    PANE_2.getChildren().remove(logo);
                });
                delay.play();
            });

            fadingTimeline.play();


            new PoisonManager();
            new FoodManager();

            new SnakeBody();

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


            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(GAME_SPEED), e -> runSnake()));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        });
    }

    public void initStart() {
        mainScene = new Scene(PANE_2, HEIGHT, WIDTH);


        setBackground();
        foodType = randInt(FOOD_COUNT);
        poisonType = randInt(POISON_COUNT);
        drawFence();
        createTiles(false);


        PANE.setEffect(new GaussianBlur(blurValue));

        drawMainMenu();
        drawLogo();
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
            Rectangle rec = new Rectangle();
            rec.setFill(Color.web("#568203"));
            rec.setHeight(HEIGHT);
            rec.setWidth(WIDTH);
            PANE.getChildren().add(rec);
            // Flat Grass
            for (int i = 0; i <= TILE_COUNT; i++) {
                for (int j = 0; j <= TILE_COUNT; j++) {

                    ImageView imageView = new ImageView(new Image(
                            getClass().getResource((i + j) % 2 == 0 ? "/images/grassTile1.png" : "/images/grassTile2.png").toExternalForm()));
                    imageView.setX(TILE_SIZE * i);
                    imageView.setY(TILE_SIZE * j);
                    imageView.setFitWidth(TILE_SIZE);
                    imageView.setFitHeight(TILE_SIZE);
                    PANE.getChildren().add(imageView);
                }

            }


        //Status Panel
        Image panelImage = new Image(getClass().getResource(
                "/images/panelG.png").toExternalForm());
        Rectangle mask = new Rectangle(HEIGHT, WIDTH);
        mask.setArcHeight(TILE_SIZE);
        mask.setArcWidth(TILE_SIZE);
        ImageView upperPanel = new ImageView(panelImage);
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
                    if (k == 0) {
                        fence[k] = new ImageView(new Image(getClass().getResource(
                                "/images/fenceX.png").toExternalForm()));
                        fence[k].setFitHeight(TILE_SIZE);
                        fence[k].setFitWidth(TILE_SIZE);
                        fence[k].setX(TILE_SIZE * k);
                        fence[k].setY(PANEL_REALSTATE);
                        fence[k].setRotate(-45);
                        PANE.getChildren().add(fence[k]);
                    } else if (k == TILE_COUNT-1) {
                        fence[k] = new ImageView(new Image(getClass().getResource(
                                "/images/fenceX.png").toExternalForm()));
                        fence[k].setFitHeight(TILE_SIZE);
                        fence[k].setFitWidth(TILE_SIZE);
                        fence[k].setX(TILE_SIZE * k);
                        fence[k].setY(PANEL_REALSTATE);
                        fence[k].setRotate(45);
                        PANE.getChildren().add(fence[k]);
                    } else {
                        fence[k] = new ImageView(new Image(getClass().getResource(
                                "/images/fenceX.png").toExternalForm()));
                        fence[k].setFitHeight(TILE_SIZE);
                        fence[k].setFitWidth(TILE_SIZE);
                        fence[k].setX(TILE_SIZE * k);
                        fence[k].setY(PANEL_REALSTATE);
                        PANE.getChildren().add(fence[k]);
                    }

                }

            }
            // Draw the lower fence
            if (i == TILE_COUNT - 1) {
                for (int k = 0; k < TILE_COUNT; k++) {
                    if (k == TILE_COUNT-1) {
                        fence[k] = new ImageView(new Image(getClass().getResource(
                                "/images/fenceX.png").toExternalForm()));
                        fence[k].setFitHeight(TILE_SIZE);
                        fence[k].setFitWidth(TILE_SIZE);
                        fence[k].setX(TILE_SIZE * k);
                        fence[k].setY(HEIGHT - TILE_SIZE);
                        fence[k].setRotate(135);
                        PANE.getChildren().add(fence[k]);
                    } else if (k == 0) {
                        fence[k] = new ImageView(new Image(getClass().getResource(
                                "/images/fenceX.png").toExternalForm()));
                        fence[k].setFitHeight(TILE_SIZE);
                        fence[k].setFitWidth(TILE_SIZE);
                        fence[k].setX(TILE_SIZE * k);
                        fence[k].setY(HEIGHT - TILE_SIZE);
                        fence[k].setRotate(-135);
                        PANE.getChildren().add(fence[k]);

                    } else {
                        fence[k] = new ImageView(new Image(getClass().getResource(
                                "/images/fenceX.png").toExternalForm()));
                        fence[k].setFitHeight(TILE_SIZE);
                        fence[k].setFitWidth(TILE_SIZE);
                        fence[k].setX(TILE_SIZE * k);
                        fence[k].setY(HEIGHT - TILE_SIZE);
                        fence[k].setRotate(180);
                        PANE.getChildren().add(fence[k]);
                    }


                }

            }

            // Peter: Complete the fence.
            if (i > (PANEL_REALSTATE / TILE_SIZE)) {
                for (int k = (PANEL_REALSTATE / TILE_SIZE) + 1; k < TILE_COUNT - 1; k++) {
                    fence[k] = new ImageView(new Image(getClass().getResource(
                            "/images/fenceX.png").toExternalForm()));
                    fence[k].setFitHeight(TILE_SIZE);
                    fence[k].setFitWidth(TILE_SIZE);
                    fence[k].setX(0);
                    fence[k].setY(TILE_SIZE * k);
                    fence[k].setRotate(-90);
                    PANE.getChildren().add(fence[k]);

                }

                for (int k = PANEL_REALSTATE / TILE_SIZE + 1; k < TILE_COUNT - 1; k++) {
                    fence[k] = new ImageView(new Image(getClass().getResource(
                            "/images/fenceX.png").toExternalForm()));
                    fence[k].setFitHeight(TILE_SIZE);
                    fence[k].setFitWidth(TILE_SIZE);
                    fence[k].setX((TILE_COUNT - 1) * TILE_SIZE);
                    fence[k].setY(TILE_SIZE * k);
                    fence[k].setRotate(90);
                    PANE.getChildren().add(fence[k]);

                }

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
                new FoodManager();
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
                    new Image(Objects.requireNonNull(getClass().getResource(
                            "/images/snakeBodySegments.png")).toExternalForm()));
            bodyParts[snakeBodyPartsCount].setFitHeight(TILE_SIZE);
            bodyParts[snakeBodyPartsCount].setFitWidth(TILE_SIZE);
            bodyParts[snakeBodyPartsCount].setX(-WIDTH);
            PANE.getChildren().add(bodyParts[snakeBodyPartsCount]);
            Score = snakeBodyPartsCount - 3;
            System.out.println("/////////////score" + Score);
            foodType = randInt(FOOD_COUNT);
            System.out.println(imagesDirectories[foodType]);
            new FoodManager();
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
        // System.exit(0);
    }

    public void drawMainMenu() {
        menu = new ImageView(
                new Image(getClass().getResource(
                        "/images/mainMenu.png").toExternalForm()));
        menu.setX(WIDTH / 2.0 - menuSizeX / 2);
        menu.setY(HEIGHT / 1.5 - menuSizeY / 2);
        menu.setFitWidth(menuSizeX);
        menu.setFitHeight(menuSizeY);
        PANE_2.getChildren().add(menu);

    }

    public void drawLogo() {
        logo = new ImageView(
                new Image(getClass().getResource(
                        "/images/Logo.png").toExternalForm()));
        logo.setX(WIDTH / 2.0 - logoSizeX / 2);
        // logo.setY(HEIGHT/2.0 - logoSizeY/2);
        logo.setY(0);
        logo.setFitWidth(logoSizeX);
        logo.setFitHeight(logoSizeY);
        PANE_2.getChildren().add(logo);

    }

}
