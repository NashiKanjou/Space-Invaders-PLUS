package main.java.entity;

import main.java.graphics.Sprite;

import javax.swing.ImageIcon;

public class Shot extends Sprite {

    private String shot = "/img/shot.png";
    private final int H_SPACE = 6;
    private final int V_SPACE = 1;
    private double angle;

    public Shot() {
    }

    public double getAngle(){
        return angle;
    }
    public Shot(int x, int y,double angle) {

        ImageIcon ii = new ImageIcon(this.getClass().getResource(shot));
        setImage(ii.getImage());
        setX(x + H_SPACE);
        setY(y - V_SPACE);
        this.angle=angle;
    }
}