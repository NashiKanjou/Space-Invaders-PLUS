package main.java.manager;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


public class InputManager implements KeyListener {
    public enum InputSource {
        KEYBOARD,
        CONTROLLER
    }

    enum TKey {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        QUIT,
        ESCAPE,
        ENTER,
        SPACE,
        ANGLE_DEC,
        ANGLE_INC,
        UNKNOWN
    }

    public static List<Key> keys = new ArrayList<>();

    private final ControllerManager controllerManager;
    private ControllerState controllerState;
    private final float DEADZONE = 0.5f;
    private InputSource currentInputSource;
    private boolean controllerShouldRumble;

    public InputManager() {
        controllerManager = new ControllerManager();
        controllerManager.initSDLGamepad();
        controllerState = controllerManager.getState(0);
        currentInputSource = InputSource.KEYBOARD;
    }

    public class Key {
        private int presses, absorbs;
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

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key quit = new Key();
    public Key escape = new Key();
    public Key enter = new Key();
    public Key space = new Key();
    public Key angleDec = new Key();
    public Key angleInc = new Key();

    public void releaseAll() {
        keys.stream().forEach(k -> k.down = false);
    }

    public void update() {
        keys.stream().forEach(k -> k.update());
        handleController();
    }

    private void toggle(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_UP)
            up.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            down.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            left.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            right.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_Q)
            quit.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            escape.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            enter.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
            space.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_A)
            angleDec.toggle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_D)
            angleInc.toggle(pressed);
    }

    private TKey getKeyType(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                return TKey.UP;
            case KeyEvent.VK_DOWN:
                return TKey.DOWN;
            case KeyEvent.VK_LEFT:
                return TKey.LEFT;
            case KeyEvent.VK_RIGHT:
                return TKey.RIGHT;
            case KeyEvent.VK_Q:
                return TKey.QUIT;
            case KeyEvent.VK_ESCAPE:
                return TKey.ESCAPE;
            case KeyEvent.VK_ENTER:
                return TKey.ENTER;
            case KeyEvent.VK_SPACE:
                return TKey.SPACE;
            case KeyEvent.VK_A:
                return TKey.ANGLE_DEC;
            case KeyEvent.VK_D:
                return TKey.ANGLE_INC;
            default:
                return TKey.UNKNOWN;
        }
    }

    private void controllerToggle(TKey key, boolean pressed) {
        switch (key) {
            case UP:
                up.toggle(pressed);
                break;
            case DOWN:
                down.toggle(pressed);
                break;
            case LEFT:
                left.toggle(pressed);
                break;
            case RIGHT:
                right.toggle(pressed);
                break;
            case QUIT:
                quit.toggle(pressed);
                break;
            case ESCAPE:
                escape.toggle(pressed);
                break;
            case ENTER:
                enter.toggle(pressed);
                break;
            case SPACE:
                space.toggle(pressed);
                break;
            case ANGLE_DEC:
                angleDec.toggle(pressed);
                break;
            case ANGLE_INC:
                angleInc.toggle(pressed);
                break;
        }
    }

    private void handleController() {
        controllerState = controllerManager.getState(0);
        if (!controllerState.isConnected)
            return;
        currentInputSource = InputSource.CONTROLLER;

        // fire bullet
        controllerToggle(TKey.SPACE, controllerState.a);

        // check the position of the controller left stick
        controllerToggle(TKey.LEFT, controllerState.leftStickX < -DEADZONE);
        controllerToggle(TKey.RIGHT, controllerState.leftStickX > DEADZONE);
        controllerToggle(TKey.UP, controllerState.leftStickY > DEADZONE);
        controllerToggle(TKey.DOWN, controllerState.leftStickY < -DEADZONE);

        // pause screen
        controllerToggle(TKey.ESCAPE, controllerState.startJustPressed);

        // quit
        controllerToggle(TKey.QUIT, controllerState.back);

        if (controllerShouldRumble) {
            System.out.println("controller rumble");
            controllerManager.doVibration(0, 0.5f, 0.5f, 2);
            controllerShouldRumble = false;
        }

        // angle control
        controllerToggle(TKey.ANGLE_DEC, controllerState.leftTrigger > DEADZONE);
        controllerToggle(TKey.ANGLE_INC, controllerState.rightTrigger > DEADZONE);
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

    public InputSource getCurrentInputSource() {
        return currentInputSource;
    }

    public boolean isControllerShouldRumble() {
        return controllerShouldRumble;
    }

    public void setControllerShouldRumble(boolean controllerShouldRumble) {
        this.controllerShouldRumble = controllerShouldRumble;
    }
}
