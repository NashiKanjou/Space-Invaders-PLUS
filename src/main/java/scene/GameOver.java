package main.java.scene;

import main.java.graphics.Sprite;
import main.java.manager.GameSceneManager;
import main.java.manager.KeyboardManager;
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
	private String gameOveMsg;

	/*
	 * Constructor
	 */
	public GameOver(GameSceneManager gsm) {
		super(gsm);

		ImageIcon ii = new ImageIcon(this.getClass().getResource(gameOver));
		sprite = new Sprite();

		setWidth(ii.getImage().getWidth(null));

		sprite.setImage(ii.getImage());
		sprite.setX((BOARD_WIDTH - ii.getIconWidth()) / 2);
		sprite.setY((BOARD_HEIGHT - (ii.getIconHeight() * GRAPHICS_SCALE)) / 2);

		gameOveMsg = "Press Q to quit or ENTER to play again";
	}

	/*
	 * Getters & Setters
	 */

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void input(KeyboardManager keyboardManager) {
		if (keyboardManager.quit.clicked)
			gsm.ingame = false;

		if (keyboardManager.enter.clicked)
			gsm.addScene(new MainGameScene(gsm), true);
	}

	@Override
	public void update() {
	}

	@Override
	public void drawHealth(Graphics g) {

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), null);
		g.setColor(Color.WHITE);
		g.drawString(gameOveMsg, gameOveMsg.length() * 3 / 2, 340);
		// TODO implement the ability for the game to be restarted
	}

	@Override
	public void dispose() {
	}
}
