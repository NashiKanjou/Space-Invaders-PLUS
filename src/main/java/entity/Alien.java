package main.java.entity;

import main.java.graphics.AnimatedSprite;
import main.java.graphics.Sprite;
import main.java.manager.AnimationManager;

import javax.swing.ImageIcon;

/**
 * 
 * @author
 */
public class Alien extends AnimatedSprite {

    private Bomb bomb;
    private final String alien = "/img/alien.png";
    private AnimatedSprite animatedSprite;

    /*
     * Constructor
     */
    public Alien(int x, int y) {
        super(AnimationManager.getInstance().getFrames(AnimationManager.Assets.ALIEN), AnimationManager.getInstance().getSpriteSheet(), 5f);
        animatedSprite = AnimationManager.getInstance().get(AnimationManager.Assets.ALIEN);
        setX(x);
        setY(y);
        animatedSprite.setX(x);
        animatedSprite.setY(y);

        bomb = new Bomb(x, y);

    }

    public void act(int direction) {
        animatedSprite.setX(animatedSprite.getX() + direction);
        this.setX(this.getX() + direction);
    }

    /*
     * Getters & Setters
     */

    public Bomb getBomb() {
        return bomb;
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }
}