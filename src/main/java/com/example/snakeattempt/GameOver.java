package com.example.snakeattempt;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

import static com.example.snakeattempt.SnakeEngine.*;

public class GameOver {

    private ImageView gameOverText;
    private final double gameOverButtonsFit = 3*TILE_SIZE;
    Buttons exitBtn;
    Buttons replayBtn;
    GameOver(){
        timeline.pause();


        blurPANE();

        drawGameOverText();

        drawGameOverPanel();
    }

    public void blurPANE(){
        PANE.setEffect(new GaussianBlur(10));
    }

    public void drawGameOverText(){
        gameOverText = new ImageView(new Image(
                getClass().getResource("/images/gameOverText.png").toExternalForm()));

        gameOverText.setX(WIDTH / 2.0 - gameOverSizeX / 2);
        gameOverText.setY(3 * TILE_SIZE);
        gameOverText.setFitWidth(gameOverSizeX);
        gameOverText.setFitHeight(gameOverSizeY);

        PANE_2.getChildren().add(gameOverText);

    }

    public void drawGameOverPanel() {


        gameOverPanel = new ImageView(new Image(
                getClass().getResource("/images/gameOverPanel.png").toExternalForm()));

        gameOverPanel.setX(WIDTH / 2.0 - gameOverPanelSizeX / 2);
        gameOverPanel.setY(TILE_COUNT / 2.0 * TILE_SIZE);
        gameOverPanel.setFitWidth(gameOverPanelSizeX);
        gameOverPanel.setFitHeight(gameOverPanelSizeY);



        // GAME OVER BUTTONS
        double gameOverButtonsY = gameOverPanel.getY() + gameOverPanelSizeY*0.5 - 0.5* gameOverButtonsFit;

        exitBtn = new Buttons("/images/exitBtn.png", gameOverButtonsFit);
        exitBtn.setX(gameOverPanel.getX() + (1/5.0) * gameOverPanelSizeX - gameOverButtonsFit / 2.0);
        exitBtn.setY(gameOverButtonsY);

        replayBtn = new Buttons("/images/replayBtn.png", gameOverButtonsFit);
        replayBtn.setX(gameOverPanel.getX() + (4/5.0) * gameOverPanelSizeX - gameOverButtonsFit/2.0);
        replayBtn.setY(gameOverButtonsY);


        exitBtn.setOnMouseClicked(exitEvent -> {
            wobbleAnimation(exitBtn);

            // Because JVM immediately closes, even if an animation is playing.
            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            delay.setOnFinished(delayEvent -> System.exit(0));
            delay.play();


        });





        // REPLAY@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        replayBtn.setOnMouseClicked(replayEvent -> {
            wobbleAnimation(replayBtn);




            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            delay.setOnFinished(removeUIsEvent ->{
                PANE_2.getChildren().removeAll(gameOverText, gameOverPanel, exitBtn, replayBtn);
                PANE.setEffect(new GaussianBlur(0));
                // Entry Point of the game.
                // timeline.play();
            });
            delay.play();




        });






        PANE_2.getChildren().add(gameOverPanel);
        replayBtn.setPane(PANE_2);
        exitBtn.setPane(PANE_2);



    }


    public void wobbleAnimation(Buttons btn){
        double originalFitWidth = btn.getFitWidth();
        double originalFitHeight = btn.getFitHeight();
        double wobbleSize = 10;

        // Create a timeline for the wobbling animation
        Timeline wobbleAnimation = new Timeline();

        // Add keyframes to the animation
        wobbleAnimation.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0), new KeyValue(btn.fitWidthProperty(), originalFitWidth)),
                new KeyFrame(Duration.seconds(0), new KeyValue(btn.fitHeightProperty(), originalFitHeight)),
                new KeyFrame(Duration.seconds(0.05), new KeyValue(btn.fitWidthProperty(), originalFitWidth + wobbleSize)),
                new KeyFrame(Duration.seconds(0.05), new KeyValue(btn.fitHeightProperty(), originalFitHeight + wobbleSize)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(btn.fitWidthProperty(), originalFitWidth)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(btn.fitHeightProperty(), originalFitHeight)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(btn.fitWidthProperty(), originalFitWidth - wobbleSize)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(btn.fitHeightProperty(), originalFitHeight - wobbleSize)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(btn.fitWidthProperty(), originalFitWidth)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(btn.fitHeightProperty(), originalFitHeight))
        );

        wobbleAnimation.play();

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

