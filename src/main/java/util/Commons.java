package main.java.util;

import java.awt.*;

/**
 * 
 * @author
 */
public interface Commons {

    int BOARD_WIDTH = 300;
    int BOARD_HEIGHT = BOARD_WIDTH / 4 * 5; // a 4/5 aspect ratio
    int GRAPHICS_SCALE = 2;
    int GROUND = BOARD_HEIGHT - 30;
    int BOMB_HEIGHT = 5;
    int ALIEN_HEIGHT = 25;
    int ALIEN_WIDTH = 25;
    int BORDER_RIGHT = 30;
    int BORDER_LEFT = 30;
    int ALIEN_DROP_SPEED = 25;
    int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    int CHANCE = 5;
    int DELAY = 17;
    int PLAYER_WIDTH = 32;
    int PLAYER_HEIGHT = 32;
    int PLAYER_SPEED = 2;
    int DEFAULT_MAX_HEALTH = 5;
    long DEFAULT_SHOT_CD = 1000;
    long MIN_SHOT_CD = 100; //recommended
    double UPDATE_PER_SECOND = 60.0;
    int ALIEN_START_X_POSITION = 150;
    int ALIEN_START_Y_POSITION = 20;
}
