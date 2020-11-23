package main.java.graphics;

import main.java.entity.Alien;
import main.java.entity.Bomb;
import main.java.util.Commons;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class AlienAnimationCycle implements Commons {
    private final String expl = "/img/explosion.png";

    private ArrayList<Sprite> aliens;
    private int direction;
    private boolean gameLost;
    private Sprite playerSprite;
    private int dropSpeed = 0;

    public AlienAnimationCycle(ArrayList<Sprite> aliens, Sprite playerSprite) {
        this.aliens = aliens;
        this.playerSprite = playerSprite;
        gameLost = false;
        direction = -1;
    }

    public AlienAnimationCycle(ArrayList<Sprite> aliens, Sprite playerSprite, int dropSpeed) {
        this(aliens, playerSprite);
        this.dropSpeed = dropSpeed;
    }

    public void animate() {
        for (Sprite sprite : aliens) {
            Alien a1 = (Alien) sprite;
            int x = a1.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {
                direction = -1;
                for (Sprite alien : aliens) {
                    Alien a2 = (Alien) alien;
                    a2.setY(a2.getY() + (dropSpeed == 0 ? ALIEN_DROP_SPEED : dropSpeed));
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {
                direction = 1;

                for (Sprite alien : aliens) {
                    Alien a = (Alien) alien;
                    a.setY(a.getY() + (dropSpeed == 0 ? ALIEN_DROP_SPEED : dropSpeed));
                }
            }
        }

        for (Sprite sprite : aliens) {
            Alien alien = (Alien) sprite;
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - ALIEN_HEIGHT) {
                    // player lost
                    gameLost = true;
//                    havewon = false;
//                    gsm.addScene(new GameOver(gsm), true);
                    // ingame = false;
                    // message = "Aliens estão invadindo a galáxia!";
                }

                alien.act(direction);
            }
        }

        // bombs
        Iterator<Sprite> aliensIterator = aliens.iterator();
        Random generator = new Random();

        while (aliensIterator.hasNext()) {
            int shot = generator.nextInt(15);
            Alien a = (Alien) aliensIterator.next();
            Bomb b = a.getBomb();
            if (shot == CHANCE && a.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(a.getX());
                b.setY(a.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = playerSprite.getX();
            int playerY = playerSprite.getY();

            if (playerSprite.isVisible() && !b.isDestroyed()) {
                if (bombX >= (playerX) && bombX <= (playerX + PLAYER_WIDTH) && bombY >= (playerY)
                        && bombY <= (playerY + PLAYER_HEIGHT)) {

                    BufferedImage explosionImage = null;
                    try {
                        explosionImage = ImageIO.read(this.getClass().getResource(expl));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    playerSprite.setImage(explosionImage);
                    playerSprite.setDying(true);
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

    public boolean isGameLost() {
        return gameLost;
    }
}
