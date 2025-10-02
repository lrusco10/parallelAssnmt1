package org.example;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class crossover implements Runnable{
    private final Floor supFloor;
    private final Floor subFloor;
    private final LinkedBlockingQueue<Floor> children;

    public crossover(LinkedBlockingQueue<Floor> children, Floor floor1, Floor floor2) {
        if (floor1.fitness >= floor2.fitness) {
            supFloor = floor1;
            subFloor = floor2;
        }
        else {
            supFloor = floor2;
            subFloor = floor1;
        }
        this.children = children;
    }

    @Override
    public void run() {
        //set up for child floorplan
        Floor child = new Floor(supFloor.getxMax(), supFloor.getyMax());
        Random rand = new Random();

        // use Arraylists to track keys
        ArrayList<coordinate> suparr = new ArrayList<>(supFloor.floorPlan.keySet().stream().toList());
        ArrayList<coordinate> subarr = new ArrayList<>(subFloor.floorPlan.keySet().stream().toList());

        while (!subarr.isEmpty()) {
            coordinate choice1 = suparr.remove(rand.nextInt(suparr.size()));
            coordinate choice2 = subarr.remove(rand.nextInt(subarr.size()));

            double num = rand.nextDouble();
            if (num > .20) {
                child.floorPlan.put(choice1, supFloor.floorPlan.get(choice1));
            }
            else {
                child.floorPlan.put(choice2, subFloor.floorPlan.get(choice2));
            }

        }
        children.add(child);
        //child is populated-- now check fitness: may be too accurate for a genetic algorithm...
//        if (child.fitness >= supFloor.fitness && child.fitness >= subFloor.fitness) {
//            children.add(child);
//        }

    }
}
