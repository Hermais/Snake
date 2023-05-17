package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static com.example.snakeattempt.SnakeEngine.TILE_SIZE;


public class MainMenu {
    private ImageView mainPanel;
    private ImageView logo;
    private int height;
    private int width;

    private double panelSizeX;
    private double menuSizeY;

    private double yMargin;

    private double logoSizeX;
    private double logoSizeY;
    private Pane pane;
    private Buttons startBtn;
    private Buttons optionBtn;
    private Buttons exitBtn;

    private  double commonYButtons ;

    private final double gameStartButtonsFit = 2*TILE_SIZE;

    public double getPanelSizeX() {
        return panelSizeX;
    }

    public void setPanelSizeX(double panelSizeX) {
        this.panelSizeX = panelSizeX;
    }

    public Buttons getStartBtn() {
        return startBtn;
    }

    public void setStartBtn(Buttons startBtn) {
        this.startBtn = startBtn;
    }

    public Buttons getOptionBtn() {
        return optionBtn;
    }

    public void setOptionBtn(Buttons optionBtn) {
        this.optionBtn = optionBtn;
    }

    public Buttons getExitBtn() {
        return exitBtn;
    }

    public void setExitBtn(Buttons exitBtn) {
        this.exitBtn = exitBtn;
    }

    public double getCommonYButtons() {
        return commonYButtons;
    }

    public void setCommonYButtons(double commonYButtons) {
        this.commonYButtons = commonYButtons;
    }

    public double getGameStartButtonsFit() {
        return gameStartButtonsFit;
    }



    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

    public double getMenuSizeX() {
        return panelSizeX;
    }

    public void setMenuSizeX(double panelSizeX) {
        this.panelSizeX = panelSizeX;
    }

    public double getMenuSizeY() {
        return menuSizeY;
    }

    public void setMenuSizeY(double menuSizeY) {
        this.menuSizeY = menuSizeY;
    }

    public double getYMargin() {
        return yMargin;
    }

    public void setYMargin(double yMargin) {
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


    public void setMenuSizeX(int panelSizeX) {
        this.panelSizeX = panelSizeX;
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
                    double panelSizeX, double menuSizeY, double yMargin, double logoSizeX,
                    double logoSizeY, Pane pane) {

        this.height = height;
        this.width = width;
        this.panelSizeX = panelSizeX;
        this.menuSizeY = menuSizeY;
        this.yMargin = yMargin;
        this.logoSizeX = logoSizeX;
        this.logoSizeY = logoSizeY;
        this.pane = pane;
        drawMainMenuPanel();
        drawMainMenuLogo();
        drawButtons();
    }

    public void drawMainMenuPanel() {
        mainPanel = new ImageView(
                new Image(getClass().getResource(
                        "/images/mainMenuPanel.png").toExternalForm()));
        mainPanel.setX(width / 2.0 - panelSizeX / 2.0);
        mainPanel.setY(height / 1.5 - menuSizeY / 2.0);
        commonYButtons = mainPanel.getY() + menuSizeY/3.0;
        mainPanel.setFitWidth(panelSizeX);
        mainPanel.setFitHeight(menuSizeY);






        pane.getChildren().addAll(mainPanel);




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

    public void drawButtons(){
        startBtn = new Buttons("/images/playBtn.png", panelSizeX / 6, pane);
        startBtn.setX(mainPanel.getX() + panelSizeX * 1/5.0 - gameStartButtonsFit/ 2.0);
        startBtn.setY(commonYButtons);


        optionBtn = new Buttons("/images/optionsBtn.png", panelSizeX / 6, pane);
        optionBtn.setX(mainPanel.getX() + panelSizeX * 1/2.1 - gameStartButtonsFit/2.0);
        optionBtn.setY(commonYButtons);


        exitBtn = new Buttons("/images/exitBtn.png", panelSizeX / 6, pane);
        exitBtn.setX(mainPanel.getX() + panelSizeX * 3/4.0  - gameStartButtonsFit/ 2.0);
        exitBtn.setY(commonYButtons);

    }







}
