package main.java.scene;

import main.java.manager.GameSceneManager;
import main.java.manager.InputManager;
import main.java.util.Commons;

import javax.swing.*;
import java.awt.*;


public abstract class BaseScene implements Commons, IScene {
    private static final long serialVersionUID = 1L;
    protected Dimension d;
    protected GameSceneManager gsm;
    protected InputManager.InputSource inputSource = null;

    public BaseScene(GameSceneManager gsm) {
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        this.gsm = gsm;
    }

    /**
     * Is responsible for updating the logic for a scene/level that is called each
     * frame
     */
    public abstract void update();

    /**
     * Is responsible for rendering player health that is called each
     * frame
     */
//    public abstract void drawHealth(Graphics g);

    /**
     * Is responsible for rendering what is viewed by the user that is called each
     * frame
     */
    public abstract void draw(Graphics g);

    /**
     * Can be used to cleanup any objects that are only used within a scene such as
     * images
     */
    public abstract void dispose();

    @Override
    public void input(InputManager inputManager) {
        this.inputSource = inputManager.getCurrentInputSource();
    }
}
