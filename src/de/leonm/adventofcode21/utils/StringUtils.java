package de.leonm.adventofcode21.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringUtils {

    /**
     * Finds char that occurs most often in all strings at given position
     * @param position Given position
     * @param input List containing the strings to be checked
     * @return Most common char at position
     */
    public static char findMostCommonCharAtPosition(int position, List<String> input) {
        Map<Character, Integer> countMap = new HashMap<>();
        for (String current : input) {
            char charAtPosition = current.charAt(position);
            countMap.merge(charAtPosition, 1, Integer::sum);
        }

        // Find largest key in hashmap
        return countMap.entrySet()
                .stream()
                .max((a, b) -> a.getValue() > b.getValue() ? 1 : -1)
                .get()
                .getKey();
    }
}
