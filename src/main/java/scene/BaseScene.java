package main.java.scene;

import main.java.manager.GameSceneManager;
import main.java.util.Commons;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public abstract class BaseScene extends JPanel implements Commons, IScene {
    private static final long serialVersionUID = 1L;
    protected Dimension d;
    protected GameSceneManager gsm;

    public BaseScene(GameSceneManager gsm) {
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
        this.gsm = gsm;
    }

    /**
     * Is responsible for updating the logic for a scene/level that is called each
     * frame
     */
    public abstract void update();

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
}
