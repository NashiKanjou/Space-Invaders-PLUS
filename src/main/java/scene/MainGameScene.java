package scene;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;

import entity.Alien;
import entity.Bomb;
import entity.Player;
import entity.Shot;
import entity.Sprite;
import manager.GameSceneManager;
import manager.KeyboardManager;
import util.MapLoader;

public class MainGameScene extends BaseScene {
    private static final long serialVersionUID = 1L;

    private ArrayList<Sprite> aliens;
    private Player player;
    private Shot shot;
    private GameOver gameend;
    private Won vunnet;
    private boolean havewon = true;
    private final String expl = "/img/explosion.png";
    private final String alienpix = "/img/alien.png";

    private int direction = -1;
    private int deaths = 0;

    private double angle = 0; // aiming angle for shooting, default straight

    public MainGameScene(GameSceneManager gsm) {
        super(gsm);
        init();
    }

    private void init() {
        aliens = MapLoader.loadMap("C:\\Users\\mcohe\\Desktop\\test.txt", 30, 18);
        player = new Player();
        shot = new Shot();
    }

    @Override
    public void update() {
        player.act();
        animationCycle();
    }

    @Override
    public void input(KeyboardManager keyboardManager) {

        // update the player movement on key events
        if (keyboardManager.left.down) {
            player.setDx(-2);
        } else if (keyboardManager.right.down) {
            player.setDx(2);
        } else {
            player.setDx(0);
        }

        if (keyboardManager.up.down) {
            player.setDy(-2);
        } else if (keyboardManager.down.down) {
            player.setDy(2);
        } else {
            player.setDy(0);
        }

        // shoot the bullet
        if (keyboardManager.space.down) {
            if (!shot.isVisible())
                shot = new Shot(player.getX(), player.getY());
        }

        // change the shot angle
        if (keyboardManager.angleDec.clicked) {
            angle += 15;
        }
        if (keyboardManager.angleInc.clicked) {
            angle -= 15;
        }

        // check for quit
        if (keyboardManager.escape.clicked)
            gsm.ingame = false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
        drawAliens(g);
        drawPlayer(g);
        drawShot(g);
        drawBombing(g);
        drawAim(g);
    }

    public void drawAliens(Graphics g) {
        Iterator<Sprite> it = aliens.iterator();

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

    public void drawShot(Graphics g) {
        if (shot.isVisible())
            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
    }

    public void drawBombing(Graphics g) {
        Iterator<Sprite> i3 = aliens.iterator();

        while (i3.hasNext()) {
            Alien a = (Alien) i3.next();

            Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    public void drawAim(Graphics g) {
        if (gsm.ingame) {
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
                                                                                                         // distance of
                                                                                                         // 20 adj. for
                                                                                                         // angle from
                                                                                                         // center of
                                                                                                         // player
        }
    }

    @Override
    public void dispose() {
    }

    public void drawPlayer(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }

        if (player.isDying()) {
            player.die();
            havewon = false;
            // TODO go to the game over screen when the player dies
            gsm.addScene(new GameOver(gsm), true);
        }
    }

    public void animationCycle() {
        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            // player won
            gsm.addScene(new Won(gsm), true);
        }

        // player

        player.act();

        // shot
        if (shot.isVisible()) {
            Iterator<Sprite> it = aliens.iterator();
            int shotX = shot.getX();
            int shotY = shot.getY();

            while (it.hasNext()) {
                Alien alien = (Alien) it.next();
                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) && shotY >= (alienY)
                            && shotY <= (alienY + ALIEN_HEIGHT)) {
                        ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }
            // do aiming code here
            // do shot.setY and .setX to fit position of aiming place
            // using mouse or keyboard.

            Point p = MouseInfo.getPointerInfo().getLocation();
            int mouseX = p.x;
            int mouseY = p.y;
            // do trig between mouse and spaceship to know trajectory
            // include to update X as well
            // SHOT POSITION UPDATING LINE
            int y = shot.getY();
            int x = shot.getX();
            // shooting angle

            double rads = angle * Math.PI / 180.0;

            // SHOT TRAVEL SPEED
            int shotSpeed = 8;
            // shot direction in x and y coordinates

            x += (int) (shotSpeed * Math.cos(rads));
            y -= (int) (shotSpeed * Math.sin(rads));

            if (y < 0 || x < 0 || x > BOARD_WIDTH || y > BOARD_HEIGTH) // if shot hits borders
                shot.die(); // shot dies
            else { // else keep shot moving
                shot.setY(y);
                shot.setX(x);
            }
        }

        // aliens

        Iterator<Sprite> it1 = aliens.iterator();

        while (it1.hasNext()) {
            Alien a1 = (Alien) it1.next();
            int x = a1.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
                Iterator<Sprite> i1 = aliens.iterator();
                while (i1.hasNext()) {
                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;

                Iterator<Sprite> i2 = aliens.iterator();
                while (i2.hasNext()) {
                    Alien a = (Alien) i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }

        Iterator<Sprite> it = aliens.iterator();

        while (it.hasNext()) {
            Alien alien = (Alien) it.next();
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    // player lost
                    havewon = false;
                    gsm.addScene(new GameOver(gsm), true);
                    // ingame = false;
                    // message = "Aliens estão invadindo a galáxia!";
                }

                alien.act(direction);
            }
        }

        // bombs

        Iterator<Sprite> i3 = aliens.iterator();
        Random generator = new Random();

        while (i3.hasNext()) {
            int shot = generator.nextInt(15);
            Alien a = (Alien) i3.next();
            Bomb b = a.getBomb();
            if (shot == CHANCE && a.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {
                if (bombX >= (playerX) && bombX <= (playerX + PLAYER_WIDTH) && bombY >= (playerY)
                        && bombY <= (playerY + PLAYER_HEIGHT)) {
                    ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    b.setDestroyed(true);
                    ;
                }
            }

            if (!b.isDestroyed()) {
                b.setY(b.getY() + 1);
                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
    }

}
