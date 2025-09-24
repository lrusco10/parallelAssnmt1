package org.example;

//similar stations should be close
//different stations should be far

public class Station {
    private final int type;
    private int x, y;

/*
types:
    1:  is green, aff = 1
    2: is red, aff = 2
    3: is blue, aff = 3
    4: is yellow, aff = 4
    green should be closest to red and furthest from yellow
    red should be closest to green or blue, but furthest from yellow
    blue should be closest to red or yellow, but furthest from green
    yellow should be closest to blue but furthest from green
    distance is calculated via Euclidean operation.
 */
    public Station(int type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
