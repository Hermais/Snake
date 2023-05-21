package com.example.snakeattempt;

import javafx.scene.input.KeyCode;

import static com.example.snakeattempt.SnakeEngine.*;

public class SnakeMovementsControl {



    public static void movementChecker(){
        mainScene.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            if(invertedCounter < 1) {
                if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
                    if (currentDirection != DOWN)
                        currentDirection = UP;
                } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {
                    if (currentDirection != UP)
                        currentDirection = DOWN;
                } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
                    if (currentDirection != LEFT)
                        currentDirection = RIGHT;
                } else if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
                    if (currentDirection != RIGHT)
                        currentDirection = LEFT;
                }else if(keyCode == KeyCode.M){
                    isMute = !isMute;
                    soundsManager.playMusic(!isMute);

                }
            }
            else {
                if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
                    if (currentDirection != UP)
                        currentDirection = DOWN;
                } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {
                    if (currentDirection != DOWN)
                        currentDirection = UP;
                } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
                    if (currentDirection != RIGHT)
                        currentDirection = LEFT;
                } else if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
                    if (currentDirection != LEFT)
                        currentDirection = RIGHT;
                }else if(keyCode == KeyCode.M){
                    isMute = !isMute;
                    soundsManager.playMusic(!isMute);

                }
            }
        });
    }
}
