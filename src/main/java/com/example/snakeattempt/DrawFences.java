package com.example.snakeattempt;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import static com.example.snakeattempt.SnakeEngine.*;


public class DrawFences {
    
    private ImageView[] fence;
    private int upperYMargin;
    private int fitSize;
    private int squareDivisionNum;
    private Pane pane;
    private int height;
    

    public DrawFences() {

    }

    public DrawFences(ImageView[] fence, int upperYMargin, int fitSize, int squareDivisionNum, Pane pane, int height) {
        this.fence = fence;
        this.upperYMargin = upperYMargin;
        this.fitSize = fitSize;
        this.squareDivisionNum = squareDivisionNum;
        this.pane = pane;
        this.height = height;

        startDraw();
    }

    public void startDraw(){
        for (int i = upperYMargin / fitSize; i < squareDivisionNum; i++) {

            drawUpperFence(i);


            drawLowerFence(i);

            drawSideFence(i);



        }
    }

    public void drawUpperFence(int i){
        // Draw the upper fence
        if (i == upperYMargin / fitSize) {
            for (int k = 0; k < squareDivisionNum; k++) {
                if (k == 0) {
                    fence[k] = new ImageView(new Image(getClass().getResource(
                            "/images/fenceX.png").toExternalForm()));
                    fence[k].setFitHeight(fitSize);
                    fence[k].setFitWidth(fitSize);
                    fence[k].setX(fitSize * k);
                    fence[k].setY(upperYMargin);
                    fence[k].setRotate(-45);
                    pane.getChildren().add(fence[k]);
                } else if (k == squareDivisionNum-1) {
                    fence[k] = new ImageView(new Image(getClass().getResource(
                            "/images/fenceX.png").toExternalForm()));
                    fence[k].setFitHeight(fitSize);
                    fence[k].setFitWidth(fitSize);
                    fence[k].setX(fitSize * k);
                    fence[k].setY(upperYMargin);
                    fence[k].setRotate(45);
                    pane.getChildren().add(fence[k]);
                } else {
                    fence[k] = new ImageView(new Image(getClass().getResource(
                            "/images/fenceX.png").toExternalForm()));
                    fence[k].setFitHeight(fitSize);
                    fence[k].setFitWidth(fitSize);
                    fence[k].setX(fitSize * k);
                    fence[k].setY(upperYMargin);
                    pane.getChildren().add(fence[k]);
                }

            }

        }
    }

    public void drawLowerFence(int i){
        // Draw the lower fence
        if (i == squareDivisionNum - 1) {
            for (int k = 0; k < squareDivisionNum; k++) {
                if (k == squareDivisionNum-1) {
                    fence[k] = new ImageView(new Image(getClass().getResource(
                            "/images/fenceX.png").toExternalForm()));
                    fence[k].setFitHeight(fitSize);
                    fence[k].setFitWidth(fitSize);
                    fence[k].setX(fitSize * k);
                    fence[k].setY(height - fitSize);
                    fence[k].setRotate(135);
                    pane.getChildren().add(fence[k]);
                } else if (k == 0) {
                    fence[k] = new ImageView(new Image(getClass().getResource(
                            "/images/fenceX.png").toExternalForm()));
                    fence[k].setFitHeight(fitSize);
                    fence[k].setFitWidth(fitSize);
                    fence[k].setX(fitSize * k);
                    fence[k].setY(height - fitSize);
                    fence[k].setRotate(-135);
                    pane.getChildren().add(fence[k]);

                } else {
                    fence[k] = new ImageView(new Image(getClass().getResource(
                            "/images/fenceX.png").toExternalForm()));
                    fence[k].setFitHeight(fitSize);
                    fence[k].setFitWidth(fitSize);
                    fence[k].setX(fitSize * k);
                    fence[k].setY(height - fitSize);
                    fence[k].setRotate(180);
                    pane.getChildren().add(fence[k]);
                }



            }

        }
    }



    public void drawSideFence(int i){
        // Peter: Complete the fence.
        if (i > (upperYMargin / fitSize)) {
            for (int k = (upperYMargin / fitSize) + 1; k < squareDivisionNum - 1; k++) {
                fence[k] = new ImageView(new Image(getClass().getResource(
                        "/images/fenceX.png").toExternalForm()));
                fence[k].setFitHeight(fitSize);
                fence[k].setFitWidth(fitSize);
                fence[k].setX(0);
                fence[k].setY(fitSize * k);
                fence[k].setRotate(-90);
                pane.getChildren().add(fence[k]);

            }

            for (int k = upperYMargin / fitSize + 1; k < squareDivisionNum - 1; k++) {
                fence[k] = new ImageView(new Image(getClass().getResource(
                        "/images/fenceX.png").toExternalForm()));
                fence[k].setFitHeight(fitSize);
                fence[k].setFitWidth(fitSize);
                fence[k].setX((squareDivisionNum - 1) * fitSize);
                fence[k].setY(fitSize * k);
                fence[k].setRotate(90);
                pane.getChildren().add(fence[k]);

            }

        }
    }


}
