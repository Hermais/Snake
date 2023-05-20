package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Info {
    private Pane pane;
    private ImageView infoPanel;
    private Buttons closeInfoPanelBtn;

    public Info(Pane pane) {
        this.pane = pane;
        infoPanel = new ImageView(new Image(getClass().getResource(
                "/images/infoPanel.png"
        ).toExternalForm()));
        closeInfoPanelBtn = new Buttons();
    }
}
