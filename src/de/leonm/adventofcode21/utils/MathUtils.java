package de.leonm.adventofcode21.utils;

import java.util.Arrays;

public class MathUtils {

    /**
     * Requires input array to be sorted.
     * @param input sorted integer array
     * @return the median of the int array
     */
    public static double findMedian(int[] input) {
        int middle = input.length / 2;
        // if input has even length, median is the avg of both "middle elements"
        if (input.length % 2 == 0) {
            return (input[middle] + input[middle - 1]) / 2.0;
            // Else mean is middle element
        } else {
            return input[middle];
        }
    }

    /**
     * Computes arithmetic mean
     * @param input integer array
     * @return mean as double
     */
    public static double findMean(int[] input) {
        return (double) Arrays.stream(input).sum() / input.length;
    }

}
