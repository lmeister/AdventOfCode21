package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.MathUtils;

import java.io.IOException;
import java.util.*;

public class Day07 extends Day {


    private final int[] input;

    public Day07(String path) throws IOException {
        input = reader.getIntArrayFromCSV(path);
    }



    @Override
    public String partOne() {
        int lowestFuelRequired = 0;
        Arrays.sort(input);

        // Property of median is that every element in the list will have lowest deviation from median
        for (int crabPosition : input) {
            lowestFuelRequired += Math.abs(crabPosition - (int) (MathUtils.findMedian(input)));
        }

        return String.valueOf(lowestFuelRequired);
    }

    @Override
    public String partTwo() {
        // Sum of 1..n can be calculated via gauss's (n(n+1))/2 which we can estimate to n^2
        // This allows us to use the arithmetic mean as an estimation of lowest deviation of each element
        // (Will have to check around it)
        double mean = MathUtils.findMean(input);
        // solution is then the lowest of the two
        int lowestFuelRequired = Math.min(calculateFuelIncreasingPrice(input, (int) Math.floor(mean)),
                calculateFuelIncreasingPrice(input, (int) Math.ceil(mean)));

        return String.valueOf(lowestFuelRequired);
    }




    /**
     * Calculates fuel via gauss
     * @param positions current depths of the crabs
     * @param target target depth of the crab
     * @return fuel price for the movement of each crab summed up
     */
    private int calculateFuelIncreasingPrice(int[] positions, int target) {
        int cost = 0;
        for (int pos : positions) {
            int n = Math.abs(target - pos);
            cost += (n * (n + 1))/2;
        }
        return cost;
    }
}
