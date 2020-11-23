package main.java.scene;

import main.java.entity.*;
import main.java.graphics.AlienAnimationCycle;
import main.java.graphics.Sprite;
import main.java.manager.AssetManager;
import main.java.manager.GameSceneManager;
import main.java.manager.KeyboardManager;
import main.java.util.Map;
import main.java.util.MapLoader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;



public class MainGameScene extends BaseScene {
    private static final long serialVersionUID = 1L;

    private ArrayList<Sprite> aliens;
    private ArrayList<Shot> shots;
    private Player player;
    private Map gameMap;
//    private Shot shot;
    private GameOver gameend;
    private Won vunnet;
    private boolean havewon = true;
    private final String expl = "/img/explosion.png";
    private final String alienpix = "/img/alien.png";
    private AlienAnimationCycle alienAnimationCycle;

//    private int direction = -1;
    private int deaths = 0;

    private double angle = 0; // aiming angle for shooting, default straight

    public MainGameScene(GameSceneManager gsm) {
        super(gsm);
        init();
    }

    private void init() {
        // load the map
        gameMap = MapLoader.loadMap("levels\\testlevel.txt", 30, 18);
        // get a reference to the list of aliens
        aliens = gameMap.getSprites();
        player = new Player();
        shots = new ArrayList<>();
        alienAnimationCycle = new AlienAnimationCycle(aliens, player.getAnimatedSprite(), 10);
    }

    @Override
    public void update() {
        player.act();

        // check for game over
        if (deaths == gameMap.getNumberOfEnemies()) {
            // player won
            // TODO add message to the Won scene
            // message = SpaceInvaders.lang.getEndingWinMessage();
            gsm.addScene(new Won(gsm), true);
        }
        alienAnimationCycle.animate();
        preformShooting();

//        animationCycle();
    }

    @Override
    public void input(KeyboardManager keyboardManager) {
        var playerSprite = player.getAnimatedSprite();

        // update the player movement on key events
        if (keyboardManager.left.down) {
            playerSprite.setFrames(AssetManager.getInstance().getFrames(AssetManager.Assets.PLAYER_LEFT));
            playerSprite.setDx(-2);
        } else if (keyboardManager.right.down) {
            playerSprite.setFrames(AssetManager.getInstance().getFrames(AssetManager.Assets.PLAYER_RIGHT));
            playerSprite.setDx(2);
        } else {
            playerSprite.setDx(0);
            playerSprite.setFrames(AssetManager.getInstance().getFrames(AssetManager.Assets.PLAYER_IDLE));
        }

        if (keyboardManager.up.down) {
            playerSprite.setDy(-2);
        } else if (keyboardManager.down.down) {
            playerSprite.setDy(2);
        } else {
            playerSprite.setDy(0);
        }

        // shoot the bullet
        if (keyboardManager.space.down) {
            if (player.canShoot()) {
               int m = player.getMultiTrajectoryProjectiles();
                int i = m / 2;
                var x = playerSprite.getX();
                var y = playerSprite.getY();

                for (int a = 0; a < i; a++) {
                    int b = a - i;
                    shots.add(new Shot(x - 5 * b, y,angle));
                    shots.add(new Shot(x + 5 * b, y,angle));
                }
                if (m % 2 != 0) {
                    shots.add(new Shot(x, y,angle));
                }
                /*
                 * if(player.isDoubleTrajectoryProjectiles()) { shots.add(new Shot(x-5, y));
                 * shots.add(new Shot(x+5, y)); }else{ shots.add(new Shot(x, y)); }
                 */
            }
        }

        // change the shot angle
        if (keyboardManager.angleDec.clicked) {
            angle += 15;
        }
        if (keyboardManager.angleInc.clicked) {
            angle -= 15;
        }

        // check for quit
        //TODO Remove quit from game level and make into a paused screen that has an option to quit or resume
//        if (keyboardManager.escape.clicked)
//            gsm.ingame = false;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.green);

        g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
        drawAliens(g);
        drawPlayer(g);
        drawShot(g);
        drawBombing(g);
        drawAim(g);
    }

    public void drawAliens(Graphics g) {

        for (Sprite sprite : aliens) {
            Alien alien = (Alien) sprite;

            if (alien.isVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }

            if (alien.isDying()) {
                alien.die();
            }
        }
    }

    public void drawShot(Graphics g) {
//        if (shot.isVisible())
//            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        for (int i = 0; i < shots.size(); i++) {
            try {
                Shot shot = shots.get(i);
                if (shot.isVisible() && !shot.isDying())
                    g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
            } catch (Exception e) {
                // porb null, current modify, out of bound
            }
        }
    }

    public void drawBombing(Graphics g) {

        for (Sprite alien : aliens) {
            Alien a = (Alien) alien;

            Bomb b = a.getBomb();

            if (!b.isDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    public void drawAim(Graphics g) {
        var playerSprite = player.getAnimatedSprite();

        if (gsm.ingame) {
            g.setColor(Color.WHITE);

            g.drawLine(playerSprite.getX() + PLAYER_WIDTH / 2, playerSprite.getY() + PLAYER_HEIGHT / 2, // from the center of the
                                                                                            // player
                    (int) (playerSprite.getX() + PLAYER_WIDTH / 2 - 20 * -Math.cos(angle * Math.PI / 180.0)), // draw end of
                                                                                                        // line x a
                                                                                                        // distance of
                                                                                                        // 20 adj. for
                                                                                                        // angle from
                                                                                                        // center of
                                                                                                        // player
                    (int) (playerSprite.getY() + PLAYER_HEIGHT / 2 - 20 * Math.sin(angle * Math.PI / 180.0))); // draw end of
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
        var playerSprite = player.getAnimatedSprite();

        if (playerSprite.isVisible()) {
            g.drawImage(player.getAnimatedSprite().getImage(), player.getAnimatedSprite().getX(), player.getAnimatedSprite().getY(), null);
        }

        if (playerSprite.isDying()) {
            playerSprite.die();
            havewon = false;
            // TODO go to the game over screen when the player dies
            gsm.addScene(new GameOver(gsm), true);
        }
    }

    private void preformShooting() {
        // new shooting
        ArrayList<Shot> temp = new ArrayList<>();
        for (int i = 0; i < shots.size(); i++) {
            try {
                Shot shot = shots.get(i);
                if (shot.isVisible()) {
                    Iterator it = aliens.iterator();
                    int shotX = shot.getX();
                    int shotY = shot.getY();

                    while (it.hasNext()) {
                        Alien alien = (Alien) it.next();
                        int alienX = alien.getX();
                        int alienY = alien.getY();

                        if (alien.isVisible() && shot.isVisible() && !alien.isDying()) {
                            if (shotX >= (alienX) && shotX <= (alienX + ALIEN_WIDTH) && shotY >= (alienY)
                                    && shotY <= (alienY + ALIEN_HEIGHT)) {
                                ImageIcon ii = new ImageIcon(getClass().getResource(expl));
                                alien.setImage(ii.getImage());
                                alien.setDying(true);
                                deaths++;
                                shot.die();
                                temp.add(shot);
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

                    double rads = shot.getAngle() * Math.PI / 180.0;

                    // SHOT TRAVEL SPEED
                    int shotSpeed = 8;
                    // shot direction in x and y coordinates

                    x += (int) (shotSpeed * Math.cos(rads));
                    y -= (int) (shotSpeed * Math.sin(rads));
                    if (y < 0 || x < 0 || x > BOARD_WIDTH || y > BOARD_HEIGHT) {
                        shot.die();
                        temp.add(shot);
                    } else {
                        shot.setY(y);
                        shot.setX(x);
                    }
                }
            } catch (Exception e) {
            }
        }
        shots.removeAll(temp);
    }
}