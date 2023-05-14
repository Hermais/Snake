package com.example.snakeattempt;

import javafx.application.Platform;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.snakeattempt.SnakeEngine.*;

public class GameOver {


    private ImageView gameOverText;

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

    public void drawGameOverPanel(){
        gameOverPanel = new ImageView(new Image(
                getClass().getResource("/images/gameOverPanel.png").toExternalForm()));

        gameOverPanel.setX(WIDTH / 2.0 - gameOverPanelSizeX / 2);
        gameOverPanel.setY(TILE_COUNT/2.0 * TILE_SIZE);
        gameOverPanel.setFitWidth(gameOverPanelSizeX);
        gameOverPanel.setFitHeight(gameOverPanelSizeY);

        PANE_2.getChildren().add(gameOverPanel);

        // When Panel is pressed game restarts
//        gameOverPanel.setOnMouseClicked(restartEvent -> {
//            Platform.runLater(() -> {
//                try {
//                    Stage newStage = new Stage();
//                    new SnakeEngine().start(newStage);
//                    mainStage.close();
//                    mainStage = newStage;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        });
    }




}
