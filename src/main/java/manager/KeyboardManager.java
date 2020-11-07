package manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class KeyboardManager implements KeyListener {
    public static List<Key> keys = new ArrayList<>();

    public class Key {
        public int presses, absorbs;
        public boolean down, clicked;

        public Key() {
            keys.add(this);
            absorbs = 0;
        }

        public void toggle(boolean pressed) {
            if (pressed != down) {
                down = pressed;
            }

            if (pressed)
                presses++;
        }

        public void update() {
            if (absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
                // absorbs = 0;
            }
        }
    }

    public KeyboardManager(JPanel panel) {
        panel.addKeyListener(this);
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key escape = new Key();
    public Key space = new Key();
    public Key angleDec = new Key();
    public Key angleInc = new Key();

    public void releaseAll() {
        keys.stream().forEach(k -> k.down = false);
    }

    public void update() {
        keys.stream().forEach(k -> k.update());
    }

    public void toggle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            up.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            down.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            left.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            right.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            escape.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            space.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_A)
            angleDec.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_D)
            angleInc.toggle(pressed);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggle(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggle(e, false);

    }

}
