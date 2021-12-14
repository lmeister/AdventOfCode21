package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day14 extends Day {

    private final String polymerTemplate;
    private Map<String, Character> pairInsertionRules;

    public Day14(String path) throws IOException {
        List<String> input = reader.getStringListFromFile(path);
        this.pairInsertionRules = new HashMap<>();
        this.polymerTemplate = input.get(0);

        // Fill map
        for (int i = 2; i < input.toArray().length; i++) {
            String line = input.get(i);
            String pair = line.substring(0, 2);
            Character result = line.charAt(line.length() - 1);
            this.pairInsertionRules.put(pair, result);
        }
    }


    @Override
    public String partOne() {
        Map<String, Long> pairOccurrence = getPairOccurrences(10);

        return String.valueOf(getDifferenceBetweenMostAndLeastCommonElement(pairOccurrence));
    }


    @Override
    public String partTwo() {
        Map<String, Long> pairOccurrence = getPairOccurrences(40);

        return String.valueOf(getDifferenceBetweenMostAndLeastCommonElement(pairOccurrence));
    }

    /**
     * Computes difference between most common element (aka char) and least comment element in the map of pair occurences.
     * @param pairOccurrence
     * @return
     */
    private long getDifferenceBetweenMostAndLeastCommonElement(Map<String, Long> pairOccurrence) {
        Map<Character, Long> frequencyMap = getFrequencyMap(pairOccurrence);

        long highestFrequency = Long.MIN_VALUE;
        long lowestFrequency = Long.MAX_VALUE;
        for (long frequency : frequencyMap.values()) {
            if (frequency < lowestFrequency) {
                lowestFrequency = frequency;
            }
            if (frequency > highestFrequency) {
                highestFrequency = frequency;
            }
        }

        return ((highestFrequency - lowestFrequency) / 2 + 1);
    }

    private Map<String, Long> getPairOccurrences(int steps) {
        Map<String, Long> pairOccurrence = new HashMap<>();
        // Count all the pairs we have
        for (int i = 1; i < polymerTemplate.length(); i ++) {
            pairOccurrence.merge(String.valueOf(polymerTemplate.charAt(i - 1) + String.valueOf(polymerTemplate.charAt(i))), 1L, Long::sum);
        }

        for (int i = 0; i < steps; i++) {
            Map<String, Long> tempMap = new HashMap<>();
            // For every pair
            for (String pair : pairOccurrence.keySet()) {
                // For each occurrence of the pair
                String firstPair = String.valueOf(pair.charAt(0)) + pairInsertionRules.get(pair);
                String secondPair = pairInsertionRules.get(pair) + String.valueOf(pair.charAt(1));
                tempMap.merge(firstPair, pairOccurrence.get(pair), Long::sum);
                tempMap.merge(secondPair, pairOccurrence.get(pair), Long::sum);

            }
            pairOccurrence.clear();
            // Copy the result maps
            pairOccurrence = new HashMap<>(tempMap);
        }

        return pairOccurrence;
    }

    private Map<Character, Long> getFrequencyMap(Map<String, Long> pairOccurrence) {
        Map<Character, Long> frequencyMap = new HashMap<>();
        for (String s : pairOccurrence.keySet()) {
            frequencyMap.merge(s.charAt(0), pairOccurrence.get(s), Long::sum);
            frequencyMap.merge(s.charAt(1), pairOccurrence.get(s), Long::sum);
        }
        return frequencyMap;
    }


}
