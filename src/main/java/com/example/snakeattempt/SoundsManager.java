package com.example.snakeattempt;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class SoundsManager {

    private static final String[] SOUND_DIRECTORIES = {
            "/sounds/MenuMusic2.mp3",
            "/sounds/hoverSound.mp3",
            "/sounds/clickSound.mp3",
            "/sounds/panelShowing.mp3",
            "/sounds/eat1.mp3",
            "/sounds/eat2.mp3",
            "/sounds/eat3.mp3",
            "/sounds/game over.wav",
            "/sounds/poison2.mp3",
    };
    private static final int MAX_FILES = SOUND_DIRECTORIES.length;


    private final Media[] media = new Media[MAX_FILES];
    private final MediaPlayer music;
    private final MediaPlayer[] buttonSound = new MediaPlayer[2];
    private static final MediaPlayer[] eatingSounds = new MediaPlayer[3];
    private  static final MediaPlayer[] poisonSound=new MediaPlayer[1];
    private final MediaPlayer panelSound;

    SoundsManager() {

        for (int i = 0; i < MAX_FILES; i++) {
            media[i] = new Media(getClass().getResource(SOUND_DIRECTORIES[i]).toExternalForm());
        }

        // prepare music file
        music = new MediaPlayer(media[0]);
        music.setCycleCount(MediaPlayer.INDEFINITE);

        // prepare buttons sounds
        buttonSound[0] = new MediaPlayer(media[1]);
        buttonSound[1] = new MediaPlayer(media[2]);

        // prepare panelSound
        panelSound = new MediaPlayer(media[7]);
        panelSound.setCycleCount(1);

        // prepare eating sounds
        eatingSounds[0] = new MediaPlayer(media[4]);
        eatingSounds[1] = new MediaPlayer(media[5]);
        eatingSounds[2] = new MediaPlayer(media[6]);
        poisonSound[0]=new MediaPlayer(media[8]);
    }

    public void playMainMusic() {
        music.play();
    }

    public void stopMainMusic() {
        music.stop();
    }
    public void playMusic(boolean play){
        if(play)
            playMainMusic();
        else
            stopMainMusic();
    }

    public void playHoverSound() {
        stopAndPlay(buttonSound[0]);
    }

    public void playClickSound() {
        stopAndPlay(buttonSound[1]);
    }

    public void playPanelShowSound() {
        stopAndPlay(panelSound);
    }

    public  void playEatSound(int i) {
        stopAndPlay(eatingSounds[i]);
    }
    public  void playPoisonSound (){stopAndPlay(poisonSound[0]);}
    private  void stopAndPlay(MediaPlayer mediaPlayer) {

        mediaPlayer.stop();
        mediaPlayer.play();
    }


}
