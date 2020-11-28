package main.java.entity;

import main.java.graphics.Sprite;

import javax.swing.ImageIcon;

/**
 * 
 * @author
 */
public class Bomb extends Sprite {

	private final String bomb = "/img/bomb.png";
	private boolean destroyed;

	/*
	 * Constructor
	 */
	public Bomb(int x, int y) {
		setDestroyed(true);
		this.setX(x);
		this.setY(y);
		ImageIcon ii = new ImageIcon(this.getClass().getResource(bomb));
		setImage(ii.getImage());
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
}
