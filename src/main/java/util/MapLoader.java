package main.java.util;

import main.java.entity.Alien;
import main.java.entity.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class MapLoader {

    enum Types {
        UNKNOWN_TYPE(-1), ALIEN(0), MEDIUM_ENEMY(1), HARD_ENEMY(2);

        public final int id;

        Types(int id) {
            this.id = id;
        }
    }

    private static HashMap<Integer, Types> typesMap = new HashMap<>();

    // setup the HashMap with the initial values
    static {
        for (Types type : Types.values()) {
            typesMap.put(type.id, type);
        }
    }

    static class MapFormatException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public MapFormatException(String err) {
            super(err);
        }
    }

    public static ArrayList<Sprite> loadMap(String filepath, int imageSize, int offset) {
        // attempt to load the text file
        Scanner in = null;
        try {
            in = new Scanner(new File(filepath));
        } catch (FileNotFoundException e) {
            System.out.println("Map file not found:");
            e.printStackTrace();
        }

        var sprites = new ArrayList<Sprite>();
        var readFirstLine = false;
        var x = 150;
        var y = 50;

        var rowSize = 0;
        var colSize = 0;
        var currentRow = 1;
        var currentCol = 1;

        while (in.hasNextLine()) {
            var values = in.nextLine().split(" ");

            // make sure the first line only has 2 numbers signifying the rows x columns
            if (values.length != 2 && !readFirstLine) {
                throw new MapFormatException(
                        "First line in map file must contain only two numbers, the row and column");
            } else if (values.length == 2 && !readFirstLine) {
                rowSize = Integer.parseInt(values[0]);
                colSize = Integer.parseInt(values[1]);
                readFirstLine = true;
            } else {

                // look at each number and build the map with the corresponds objects
                for (String s : values) {
                    Types obj = null;
                    try {
                        obj = typesMap.getOrDefault(Integer.parseInt(s), Types.UNKNOWN_TYPE);
                    } catch (NumberFormatException e) {
                        System.out.println("Only numbers are allowed in the map file:");
                        e.printStackTrace();
                    }

                    switch (obj) {
                        case ALIEN:
                            sprites.add(new Alien(x, y));
                            break;
                        case MEDIUM_ENEMY:
                            System.out.println("medium enemy");
                            break;
                        case HARD_ENEMY:
                            System.out.println("hard enemy");
                            break;
                        case UNKNOWN_TYPE:
                            System.out.println("unknown type");
                            break;
                        default:
                            System.out.println("not implemented yet");
                    }

                    x += offset;
                    // y = ;
                }
                y += offset;
                x = 150;
                currentRow++;
                currentCol++;
            }
        }
        in.close();
        return sprites;
    }

}
