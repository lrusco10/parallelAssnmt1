package org.example;
import java.util.List;
import java.util.Random;

public class mutation implements Runnable{

    Floor floor;
    Random rand;

    public mutation(Floor floor) {
        this.floor = floor;
        rand = new Random();
    }
    @Override
    public void run() {
        List<coordinate> unfitStations = floor.floorPlan.keySet().stream()
                .filter(cord -> !floor.floorPlan.get(cord).getFit())
                .toList();
        for (int i = 0; i < rand.nextInt(unfitStations.size()); i++) {
            if (i%2 == 0) {
                floor.floorPlan.get(unfitStations.get(rand.nextInt(unfitStations.size()))).changeType(rand.nextInt(0,5));
            }
            else {
                coordinate first = unfitStations.get(rand.nextInt(unfitStations.size()));
                coordinate second = unfitStations.get(rand.nextInt(unfitStations.size()));
                Station temp1 = floor.floorPlan.get(first);
                Station temp2 = floor.floorPlan.get(second);
                //swap addresses!
                floor.floorPlan.put(second, temp1);
                floor.floorPlan.put(first, temp2);
                //swap vars!
                floor.floorPlan.get(first).changePosition(first);
                floor.floorPlan.get(second).changePosition(second);
            }

        }
        //recompute fitness
        floor.calcFit();
    }
}
