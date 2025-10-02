package org.example;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {

        final int startfloors = 50;
        final int stations = 50;
        final int maxX = 50;
        final int maxY = 50;
        final int maxFit = stations; //for clarity
        final int maxRounds = 30;
        final Random rand = new Random();


        //generation phase
        //Only happens once-- generate floors at start of program
        ConcurrentHashMap<String, Floor> map = new ConcurrentHashMap<>();
        for (int i = 0; i < startfloors; i++) {
            Floor temp = new Floor(stations, maxX, maxY);
            map.put(UUID.randomUUID().toString(), temp);
        }

        //initialize GUI ops
        JFrame frame = new JFrame("Factory Floor");
        List<GridSquare> squares = new ArrayList<>();
        ColoredGridPanel panel = new ColoredGridPanel(maxX, maxY, 10, squares);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        ExecutorService executorService = Executors.newFixedThreadPool(map.size()*2);


        for (int i = 0; i < maxRounds; i++) { //computationally expensive, but I don't expect THAT many rounds....
            //selection phase
            //Take most fit floorplans
            double avg = map.values().stream()
                    .mapToInt(Floor::getFitness)
                    .average().orElse(0.0);
            map.values().removeIf(f -> f.getFitness() < avg*.9);

            //grab the current best floor
            int max = 0;
            Floor currentBest = null;
            for (Floor f : map.values()) {
                if (f.getFitness() > max) {
                    max = f.getFitness();
                    currentBest = f;
                }
            }
            System.out.println("Round " + i + ": Best Fitness: " + currentBest.fitness + " Number of Floors: " + map.size());
            //make a panel in accordance with the best Floor
            squares.clear();
            if (currentBest != null) {
                synchronized (squares) {
                    for (Station s : currentBest.floorPlan.values()) {
                        squares.add(new GridSquare(s.getX(), s.getY(), s.getType()));
                    }
                }
            }

            //now display that panel:
            panel.repaint();
            try {
                Thread.sleep(500); // 0.5 second per round
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }


            //crossover phase
            //pick a random pair of floors and make children
            ArrayList<String> refs = new ArrayList<>(map.keySet().stream().toList());
            List<Future<?>> futures = new ArrayList<>();
            LinkedBlockingQueue<Floor> children = new LinkedBlockingQueue<>();
            while (refs.size() >= 2) {
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
        }
    }
}