package com.example.snakeattempt;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

    public void drawGameOverPanel() {


        gameOverPanel = new ImageView(new Image(
                getClass().getResource("/images/gameOverPanel.png").toExternalForm()));

        gameOverPanel.setX(WIDTH / 2.0 - gameOverPanelSizeX / 2);
        gameOverPanel.setY(TILE_COUNT / 2.0 * TILE_SIZE);
        gameOverPanel.setFitWidth(gameOverPanelSizeX);
        gameOverPanel.setFitHeight(gameOverPanelSizeY);

        Button b1 = new Button("Restart");
        b1.setPrefWidth(150);
        b1.setPrefHeight(30);
        b1.setWrapText(false);

        b1.setStyle("-fx-background-color:ORANGE;");


        Button b2 = new Button("Exit");
        b1.setPrefWidth(150);
        b1.setPrefHeight(30);
        b2.setStyle("-fx-background-color:ORANGE;");
        HBox hB = new HBox(b1, b2);
        hB.setAlignment(Pos.CENTER);
        hB.setLayoutX(PANE_2.getWidth() / 2 - 270);
        hB.setLayoutY(PANE_2.getHeight() / 2 - 140);
        hB.setSpacing(100);
        hB.setFillHeight(true);
        hB.setPrefSize(500, 500);


//        t1.setY(PANE_2.getHeight()/2+70);
//        restart=new ImageView(new Image(
//                getClass().getResource("/images/restart.png").toExternalForm()));
//
//        restart.setX(gameOverPanel.getX()/2);
//        restart.setY(gameOverPanel.getY()/2);
//        restart.setFitHeight(gameOverPanel.getFitHeight());
//        restart.setFitWidth(gameOverPanel.getFitWidth());


//        Text t1=new Text("please press enter for restart game ");
//        t1.setFill(Color.DARKGREEN);
//        t1.setFont(new Font(28));
//        t1.setX(PANE_2.getWidth()/2-220);
//        t1.setY(PANE_2.getHeight()/2+70);
//       Text t2=new Text("please press Esc for end game ");
//        t2.setFill(Color.RED);
//        t2.setFont(new Font(28));
//        t2.setX(PANE_2.getWidth()/2-200);
//        t2.setY(PANE_2.getHeight()/2+130);


        PANE_2.getChildren().addAll(gameOverPanel, hB);


        //When Panel is pressed game restarts
        b1.setOnMouseClicked(restartEvent -> {
            Platform.runLater(() -> {
                try {

                    Stage newStage = new Stage();
                    new SnakeEngine().start(newStage);
                    mainStage.close();
                    mainStage = newStage;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });

        b2.setOnMouseClicked(ExitEvent -> {
            Platform.runLater(() -> {
                try {

                  mainStage.close();
                }



                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }




}

