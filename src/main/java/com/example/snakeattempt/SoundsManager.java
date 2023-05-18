package com.example.snakeattempt;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundsManager {


    private final String musicFile = "/sounds/Music.mp3";

    private  final Media media = new Media(getClass().getResource(musicFile).toExternalForm());

    private  final MediaPlayer mediaPlayer = new MediaPlayer(media);

    SoundsManager(){

    }

    public  void playMainMusic(){
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        mediaPlayer.play();
    }
    public  void stopMainMusic(){

        mediaPlayer.stop();
    }


}
