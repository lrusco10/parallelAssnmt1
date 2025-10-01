package org.example;

public class coordinate {
    public final int x, y;

    public coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public coordinate getUp() {
        return new coordinate(x, y+1);
    }
    public coordinate getDown() {
        return new coordinate(x, y-1);
    }
    public coordinate getLeft() {
        return new coordinate(x-1, y);
    }
    public coordinate getRight() {
        return new coordinate(x+1, y+1);
    }

}