package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.*;


public class Day08 extends Day {

    // First 10 elements found are prior to the |
    // Next 4 are the output values
private final List<String> input;

    public Day08(String path) throws IOException {
        input = reader.getStringListFromFile(path);
    }



    @Override
    public String partOne() {

        int amountOfUniqueSegmentNumbers = 0;
        for (String line : input) {
            // ignore index 10, since it's the pipe
            String[] values = line.split(" ");
            for (int i = 11; i < 15; i++) {
                int outputValueLength = values[i].length();
                    if (outputValueLength == 2
                    || outputValueLength == 3
                    || outputValueLength == 4
                    || outputValueLength == 7) {
                        amountOfUniqueSegmentNumbers++;
                    }
            }
        }
        return String.valueOf(amountOfUniqueSegmentNumbers);
    }


    /* Explanation of Part two algorithms:
        Structure of a digital digit
        aaaa            We know which segments are turned on by the given String
       b    c           e.g. cf would indicate the c and f segments to be turned on, resulting in a 1
       b    c           0 = abcefg, 1 = cf, 2 = acdeg, 3 = acdfg, 4 = bcdf, 5 = abdfg, 6 = abdefg, 7 = acf, 8 = abcdefg, 9 = abcdfg
        dddd            The numbers 1, 4, 7 and 8 all have a unique amount of segments and thus can be identified
       e    f
       e    f           ALso go via length: If length is 2,3,4 or 7 we can identify immediately
        gggg            If length = 5 it can be either 2, 3 or 5
                        -> if it contains the 7 (i.e. the edges acf) it's a 3
                        -> Else: 2 or 5
                        ---> if it shares 3 spots with the 4 (i.e. edges bcdf) its a 5
                        ----> else its 2

                        If length = 6 it can be 0, 6 or 9
                        -> if it contains the 4 (i.e. the edges bcdf) it's a 9
                        -> Else: 0 or 6
                        ---> if it contains a 7 it's a 0
                        ----> else its a 6
     */
    @Override
    public String partTwo() {
        Map<Integer, String> combinationDigitMap = new HashMap<>();
        Map<String, Integer> digitCombinationMap = new HashMap<>();
        int sum = 0;

        for (String line : input) {
            String[] signalPatterns = Arrays.copyOfRange(line.split(" "), 0, 10);
            String[] outputValues = Arrays.copyOfRange(line.split(" "), 11, 15);
            Arrays.sort(signalPatterns, Comparator.comparingInt(String::length));

            // Decode input values
            for (int i = 0; i < 10; i++) {
                char[] tempArray = signalPatterns[i].toCharArray();
                Arrays.sort(tempArray);
                String inputValue = new String(tempArray);

                switch (inputValue.length()) {
                    case 2:
                        combinationDigitMap.put(1, inputValue);
                        digitCombinationMap.put(inputValue, 1);
                        break;
                    case 3:
                        combinationDigitMap.put(7, inputValue);
                        digitCombinationMap.put(inputValue, 7);
                        break;
                    case 4:
                        combinationDigitMap.put(4, inputValue);
                        digitCombinationMap.put(inputValue, 4);
                        break;
                    case 5:
                        if (checkIfSubset(inputValue.toCharArray(), combinationDigitMap.get(7).toCharArray())) {
                            combinationDigitMap.put(3, inputValue);
                            digitCombinationMap.put(inputValue, 3);
                        } else {
                            if (checkIfIntersectionOfSize(inputValue.toCharArray(), combinationDigitMap.get(4).toCharArray(), 3)) {
                                combinationDigitMap.put(5, inputValue);
                                digitCombinationMap.put(inputValue, 5);
                            } else {
                                combinationDigitMap.put(2, inputValue);
                                digitCombinationMap.put(inputValue, 2);
                            }
                        }
                        break;
                    case 6:
                        if (checkIfSubset(inputValue.toCharArray(), combinationDigitMap.get(4).toCharArray())) {
                            combinationDigitMap.put(9, inputValue);
                            digitCombinationMap.put(inputValue, 9);
                        } else {
                            if (checkIfSubset(inputValue.toCharArray(), combinationDigitMap.get(7).toCharArray())) {
                                combinationDigitMap.put(0, inputValue);
                                digitCombinationMap.put(inputValue, 0);
                            } else {
                                combinationDigitMap.put(6, inputValue);
                                digitCombinationMap.put(inputValue, 6);
                            }
                        }
                        break;
                    case 7:
                        combinationDigitMap.put(8, inputValue);
                        digitCombinationMap.put(inputValue, 8);
                        break;

                }
            }

            StringBuilder decodedNumber = new StringBuilder();
            // Calculate output value (i.e. last 4 digits)
            for (int j = 0; j < 4; j++) {
                char[] tempArray = outputValues[j].toCharArray();
                Arrays.sort(tempArray);
                decodedNumber.append(digitCombinationMap.get(new String(tempArray)));
            }
            // Add to sum
            sum += Integer.parseInt(decodedNumber.toString());

            digitCombinationMap.clear();
            combinationDigitMap.clear();
        }

        return String.valueOf(sum);
    }

    /**
     * Checks if second is a subset of first
     * @param first char array
     * @param second char array
     * @return
     */
    private boolean checkIfSubset(char[] first, char[] second) {
        Set<Character> superset = new HashSet<>(first.length);
        for (char f : first) {
            superset.add(f);
        }

        for (char s : second) {
            if (!superset.contains(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the amount of elements in the intersection of two sets is at least as big as given amount
     * @param first char array
     * @param second char array
     * @param amount the minimum amount of elements in intersection
     * @return
     */
    private boolean checkIfIntersectionOfSize(char[] first, char[] second, int amount) {
        Set<Character> superset = new HashSet<>(first.length);
        int elementsInIntersection = 0;
        for (char f : first) {
            superset.add(f);
        }

        for (char s : second) {
            if (superset.contains(s)) {
                elementsInIntersection++;
            }
        }
        return elementsInIntersection >= amount;
    }

}
