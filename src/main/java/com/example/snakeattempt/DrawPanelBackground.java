package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class DrawPanelBackground {
    // Only for Class
    private  Image panelImage;
    private   ImageView grassTiles;
    private    ImageView upperPanel;
    private Rectangle mask;
    private Rectangle rec;



    // Parameters
    private Pane pane;
    private int height;
    private int width;
    private int fitSize;
    private int upperYMargin;
    private int squareDivision;

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getFitSize() {
        return fitSize;
    }

    public void setFitSize(int fitSize) {
        this.fitSize = fitSize;
    }

    public int getUpperYMargin() {
        return upperYMargin;
    }

    public void setUpperYMargin(int upperYMargin) {
        this.upperYMargin = upperYMargin;
    }

    public int getSquareDivision() {
        return squareDivision;
    }

    public void setSquareDivision(int squareDivision) {
        this.squareDivision = squareDivision;
    }

    public DrawPanelBackground(Pane pane, int height, int width, int fitSize, int upperYMargin, int squareDivision) {
        this.pane = pane;
        this.height = height;
        this.width = width;
        this.fitSize = fitSize;
        this.upperYMargin = upperYMargin;
        this.squareDivision = squareDivision;

        drawBackground();

        drawStatusPanel();



        pane.getChildren().add(upperPanel);
    }

    public DrawPanelBackground() {

    }

    public void drawBackground(){
        // Cover Background for spacings between grass images.
        rec = new Rectangle();
        rec.setFill(Color.web("#568203"));
        rec.setHeight(height);
        rec.setWidth(width);
        pane.getChildren().add(rec);

        // Flat Grass
        for (int i = 0; i <= squareDivision; i++) {
            for (int j = 0; j <= squareDivision; j++) {

                grassTiles = new ImageView(new Image(
                        getClass().getResource((i + j) % 2 == 0 ? "/images/grassTile1.png" : "/images/grassTile2.png").toExternalForm()));
                grassTiles.setX(fitSize * i);
                grassTiles.setY(fitSize * j);
                grassTiles.setFitWidth(fitSize);
                grassTiles.setFitHeight(fitSize);
                pane.getChildren().add(grassTiles);
            }

        }

    }

    public void drawStatusPanel(){
        // Optional mask
        mask = new Rectangle(height, width);
        mask.setArcHeight(fitSize);
        mask.setArcWidth(fitSize);

        // Status Panel
        panelImage = new Image(getClass().getResource(
                "/images/panelG.png").toExternalForm());
        upperPanel = new ImageView(panelImage);
        upperPanel.setFitHeight(height);
        upperPanel.setFitWidth(width);
        upperPanel.setY(upperYMargin - height);
        mask.setY(upperPanel.getY());
        upperPanel.setClip(mask);

    }
}
