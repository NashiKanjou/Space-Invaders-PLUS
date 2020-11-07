package main.java.manager;

import main.java.scene.IScene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.Stack;

import javax.swing.JPanel;


public class GameSceneManager {
    private Stack<IScene> scenes;
//    public final JPanel panel;
//    private final Graphics g;

    public boolean ingame;

    public GameSceneManager() {
        ingame = true;
        scenes = new Stack<>();
    }

    public void input(KeyboardManager keyboardManager) {
        if (!scenes.empty())
            scenes.peek().input(keyboardManager);
    }

    /**
     * Adds the new scene and sets it as the current scene that will be updated and
     * rendered per frame
     * 
     * @param scene The scene to be add and marked as the current scene to be
     *              displayed
     */
    public void addScene(IScene scene) {
        if (scene != null) {
            scenes.push(scene);
        }
    }

    /**
     * Adds the new scene and sets it as the current scene that will be updated and
     * rendered per frame
     * 
     * @param scene              The scene to be add and marked as the current scene
     *                           to be displayed
     * 
     * @param removeCurrentScene Whether the current scene should be disposed before
     *                           adding the new scene
     */
    public void addScene(IScene scene, boolean removeCurrentScene) {
        if (scene != null) {
            if (removeCurrentScene && !scenes.empty()) {
                scenes.pop().dispose();
            }
            this.addScene(scene);
        }
    }

    public boolean removeCurrentScene() {
        if (scenes.size() > 0) {
            scenes.pop().dispose();
            return true;
        }

        return false;
    }

    public void update() {
        if (scenes.size() > 0) {
            scenes.peek().update();
        }
    }

    public void draw(Graphics g) {
        if (scenes != null && scenes.size() > 0) {
            scenes.peek().draw(g);
        }
    }

    public boolean isIngame() {
        return ingame;
    }

    public void dispose() {
        while (!scenes.empty()) {
            scenes.pop().dispose();
        }
    }
}