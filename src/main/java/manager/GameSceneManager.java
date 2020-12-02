package main.java.manager;

import main.java.scene.IScene;
import main.java.scene.PausedScene;

import java.awt.Graphics;
import java.util.Stack;


public class GameSceneManager {
    private Stack<IScene> scenes;

    public boolean paused;
    public boolean ingame;
    private boolean canStateBePaused = true;

    public GameSceneManager() {
        ingame = paused = false;
        scenes = new Stack<>();
    }

    public void input(InputManager inputManager) {
        if (canStateBePaused && inputManager.escape.clicked) {
            if (!paused) {
                paused = true;
                addScene(new PausedScene(this));
            } else {
                System.out.println("game is already paused. un-pause");
                this.removeCurrentScene();
                paused = false;
            }
        } else {
            if (!scenes.empty())
                scenes.peek().input(inputManager);
        }
    }

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
     *
     * @param canStateBePaused Tells the GSM which state can go into a paused state
     */
    public void addScene(IScene scene, boolean removeCurrentScene, boolean canStateBePaused) {
        if (scene != null) {
            if (removeCurrentScene && !scenes.empty()) {
                scenes.pop().dispose();
            }
            this.canStateBePaused = canStateBePaused;
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
        if (scenes.size() > 0) {
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