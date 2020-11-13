package main.java.graphics;

import main.java.util.Pair;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class AnimatedSprite {
    // the start and end tiles to use in the sprite sheet
    private int currentRow, currentCol, currentIndex;
    // animation interval in seconds
    private float interval, count;
    private final SpriteSheet spriteSheet;
    private final ArrayList<Pair> frames;
    private BufferedImage image;
    public Sprite sprite;


    public AnimatedSprite(ArrayList<Pair> frames, SpriteSheet spriteSheet, float interval) {
        this.interval = interval;
        count = 0f;
        this.spriteSheet = spriteSheet;
        this.frames = frames;
        currentIndex = 0;


        getCurrentSprite();
        sprite = new Sprite(0, 0, image);
    }

    private void getCurrentSprite() {
        currentRow = (Integer)this.frames.get(currentIndex).getX();
        currentCol = (Integer)this.frames.get(currentIndex).getY();

        image = spriteSheet.getImage(currentRow, currentCol);
        if (sprite == null)
            sprite = new Sprite(0, 0, image);
        else
            sprite.setImage(image);
    }

    public void update() {
        sprite.act();
        if (count >= interval) {
            // switch to the next image
            if (currentIndex + 1 >= frames.size())
                currentIndex = 0;
            else
                currentIndex++;

            count = 0f;
            getCurrentSprite();
            return;
        }
        //TODO change to use a timer that takes into account the time to run the whole animation like the main game loop
        count++;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
