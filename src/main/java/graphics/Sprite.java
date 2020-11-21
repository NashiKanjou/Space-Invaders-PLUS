package main.java.graphics;

import java.awt.*;

public class Sprite {
    private int x, y, dx, dy;
    private SpriteSheet spriteSheet;
    private Image image;
    private boolean dying, visible;

    public Sprite() {
        visible = true;
        dying = false;
    }

    public Sprite(int x, int y, Image image) {
        this();

        dx = 0;
        dy = 0;
        this.x = x;
        this.y = y;
        this.image = image;
    }


    public void act() {
        x += dx;
        y += dy;
    }
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void die() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDx() {
        return dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getDy() {
        return dy;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public boolean isDying() {
        return this.dying;
    }
}
