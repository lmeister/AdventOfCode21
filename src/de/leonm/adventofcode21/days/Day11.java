package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.NumberGrid;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


public class Day11 extends Day {

    private NumberGrid octopusGrid;

    public Day11(String path) throws IOException {
        octopusGrid = new NumberGrid(reader.getStringListFromFile(path));
    }


    @Override
    public String partOne() {
        NumberGrid copy = new NumberGrid(octopusGrid);
        long flashCounter = 0;
        for (int i = 0; i < 100; i++) {
            //System.out.println("---------- " + i + " ----------");
            //System.out.println(octopusGrid);
            flashCounter += cellularAutomataStep(copy);
        }

        return String.valueOf(flashCounter);
    }


    @Override
    public String partTwo() {
        NumberGrid copy = new NumberGrid(octopusGrid);

        for (int step = 1; true; step++) {
            cellularAutomataStep(copy);
            if (copy.countOccurrences(0) == copy.getAmountOfElements()) {
                return String.valueOf(step);
            }
        }
    }

    private Long cellularAutomataStep(NumberGrid grid) {
        grid.getPointStream().forEach(point -> grid.incrementPointByValue(point, 1L));
        AtomicBoolean flashed = new AtomicBoolean(false);
        AtomicLong flashCounter = new AtomicLong(0L);

        do {
            flashed.set(false);
            grid.getPointStream()
                    .filter(point -> grid.getValueAt(point).isPresent() && grid.getValueAt(point).get() > 9L)
                    .forEach(flashingPoint -> {

                        // Increment each neighboring point
                        for (Point neighbor : grid.getAllNeighbors(flashingPoint)) {
                            if (grid.getValueAt(neighbor).isPresent() && grid.getValueAt(neighbor).get() != -1L)
                                grid.incrementPointByValue(neighbor, 1L);
                        }

                        // set flashing point to -1, so that it wont flash again
                        grid.setValueAt(flashingPoint, -1L);

                        flashCounter.getAndIncrement();
                        flashed.set(true);
                    });
        } while (flashed.get());

        // Reset all points that have flashed
        grid.replaceAll(-1L, 0L);

        return flashCounter.get();
    }

}
