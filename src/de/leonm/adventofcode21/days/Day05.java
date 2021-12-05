package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO: Refactor this to use a map instead of an array to save some time
 */
public class Day05 extends Day {

    private final Pattern pattern = Pattern.compile("(?<begin>(\\d+,\\d+)) -> (?<end>(\\d+,\\d+))");
    private final List<String> input;

    public Day05(String path) throws IOException {
        input = reader.getStringListFromFile(path);
    }

    @Override
    int partOne() {
        int[][] grid = initializeVentGridNoDiagonals(1000, input);
        return countIntersections(grid);
    }

    @Override
    int partTwo() {
        int[][] grid = initializeVentGrid(1000, input);
        //print2DArray(grid);
        return countIntersections(grid);
    }

    private int countIntersections(int[][] grid) {
        int counterOfIntersections = 0;
        for (int[] ints : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (ints[j] > 1) {
                    counterOfIntersections++;
                }
            }
        }
        return counterOfIntersections;
    }

    private int[][] initializeVentGridNoDiagonals(int size, List<String> input) {
        int[][] grid = new int[size][size];
        for (String current : input) {
            Matcher matcher = pattern.matcher(current);
            if (matcher.find()) {
                int[] x = Arrays.stream(matcher.group("begin").split(",")).mapToInt(Integer::parseInt).toArray();
                int[] y = Arrays.stream(matcher.group("end").split(",")).mapToInt(Integer::parseInt).toArray();

                if (x[0] == y[0] || x[1] == y[1]) {
                    int maxX = Math.max(x[0], y[0]);
                    int minX = Math.min(x[0], y[0]);
                    int maxY = Math.max(x[1], y[1]);
                    int minY = Math.min(x[1], y[1]);

                    if (x[0] == y[0]) {
                        for (int i = minY; i <= maxY; i++) {
                            grid[i][x[0]] = grid[i][x[0]] + 1;
                        }
                    } else {
                        for (int i = minX; i <= maxX; i++) {
                            grid[x[1]][i] = grid[x[1]][i] + 1;
                        }
                    }
                }
            }
        }
        return grid;
    }

    private int[][] initializeVentGrid(int size, List<String> input) {
        int[][] grid = new int[size][size];
        for (String current : input) {
            Matcher matcher = pattern.matcher(current);
            if (matcher.find()) {
                int[] x = Arrays.stream(matcher.group("begin").split(",")).mapToInt(Integer::parseInt).toArray();
                int[] y = Arrays.stream(matcher.group("end").split(",")).mapToInt(Integer::parseInt).toArray();

                int maxX = Math.max(x[0], y[0]);
                int minX = Math.min(x[0], y[0]);
                int maxY = Math.max(x[1], y[1]);
                int minY = Math.min(x[1], y[1]);

                if (x[0] == y[0]) {
                    for (int i = minY; i <= maxY; i++) {
                        grid[i][x[0]] = grid[i][x[0]] + 1;
                    }
                } else if (x[1] == y[1]) {
                    for (int i = minX; i <= maxX; i++) {
                        grid[x[1]][i] = grid[x[1]][i] + 1;
                    }
                } else {
                    // Beide ungleich
                    int yOfStartingPoint = minX == x[0] ? x[1] : y[1];
                    int yOfEndPoint = maxX == x[0] ? x[1] : y[1];
                    for (int curX = minX; curX <= maxX; curX++) {
                        grid[yOfStartingPoint][curX] = grid[yOfStartingPoint][curX] + 1;
                        if (yOfStartingPoint <= yOfEndPoint) {
                            yOfStartingPoint++;
                        } else if (yOfStartingPoint >= yOfEndPoint) {
                            yOfStartingPoint--;
                        }
                    }
                }
            }
        }
        return grid;
    }

    private void print2DArray(int[][] array) {
        for (int[] ints : array) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(ints[j] + " ");
            }
            System.out.println("");
        }
    }
}
