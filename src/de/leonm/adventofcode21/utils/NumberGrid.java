package de.leonm.adventofcode21.utils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class NumberGrid {
    private long[][] grid;

    public NumberGrid(long[][] grid) {
        this.grid = grid;
    }

    public NumberGrid(NumberGrid grid) {
        this.grid = Arrays.stream(grid.grid).map(long[]::clone).toArray(long[][]::new);
    }

    public NumberGrid(List<String> input) {
        this.grid = get2DArrayFromStringList(input);
    }

    public NumberGrid(List<Point> points, long value) {
        // Sort Points by highest x value to get amount of columns
        points.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });
        int columns = points.get(points.size() - 1).x + 1;
        points.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.getY(), o2.getY());
            }
        });
        int rows = points.get(points.size() - 1).y + 1;
        long[][] grid = new long[columns][rows];
        for (Point point : points) {
            grid[point.x][point.y] = value;
        }
        this.grid = grid;
    }

    /**
     * Counts all the elements in the grid, that do match any of the given numbers.
     * @param n varargs containing the numbers that should be matched
     * @return
     */
    public long countOccurrences(long... n) {
        return iterateLong().filter(cur -> LongStream.of(n).anyMatch(curN -> curN == cur)).count();
    }

    public long getAmountOfElements() {
        return grid.length * grid[0].length;
    }

    /**
     * Counts all the elements in the grid, that do not match any of the given numbers.
     * @param n varargs containing the numbers that should not be matched
     * @return
     */
    public long countOccurrencesExcept(long... n) {
        return iterateLong().filter(cur -> LongStream.of(n).noneMatch(curN -> curN == cur)).count();
    }

    /**
     * Calculates the Sum of all values in grid where value equals the given numbers.
     * @param n
     * @return
     */
    public long sum(long... n) {
        return iterateLong().filter(cur -> LongStream.of(n).anyMatch(curN -> curN == cur)).sum();
    }

    /**
     * Calculates the Sum of all values in grid where value does not equal the given numbers.
     * @param n
     * @return
     */
    public long sumExcept(long... n) {
        return iterateLong().filter(cur -> LongStream.of(n).noneMatch(curN -> curN == cur)).sum();
    }

    /**
     * Creates a Stream containing Points in the Grid (i.e. every position of the Grid)
     * @return
     */
    public Stream<Point> getPointStream() {
        return IntStream.range(0, grid.length).boxed().flatMap(x -> IntStream.range(0, grid[x].length).mapToObj(y -> new Point(x, y)));
    }

    /**
     * Returns the value at point in grid.
     * @param point
     * @return -1 if point is not in grid.
     */
    public Optional<Long> getValueAt(Point point) {
        if (point.x < 0 || point.x >= grid.length || point.y < 0 || point.y >= grid[0].length) {
            return Optional.empty();
        } else {
            return Optional.of(grid[point.x][point.y]);
        }
    }

    /**
     * Set the value at point in grid.
     * @param point
     * @return
     * @throws IllegalArgumentException If given point is out of bounds
     */
    public boolean setValueAt(Point point, long value) {
        if (getValueAt(point).isEmpty()) {
            return false;
        } else {
            grid[point.x][point.y] = value;
        }
        return true;
    }

    /**
     * Increments the grid at point by given value.
     * @param point
     * @param value
     */
    public void incrementPointByValue(Point point, long value) {
        if (getValueAt(point).isPresent()) {

            setValueAt(point, getValueAt(point).get() + value);
        }
    }

    /**
     * Replaces all elements in grid, where value equals oldValue, with newValue.
     * @param oldValue
     * @param newValue
     * @return
     */
    public boolean replaceAll(long oldValue, long newValue){
        AtomicBoolean changed = new AtomicBoolean(false);

        getPointStream().forEach(point -> {
            if (getValueAt(point).isPresent() && getValueAt(point).get() == oldValue) {
                setValueAt(point, newValue);
                changed.set(true);
            }
        });

        return changed.get();
    }

    /**
     * Creates a long[][] from given List of Strings.
     * Each element in the list creates a new row. Amount of columns is equivalent to amount of chars in the line.
     * @param input
     * @return
     */
    private static long[][] get2DArrayFromStringList(List<String> input) {
        long[][] array = new long[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                array[i][j] = Character.getNumericValue(input.get(i).charAt(j));
            }
        }

        return array;
    }

    /**
     * Returns a stream of longs, containing every long in the grid.
     * @return Long Stream
     */
    public LongStream iterateLong() {
        return LongStream.range(0, grid.length).flatMap(i -> LongStream.of(grid[Math.toIntExact(i)]));
    }

    /**
     * Gets a List of all existing neighbors in range of 1, connected diagonally
     * @param p Given point
     * @return List of existing points in the neighborhood
     */
    public List<Point> getDiagonalNeighbors(Point p) {
        List<Point> neighbors = new ArrayList<>(4);

        Point[] diagonals = {
                new Point(p.x - 1, p.y - 1), // top left
                new Point(p.x - 1, p.y + 1), // top right
                new Point(p.x + 1, p.y - 1), // bottom left
                new Point(p.x + 1, p.y + 1) // bottom right
        };
        for (Point point : diagonals) {
            if (this.getValueAt(point).isPresent()) {
                neighbors.add(point);
            }
        }

        return neighbors;
    }

    /**
     * Gets a List of all existing neighbors in range of 1, connected non-diagonally
     * @param p Given point
     * @return List of existing points in the neighborhood
     */
    public List<Point> getStraightNeighbors(Point p) {
        List<Point> neighbors = new ArrayList<>(4);

        Point[] straights = {
                new Point(p.x - 1, p.y), // up
                new Point(p.x + 1, p.y), // down
                new Point(p.x, p.y - 1), // left
                new Point(p.x, p.y + 1) // right
        };

        for (Point point : straights) {
            if (this.getValueAt(point).isPresent()) {
                neighbors.add(point);
            }
        }

        return neighbors;
    }

    /**
     * Gets a List of all existing neighbors in range of 1, connected non-diagonally and diagonally
     * @param p Given point
     * @return List of existing points in the neighborhood
     */
    public List<Point> getAllNeighbors(Point p) {
        List<Point> neighbors = getDiagonalNeighbors(p);
        neighbors.addAll(getStraightNeighbors(p));

        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (getValueAt(new Point(i, j)).isPresent()) {
                    stringBuilder.append(getValueAt(new Point(i, j)).get()).append(" ");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public void transposeGrid() {
        long[][] transposedGrid = new long[this.grid[0].length][this.grid.length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (getValueAt(new Point(i, j)).isPresent()) {
                    transposedGrid[j][i] = getValueAt(new Point(i, j)).get();
                }
            }
        }
        this.grid = transposedGrid;
    }

    public int getAmountOfRows() {
        return this.grid.length;
    }

    public int getAmountOfColumns() {
        return this.grid[0].length;
    }

    public long[][] getGrid() {
        return grid;
    }
}
