package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.StringUtils;

import java.io.IOException;
import java.util.*;

public class Day03 extends Day {

    private final List<String> input;

    public Day03(String path) throws IOException {
        this.input = reader.getStringListFromFile(path);
    }

    @Override
    public String partOne() {
        // form the binary strings
        StringBuilder gammaRate = new StringBuilder();
        StringBuilder epsilonRate = new StringBuilder();

        for (int i = 0; i < input.get(0).length(); i++) {
            char mostCommonBit = StringUtils.findMostCommonCharAtPosition(i, input);
            gammaRate.append(mostCommonBit);

            char leastCommonBit = (char) (mostCommonBit ^ 1); // flips the bit and cast to char
            epsilonRate.append(leastCommonBit);
        }

        return String.valueOf(Long.parseLong(gammaRate.toString(), 2) * Long.parseLong(epsilonRate.toString(), 2));
    }

    @Override
    public String partTwo() {
        String oxygenRating = findFinalCandidate(true, input);
        String scrubberRating = findFinalCandidate(false, input);
        return String.valueOf(Long.parseLong(oxygenRating, 2) * Long.parseLong(scrubberRating, 2));
    }

    /**
     * Filters the list according to criteria.
     * @param mostCommon if true will filter out values that dont share most common, else least common
     * @param input The list to be searched
     * @return last candidate
     */
    private String findFinalCandidate(boolean mostCommon, List<String> input) {
        List<String> copy = new ArrayList<>(input);

        // Iterate through String length
        for (int i = 0; i < copy.get(0).length(); i++) {
            // In each iteration determine most common bit for the current position i
            char mostCommonBit = StringUtils.findMostCommonCharAtPosition(i, copy);

            if (mostCommon) {
                int currentPosition = i; // Helper variable for lambda
                copy.removeIf(c -> c.charAt(currentPosition) != mostCommonBit);
            } else {
                // Flip the bit and cast to char
                char leastCommonBit = (char) (mostCommonBit ^ 1);

                int finalI = i; // Helper variable for lambda
                copy.removeIf(c -> c.charAt(finalI) != leastCommonBit);
            }

            // Stop if we already have only 1 candidate left
            if (copy.size() == 1) {
                break;
            }
        }
        return copy.get(0);
    }



}
