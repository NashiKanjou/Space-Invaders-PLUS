
/** 
 * 
 * The MainGameScene file is being used now instead of this one. Most
 * changes are already in the new file, but there are still some that need
 * to be brought over and tested.
 * 
*/

import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import entity.Alien;
import entity.Bomb;
import scene.GameOver;
import entity.Player;
import entity.Shot;
import entity.Sprite;
import scene.Won;
import util.Commons;
import util.MapLoader;

/**
 *
 * @author
 */
public class Board extends JPanel implements Runnable, Commons {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Dimension d;
	private ArrayList<Sprite> aliens;
	private ArrayList<Sprite> map;
	private Player player;
	private Shot shot;
	private GameOver gameend;
	private Won vunnet;

	private int alienX = 150;
	private int alienY = 25;
	private int direction = -1;
	private int deaths = 0;

	private boolean ingame = true;
	private boolean havewon = true;
	private final String expl = "/img/explosion.png";
	private final String alienpix = "/img/alien.png";
	private String message = SpaceInvaders.lang.getEndingLoseMessage();
	private double angle = 0;

	private Thread animator;

	/*
	 * Constructor
	 */
	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		d = new Dimension(BOARD_WIDTH, BOARD_HEIGTH);
		setBackground(Color.black);

		gameInit();
		setDoubleBuffered(true);
	}

	public void addNotify() {
		super.addNotify();
		gameInit();
	}

	public void gameInit() {
		aliens = MapLoader.loadMap("C:\\Users\\mcohe\\Desktop\\test.txt", 30, 18);
		System.out.println("size: " + aliens.size());
		// map = MapLoader.loadMap("C:\\Users\\mcohe\\Desktop\\test.txt", 30, 18);
		// ImageIcon ii = new ImageIcon(this.getClass().getResource(alienpix));

		// for (int i = 0; i < 4; i++) {
		// for (int j = 0; j < 6; j++) {
		// Alien alien = new Alien(alienX + 18 * j, alienY + 18 * i);
		// alien.setImage(ii.getImage());
		// aliens.add(alien);
		// }
		// }

		player = new Player();
		shot = new Shot();

		if (animator == null || !ingame) {
			animator = new Thread(this);
			animator.start();
		}
	}

	public void drawMap(Graphics g) {
		for (Sprite s : map) {
			g.drawImage(s.getImage(), s.getX(), s.getY(), this);
		}
	}

	public void drawAliens(Graphics g) {
		Iterator it = aliens.iterator();

		while (it.hasNext()) {
			Alien alien = (Alien) it.next();

			if (alien.isVisible()) {
				g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
			}

			if (alien.isDying()) {
				alien.die();
			}
		}
	}

	public void drawPlayer(Graphics g) {
		if (player.isVisible()) {
			g.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}

		if (player.isDying()) {
			player.die();
			havewon = false;
			ingame = false;
		}
	}

	public void drawGameEnd(Graphics g) {
		// g.drawImage(gameend.getImage(), 0, 0, this);
	}

	public void drawShot(Graphics g) {
		if (shot.isVisible())
			g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
	}

	public void drawAim(Graphics g) {
		if (ingame) {
			g.setColor(Color.WHITE);

			g.drawLine(player.getX() + PLAYER_WIDTH / 2, player.getY() + PLAYER_HEIGHT / 2, // from the center of the
																							// player
					(int) (player.getX() + PLAYER_WIDTH / 2 - 20 * -Math.cos(angle * Math.PI / 180.0)), // draw end of
																										// line x a
																										// distance of
																										// 20 adj. for
																										// angle from
																										// center of
																										// player
					(int) (player.getY() + PLAYER_HEIGHT / 2 - 20 * Math.sin(angle * Math.PI / 180.0))); // draw end of
																											// line y a
																											// distance
																											// of 20
																											// adj. for
																											// angle
																											// from
																											// center of
																											// player
		}
	}

	public void drawBombing(Graphics g) {
		Iterator i3 = aliens.iterator();

		while (i3.hasNext()) {
			Alien a = (Alien) i3.next();

			Bomb b = a.getBomb();

			if (!b.isDestroyed()) {
				g.drawImage(b.getImage(), b.getX(), b.getY(), this);
			}
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.black);
		g.fillRect(0, 0, d.width, d.height);
		g.setColor(Color.green);

		if (ingame) {

			g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
			drawAliens(g);
			// drawMap(g);
			drawPlayer(g);
			drawShot(g);
			drawBombing(g);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void gameOver() {
		Graphics g = this.getGraphics();

		// gameend = new GameOver();
		// vunnet = new Won();

		// g.setColor(Color.black);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGTH);
		if (havewon == true) {
			// g.drawImage(vunnet.getImage(), 0, 0, this);
		} else {
			// g.drawImage(gameend.getImage(), 0, 0, this);
		}
		g.setColor(new Color(0, 32, 48));
		g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
		g.setColor(Color.white);
		g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2, BOARD_WIDTH / 2);
	}

	// public void animationCycle() {
	// if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
	// ingame = false;
	// message = SpaceInvaders.lang.getEndingWinMessage();
	// }

	// // player

	// player.act();

	// // shot
	// if (shot.isVisible()) {
	// Iterator it = aliens.iterator();
	// int shotX = shot.getX();
	// int shotY = shot.getY();

	// while (it.hasNext()) {
	// Alien alien = (Alien) it.next();
	// int alienX = alien.getX();
	// int alienY = alien.getY();

	// if (alien.isVisible() && shot.isVisible()) {
	// if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) && shotY >= (alienY)
	// && shotY <= (alienY + ALIEN_HEIGHT)) {
	// ImageIcon ii = new ImageIcon(getClass().getResource(expl));
	// alien.setImage(ii.getImage());
	// alien.setDying(true);
	// deaths++;
	// shot.die();
	// }
	// }
	// }
	// // do aiming code here
	// // do shot.setY and .setX to fit position of aiming place
	// // using mouse or keyboard.

	// Point p = MouseInfo.getPointerInfo().getLocation();
	// int mouseX = p.x;
	// int mouseY = p.y;
	// // do trig between mouse and spaceship to know trajectory
	// // include to update X as well
	// // SHOT POSITION UPDATING LINE
	// int y = shot.getY();
	// int x = shot.getX();
	// // shooting angle

	// double rads = angle * Math.PI / 180.0;

	// // SHOT TRAVEL SPEED
	// int shotSpeed = 8;
	// // shot direction in x and y coordinates

	// x += (int) (shotSpeed * Math.cos(rads));
	// y -= (int) (shotSpeed * Math.sin(rads));

	// if (y < 0 || x < 0 || x > BOARD_WIDTH || y > BOARD_HEIGTH) // if shot hits
	// borders
	// shot.die(); // shot dies
	// else { // else keep shot moving

	// int y = shot.getY();
	// y -= 8;
	// if (y < 0)
	// shot.die();
	// else
	// shot.setY(y);
	// }

	// // aliens

	// Iterator it1 = aliens.iterator();

	// while (it1.hasNext()) {
	// Alien a1 = (Alien) it1.next();
	// int x = a1.getX();

	// if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
	// direction = -1;
	// Iterator i1 = aliens.iterator();
	// while (i1.hasNext()) {
	// Alien a2 = (Alien) i1.next();
	// a2.setY(a2.getY() + GO_DOWN);
	// }
	// }

	// if (x <= BORDER_LEFT && direction != 1) {
	// direction = 1;

	// Iterator i2 = aliens.iterator();
	// while (i2.hasNext()) {
	// Alien a = (Alien) i2.next();
	// a.setY(a.getY() + GO_DOWN);
	// }
	// }
	// }

	// Iterator it = aliens.iterator();

	// while (it.hasNext()) {
	// Alien alien = (Alien) it.next();
	// if (alien.isVisible()) {

	// int y = alien.getY();

	// if (y > GROUND - ALIEN_HEIGHT) {
	// havewon = false;
	// ingame = false;
	// message = SpaceInvaders.lang.getInvadeMessage();
	// }

	// alien.act(direction);
	// }
	// }

	// // bombs

	// Iterator i3 = aliens.iterator();
	// Random generator = new Random();

	// while (i3.hasNext()) {
	// int shot = generator.nextInt(15);
	// Alien a = (Alien) i3.next();
	// Bomb b = a.getBomb();
	// if (shot == CHANCE && a.isVisible() && b.isDestroyed()) {

	// b.setDestroyed(false);
	// b.setX(a.getX());
	// b.setY(a.getY());
	// }

	// int bombX = b.getX();
	// int bombY = b.getY();
	// int playerX = player.getX();
	// int playerY = player.getY();

	// if (player.isVisible() && !b.isDestroyed()) {
	// if (bombX >= (playerX) && bombX <= (playerX + PLAYER_WIDTH) && bombY >=
	// (playerY)
	// && bombY <= (playerY + PLAYER_HEIGHT)) {
	// ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
	// player.setImage(ii.getImage());
	// player.setDying(true);
	// b.setDestroyed(true);
	// int health = player.damage();
	// if(health<=0) {
	// ImageIcon ii = new ImageIcon(this.getClass().getResource(
	// expl));
	// player.setImage(ii.getImage());
	// player.setDying(true);
	// }
	// }
	// }

	// if (!b.isDestroyed()) {
	// b.setY(b.getY() + 1);
	// if (b.getY() >= GROUND - BOMB_HEIGHT) {
	// b.setDestroyed(true);
	// }
	// }
	// }
	// }

	public void run() {
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (ingame) {
			repaint();
			// animationCycle();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0)
				sleep = 1;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}
			beforeTime = System.currentTimeMillis();
		}
		gameOver();
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {

			player.keyPressed(e);

			int x = player.getX();
			int y = player.getY();

			if (ingame) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_SPACE) {

					if (!shot.isVisible())
						shot = new Shot(x, y);
				}
			}
		}
	}
}