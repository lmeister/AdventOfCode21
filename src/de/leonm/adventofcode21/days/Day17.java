package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.NumberGrid;

import java.awt.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day17 extends Day {

    private final Pattern pattern = Pattern.compile("target area: x=(?<x1>\\d+)..(?<x2>\\d+), y=(?<y1>-\\d+)..(?<y2>-\\d+)");
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    public Day17(String path) throws IOException {
        String line = reader.getStringListFromFile(path).get(0);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            this.x1 = Integer.parseInt(matcher.group("x1"));
            this.x2 = Integer.parseInt(matcher.group("x2"));
            this.y1 = Integer.parseInt(matcher.group("y1"));
            this.y2 = Integer.parseInt(matcher.group("y2"));
        }
        System.out.println("X1: " + x1 + ", x2: " + x2 + " - y1: " + y1 + ", y2: " + y2);
    }


    @Override
    public String partOne() {
        // lol
        return String.valueOf((Math.abs(this.y1) * (Math.abs(this.y1) - 1)) / 2);
    }

    @Override
    public String partTwo() {
        int sumOfDistinctVelocityValues = 0;
        for (int x = 0; x <= this.x2; x++) {
            for (int y = this.y1; y <= Math.abs(this.y1); y++) {
                sumOfDistinctVelocityValues += landsWithinArea(x, y);
            }
        }
        return String.valueOf(sumOfDistinctVelocityValues);
    }

    private int landsWithinArea(int xVelocity, int yVelocity) {
        int x = 0;
        int y = 0;

        while (y > this.y1) {
            x += xVelocity;
            y += yVelocity;

            if (xVelocity > 0) {
                xVelocity--;
            }
            yVelocity--;
            // Are we in the target?
            if (this.x1 <= x && x <= this.x2 && this.y1 <= y && y <= this.y2) {
                System.out.println(x + ", " + y);
                return 1;
            }
        }
        return 0;
    }

}
