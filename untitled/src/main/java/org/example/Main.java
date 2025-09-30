package org.example;


import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        final int startfloors = 50;
        final int stations = 50;
        final int maxX = 100;
        final int maxY = 100;
        final int maxFit = stations; //for clarity
        final int maxRounds = 100;
        final Random rand = new Random();


        //generation phase
        //Only happens once-- generate floors at start of program
        ConcurrentHashMap<String, Floor> map = new ConcurrentHashMap<>();
        for (int i = 0; i < startfloors; i++) {
            Floor temp = new Floor(stations, maxX, maxY);
            map.put(UUID.randomUUID().toString(), temp);
        }
        Phaser phaser = new Phaser(map.size());
        ExecutorService executorService = Executors.newFixedThreadPool(map.size());


        for (int i = 0; i < maxRounds; i++) {


            //selection phase
            //Take most fit floorplans
            double avg = map.values().stream()
                    .mapToInt(Floor::getFitness)
                    .average().orElse(0.0);
            map.values().removeIf(f -> f.getFitness() < avg);


            //crossover phase
            //pick a random pair of floors and make children ( ͡° ͜ʖ ͡°)
            ArrayList<String> refs = new ArrayList<>(map.keySet().stream().toList());
            LinkedBlockingQueue<Floor> children = new LinkedBlockingQueue<>();
            while (!refs.isEmpty()) {
                Floor choice1 = map.get(refs.remove(rand.nextInt(refs.size())));
                Floor choice2 = map.get(refs.remove(rand.nextInt(refs.size())));
                Thread task = new Thread(new crossover(children, choice1, choice2));
                task.start();
            }
            //dump the children into the map
            for (Floor c : children) {
                map.put(UUID.randomUUID().toString(), c);
            }

            //mutation phase
            //mutate random unfit stations in remaining bunch.

            //take average, reevaluate floormap.

        }
    }
}