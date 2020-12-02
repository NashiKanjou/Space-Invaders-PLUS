package main.java.scene;

import main.java.graphics.AnimatedSprite;
import main.java.graphics.Sprite;
import main.java.graphics.SpriteSheet;
import main.java.manager.GameSceneManager;
import main.java.manager.InputManager;
import main.java.util.Pair;

import java.awt.*;
import java.util.ArrayList;

public class AnimationTestScene extends BaseScene {
    private SpriteSheet spriteSheet;
    private Sprite sprite;
    private AnimatedSprite animatedSprite;
    private ArrayList<Pair> frames, leftFrame, rightFrame;
    private boolean wasFrameChanged = false;


    public AnimationTestScene(GameSceneManager gsm) {
        super(gsm);
        spriteSheet = new SpriteSheet("/img/sprite_sheet.png", 32);

        frames = new ArrayList<>();
        // add the location of frames in the sprite sheet
        frames.add(new Pair<Integer, Integer>(1, 1));
        frames.add(new Pair<Integer, Integer>(2, 1));

        rightFrame = new ArrayList<>();
        rightFrame.add(new Pair<Integer, Integer>(3,1));

        leftFrame = new ArrayList<>();
        leftFrame.add(new Pair<Integer, Integer>(3,1));

        animatedSprite = new AnimatedSprite(frames, spriteSheet, 5f);
        sprite = animatedSprite;
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
    public void input(InputManager inputManager) {
        if (inputManager.right.down) {
            sprite.setDx(2);
            wasFrameChanged = true;
            animatedSprite.setFrames(rightFrame);
        }
        else if (inputManager.left.down) {
            sprite.setDx(-2);
            wasFrameChanged = true;
            animatedSprite.setFrames(leftFrame);
        }
        else {
            sprite.setDx(0);
            if (wasFrameChanged) {
                animatedSprite.setFrames(frames);
                wasFrameChanged = false;
            }
        }


        if (inputManager.up.down)
            sprite.setDy(-2);
        else if (inputManager.down.down)
            sprite.setDy(2);
        else {
            sprite.setDy(0);
        }
    }
}
