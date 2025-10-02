package org.example;

import java.awt.*;

public class GridSquare {
    private int x;
    private int y;
    private Color color;

    public GridSquare(int x, int y, int type) {
        this.x = x;
        this.y = y;
        switch (type) {
            case 1:
                color = Color.BLUE;
                break;
            case 2:
                color = Color.GREEN;
                break;
            case 3:
                color = Color.RED;
                break;
            case 4:
                color = Color.YELLOW;
                break;
            default:
                color = Color.BLACK;
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Color getColor() { return color; }
}