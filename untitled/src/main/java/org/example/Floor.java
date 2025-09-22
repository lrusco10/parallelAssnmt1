package org.example;

import java.util.HashMap;

public class Floor {
    HashMap<Integer, Station> floorPlan;
    int fitness;

    public Floor(HashMap<Integer, Station> floorPlan) {
        this.floorPlan = floorPlan;
        fitness = calcFit();
    }

    private int calcFit() {

    }
}
