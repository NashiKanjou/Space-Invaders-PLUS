package main.java.scene;

import main.java.graphics.AnimatedSprite;
import main.java.graphics.Sprite;
import main.java.graphics.SpriteSheet;
import main.java.manager.GameSceneManager;
import main.java.manager.KeyboardManager;
import main.java.util.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimationTestScene extends BaseScene {
    private SpriteSheet spriteSheet;
    private Sprite sprite;
    private AnimatedSprite animatedSprite;
    private ArrayList<Pair> frames;

    public AnimationTestScene(GameSceneManager gsm) {
        super(gsm);
        spriteSheet = new SpriteSheet("/img/sprite_sheet.png", 32);

        frames = new ArrayList<>();
        // add the location of frames in the sprite sheet
        frames.add(new Pair<Integer, Integer>(1, 1));
        frames.add(new Pair<Integer, Integer>(2, 1));

        animatedSprite = new AnimatedSprite(frames, spriteSheet, 5f);
        sprite = animatedSprite.getSprite();
    }

    @Override
    public void update() {
        animatedSprite.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(), null);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void input(KeyboardManager keyboardManager) {
        if (keyboardManager.right.down)
            sprite.setDx(2);
        else if (keyboardManager.left.down)
            sprite.setDx(-2);
        else {
            sprite.setDx(0);
        }


        if (keyboardManager.up.down)
            sprite.setDy(-2);
        else if (keyboardManager.down.down)
            sprite.setDy(2);
        else {
            sprite.setDy(0);
        }
    }
}
