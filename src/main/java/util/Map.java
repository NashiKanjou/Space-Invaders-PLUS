package main.java.util;

/*
* This class is a wrapper for a game level that is read in from a
* text file that has useful information that is specific to a level
* instead of a global constant.
* */

import main.java.entity.Sprite;

import java.util.ArrayList;

public class Map {
    private ArrayList<Sprite> sprites;
    private int numberOfEnemies;

    public Map(ArrayList<Sprite> sprites, int numberOfEnemies) {
        this.sprites = sprites;
        this.numberOfEnemies = numberOfEnemies;
    }

    public ArrayList<Sprite> getSprites() {
        return sprites;
    }

    public int getNumberOfEnemies() {
        return numberOfEnemies;
    }
}
