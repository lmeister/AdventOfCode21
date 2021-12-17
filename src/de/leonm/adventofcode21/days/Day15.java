package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.ArrayUtils;
import de.leonm.adventofcode21.utils.NumberGrid;

import java.awt.*;
import java.io.IOException;
import java.util.*;


public class Day15 extends Day {
    private NumberGrid grid;

    public Day15(String path) throws IOException {
        this.grid = new NumberGrid(new NumberGrid(reader.getStringListFromFile(path)));
    }


    @Override
    public String partOne() {
        Point destination = new Point(grid.getAmountOfRows() - 1, grid.getAmountOfColumns() - 1);
        return String.valueOf(findCheapestPath(grid, grid.getAmountOfRows() - 1, grid.getAmountOfColumns() - 1, 1, destination));
    }

    @Override
    public String partTwo() {
        int sizeFactor = 5;
        Point destination = new Point((grid.getAmountOfRows() * sizeFactor) - 1, (grid.getAmountOfColumns() * sizeFactor) - 1);
        return String.valueOf(findCheapestPath(grid, (grid.getAmountOfRows() * sizeFactor) - 1, (grid.getAmountOfColumns() * sizeFactor) - 1, sizeFactor, destination));
    }

    private static long findCheapestPath(NumberGrid numberGrid, int amountOfRows, int amountOfColumns, int sizeFactor, Point destination) {
        long[][] grid = numberGrid.getGrid();
        // Resize the grid
        long[][] riskGrid = new long[numberGrid.getAmountOfRows() * sizeFactor][numberGrid.getAmountOfColumns() * sizeFactor];
        // If Target is out of bounds, return -1
        if (!isInBounds(destination.x, destination.y, riskGrid)) {
            return -1;
        }

        int[] dx = {1, 0, -1, 0}; // for moving
        int[] dy = {0, 1, 0, -1}; // for moving


        for (int i = 0; i < riskGrid.length; i++) {
            for (int j = 0; j < riskGrid[0].length; j++) {
                riskGrid[i][j] = Long.MAX_VALUE; // Set each distance to max value first
            }
        }

        riskGrid[0][0] = 0; // Start point has 0 dist

        PriorityQueue<GridSquare> queue = new PriorityQueue<>(amountOfRows * amountOfColumns, Comparator.comparing(GridSquare::getRisk));
        queue.add(new GridSquare(0, 0, riskGrid[0][0]));

        while (!queue.isEmpty()) {
            GridSquare curSquare = queue.poll();

            for (int i = 0; i < dx.length; i++) {
                int neighborX = curSquare.x + dx[i];
                int neighborY = curSquare.y + dy[i];

                // Assures that we're not out of bounds
                if (isInBounds(neighborX, neighborY, riskGrid)) {
                    // Cope with size
                    long valueAtCurrentNeighbor = grid[neighborX % grid.length][neighborY % grid.length] + (neighborX / grid.length) + (neighborY / grid[0].length);
                    if (valueAtCurrentNeighbor >= 10) {
                        valueAtCurrentNeighbor -= 9;
                    }

                    // Upgrade with cheaper value
                    if (riskGrid[neighborX][neighborY] > riskGrid[curSquare.x][curSquare.y] + valueAtCurrentNeighbor) {
                        if (riskGrid[neighborX][neighborY] != Long.MAX_VALUE) {
                            // remove it so we can put back in the updated square
                            GridSquare neighbor = new GridSquare(neighborX, neighborY, riskGrid[neighborX][neighborY]);
                            queue.remove(neighbor);
                        }

                        riskGrid[neighborX][neighborY] = riskGrid[curSquare.x][curSquare.y] + valueAtCurrentNeighbor;

                        queue.add(new GridSquare(neighborX, neighborY, riskGrid[neighborX][neighborY]));
                    }
                }

            }
        }

        // Return the destination square's value
        return riskGrid[riskGrid.length - 1][riskGrid[0].length - 1];
    }

    public static boolean isInBounds(int x, int y, long[][] grid) {
        return x >= 0 && x < grid.length && y >= 0 && y < grid[0].length;
    }

    // Saves distance (distance) from start to given point (x, y)

    /**
     * Saves the lowest risk possible from start (0, 0) to given point
     */
    private static class GridSquare {
        private final int x;
        private final int y;

        private final long risk;

        public GridSquare(int x, int y, long risk) {
            this.x = x;
            this.y = y;
            this.risk = risk;
        }

        public long getRisk() {
            return risk;
        }
    }

}
