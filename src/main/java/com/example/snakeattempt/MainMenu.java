package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.example.snakeattempt.SnakeEngine.*;

public class MainMenu {

    public MainMenu() {
        menu = new ImageView(
                new Image(getClass().getResource(
                        "/images/mainMenu.png").toExternalForm()));
        menu.setX(WIDTH / 2.0 - menuSizeX / 2);
        menu.setY(HEIGHT / 1.5 - menuSizeY / 2);
        menu.setFitWidth(menuSizeX);
        menu.setFitHeight(menuSizeY);
        PANE_2.getChildren().add(menu);
    }
}
