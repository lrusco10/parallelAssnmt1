package org.example;

//similar stations should be close
//different stations should be far

public class Station {
    private final int affinity;
    private int x, y;

/*
types:
    1:  is green, aff = 1
    2: is red, aff = 2
    3: is blue, aff = 3
    4: is yellow, aff = 4
    green should be closest to red and furthest from yellow
    red should be closest to green or blue, but furthest from yellow
    blue should be closest to red or yellow, but furthest from green
    yellow should be closest to blue but furthest from green
    distance is calculated via Euclidean operation.
 */
    public Station(int type, int affinity) {
        this.affinity = affinity;
    }


    public double getFitness(Station other) {

        int afScore = Math.abs(this.affinity - other.affinity); // find difference in affinity
        double distance = Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));

        switch (afScore) {
            case 1: // close
                if (distance <= 5.0) {
                    return 1.0;
                } else if (distance >= 10.0) {
                    return 0.0;
                } else {
                    // linear from 1 at 5 -> 0 at 10
                    return clamp(1.0 - (distance - 5.0) / 5.0, 0.0, 1.0);
                }

            case 2: // mid
                if (distance >= 6.0 && distance <= 10.0) {
                    return 1.0;
                } else if (distance < 6.0) {
                    // ramp from 0 at 0 -> 1 at 6
                    return clamp(distance / 6.0, 0.0, 1.0);
                } else { // distance > 10
                    if (distance >= 15.0) return 0.0;
                    // linear from 1 at 10 -> 0 at 15
                    return clamp(1.0 - (distance - 10.0) / 5.0, 0.0, 1.0);
                }

            case 3: // far
                if (distance >= 15.0) {
                    return 1.0;
                } else if (distance <= 10.0) {
                    return 0.0;
                } else {
                    // linear from 0 at 10 -> 1 at 15
                    return clamp((distance - 10.0) / 5.0, 0.0, 1.0);
                }

            default:
                return 0.0;
        }
    }

    private static double clamp(double v, double min, double max) {
        if (v < min) return min;
        if (v > max) return max;
        return v;
    }



}
