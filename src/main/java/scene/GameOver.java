package main.java.scene;

import main.java.graphics.Sprite;
import main.java.manager.GameSceneManager;
import main.java.manager.InputManager;
import main.java.util.Commons;

import javax.swing.*;
import java.awt.*;


/**
 * 
 * @author
 */
public class GameOver extends BaseScene implements Commons {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final String gameOver = "/img/gameover.png";
	private Sprite sprite;
	private int width;
	private String gameOverMsg;

	/*
	 * Constructor
	 */
	public GameOver(GameSceneManager gsm) {
		super(gsm);

		ImageIcon ii = new ImageIcon(this.getClass().getResource(gameOver));
		sprite = new Sprite();

		sprite.setImage(ii.getImage());
		sprite.setX((BOARD_WIDTH - ii.getIconWidth()) / 2);
		sprite.setY((BOARD_HEIGHT - (ii.getIconHeight() * GRAPHICS_SCALE)) / 2);

		gameOverMsg = "Press ENTER to play again or Q to quit";
	}

	@Override
	public void input(InputManager inputManager) {
		super.input(inputManager);
		if (inputManager.quit.clicked)
			gsm.ingame = false;

		if (inputManager.enter.clicked)
			gsm.addScene(new MainGameScene(gsm), true, true);
	}

	@Override
	public void update() {
		if (inputSource == null || inputSource == InputManager.InputSource.KEYBOARD) {
			gameOverMsg = "Press ENTER to play again or Q to quit";
		} else {
			gameOverMsg = "PRESS Y to play again or SELECT to quit";
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), null);
		g.setColor(Color.WHITE);

		g.drawString(gameOverMsg, 39, 340);
		// TODO implement the ability for the game to be restarted
	}

	@Override
	public void dispose() {
	}
}
