package main.java.graphics;

import java.awt.image.BufferedImage;

public class Sprite {
    private int x, y, dx, dy;
    private SpriteSheet spriteSheet;
    private BufferedImage image;

    public Sprite(int x, int y, BufferedImage image) {
        dx = 0;
        dy = 0;
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void act() {
        x += dx;
        y += dy;
    }
    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
