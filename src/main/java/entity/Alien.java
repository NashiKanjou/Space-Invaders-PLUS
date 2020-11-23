package main.java.entity;

import main.java.graphics.Sprite;

import javax.swing.ImageIcon;

/**
 * 
 * @author
 */
public class Alien extends Sprite {

    private Bomb bomb;
    private final String alien = "/img/alien.png";

    /*
     * Constructor
     */
    public Alien(int x, int y) {
        this.setX(x);
        this.setY(y);

        bomb = new Bomb(x, y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource(alien));
        setImage(ii.getImage());

    }

    public void act(int direction) {
        this.setX(this.getX() + direction);
    }

    /*
     * Getters & Setters
     */

    public Bomb getBomb() {
        return bomb;
    }

}