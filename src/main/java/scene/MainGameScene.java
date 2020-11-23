package main.java.scene;

import main.java.entity.*;
import main.java.manager.GameSceneManager;
import main.java.manager.KeyboardManager;
import main.java.util.Map;
import main.java.util.MapLoader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;



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

    private int direction = -1;
    private int deaths = 0;

    private double angle = 0; // aiming angle for shooting, default straight

    public MainGameScene(GameSceneManager gsm) {
        super(gsm);
        init();
    }

    private void init() {
        // load the map
        gameMap = MapLoader.loadMap("/levels/testlevel.txt", 30, 18);
        // get a reference to the list of aliens
        aliens = gameMap.getSprites();
        //player = new Player();
        player = new Player(5,5,500,3);
        shots = new ArrayList<>();
//        shot = new Shot();
    }

    @Override
    public void update() {
        player.act();
        animationCycle();
    }

    @Override
    public void drawHealth(Graphics g) {
        int max = player.getMaxhealth();
        int hp = player.getHealth();
        g.setColor(Color.black);
        g.fillRect(0, 450, 200, 20);
        g.setColor(Color.red);
        g.fillRect(0, 450, 200/max*hp, 20);

        int shield = player.getShield();
        g.setColor(Color.cyan);
        int s = -200/max*shield;
        if(s<-200){
            s=-200;
        }
        g.fillRect(200, 450, s, 20);
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
            if (player.canShoot()) {
               int m = player.getMultiTrajectoryProjectiles();
                int i = m / 2;
                int x,y;
                if(player.getShield()>0){
                    x = player.getX() + (player.getWidth() / 5);
                    y = player.getY() + (player.getHeight() / 5);
                }else{
                    x = player.getX();
                    y = player.getY();
                }

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
        drawHealth(g);
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
        if (gsm.ingame) {
            g.setColor(Color.WHITE);

            g.drawLine(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, // from the center of the
                                                                                            // player
                    (int) (player.getX() + player.getWidth() / 2 - 20 * -Math.cos(angle * Math.PI / 180.0)), // draw end of
                                                                                                        // line x a
                                                                                                        // distance of
                                                                                                        // 20 adj. for
                                                                                                        // angle from
                                                                                                        // center of
                                                                                                        // player
                    (int) (player.getY() + player.getHeight() / 2 - 20 * Math.sin(angle * Math.PI / 180.0))); // draw end of
                                                                                                         // line y a
                                                                                                         // distance of
                                                                                                         // 20 adj. for
                                                                                                         // angle from
                                                                                                         // center of
                                                                                                         // player
        }
    }

    public Player getPlayer(){
        return this.player;
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
        if (deaths == gameMap.getNumberOfEnemies()) {
            // player won
            // TODO add message to the Won scene
            // message = SpaceInvaders.lang.getEndingWinMessage();
            gsm.addScene(new Won(gsm), true);
        }

        // player

        player.act();
        if(player.getShield()>0) {
            player.setShielded();
        }else{
            player.setUnShielded();
        }
        // shot
/*
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
*/
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

        // aliens

        for (Sprite sprite : aliens) {
            Alien a1 = (Alien) sprite;
            int x = a1.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
                for (Sprite alien : aliens) {
                    Alien a2 = (Alien) alien;
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;

                for (Sprite alien : aliens) {
                    Alien a = (Alien) alien;
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }

        for (Sprite sprite : aliens) {
            Alien alien = (Alien) sprite;
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
                if (bombX >= (playerX) && bombX <= (playerX + player.getWidth()) && bombY >= (playerY)
                        && bombY <= (playerY + player.getHeight())) {
                    ImageIcon ii = new ImageIcon(this.getClass().getResource(expl));
                    int hp = player.damage();
                    if(hp<=0) {
                        player.setImage(ii.getImage());
                        player.setDying(true);
                    }
                    b.setDestroyed(true);
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