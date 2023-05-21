package com.example.snakeattempt;

import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Info {
    private double delayD = 0.4;
    private Pane pane;
    private ImageView infoPanel;
    private Buttons closeInfoPanelBtn;
    private Buttons nextBtn;
    private Buttons prevBtn;
    private int fitXY;
    private int height;
    private int width;
    private int squareDivisionCount;
    private int currentPage = 1;

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public ImageView getInfoPanel() {
        return infoPanel;
    }

    public void setInfoPanel(ImageView infoPanel) {
        this.infoPanel = infoPanel;
    }

    public Buttons getCloseInfoPanelBtn() {
        return closeInfoPanelBtn;
    }

    public void setCloseInfoPanelBtn(Buttons closeInfoPanelBtn) {
        this.closeInfoPanelBtn = closeInfoPanelBtn;
    }

    public int getFitXY() {
        return fitXY;
    }

    public void setFitXY(int fitXY) {
        this.fitXY = fitXY;
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

    public int getSquareDivisionCount() {
        return squareDivisionCount;
    }

    public void setSquareDivisionCount(int squareDivisionCount) {
        this.squareDivisionCount = squareDivisionCount;
    }

    public Info() {
    }

    public Info(Pane pane, int fitXY, int height, int width, int squareDivisionCount) {
        this.pane = pane;
        this.fitXY = fitXY;
        this.height = height;
        this.width = width;
        this.squareDivisionCount = squareDivisionCount;



    }
    public void drawAll(){
        drawInfoPanel();
        drawInfoBtn();
        drawNextBtn();
        drawPrevBtn();

    }

    public void drawInfoPanel() {
        infoPanel = new ImageView(new Image(
                getClass().getResource("/images/infoPanel.png").toExternalForm()
        ));

        infoPanel.setFitHeight(20 * fitXY);
        infoPanel.setFitWidth(17.5 * fitXY);
        infoPanel.setX(width / 2.0 - infoPanel.getFitWidth() / 2);
        infoPanel.setY(height / 2.0 - infoPanel.getFitHeight() / 2);


        pane.getChildren().add(infoPanel);

    }

    public void drawInfoBtn() {
        closeInfoPanelBtn = new Buttons(
                "/images/closeBtn.png",
                2 * fitXY, pane);

        closeInfoPanelBtn.setX(infoPanel.getX() + 0.85 * infoPanel.getFitWidth());
        closeInfoPanelBtn.setY(infoPanel.getY() + fitXY);

        closeInfoPanelBtn.setOnMouseClicked(event -> {
            closeInfoPanelBtn.wobbleAnimation();

            PauseTransition delay = new PauseTransition(Duration.seconds(delayD));
            delay.setOnFinished(e -> {
                pane.getChildren().remove(infoPanel);
                closeInfoPanelBtn.removeBtnFromCurrentPane(pane);
                nextBtn.removeBtnFromCurrentPane(pane);
                prevBtn.removeBtnFromCurrentPane(pane);

            });

            delay.play();

        });

    }

    public void drawNextBtn() {
        nextBtn = new Buttons("/images/nextBtn.png", 2 * fitXY, pane);
        nextBtn.setX(infoPanel.getX() + infoPanel.getFitWidth() * 0.7);
        nextBtn.setY(infoPanel.getY() + infoPanel.getFitHeight() * 0.8);

        nextBtn.setOnMouseClicked(event -> {
            if (currentPage < 3) {
                PauseTransition delay = new PauseTransition(Duration.seconds(delayD));
                if (prevBtn.getPane() == null || !prevBtn.getPane().getChildren().contains(prevBtn))
                    prevBtn.setPane(pane);


                switch (currentPage) {
                    case 1:
                        nextBtn.wobbleAnimation();

                        delay.setOnFinished(e -> {
                            infoPanel.setImage(new Image(getClass().getResource(
                                    "/images/creditsPanel0.png").toExternalForm()));

                        });
                        currentPage = 2;

                        delay.play();
                        break;

                    case 2:
                        nextBtn.wobbleAnimation();

                        delay.setOnFinished(e -> {
                            nextBtn.removeBtnFromCurrentPane(pane);
                            infoPanel.setImage(new Image(getClass().getResource(
                                    "/images/creditsPanel1.png").toExternalForm()));

                        });
                        currentPage = 3;

                        delay.play();
                        break;
                }

            }


        });
    }

    public void drawPrevBtn() {
        prevBtn = new Buttons("/images/prevBtn.png", 2 * fitXY);
        prevBtn.setX(infoPanel.getX() + infoPanel.getFitWidth() * 0.2);
        prevBtn.setY(infoPanel.getY() + infoPanel.getFitHeight() * 0.8);

        prevBtn.setOnMouseClicked(event -> {
            if (currentPage > 1) {
                PauseTransition delay = new PauseTransition(Duration.seconds(delayD));
                if (nextBtn.getPane() == null || !nextBtn.getPane().getChildren().contains(nextBtn))
                    nextBtn.setPane(pane);

                switch (currentPage){
                    case 2:
                        prevBtn.wobbleAnimation();

                        delay.setOnFinished(e -> {
                            prevBtn.removeBtnFromCurrentPane(pane);
                            infoPanel.setImage(new Image(getClass().getResource(
                                    "/images/infoPanel.png").toExternalForm()));

                        });
                        currentPage = 1;

                        delay.play();
                        break;
                    case 3:
                        prevBtn.wobbleAnimation();

                        delay.setOnFinished(e -> {
                            infoPanel.setImage(new Image(getClass().getResource(
                                    "/images/creditsPanel0.png").toExternalForm()));

                        });
                        currentPage = 2;

                        delay.play();

                        break;
                }


            }


        });
    }
}
