package main.java.scene;

import main.java.manager.GameSceneManager;
import main.java.manager.InputManager;

import java.awt.*;

public class PausedScene extends BaseScene {

    private InputManager.InputSource inputSource = null;

    public PausedScene(GameSceneManager gsm) {
        super(gsm);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("GAME PAUSED", (BOARD_WIDTH / 2 ) - 32, (BOARD_HEIGHT / 2 ) - 32);

        if (inputSource == null || inputSource == InputManager.InputSource.KEYBOARD) {
            g.drawString("PRESS ESCAPE TO RESUME", (BOARD_WIDTH / 2 ) - 60, (BOARD_HEIGHT / 2 ) + 52);
            g.drawString("PRESS Q TO QUIT", (BOARD_WIDTH / 2 ) - 50, (BOARD_HEIGHT / 2 ) + 72);
        } else {
            g.drawString("PRESS START TO RESUME", (BOARD_WIDTH / 2 ) - 60, (BOARD_HEIGHT / 2 ) + 52);
            g.drawString("PRESS BACK TO QUIT", (BOARD_WIDTH / 2 ) - 50, (BOARD_HEIGHT  / 2 ) + 72);
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void input(InputManager inputManager) {
        inputSource = inputManager.getCurrentInputSource();
        if (inputManager.quit.clicked) {
            gsm.ingame = false;
        }
    }
}
