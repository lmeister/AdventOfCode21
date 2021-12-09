package de.leonm.adventofcode21.days;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Day09 extends Day {

    // First 10 elements found are prior to the |
    // Next 4 are the output values
    private final List<String> input;
    private static final int MIN_X = 0;
    private static final int MAX_X = 99;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 99;
    private int[][] heightmap;
    private List<Point> lowpoints;

    public Day09(String path) throws IOException {
        input = reader.getStringListFromFile(path);
        heightmap = new int[MAX_X + 1][MAX_Y + 1];
        lowpoints = new ArrayList<>();
        // Initialize the height map
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                heightmap[i][j] = Character.getNumericValue(input.get(i).charAt(j));
            }
        }
    }



    @Override
    public String partOne() {
        int riskLevelSum = 0;

        for (int x = 0; x < heightmap.length; x++) {
            for (int y = 0; y < heightmap[0].length; y++) {
                if (isLowestPoint(x, y)) {
                    lowpoints.add(new Point(x, y));
                    riskLevelSum += (1 + heightmap[x][y]);
                }
            }
        }

        return String.valueOf(riskLevelSum);
    }


    @Override
    public String partTwo() {

        // Divide grid into sections where 9s are walls
        List<Integer> foundSizes = new ArrayList<>();
        Set<Point> visitedPoints = new HashSet<>();
        for (Point curPoint : lowpoints) {
                if (!visitedPoints.contains(curPoint) && heightmap[curPoint.x][curPoint.y] != 9) {
                    int basinSize = 0;
                    Deque<Point> queue = new ArrayDeque<>();
                    queue.add(curPoint);
                    while (!queue.isEmpty()) {
                        Point point = queue.removeFirst();
                        // If we have seen this square, do nothing
                        if (visitedPoints.contains(point)) {
                            continue;
                        }

                        // otherwise mark as seen and increase basin size
                        visitedPoints.add(point);
                        basinSize++;

                        // Put all neighbors that arent 9 in queue
                        queue.addAll(getAllNon9Neighbors(point.x, point.y));
                    }
                    foundSizes.add(basinSize);
                }
            }
        foundSizes.sort(Collections.reverseOrder());
        int productOf3LargestBasins = foundSizes.get(0) * foundSizes.get(1) * foundSizes.get(2);
        return String.valueOf(productOf3LargestBasins);
    }

    /**
     * Checks if straight neighbors (no diagonals) of point x and y in map are of higher value.
     * @param x Current x position
     * @param y Current y position
     * @return true if current position is greater than all neighbors, else false
     */
    private boolean isLowestPoint(int x, int y) {
        if (x != 0) {
            if (heightmap[x - 1][y] <= heightmap[x][y]) {
                return false;
            }
        }

        if (x != heightmap.length - 1) {
            if (heightmap[x + 1][y] <= heightmap[x][y]) {
                return false;
            }
        }

        if (y != 0) {
            if (heightmap[x][y - 1] <= heightmap[x][y]) {
                return false;
            }
        }

        if (y != heightmap[0].length - 1) {
            if (heightmap[x][y + 1] <= heightmap[x][y]) {
                return false;
            }
        }
        return true;
    }

    private List<Point> getAllNon9Neighbors(int x, int y) {
        List<Point> non9Points = new ArrayList<>(4);

        // Check the left neighbor unless we're at boundary
        if (x != 0) {
            if (heightmap[x - 1][y] >= heightmap[x][y] && heightmap[x - 1][y] != 9) {
                non9Points.add(new Point(x - 1, y));
            }
        }

        // Check the right neighbor unless we're at boundary
        if (x != heightmap.length - 1) {
            if (heightmap[x + 1][y] >= heightmap[x][y] && heightmap[x + 1][y] != 9) {
                non9Points.add(new Point(x + 1, y));
            }
        }

        // Check the lower neighbor unless we're at boundary
        if (y != 0) {
            if (heightmap[x][y - 1] >= heightmap[x][y] && heightmap[x][y - 1] != 9) {
                non9Points.add(new Point(x, y - 1));
            }
        }

        // Check the upper neighbor unless we're at boundary
        if (y != heightmap[0].length - 1) {
            if (heightmap[x][y + 1] >= heightmap[x][y] && heightmap[x][y + 1] != 9) {
                non9Points.add(new Point(x, y + 1));
            }
        }

        return non9Points;
    }

}
