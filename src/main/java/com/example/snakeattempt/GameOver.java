package com.example.snakeattempt;

import javafx.animation.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import static com.example.snakeattempt.SnakeEngine.*;

public class GameOver {


    private final GameOverMenu gameOverMenu;

    GameOver(){

        timeline.pause();

        soundsManager.stopMainMusic();


        blurPANE();

        gameOverMenu = new GameOverMenu(TILE_SIZE, WIDTH, TILE_COUNT, PANE_2);


        drawGameOverPanel();
        soundsManager.playPanelShowSound();
    }

    public void blurPANE(){

        PANE.setEffect(new GaussianBlur(10));
    }



    public void drawGameOverPanel() {






        // GAME OVER BUTTONS




        gameOverMenu.getExitBtn().setOnMouseClicked(exitEvent -> {
            gameOverMenu.getExitBtn().wobbleAnimation();
            // Because JVM immediately closes, even if an animation is playing.
            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            delay.setOnFinished(delayEvent -> System.exit(0));
            delay.play();


        });





        // REPLAY@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        gameOverMenu.getReplayBtn().setOnMouseClicked(replayEvent -> {
            gameOverMenu.getReplayBtn().wobbleAnimation();

            //resetting poisonous state
            poisonCounter = 0;
            poisoned=false;
            poisonous = false;

            Score = 0;
            score.setText("Score = "+Score);


            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            delay.setOnFinished(removeUIsEvent ->{
                PANE_2.getChildren().removeAll(gameOverMenu.getGameOverText(), gameOverMenu.getGameOverPanel(), gameOverMenu.getExitBtn(), gameOverMenu.getReplayBtn());
                PANE.setEffect(new GaussianBlur(0));

                snake.removeSnake(PANE, bodyParts, snakeBodyPartsCount);
                snake.putSnake(PANE, initialSnakeBodyPartsCount);

                snakeBodyPartsCount = initialSnakeBodyPartsCount;
                currentDirection = UP;




                timeline.play();
            });
            delay.play();
            if(!isMute)
                soundsManager.playMainMusic();
            invertedCounter = 0;




        });










    }





//    public void fadeOutOrInNode(boolean fadeOut, Node fadingNode, final int cycleOfFade){
//
//        Timeline fadingTimeline = new Timeline(new KeyFrame(Duration.millis(FADE_DURATION), e -> {
//            int temp = cycleOfFade;
//
//            temp--;
////            fadingNode.setEffect(new GaussianBlur(fadeOut ? temp--:temp++));
//            fadingNode.setOpacity( ( (double) (temp) / cycleOfFade) );
//
//        }));
//
//        fadingTimeline.setCycleCount(cycleOfFade);
//        fadingTimeline.play();
//
//    }

}

