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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Random;
import javafx.scene.media.*;

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
    public static int initialSnakeBodyPartsCount = 3;
    public static final ImageView[] bodyParts = new ImageView[TILE_COUNT * TILE_COUNT];
    public static final ImageView[] fence = new ImageView[TILE_COUNT];

    public static final double menuSizeX = TILE_SIZE * 16;
    public static final double menuSizeY = TILE_SIZE * 8;
    public static final double logoSizeX = TILE_SIZE * 16;
    public static final double logoSizeY = TILE_SIZE * 6;

    public static final double FADE_DURATION = 25;
    public static int blurValue = 20;
    public static Timeline timeline;
    public static Stage mainStage;
    public MainMenu mainMenu = new MainMenu( HEIGHT, WIDTH, menuSizeX,
            menuSizeY, PANEL_REALSTATE, logoSizeX, logoSizeY, PANE_2);

    public static Snake snake = new Snake(initialSnakeBodyPartsCount, bodyParts, TILE_SIZE, initialSnakeHeadX, initialSnakeHeadY);
    public static SoundsManager soundsManager = new SoundsManager();

    Buttons muteBtn = new Buttons("/images/soundsBtn.png", PANEL_REALSTATE/1.4);
    public boolean isMute = false;
    public final double pauseTransitionDelay = 0.3;

    public static Text score = new Text();
    public static int poisonCounter = 0;
    public static int invertedCounter = 0;










    public static int Score = 0;
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


        soundsManager.playMainMusic();


        muteBtn.setOnMouseClicked(eventMute -> {
            muteBtn.wobbleAnimation();

            muteBtn.setImage(new Image(
                    getClass().getResource(
                            !isMute ? "/images/soundsMuteBtn.png":"/images/soundsBtn.png").toExternalForm()));

            if(isMute)
                soundsManager.playMainMusic();

            if(!isMute)
                soundsManager.stopMainMusic();

            isMute = !isMute;

        });

        mainMenu.getExitBtn().setOnMouseClicked(exit -> {
            mainMenu.getExitBtn().wobbleAnimation();
            PauseTransition delay = new PauseTransition(Duration.seconds(pauseTransitionDelay));


            delay.setOnFinished(finished -> System.exit(0));

            delay.play();
        });



        mainMenu.getStartBtn().setOnMouseClicked(event -> {
            int temp = blurValue;
            Timeline fadingTimeline = new Timeline(new KeyFrame(Duration.millis(FADE_DURATION), e -> {
                PauseTransition delay = new PauseTransition(Duration.seconds(pauseTransitionDelay/2));
                delay.setOnFinished(finis -> {
                    mainMenu.getStartBtn().wobbleAnimation();
                    PANE.setEffect(new GaussianBlur(blurValue--));
                    mainMenu.getMainPanel().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getLogo().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getStartBtn().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getInfoBtn().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getOptionBtn().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getExitBtn().setOpacity(((double) (blurValue) / temp));
                } );
                delay.play();

            }));

            fadingTimeline.setCycleCount(temp);

            fadingTimeline.setOnFinished(finishEvent -> {
                PauseTransition delay = new PauseTransition(Duration.seconds(pauseTransitionDelay)); // Adjust the delay duration as needed
                delay.setOnFinished(delayEvent -> {


                    PANE_2.getChildren().removeAll(mainMenu.getMainPanel(),mainMenu.getLogo());

                    mainMenu.getStartBtn().removeBtnFromCurrentPane(PANE_2);
                    mainMenu.getExitBtn().removeBtnFromCurrentPane(PANE_2);
                    mainMenu.getOptionBtn().removeBtnFromCurrentPane(PANE_2);
                    mainMenu.getInfoBtn().removeBtnFromCurrentPane(PANE_2);



                });
                delay.play();
            });

            fadingTimeline.play();







            new FoodManager(PANE, foodType, FOOD, TILE_SIZE, PANEL_REALSTATE, TILE_COUNT);
            new PoisonManager(POISON, poisonType, TILE_SIZE, TILE_COUNT, PANEL_REALSTATE, PANE);

            snake.putSnake(PANE,initialSnakeBodyPartsCount);


            // Set up key press event handling
            mainScene.setOnKeyPressed(keyEvent -> {
                KeyCode keyCode = keyEvent.getCode();
                if(invertedCounter < 1) {
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
                }
                else {
                    if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
                        if (currentDirection != UP)
                            currentDirection = DOWN;
                    } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {
                        if (currentDirection != DOWN)
                            currentDirection = UP;
                    } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
                        if (currentDirection != RIGHT)
                            currentDirection = LEFT;
                    } else if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
                        if (currentDirection != LEFT)
                            currentDirection = RIGHT;
                    }
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
        muteBtn.setX(WIDTH - muteBtn.getFitXY() - TILE_SIZE/1.5);
        muteBtn.setY(PANEL_REALSTATE/12.0);
        muteBtn.setPane(PANE_2);

        score();



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



        // Food detection
        if (bodyParts[0].getX() == FOOD[foodType].getX() && bodyParts[0].getY() - TILE_SIZE == FOOD[foodType].getY()) {
            // Collision with FOOD detected
            System.out.println("Food is eaten.");
            // playEatSound(); to be implemented.
            FOOD[foodType].setImage(null);

            for(int i=0;i<2;i++) {
                soundsManager.playEatSound(i);
            }


            // Anton:
            // Add snake body segment
            snakeBodyPartsCount++;
            //to invert back to normal
            if(invertedCounter>0){invertedCounter--;}

            bodyParts[snakeBodyPartsCount] = new ImageView(
                    new Image(getClass().getResource(
                            "/images/snakeBodySegments.png").toExternalForm()));
            bodyParts[snakeBodyPartsCount].setFitHeight(TILE_SIZE);
            bodyParts[snakeBodyPartsCount].setFitWidth(TILE_SIZE);
            bodyParts[snakeBodyPartsCount].setX(-WIDTH);
            PANE.getChildren().add(bodyParts[snakeBodyPartsCount]);
            Score = snakeBodyPartsCount - 3;
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

           if(snakeBodyPartsCount > 3){
              for(int i=0; i<3; i++){
                    bodyParts[snakeBodyPartsCount - i].setImage(null);
                }
                snakeBodyPartsCount -= 3;

            }

            else{
               new GameOver();
            }


            // Snake dies if it's only a head.
            Score--;
            score.setText("Score = " + Score);
            // ??
            if(snakeBodyPartsCount < 1){
                new GameOver();


            }
            if(poisonType == 0){
                poisonCounter++;
            }
            if(poisonType == 1){
                invertedCounter += 2;
            }

            poisonType = randInt(POISON_COUNT);
            new PoisonManager(POISON, poisonType, TILE_SIZE, TILE_COUNT, PANEL_REALSTATE, PANE);



        }

        //indicating if the snake is poisonous
        //if(poisonCounter>2){score.setText("P,Score = " + Score);}
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

    public void score(){
        score.setX(WIDTH/2.0 - TILE_SIZE*3);
        score.setY(TILE_SIZE*1.5);
        score.setText("Score = " + Score);
        score.setFont(Font.font("Bauhaus 93", TILE_SIZE*2));


        PANE.getChildren().add(score);
    }




}

