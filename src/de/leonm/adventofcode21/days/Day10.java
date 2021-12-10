package de.leonm.adventofcode21.days;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;


public class Day10 extends Day {

    private final List<String> input;
    private final Set<String> illegalLines;
    private static Map<Character, Integer> charValue;


    public Day10(String path) throws IOException {
        input = reader.getStringListFromFile(path);
        illegalLines = new HashSet<>(input.size());
        charValue = new HashMap<>();
    }


    @Override
    public String partOne() {
        // Set up for point calculation
        charValue = Map.of(')', 3, ']', 57, '}', 1197, '>', 25137);

        Map<Character, Integer> charErrorCounter = new HashMap<>();

        input.forEach(line -> {
            Optional<Character> illegalBracket = findIllegalBracket(line);
            illegalBracket.ifPresent(character -> {
                // count up in map
                charErrorCounter.merge(character, 1, Integer::sum);
                // mark as illegal for part two
                illegalLines.add(line);
            });
        });

        int syntaxScore = 0;
        for (Character bracket : charErrorCounter.keySet()) {
            syntaxScore += (charErrorCounter.get(bracket) * charValue.get(bracket));

        }
        System.out.println(charErrorCounter);
        return String.valueOf(syntaxScore);
    }


    @Override
    public String partTwo() {
        charValue = Map.of(')', 1, ']', 2, '}', 3, '>', 4);

        List<Long> points = input.stream()
                .filter(line -> !illegalLines.contains(line)) // Ignore illegal lines
                .map(Day10::findMissingBrackets) // Finds missing brackets in line
                .map(Day10::pointsPerList)// calculates points for missing brackets
                .sorted()
                .collect(Collectors.toList());


        return String.valueOf(points.get((points.size() / 2)));
    }

    private static Optional<Character> findIllegalBracket(String line) {
        Stack<Character> bracketStack = new Stack<>();

        for (char bracket : line.toCharArray()) {
            if (bracket == '(' || bracket == '[' || bracket == '{' || bracket == '<') {
                bracketStack.push(bracket);
            } else {
                if (bracketStack.empty()) {
                    return Optional.of(bracket);
                }
                if (bracketStack.peek() == getInvertedBracket(bracket, true)) {
                    bracketStack.pop();
                } else {
                    return Optional.of(bracket);
                }
            }
        }

        return Optional.empty();
    }

    private static List<Character> findMissingBrackets(String line) {
        Stack<Character> bracketStack = new Stack<>();
        List<Character> missingBrackets = new ArrayList<>();

        for (char bracket : line.toCharArray()) {
            if (bracket == '(' || bracket == '[' || bracket == '{' || bracket == '<') {
                bracketStack.push(bracket);
            } else {
                if (bracketStack.peek() == getInvertedBracket(bracket, true)) {
                    bracketStack.pop();
                }
            }
        }

        // Get closing counter part for all non closed brackets
        for (Character bracket : bracketStack) {
            missingBrackets.add(getInvertedBracket(bracket, false));
        }

        Collections.reverse(missingBrackets);
        return missingBrackets;
    }


    private static char getInvertedBracket(char bracket, boolean isClosedBracket) {
        int asciiAdd = 2; // if we add +2 on the char we get the closing bracket

        // Except for rounded ones, for them it's only +1
        if (bracket == '(' || bracket == ')') {
            asciiAdd = 1;
        }
        char invertedBracket;

        if (isClosedBracket) {
            invertedBracket = (char) (bracket - asciiAdd);
        } else {
            invertedBracket = (char) (bracket + asciiAdd);
        }

        return invertedBracket;
    }

    private static long pointsPerList(List<Character> list) {

        long result = 0;
        for (char c : list) {
            result *= 5;
            result += charValue.get(c);
        }
        return result;
    }
}
