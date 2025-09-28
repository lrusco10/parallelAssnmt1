package org.example;


import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {

        final int startfloors = 50;
        final int stations = 50;
        final int maxX = 100;
        final int maxY = 100;


        //generation phase
        //Only happens once-- generate floors at start of program
        ConcurrentHashMap<Integer, Floor> map = new ConcurrentHashMap<>();
        for (int i = 0; i < startfloors; i++) {
            map.put(i, new Floor(stations, maxX, maxY));
        }
        double avg = map.values().stream()
                .mapToInt(Floor::getFitness)
                .average().orElse(0.0);

        //selection phase
        //Take most fit floorplans
        map.values().removeIf(f -> f.getFitness() < avg);

        //crossover phase
        // swap random stations. keep configuration if fitness is better for either or both
        // swap back if fitness is worse for both.


        //mutation phase
        //mutate random unfit stations in remaining bunch.

        //return to selection phase
        //take average, reevaluate floormap.


    }
}