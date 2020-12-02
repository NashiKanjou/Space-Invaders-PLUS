package main.java.scene;

import main.java.graphics.Sprite;
import main.java.manager.GameSceneManager;
import main.java.manager.InputManager;

import javax.swing.*;
import java.awt.*;


/**
 * 
 * @author
 */
public class Won extends BaseScene {
    private static final long serialVersionUID = 1L;
    private Sprite sprite;
    private final String won = "/img/won.jpg";
    private int width;
    private Image image;
    private String gameWonMsg;

    /*
     * Constructor
     */
    public Won(GameSceneManager gsm) {
        super(gsm);
        sprite = new Sprite();
        ImageIcon ii = new ImageIcon(this.getClass().getResource(won));
        sprite.setImage(ii.getImage());

        width = ii.getImage().getWidth(null);

        sprite.setX(0);
        sprite.setY(0);
        gameWonMsg = "Press ENTER to play again or Q to quit";
    }

    @Override
    public void update() {
        if (inputSource == null || inputSource == InputManager.InputSource.KEYBOARD) {
            gameWonMsg = "Press ENTER to play again or Q to quit";
        } else {
            gameWonMsg = "PRESS Y to play again or SELECT to quit";
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite.getImage(), 0, 0, null);
        g.setColor(Color.WHITE);

        g.drawString(gameWonMsg, 39, 340);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void input(InputManager inputManager) {
        super.input(inputManager);
        if (inputManager.quit.clicked)
            gsm.ingame = false;

        if (inputManager.enter.clicked)
            gsm.addScene(new MainGameScene(gsm), true, true);
    }
}
