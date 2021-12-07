package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.*;

public class Day07 extends Day {


    private final int[] input;

    public Day07(String path) throws IOException {
        input = reader.getIntArrayFromCSV(path);
    }



    @Override
    public String partOne() {
        Arrays.sort(input);

        int lowestFuelRequired = Integer.MAX_VALUE;
        int depthForLowestFuel = -1;
        for (int depth = 0; depth < input[input.length - 1]; depth++) {
            int tempFuel = 0;
            for (int crabPosition : input) {
                tempFuel += Math.abs(depth - crabPosition);
                if (tempFuel > lowestFuelRequired) {
                    break;
                }
            }
            if (tempFuel <= lowestFuelRequired) {
                lowestFuelRequired = tempFuel;
            }
            // System.out.println("Fuel: " + tempFuel + ", Depth: " + depth);
        }

        return String.valueOf(lowestFuelRequired);
    }

    @Override
    public String partTwo() {
        Arrays.sort(input);

        int lowestFuelRequired = Integer.MAX_VALUE;
        for (int depth = 0; depth < input[input.length - 1]; depth++) {
            int tempFuel = 0;
            for (int crabPosition : input) {
                int n = Math.abs(depth - crabPosition);
                tempFuel += (n * (n+1)) / 2;
                if (tempFuel > lowestFuelRequired) {
                    break;
                }
            }
            if (tempFuel <= lowestFuelRequired) {
                lowestFuelRequired = tempFuel;
            }
            // System.out.println("Fuel: " + tempFuel + ", Depth: " + depth);
        }

        return String.valueOf(lowestFuelRequired);
    }
}
