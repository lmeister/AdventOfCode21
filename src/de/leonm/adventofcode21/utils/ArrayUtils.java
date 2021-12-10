package de.leonm.adventofcode21.utils;

import java.util.HashSet;
import java.util.Set;

public class ArrayUtils {

    /**
     * Checks if second is a subset of first
     * @param first char array
     * @param second char array
     * @return
     */
    public static boolean checkIfSubset(char[] first, char[] second) {
        Set<Character> superset = new HashSet<>(first.length);
        for (char f : first) {
            superset.add(f);
        }

        for (char s : second) {
            if (!superset.contains(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the amount of elements in the intersection of two sets is at least as big as given amount
     * @param first char array
     * @param second char array
     * @param amount the minimum amount of elements in intersection
     * @return
     */
    public static boolean checkIfIntersectionOfSize(char[] first, char[] second, int amount) {
        Set<Character> superset = new HashSet<>(first.length);
        int elementsInIntersection = 0;
        for (char f : first) {
            superset.add(f);
        }

        for (char s : second) {
            if (superset.contains(s)) {
                elementsInIntersection++;
            }
        }
        return elementsInIntersection >= amount;
    }

    public <T> void print2DArray(T[][] array) {
        for (T[] ints : array) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println("");
        }
    }
}
