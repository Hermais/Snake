package com.example.snakeattempt;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;



public class GameOverMenu {
    private int gameUnitSize;
    private int width;
    private int squareDivisionsNum;

    private  double gameOverButtonsFit;

    private ImageView gameOverText;
    private ImageView gameOverPanel;



    private Buttons exitBtn;
    private Buttons replayBtn;
    private  double gameOverButtonsY;

    private  double gameOverTextSizeX;
    private  double gameOverTextSizeY;
    private  double gameOverPanelSizeX ;
    private  double gameOverPanelSizeY ;
    private Pane pane;

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getSquareDivisionsNum() {
        return squareDivisionsNum;
    }

    public void setSquareDivisionsNum(int squareDivisionsNum) {
        this.squareDivisionsNum = squareDivisionsNum;
    }

    public int getGameUnitSize() {
        return gameUnitSize;
    }

    public void setGameUnitSize(int gameUnitSize) {
        this.gameUnitSize = gameUnitSize;
    }

    public double getGameOverButtonsFit() {
        return gameOverButtonsFit;
    }

    public ImageView getGameOverPanel() {
        return gameOverPanel;
    }

    public void setGameOverPanel(ImageView gameOverPanel) {
        this.gameOverPanel = gameOverPanel;
    }

    public double getGameOverButtonsY() {
        return gameOverButtonsY;
    }

    public void setGameOverButtonsY(double gameOverButtonsY) {
        this.gameOverButtonsY = gameOverButtonsY;
    }

    public ImageView getGameOverText() {
        return gameOverText;
    }

    public void setGameOverText(ImageView gameOverText) {
        this.gameOverText = gameOverText;
    }

    public Buttons getExitBtn() {
        return exitBtn;
    }

    public void setExitBtn(Buttons exitBtn) {
        this.exitBtn = exitBtn;
    }

    public Buttons getReplayBtn() {
        return replayBtn;
    }

    public void setReplayBtn(Buttons replayBtn) {
        this.replayBtn = replayBtn;
    }

    GameOverMenu(){

    }

    public GameOverMenu(int gameUnitSize, int width, int squareDivisionsNum, Pane pane) {
        this.gameUnitSize = gameUnitSize;
        this.width = width;
        this.squareDivisionsNum = squareDivisionsNum;
        this.pane = pane;

        gameOverButtonsFit = 3 * gameUnitSize;
        gameOverTextSizeX = gameUnitSize * 16;
        gameOverTextSizeY = gameUnitSize * 3;
        gameOverPanelSizeX = gameUnitSize * 15;
        gameOverPanelSizeY = gameUnitSize * 5;
        drawGameOverText();
        drawGameOverPanel();
        drawGameOverButtons();

    }

    public void drawGameOverText(){
        gameOverText = new ImageView(new Image(
                getClass().getResource("/images/gameOverText.png").toExternalForm()));

        gameOverText.setX(width / 2.0 - gameOverTextSizeX / 2);
        gameOverText.setY(3 * gameUnitSize);
        gameOverText.setFitWidth(gameOverTextSizeX);
        gameOverText.setFitHeight(gameOverTextSizeY);

        pane.getChildren().add(gameOverText);


    }

    public void drawGameOverPanel(){
        gameOverPanel = new ImageView(new Image(
                getClass().getResource("/images/gameOverPanel.png").toExternalForm()));

        gameOverPanel.setX(width / 2.0 - gameOverPanelSizeX / 2);
        gameOverPanel.setY(squareDivisionsNum / 2.0 * gameUnitSize);
        gameOverPanel.setFitWidth(gameOverPanelSizeX);
        gameOverPanel.setFitHeight(gameOverPanelSizeY);
        gameOverButtonsY = gameOverPanel.getY() + gameOverPanel.getY()*0.5 - 0.5* gameOverButtonsFit;

        pane.getChildren().add(gameOverPanel);
    }



    public void drawGameOverButtons(){
        gameOverButtonsY = gameOverPanel.getY() + gameOverPanelSizeY*0.5 - 0.5* gameOverButtonsFit;

        exitBtn = new Buttons("/images/exitBtn.png", gameOverButtonsFit, pane);
        exitBtn.setX(gameOverPanel.getX() + (gameOverPanelSizeX / 5.0) - gameOverButtonsFit / 2.0);
        exitBtn.setY(gameOverButtonsY);

        replayBtn = new Buttons("/images/replayBtn.png", gameOverButtonsFit, pane);
        replayBtn.setX(gameOverPanel.getX() + (4.0 * gameOverPanelSizeX / 5.0) - gameOverButtonsFit / 2.0);
        replayBtn.setY(gameOverButtonsY);
    }

}
