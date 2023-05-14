package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.example.snakeattempt.SnakeEngine.*;

public class DrawPanelBackground {

    private  Image panelImage;
    private   ImageView grassTiles;
    private    ImageView upperPanel;
    private Rectangle mask;
    private Rectangle rec;

    public DrawPanelBackground() {
        drawBackground();

        drawStatusPanel();



        PANE.getChildren().add(upperPanel);
    }

    public void drawBackground(){
        // Cover Background for spacings between grass images.
        rec = new Rectangle();
        rec.setFill(Color.web("#568203"));
        rec.setHeight(HEIGHT);
        rec.setWidth(WIDTH);
        PANE.getChildren().add(rec);

        // Flat Grass
        for (int i = 0; i <= TILE_COUNT; i++) {
            for (int j = 0; j <= TILE_COUNT; j++) {

                grassTiles = new ImageView(new Image(
                        getClass().getResource((i + j) % 2 == 0 ? "/images/grassTile1.png" : "/images/grassTile2.png").toExternalForm()));
                grassTiles.setX(TILE_SIZE * i);
                grassTiles.setY(TILE_SIZE * j);
                grassTiles.setFitWidth(TILE_SIZE);
                grassTiles.setFitHeight(TILE_SIZE);
                PANE.getChildren().add(grassTiles);
            }

        }

    }

    public void drawStatusPanel(){
        // Optional mask
        mask = new Rectangle(HEIGHT, WIDTH);
        mask.setArcHeight(TILE_SIZE);
        mask.setArcWidth(TILE_SIZE);

        // Status Panel
        panelImage = new Image(getClass().getResource(
                "/images/panelG.png").toExternalForm());
        upperPanel = new ImageView(panelImage);
        upperPanel.setFitHeight(HEIGHT);
        upperPanel.setFitWidth(WIDTH);
        upperPanel.setY(PANEL_REALSTATE - HEIGHT);
        mask.setY(upperPanel.getY());
        upperPanel.setClip(mask);

    }
}
