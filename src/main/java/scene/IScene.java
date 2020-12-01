package main.java.scene;

import main.java.manager.InputManager;

import java.awt.Graphics;


public interface IScene {

    /**
     * Is responsible for updating the logic for a scene/level that is called each
     * frame
     */
    void update();

    /**
     * Is responsible for rendering what is viewed by the user that is called each
     * frame
     */
    void draw(Graphics g);

    /**
     * Can be used to cleanup any objects that are only used within a scene such as
     * images
     */
    void dispose();

    /**
     * Gives the current scene access to the KeyboardManager to preform any input
     * actions
     */
    void input(InputManager inputManager);
}
