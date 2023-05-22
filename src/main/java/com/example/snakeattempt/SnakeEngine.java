package com.example.snakeattempt;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;



public class SnakeEngine extends Application implements ButtonsActions{
    public static final int HEIGHT = 800;
    public static final int WIDTH = HEIGHT;// Height MUST EQUAL Width.

    public static final int TILE_COUNT = 20; // 16 But 20 is recommended.
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
    public static int GAME_SPEED = 256;// Actually lower values give higher speeds.
    public static final int borderForSnake = TILE_SIZE;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = 3;

    public static int currentDirection = UP;
    public static final double initialSnakeHeadX = TILE_SIZE * (TILE_COUNT / 2.0);
    public static final double initialSnakeHeadY = TILE_SIZE * (TILE_COUNT / 2.0);
    public static int foodType;
    public static int poisonType;
    public static final int initialSnakeBodyPartsCount = 3;
    public static int snakeBodyPartsCount = initialSnakeBodyPartsCount;
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


    public MainMenu mainMenu = new MainMenu(HEIGHT, WIDTH, menuSizeX,
            menuSizeY, PANEL_REALSTATE, logoSizeX, logoSizeY, PANE_2);

    public static Snake snake = new Snake(initialSnakeBodyPartsCount, bodyParts, TILE_SIZE, initialSnakeHeadX, initialSnakeHeadY);
    public static SoundsManager soundsManager = new SoundsManager();
    public static Info info = new Info(PANE_2, TILE_SIZE, HEIGHT, WIDTH, TILE_COUNT);
    public static PoisonManager poisonManager = new PoisonManager();
    public static FoodManager foodManager = new FoodManager();
    public static DrawFences drawFences = new DrawFences();
    public static DrawPanelBackground drawPanelBackground = new DrawPanelBackground();


    Buttons muteBtn = new Buttons("/images/soundsBtn.png", PANEL_REALSTATE / 1.4);

    public static boolean isMute = false;
    public final double pauseTransitionDelay = 0.3;

    public static Text score = new Text();
    public static int poisonCounter = 0;
    public static boolean poisoned = false;
    public static boolean poisonous = false;

    public static int invertedCounter = 0;
    public static KeyFrame keyFrame ;
    public static boolean isOver = false ;


    public static int Score = 0;

    Buttons increaseSpeed = new Buttons("/images/nextBtn.png", TILE_SIZE);
    Buttons decreaseSpeed = new Buttons("/images/prevBtn.png", TILE_SIZE);
    // Notes: Overlapping method does not work.

    @Override
    public void start(Stage stage){
        SnakeEngine.mainStage = stage;
        initStart();

        mainStage.setResizable(false);
        mainStage.setScene(mainScene);
        mainStage.setTitle("Go: Snake!");
        mainStage.getIcons().add(new Image(getClass().getResource("/images/stageIcon.png").toExternalForm()));
        mainStage.show();
        soundsManager.playMainMusic();


        buttonsActions();



    }

    public void initStart() {
        mainScene = new Scene(PANE_2, HEIGHT, WIDTH);

        new DrawPanelBackground(PANE, HEIGHT, WIDTH, TILE_SIZE, PANEL_REALSTATE, TILE_COUNT);
        foodType = randInt(FOOD_COUNT);
        poisonType = randInt(POISON_COUNT);
        new DrawFences(fence, PANEL_REALSTATE, TILE_SIZE, TILE_COUNT, PANE, HEIGHT);
        createTiles(false);


        PANE.setEffect(new GaussianBlur(blurValue));
        muteBtn.setX(WIDTH - muteBtn.getFitXY() - TILE_SIZE / 1.5);
        muteBtn.setY(PANEL_REALSTATE / 12.0);
        muteBtn.setPane(PANE_2);

        increaseSpeed.setX(muteBtn.getX() - TILE_SIZE*2);
        increaseSpeed.setY(muteBtn.getY());
        increaseSpeed.setPane(PANE_2);

        decreaseSpeed.setX(increaseSpeed.getX() - TILE_SIZE*2);
        decreaseSpeed.setY(muteBtn.getY());
        decreaseSpeed.setPane(PANE_2);

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



    public static int randInt(int max) {
        return new Random(System.currentTimeMillis()).nextInt(0, max);
    }


    public void score() {
        score.setX(WIDTH / 8.0 - TILE_SIZE);
        score.setY(TILE_SIZE * 1.5);
        score.setText("Score = " + Score);
        score.setFont(Font.font("Bauhaus 93", TILE_SIZE * 2));
        score.setFill(Color.web("7C5B2A"));

        PANE.getChildren().add(score);
    }

    @Override
    public void  buttonsActions(){
        increaseSpeed.setOnMouseClicked(incEvent -> {
            increaseSpeed.wobbleAnimation();

            GAME_SPEED /= 1.2;
            timeline.stop();
            keyFrame = new KeyFrame(Duration.millis(GAME_SPEED), e -> RuntimeOfSnake.runSnake());
            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(Animation.INDEFINITE);
            if(!isOver)
                timeline.play();



        });

        decreaseSpeed.setOnMouseClicked(incEvent -> {
            decreaseSpeed.wobbleAnimation();

            GAME_SPEED *= 1.2;
            timeline.stop();
            keyFrame = new KeyFrame(Duration.millis(GAME_SPEED), e -> RuntimeOfSnake.runSnake());
            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(Animation.INDEFINITE);
            if(!isOver)
                timeline.play();



        });

        mainMenu.getInfoBtn().setOnMouseClicked(infoEvent -> {
            mainMenu.getInfoBtn().wobbleAnimation();
            PauseTransition delay = new PauseTransition(Duration.seconds(pauseTransitionDelay));
            delay.setOnFinished(delayEvent -> info.drawAll());
            delay.play();
        });

        muteBtn.setOnMouseClicked(eventMute -> {
            muteBtn.wobbleAnimation();

            muteBtn.setImage(new Image(
                    getClass().getResource(
                            !isMute ? "/images/soundsMuteBtn.png" : "/images/soundsBtn.png").toExternalForm()));

            if (isMute)
                soundsManager.playMainMusic();

            if (!isMute)
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
            keyFrame = new KeyFrame(Duration.millis(GAME_SPEED), e -> RuntimeOfSnake.runSnake());
            int temp = blurValue;
            Timeline fadingTimeline = new Timeline(new KeyFrame(Duration.millis(FADE_DURATION), e -> {
                PauseTransition delay = new PauseTransition(Duration.seconds(pauseTransitionDelay / 2));
                delay.setOnFinished(finis -> {
                    mainMenu.getStartBtn().wobbleAnimation();
                    PANE.setEffect(new GaussianBlur(blurValue--));
                    mainMenu.getMainPanel().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getLogo().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getStartBtn().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getInfoBtn().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getOptionBtn().setOpacity(((double) (blurValue) / temp));
                    mainMenu.getExitBtn().setOpacity(((double) (blurValue) / temp));
                });
                delay.play();

            }));

            fadingTimeline.setCycleCount(temp);

            fadingTimeline.setOnFinished(finishEvent -> {
                PauseTransition delay = new PauseTransition(Duration.seconds(pauseTransitionDelay)); // Adjust the delay duration as needed
                delay.setOnFinished(delayEvent -> {


                    PANE_2.getChildren().removeAll(mainMenu.getMainPanel(), mainMenu.getLogo());

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

            snake.putSnake(PANE, initialSnakeBodyPartsCount);


            SnakeMovementsControl.movementChecker();


            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

        });
    }


}

