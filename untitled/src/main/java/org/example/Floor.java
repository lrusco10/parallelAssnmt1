package org.example;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Floor {
    ConcurrentHashMap<Integer, Station> floorPlan;
    double fitness;

    public Floor(ConcurrentHashMap<Integer, Station> floorPlan) {
        this.floorPlan = floorPlan;
        fitness = calcFit();
    }

    private double calcFit() {
        HashMap<Long, Double> toBeAveraged = new HashMap<>();
        AtomicLong fitness = new AtomicLong();

        //CLASSIC NESTED FOR LOOP BABY!
        for (Station n : floorPlan.values()) {
            for (Station m : floorPlan.values()) {

            }
        }
    }


}
