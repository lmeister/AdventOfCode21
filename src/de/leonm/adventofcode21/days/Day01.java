package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day01 extends Day {

    private final List<Integer> input;

    public Day01(String path) throws IOException {
        this.input = reader.getIntListFromFile(path);
    }

    @Override
    long partOne() {
        int lastElement = Integer.MAX_VALUE;
        int increaseCounter = 0;
        for (int current : input) {
                if (current > lastElement) {
                    increaseCounter++;
                }
                lastElement = current;
            }
        return increaseCounter;
    }

    @Override
    long partTwo() {
        int[] lastWindow = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        int increaseCounter = 0;

        for (int i = 2; i < input.size(); i++) {
            int[] currentWindow = {input.get(i - 2), input.get(i - 1), input.get(i)};
            if (Arrays.stream(currentWindow).sum() > Arrays.stream(lastWindow).sum()) {
                increaseCounter++;
            }
            lastWindow = currentWindow;
        }
        return increaseCounter;
    }
}
