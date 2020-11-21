package main.java.manager;

import main.java.graphics.AnimatedSprite;
import main.java.graphics.SpriteSheet;
import main.java.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class AssetManager {
    private HashMap<Assets, AnimatedSprite> map;
    private HashMap<Assets, ArrayList<Pair>> frames;
    private boolean wasLoaded;
    private static AssetManager instance;

    public enum Assets {
        PLAYER_IDLE,
        PLAYER_LEFT,
        PLAYER_RIGHT,
        ALIEN
    }

    private AssetManager() {
        map = new HashMap<>();
        frames = new HashMap<>();
        wasLoaded = false;
    }

    public void load(SpriteSheet spriteSheet) {
        if (wasLoaded) {
            System.out.println("Assets all ready loaded");
        } else if (spriteSheet == null) {
            System.out.println("Invalid sprite sheet");
        } else {

            // setup the idle player sprites
            createFrames();

            var idleAnimatedSprite = new AnimatedSprite(frames.get(Assets.PLAYER_IDLE), spriteSheet, 5f);
            map.put(Assets.PLAYER_IDLE, idleAnimatedSprite);

            var playerLeftAnimateSprite = new AnimatedSprite(frames.get(Assets.PLAYER_LEFT), spriteSheet, 5f);
            map.put(Assets.PLAYER_LEFT, playerLeftAnimateSprite);

            wasLoaded = true;
        }
    }

    private void createFrames() {
        // player ship idle
        var idleFrames = new ArrayList<Pair>();
        idleFrames.add(new Pair<Integer, Integer>(1, 1));
        idleFrames.add(new Pair<Integer, Integer>(2, 1));
        frames.put(Assets.PLAYER_IDLE, idleFrames);

        // player ship move to the left
        var leftFrame = new ArrayList<Pair>();
        leftFrame.add(new Pair<Integer, Integer>(3, 1));
        frames.put(Assets.PLAYER_LEFT, leftFrame);

        // player ship move to the right
        var rightFrame = new ArrayList<Pair>();
        rightFrame.add(new Pair<Integer, Integer>(3,1));
        frames.put(Assets.PLAYER_RIGHT, rightFrame);
    }

    public void update() {
        if (wasLoaded) {
            for (Assets a : map.keySet()) {
                var animatedSprite = map.get(a);
                animatedSprite.update();
            }
        }
    }

    public AnimatedSprite get(Assets type) {
        if (!wasLoaded) {
            System.out.println("No assets were loaded.");
            return null;
        }

        return map.get(type);
    }

    public ArrayList<Pair> getFrames(Assets type) {
        return frames.get(type);
    }

    public static AssetManager getInstance() {
        if (instance == null) instance = new AssetManager();

        return instance;
    }
}
