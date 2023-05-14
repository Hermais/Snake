package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.snakeattempt.SnakeEngine.*;

public class DrawFences {

    public DrawFences() {
        for (int i = PANEL_REALSTATE / TILE_SIZE; i < TILE_COUNT; i++) {

            drawUpperFence(i);


            drawLowerFence(i);

            drawSideFence(i);



        }
    }

    public void drawUpperFence(int i){
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
    }

    public void drawLowerFence(int i){
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
    }



    public void drawSideFence(int i){
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
