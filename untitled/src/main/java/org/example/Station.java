package org.example;

//similar stations should be close
//different stations should be far

public class Station {
    private int type;
    private coordinate cord;
    private boolean isfit;

/*
affinity by quadrant:
type 1 : q1
type 2 : q2
...and so on

also, affinity +1 if adjacent to any number of stations

thus, maxAffinity = 2 * #stations
 */
    public Station(int type, int x, int y) {
        this.type = type;
        this.cord = new coordinate(x, y);
        this.isfit = false; //just the default.
    }
    public void setFit(boolean b) {isfit = b;}

    public boolean getFit() {return isfit;}

    public int getType() {
        return type;
    }

    public coordinate getCord() {return cord;}

    public int getX() {
        return cord.x;
    }

    public int getY() {
        return cord.y;
    }

    public void changeType(int type) {
        this.type = type;
    }

    public void changePosition(coordinate cord) {
        this.cord = cord;
    }
}
