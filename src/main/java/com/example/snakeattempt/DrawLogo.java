package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.snakeattempt.SnakeEngine.*;

public class DrawLogo {


    public DrawLogo() {
        logo = new ImageView(
                new Image(getClass().getResource(
                        "/images/Logo.png").toExternalForm()));
        logo.setX(WIDTH / 2.0 - logoSizeX / 2);
        logo.setY( logoSizeY/2 + PANEL_REALSTATE + TILE_SIZE);
        logo.setY(0);
        logo.setFitWidth(logoSizeX);
        logo.setFitHeight(logoSizeY);
        PANE_2.getChildren().add(logo);
    }
}
