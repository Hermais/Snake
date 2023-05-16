package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import static com.example.snakeattempt.SnakeEngine.TILE_SIZE;


public class MainMenu {
    private ImageView mainPanel;
    private ImageView logo;
    private int height;
    private int width;

    private double menuSizeX;
    private double menuSizeY;

    private double yMargin;

    private double logoSizeX;
    private double logoSizeY;
    private Pane pane;
     public  Buttons startBtn;
    public Buttons optionBtn;
    public Buttons exitBtn;

    private final double gamestartButtonsFit = 2*TILE_SIZE;

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

    public double getMenuSizeX() {
        return menuSizeX;
    }

    public void setMenuSizeX(double menuSizeX) {
        this.menuSizeX = menuSizeX;
    }

    public double getMenuSizeY() {
        return menuSizeY;
    }

    public void setMenuSizeY(double menuSizeY) {
        this.menuSizeY = menuSizeY;
    }

    public double getyMargin() {
        return yMargin;
    }

    public void setyMargin(double yMargin) {
        this.yMargin = yMargin;
    }

    public double getLogoSizeX() {
        return logoSizeX;
    }

    public void setLogoSizeX(double logoSizeX) {
        this.logoSizeX = logoSizeX;
    }

    public double getLogoSizeY() {
        return logoSizeY;
    }

    public void setLogoSizeY(double logoSizeY) {
        this.logoSizeY = logoSizeY;
    }

    public void setLogoSizeX(int logoSizeX) {
        this.logoSizeX = logoSizeX;
    }


    public void setLogoSizeY(int logoSizeY) {
        this.logoSizeY = logoSizeY;
    }


    public ImageView getMainPanel() {
        return mainPanel;
    }


    public void setMainPanel(ImageView mainPanel) {
        this.mainPanel = mainPanel;
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


    public void setMenuSizeX(int menuSizeX) {
        this.menuSizeX = menuSizeX;
    }


    public void setMenuSizeY(int menuSizeY) {
        this.menuSizeY = menuSizeY;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }



    public MainMenu() {

    }

    public MainMenu(int height, int width,
                    double menuSizeX, double menuSizeY, double yMargin, double logoSizeX,
                    double logoSizeY, Pane pane) {

        this.height = height;
        this.width = width;
        this.menuSizeX = menuSizeX;
        this.menuSizeY = menuSizeY;
        this.yMargin = yMargin;
        this.logoSizeX = logoSizeX;
        this.logoSizeY = logoSizeY;
        this.pane = pane;
        drawMainMenuPanel();
        drawMainMenuLogo();
    }

    public void drawMainMenuPanel() {
        mainPanel = new ImageView(
                new Image(getClass().getResource(
                        "/images/mainMenuPanel.png").toExternalForm()));
        mainPanel.setX(width / 2.0 - menuSizeX / 2.0);
        mainPanel.setY(height / 1.5 - menuSizeY / 2.0);
        mainPanel.setFitWidth(menuSizeX);
        mainPanel.setFitHeight(menuSizeY);

        HBox hb = new HBox(70);


        startBtn = new Buttons("/images/playBtn.png", menuSizeX / 6);
        // startBtn.setX(mainPanel.getX() + (1/5.0) * menuSizeX - gamestartButtonsFit/10);
        //  startBtn.setY(gamestartButtonsFit*5.8);


        optionBtn = new Buttons("/images/options.png", menuSizeX / 6);
        // startBtn.setX(mainPanel.getX() + (1/5.0) * menuSizeX - gamestartButtonsFit/2);
        //startBtn.setY(gamestartButtonsFit*5.6);


        exitBtn = new Buttons("/images/exitBtn.png", menuSizeX / 6);
        //startBtn.setX(mainPanel.getX() + (1/5.0) * menuSizeX - gamestartButtonsFit/2);
        //startBtn.setY(gamestartButtonsFit*5.8);
        hb.setPrefSize(menuSizeX, menuSizeY);
        hb.getChildren().addAll(startBtn, optionBtn, exitBtn);
        hb.setLayoutX(mainPanel.getX() + (1 / 5.0) * menuSizeX - gamestartButtonsFit / 2);
        hb.setLayoutY(gamestartButtonsFit * 6);

        pane.getChildren().addAll(mainPanel, hb/*,optionBtn,exitBtn*/);

        mainPanel.setOnMouseClicked(event -> {
           // PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            //delay.setOnFinished(removeUIsEvent -> {
                pane.getChildren().removeAll(exitBtn, optionBtn, startBtn);


            });


      //  });
    }
    public void drawMainMenuLogo() {
        logo = new ImageView(
                new Image(getClass().getResource(
                        "/images/Logo.png").toExternalForm()));
        logo.setX(width / 2.0 - logoSizeX / 2.0);
        logo.setY(logoSizeY / 2.0 + yMargin * 1.5);
        logo.setY(0);
        logo.setFitWidth(logoSizeX);
        logo.setFitHeight(logoSizeY);
        pane.getChildren().add(logo);
    }





}
