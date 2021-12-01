package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day01 extends Day {

    List<Integer> input;

    public Day01() throws IOException {
        this.input = reader.getIntListFromFile("src/de/leonm/adventofcode21/inputs/Day01.txt");
    }

    @Override
    int partOne() {
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
    int partTwo() {
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
