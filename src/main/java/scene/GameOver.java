package main.java.scene;

import main.java.entity.Sprite;
import main.java.manager.GameSceneManager;
import main.java.manager.KeyboardManager;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;


/**
 * 
 * @author
 */
public class GameOver extends BaseScene {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private final String gameOver = "/img/gameover.png";
	private Sprite sprite;
	private int width;

	/*
	 * Constructor
	 */
	public GameOver(GameSceneManager gsm) {
		super(gsm);

		ImageIcon ii = new ImageIcon(this.getClass().getResource(gameOver));
		sprite = new Sprite();

		setWidth(ii.getImage().getWidth(null));

		sprite.setImage(ii.getImage());
		sprite.setX(0);
		sprite.setY(0);
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
		if (keyboardManager.escape.down)
			gsm.ingame = false;

	}

	@Override
	public void update() {
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(sprite.getImage(), 0, 0, null);
		g.setColor(Color.WHITE);
		g.drawString("Press ESCAPE to quit or SPACE(not working yet) to play again", 200, 340);
		// TODO implement the ability for the game to be restarted
	}

	@Override
	public void dispose() {
	}
}
