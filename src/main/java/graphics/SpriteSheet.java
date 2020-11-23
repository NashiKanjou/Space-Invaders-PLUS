package main.java.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage image;
    private String path;
    // this is the size per sprite in the sprite sheet
    private int size;

    public SpriteSheet(String path, int size) {
        this.path = path;
        this.size = size;
        load();
    }

    public BufferedImage getImage(int col, int row) {
        var subImage = image.getSubimage((col * size) - size, (row * size) - size, size, size);
        return subImage;
    }

    public void load() {
        try {
            image = ImageIO.read(this.getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
