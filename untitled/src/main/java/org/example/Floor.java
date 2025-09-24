package org.example;


import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class Floor {
    ConcurrentHashMap<coordinate, Station> floorPlan = new ConcurrentHashMap<>();
    int xMax;
    int yMax;
    double fitness;

    public Floor(int stations, int xMax, int yMax) {
        Random rand = new Random();
        this.xMax = xMax;
        this.yMax = yMax;
        for (int i = 0; i < stations; i++) {
            int x = rand.nextInt(0, xMax);
            int y = rand.nextInt(0, yMax);
            coordinate cord = new coordinate(x,y);
            if (floorPlan.containsKey(cord)) {
                i--;
                continue;
            }
            floorPlan.put(cord, new Station(rand.nextInt(1,5), x, y));
        }
        fitness = calcFit();
    }

    private int calcFit() {
        int fit = 0;
        for (Station s : floorPlan.values()) {
            switch (s.getType()) {
                case 1:
                    //upper left quadrant
                    if (s.getX() < (xMax/2) && s.getY() > (yMax/2)) {
                        fit++;
                    }
                case 2:
                    //upper left quadrant
                    if (s.getX() > (xMax/2) && s.getY() > (yMax/2)) {
                        fit++;
                    }
                case 3:
                    //upper left quadrant
                    if (s.getX() < (xMax/2) && s.getY() < (yMax/2)) {
                        fit++;
                    }
                case 4:
                    //upper left quadrant
                    if (s.getX() > (xMax/2) && s.getY() < (yMax/2)) {
                        fit++;
                    }
            }
        }
        return fit;
    }


}
