package com.example.snakeattempt;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;


public class Buttons extends ImageView {

    private String imageDirectory;

    private final double swellSize = 20;
    private Image btnImage;

    private double fitXY;
    private Pane pane;

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
        pane.getChildren().add(this);
    }



    public double getFitXY() {
        return fitXY;
    }

    public void setFitXY(double fitXY) {
        this.fitXY = fitXY;
        setFitWidth(fitXY);
        setFitHeight(fitXY);
    }


    public String getImageDirectory() {
        return imageDirectory;
    }

    public void setImageDirectory(String imageDirectory) {
        this.imageDirectory = imageDirectory;
    }

    public Image getBtnImage() {
        return btnImage;
    }

    public void setBtnImage(Image btnImage) {
        this.btnImage = btnImage;
    }


    Buttons(){
        super();


        mouseEffects();
    }

    Buttons(Image btnImage, double fitXY, Pane pane) {
        if(this.btnImage != null)
            setImage(this.btnImage);

        else
            throw new RuntimeException();

        setImage(btnImage);


        this.fitXY = fitXY;

        setFittings();

        mouseEffects();

        pane.getChildren().add(this);

    }

    Buttons(String imageDirectory, double fitXY, Pane pane){
        this.imageDirectory = imageDirectory;

        btnImage = new Image(getClass().getResource(imageDirectory).toExternalForm());

        setImage(btnImage);

        this.fitXY = fitXY;

        setFittings();

        mouseEffects();

        pane.getChildren().add(this);

    }

    Buttons(Image btnImage, double fitXY) {
        if(this.btnImage != null)
            setImage(this.btnImage);

        else
            throw new RuntimeException();

        setImage(btnImage);


        this.fitXY = fitXY;

        setFittings();

        mouseEffects();


    }

    Buttons(String imageDirectory, double fitXY){
        this.imageDirectory = imageDirectory;

        btnImage = new Image(getClass().getResource(imageDirectory).toExternalForm());

        setImage(btnImage);

        this.fitXY = fitXY;

        setFittings();

        mouseEffects();


    }

    public void setFittings(){
        setFitWidth(fitXY);
        setFitHeight(fitXY);
    }

    public void mouseEffects(){
        setOnMouseEntered(inEvent -> {
            setFitWidth(getFitWidth() + swellSize);
            setFitHeight(getFitHeight() + swellSize);
            setEffect(new Glow(0.5));


        });

        setOnMouseExited(outEvent -> {
            setFitWidth(getFitWidth()- swellSize);
            setFitHeight(getFitHeight()- swellSize);
            setEffect(null);

        });



    }

    public void removeBtnFromCurrentPane(Pane pane){
        pane.getChildren().remove(this);
    }

    public void wobbleAnimation(){
        double originalFitWidth = this.getFitWidth();
        double originalFitHeight = this.getFitHeight();
        double wobbleSize = 10;

        // Create a timeline for the wobbling animation
        Timeline wobbleAnimation = new Timeline();

        // Add keyframes to the animation
        wobbleAnimation.getKeyFrames().addAll(
                new KeyFrame(Duration.seconds(0), new KeyValue(this.fitWidthProperty(), originalFitWidth)),
                new KeyFrame(Duration.seconds(0), new KeyValue(this.fitHeightProperty(), originalFitHeight)),
                new KeyFrame(Duration.seconds(0.05), new KeyValue(this.fitWidthProperty(), originalFitWidth + wobbleSize)),
                new KeyFrame(Duration.seconds(0.05), new KeyValue(this.fitHeightProperty(), originalFitHeight + wobbleSize)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(this.fitWidthProperty(), originalFitWidth)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(this.fitHeightProperty(), originalFitHeight)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(this.fitWidthProperty(), originalFitWidth - wobbleSize)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(this.fitHeightProperty(), originalFitHeight - wobbleSize)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(this.fitWidthProperty(), originalFitWidth)),
                new KeyFrame(Duration.seconds(0.1), new KeyValue(this.fitHeightProperty(), originalFitHeight))
        );

        wobbleAnimation.play();

    }





}

