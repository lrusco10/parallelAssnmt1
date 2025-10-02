package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ColoredGridPanel extends JPanel {
    private int rows;
    private int cols;
    private int cellSize;
    private List<GridSquare> squares;

    // Constructor
    public ColoredGridPanel(int rows, int cols, int cellSize, List<GridSquare> squares) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.squares = squares;
        setPreferredSize(new Dimension(cols * cellSize, rows * cellSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid
        g.setColor(Color.LIGHT_GRAY);
        for (int r = 0; r <= rows; r++) {
            g.drawLine(0, r * cellSize, cols * cellSize, r * cellSize);
        }
        for (int c = 0; c <= cols; c++) {
            g.drawLine(c * cellSize, 0, c * cellSize, rows * cellSize);
        }

        // Draw squares
        for (GridSquare square : squares) {
            g.setColor(square.getColor());
            g.fillRect(square.getX() * cellSize, square.getY() * cellSize, cellSize, cellSize);
        }
    }
}