package de.leonm.adventofcode21.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public static void print2DArray(int[][] array) {
        for (int[] line : array) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(line[j] + " ");
            }
            System.out.println("");
        }
    }

    /**
     * Retrieves all 4 neighbors of a point given by x and y in the heightmap.
     * Aware of boundaries.
     * @param x x Position of given point
     * @param y y position of given point
     * @return A list containing the neighbors as Point objects
     */
    public static List<Point> getStraightNeighbors(int x, int y, int[][] array) {
        List<Point> neighbors = new ArrayList<>(4);

        // Check the left neighbor unless we're at boundary
        if (x != 0) {
            neighbors.add(new Point(x - 1, y));
        }

        // Check the right neighbor unless we're at boundary
        if (x != array.length - 1) {
            neighbors.add(new Point(x + 1, y));
        }

        // Check the lower neighbor unless we're at boundary
        if (y != 0) {
            neighbors.add(new Point(x, y - 1));
        }

        // Check the upper neighbor unless we're at boundary
        if (y != array[0].length - 1) {
            neighbors.add(new Point(x, y + 1));
        }

        return neighbors;
    }

    public static List<Point> getDiagonalNeighbors(int x, int y, int[][] array) {
        List<Point> neighbors = new ArrayList<>(4);

        // First line
        if (x == 0) {
            if (y == 0) {
                neighbors.add(new Point(x + 1, y + 1));
            } else if (y == array[0].length - 1) {
                neighbors.add(new Point(x + 1, y -1));
            } else {
                neighbors.add(new Point(x + 1, y + 1));
                neighbors.add(new Point(x + 1, y -1));
            }
        } else if (x == array.length - 1) {
            if (y == 0) {
                neighbors.add(new Point(x - 1, y + 1));
            } else if (y == array[0].length - 1) {
                neighbors.add(new Point(x - 1, y - 1));
            } else {
                neighbors.add(new Point(x - 1, y + 1));
                neighbors.add(new Point(x - 1, y - 1));
            }
            // Somewhere in middle of x
        } else {
            if (y == 0) {
                neighbors.add(new Point(x + 1, y - 1));
                neighbors.add(new Point(x + 1, y + 1));
            } else if (y == array[0].length - 1) {
                neighbors.add(new Point(x - 1, y - 1));
                neighbors.add(new Point(x - 1, y + 1));
            } else {
                neighbors.add(new Point(x - 1, y - 1));
                neighbors.add(new Point(x - 1, y + 1));
                neighbors.add(new Point(x + 1, y - 1));
                neighbors.add(new Point(x + 1, y + 1));
            }
        }

        return neighbors;
    }

    // Todo this will be removed, as NumberGrid class is used
    public static int[][] get2DArrayFromStringList(List<String> input) {
        int[][] array = new int[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                array[i][j] = Character.getNumericValue(input.get(i).charAt(j));
            }
        }

        return array;
    }
}
