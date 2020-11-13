package main.java.scene;

import main.java.manager.GameSceneManager;
import main.java.manager.KeyboardManager;

import java.awt.*;

public class PausedScene extends BaseScene {

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
        g.drawString("PRESS ENTER TO RESUME", (BOARD_WIDTH / 2 ) - 32, (BOARD_HEIGHT / 2 ) + 52);
        g.drawString("PRESS Q TO QUIT", (BOARD_WIDTH / 2 ) - 32, (BOARD_HEIGHT / 2 ) + 72);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void input(KeyboardManager keyboardManager) {
        if (keyboardManager.enter.clicked) {
            gsm.removeCurrentScene();
            gsm.paused = false;
        }
        if (keyboardManager.quit.clicked) {
            gsm.ingame = false;
        }
    }
}
