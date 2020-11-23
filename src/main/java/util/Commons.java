package main.java.util;

/**
 * 
 * @author
 */
public interface Commons {

    public static final int BOARD_WIDTH = 300;
    public static final int BOARD_HEIGHT = BOARD_WIDTH / 4 * 5; // a 4/5 aspect ratio
    public static final int GRAPHICS_SCALE = 2;
    public static final int GROUND = BOARD_HEIGHT - 30;
    public static final int BOMB_HEIGHT = 5;
    public static final int ALIEN_HEIGHT = 25;
    public static final int ALIEN_WIDTH = 25;
    public static final int BORDER_RIGHT = 30;
    public static final int BORDER_LEFT = 30;
    public static final int ALIEN_DROP_SPEED = 25;
    public static final int NUMBER_OF_ALIENS_TO_DESTROY = 24;
    public static final int CHANCE = 5;
    public static final int DELAY = 17;
    public static final int PLAYER_WIDTH = 32;
    public static final int PLAYER_HEIGHT = 32;
    public static final int PLAYER_SPEED = 200;
    public static final int DEFAULT_MAX_HEALTH = 5;
    public static final long DEFAULT_SHOT_CD = 1000;
    public static final long MIN_SHOT_CD = 100; //recommended
    public static final double UPDATE_PER_SECOND = 60.0;
    public static final int ALIEN_START_X_POSITION = 150;
    public static final int ALIEN_START_Y_POSITION = 20;
}
