package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.ArrayUtils;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class Day09 extends Day {

    private final int[][] heightMap;
    private final List<Point> lowPoints;

    public Day09(String path) throws IOException {
        List<String> input = reader.getStringListFromFile(path);
        lowPoints = new ArrayList<>();
        heightMap = ArrayUtils.get2DArrayFromStringList(input);
    }


    @Override
    public String partOne() {
        int riskLevelSum = 0;

        for (int x = 0; x < heightMap.length; x++) {
            for (int y = 0; y < heightMap[0].length; y++) {
                if (isLowestPoint(x, y)) {
                    lowPoints.add(new Point(x, y));
                    riskLevelSum += (1 + heightMap[x][y]);
                }
            }
        }

        return String.valueOf(riskLevelSum);
    }


    @Override
    public String partTwo() {
        List<Integer> foundSizes = new ArrayList<>();
        Set<Point> visitedPoints = new HashSet<>();

        for (Point curPoint : lowPoints) {
            // Disregard seen points or points that have a value of 9 in heightmap
            if (!visitedPoints.contains(curPoint) && heightMap[curPoint.x][curPoint.y] != 9) {
                int basinSize = 0;
                Deque<Point> queue = new ArrayDeque<>();
                queue.add(curPoint);

                // Queue contains points that we need to examine
                while (!queue.isEmpty()) {
                    Point point = queue.removeFirst();

                    // If we haven't seen the point
                    if (!visitedPoints.contains(point)) {
                        visitedPoints.add(point);
                        basinSize++;

                        // Put all basinNeighbors (i.e. !=9 and larger than curPoint) into queue
                        queue.addAll(getBasinNeighbors(point.x, point.y));
                    }
                }
                // Add the size of this basin to the list of sizes
                foundSizes.add(basinSize);
            }
        }
        // Find 3 largest basin sizes and multiply them. Return the result
        return foundSizes.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .reduce(1, (a, b) -> a * b)
                .toString();
    }

    /**
     * Checks if straight neighbors (no diagonals) of point x and y in map are of higher value.
     *
     * @param x Current x position
     * @param y Current y position
     * @return true if current position is greater than all neighbors, else false
     */
    private boolean isLowestPoint(int x, int y) {
        int valueAtPosition = heightMap[x][y];
        return ArrayUtils.getStraightNeighbors(x, y, heightMap).stream().noneMatch(p -> heightMap[p.x][p.y] <= valueAtPosition);
    }

    /**
     * Retrieves all neighboring points that are part of the same basin as the given point.
     *
     * @param x x Position of given point
     * @param y y position of given point
     * @return List of Points that are not 9, for which heightmap at each point has a higher value than for the given point.
     */
    private List<Point> getBasinNeighbors(int x, int y) {
        List<Point> basinNeighbors = new ArrayList<>(4);

        for (Point neighbor : ArrayUtils.getStraightNeighbors(x, y, heightMap)) {
            if (heightMap[neighbor.x][neighbor.y] >= heightMap[x][y] && heightMap[neighbor.x][neighbor.y] != 9) {
                basinNeighbors.add(neighbor);
            }
        }

        return basinNeighbors;
    }


}
