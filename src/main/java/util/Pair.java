package main.java.util;

/**
 * A simple helper class that only holds two values that are related
 * */
public class Pair<T, U> {
    private T x;
    private U y;

    public Pair(T x, U y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public U getY() {
        return y;
    }
}
