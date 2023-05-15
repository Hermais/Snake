package com.example.snakeattempt;

import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Buttons extends ImageView {

    private String imageDirectory;

    private final double swellSize = 20;
    private Image btnImage;

    private double fitXY;

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





}

