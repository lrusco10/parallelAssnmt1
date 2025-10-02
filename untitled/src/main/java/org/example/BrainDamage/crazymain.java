//package org.example;
//
//
//import java.util.*;
//import java.util.concurrent.*;
//
//public class crazymain {
//    public static void main(String[] args) {
//
//        final int startfloors = 50;
//        final int stations = 50;
//        final int maxX = 50;
//        final int maxY = 50;
//        final int maxFit = stations; //for clarity
//        final int maxRounds = 30;
//        final Random rand = new Random();
//
//
//        //generation phase
//        //Only happens once-- generate floors at start of program
//        ConcurrentHashMap<Double, Floor> map = new ConcurrentHashMap<>();
//        ArrayList<Double> mapKeysSorted = new ArrayList<>();
//        for (int i = 0; i < startfloors; i++) {
//            Floor temp = new Floor(stations, maxX, maxY);
//            double keyToAdd;
//            if (map.containsKey((double)temp.getFitness())) {
//                System.out.println("hit");
//                keyToAdd = (1/2.0) + temp.getFitness();
//                for (double n = 2.0; map.containsKey(keyToAdd); n++) {
//                    keyToAdd = (1/n) + temp.getFitness();
//                }
//            }
//            else {
//                keyToAdd = temp.getFitness();
//            }
//            mapKeysSorted.add(keyToAdd);
//            map.put(keyToAdd, temp);
//            System.out.println(keyToAdd);
//        }
//
//        double avg = map.values().stream()
//                .mapToInt(Floor::getFitness)
//                .average().orElse(0.0);
//        double finalAvg = avg;
//        map.values().removeIf(f -> f.getFitness() < finalAvg *.9);
//        int maxMapSize = map.size();
//        ExecutorService executorService = Executors.newFixedThreadPool(maxMapSize);
//        for (int i = 0; i < maxRounds; i++) {
//
//            //selection phase
//            //Take most fit floorplan
//            System.out.println("Round " + i + ": Fitness of most fit floor-- " + map.values().stream()
//                    .map(Floor::getFitness)
//                    .max(Integer::compareTo)
//                    .orElseThrow(() -> new RuntimeException("no values in map")));
//
//            //crossover phase
//            //pick a random pair of floors and make children
//            ArrayList<Double> refs = new ArrayList<>(map.keySet().stream().toList());
//            List<Future<?>> futures = new ArrayList<>();
//            LinkedBlockingQueue<Floor> children = new LinkedBlockingQueue<>();
//            while (refs.size() >= 2) {
//                Floor choice1 = map.get(refs.remove(rand.nextInt(refs.size())));
//                Floor choice2 = map.get(refs.remove(rand.nextInt(refs.size())));
//                futures.add(executorService.submit(new crossover(children, choice1, choice2)));
//            }
//            // Wait for all tasks to complete
//            for (Future<?> future : futures) {
//                try {
//                    future.get();
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//            //mutation phase
//            //mutate random unfit stations in remaining bunch.
//            for (Floor f : map.values()) {
//                futures.add(executorService.submit(new mutation(f)));
//            }
//            for (Future<?> future : futures) {
//                try {
//                    future.get();
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//            for (int j = 0; j < startfloors; j++) {
//                double oldKey = map.
//                double keyToAdd;
//                if (map.containsKey((double)temp.getFitness())) {
//                    System.out.println("hit");
//                    keyToAdd = (1/2.0) + temp.getFitness();
//                    for (double n = 2.0; map.containsKey(keyToAdd); n++) {
//                        keyToAdd = (1/n) + temp.getFitness();
//                    }
//                }
//                else {
//                    keyToAdd = temp.getFitness();
//                }
//                mapKeysSorted.add(keyToAdd);
//                map.put(keyToAdd, temp);
//                System.out.println(keyToAdd);
//            }
//
//
//            //dump the children into the map without.
//            Collections.sort(mapKeysSorted);
//            for (Floor c : children) {
//                double min = mapKeysSorted.remove(0);
//                map.put(min, c);
//            }
//            executorService.shutdown();
//        }
//    }
//}