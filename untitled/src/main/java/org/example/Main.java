package org.example;


import java.util.ArrayList;
import java.util.List;
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

        for (int i = 0; i < maxRounds; i++) { //computationally expensive, but I don't expect THAT many rounds....
            ExecutorService executorService = Executors.newFixedThreadPool(map.size());

            //selection phase
            //Take most fit floorplans
            double avg = map.values().stream()
                    .mapToInt(Floor::getFitness)
                    .average().orElse(0.0);
            map.values().removeIf(f -> f.getFitness() < avg);
            System.out.println("Fitness of most fit floor: " + map.values().stream().mapToInt(Floor::getFitness).max());

            //crossover phase
            //pick a random pair of floors and make children
            //DEADLOCK PROBLEM:: NEED TO INPUT A CHOICE FOR INSTANCES WHERE THERE IS ONLY ONE REF LEFT
            ArrayList<String> refs = new ArrayList<>(map.keySet().stream().toList());
            List<Future<?>> futures = new ArrayList<>();
            LinkedBlockingQueue<Floor> children = new LinkedBlockingQueue<>();
            while (!refs.isEmpty()) {
                Floor choice1 = map.get(refs.remove(rand.nextInt(refs.size())));
                Floor choice2 = map.get(refs.remove(rand.nextInt(refs.size())));
                futures.add(executorService.submit(new crossover(children, choice1, choice2)));
            }
            // Wait for all tasks to complete
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }


            //mutation phase
            //mutate random unfit stations in remaining bunch.
            for (Floor f : map.values()) {
                futures.add(executorService.submit(new mutation(f)));
            }
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }


            //dump the children into the map
            for (Floor c : children) {
                map.put(UUID.randomUUID().toString(), c);
            }
            executorService.shutdown();
        }
    }
}