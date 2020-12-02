package main.java.scene;

import main.java.manager.GameSceneManager;
import main.java.manager.InputManager;

import java.awt.Graphics;
import java.awt.Color;


public class PausedScene extends BaseScene {

    private String topMsg, bottomMsg;
    private int xOffset;

    public PausedScene(GameSceneManager gsm) {
        super(gsm);
        topMsg = "PRESS ESCAPE TO RESUME";
        bottomMsg = "PRESS Q TO QUIT";
        xOffset = 65;
    }

    @Override
    public void update() {
        if (inputSource == null || inputSource == InputManager.InputSource.KEYBOARD) {
            topMsg = "PRESS ESCAPE TO RESUME";
            bottomMsg = "PRESS Q TO QUIT";
            xOffset = 40;
        } else {
            topMsg = "PRESS START TO RESUME";
            bottomMsg = "PRESS SELECT TO QUIT";
            xOffset = 65;
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawString("GAME PAUSED", (BOARD_WIDTH / 2 ) - 40, (BOARD_HEIGHT / 2 ) - 32);

        g.drawString(topMsg, (BOARD_WIDTH / 2 ) - 70, (BOARD_HEIGHT / 2 ) + 52);
        g.drawString(bottomMsg, (BOARD_WIDTH / 2 ) - xOffset, (BOARD_HEIGHT / 2 ) + 72);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void input(InputManager inputManager) {
        super.input(inputManager);
        if (inputManager.quit.clicked) {
            gsm.ingame = false;
        }
    }
}
