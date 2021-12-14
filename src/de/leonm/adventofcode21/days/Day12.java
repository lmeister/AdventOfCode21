package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.NumberGrid;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day12 extends Day {

    private final Map<String, Cave> identifierCaveMap;

    public Day12(String path) throws IOException {
        this.identifierCaveMap = new HashMap<>();

        // Initialize cave map
        reader.getStringStreamFromFile(path).forEach(line -> {
            //System.out.println("Line: "+ line);
            String from = line.substring(0, line.indexOf('-'));
            String to = line.substring(line.indexOf('-') + 1);
            //System.out.println("\t" + from + " - " + to);
            identifierCaveMap.putIfAbsent(from, new Cave(from));
            identifierCaveMap.putIfAbsent(to, new Cave(to));

            // Establish connection
            identifierCaveMap.get(from).addNeighbor(identifierCaveMap.get(to));
            identifierCaveMap.get(to).addNeighbor(identifierCaveMap.get(from));
        });
    }


    @Override
    public String partOne() {
        List<List<Cave>> allPaths = new ArrayList<>();

        ArrayList<Cave> beginning = new ArrayList<>();
        beginning.add(identifierCaveMap.get("start"));
        findAllPaths(identifierCaveMap.get("start"), beginning, allPaths, false, false);

//        for (Cave cave : identifierCaveMap.values()) {
//            System.out.println("Cave " + cave.identifier + " has neighbors: " + cave.neighbors);
//        }
        return String.valueOf(allPaths.size());
    }

    @Override
    public String partTwo() {
        List<List<Cave>> allPaths = new ArrayList<>();

        ArrayList<Cave> beginning = new ArrayList<>();
        beginning.add(identifierCaveMap.get("start"));
        findAllPaths(identifierCaveMap.get("start"), beginning, allPaths, true, false);

//        for (Cave cave : identifierCaveMap.values()) {
//            System.out.println("Cave " + cave.identifier + " has neighbors: " + cave.neighbors);
//        }
        return String.valueOf(allPaths.size());
    }

    private static void findAllPaths(Cave currentCave, List<Cave> currentPath, List<List<Cave>> allPaths, boolean twoVisitsAllowed, boolean visitedTwice) {
        // If we're at the end, we have reached a path
        if (currentCave.identifier.equals("end")) {
            allPaths.add(currentPath);
        } else {
            for (Cave neighbor : currentCave.neighbors) {
                boolean twiceVisited = visitedTwice;
                if (neighbor.isSmall && currentPath.contains(neighbor)) {
                    // PART 1: Ignore already visited small caves if not allowed
                    if (!twoVisitsAllowed) {
                        continue;
                    }

                    // PART 2:Ignore twice visited caves and start cave (since we already visited first and it can only be visited once)
                    if (visitedTwice || neighbor.identifier.equals("start")) {
                        continue;
                    } else {
                        twiceVisited = true;
                    }
                }
                List<Cave> newPath = new ArrayList<>(currentPath);
                newPath.add(neighbor);
                findAllPaths(neighbor, newPath, allPaths, twoVisitsAllowed, twiceVisited);
            }
        }
    }


    private static class Cave {
        String identifier;
        boolean isSmall;
        List<Cave> neighbors;

        public Cave(String identifier) {
            this.identifier = identifier;
            isSmall = Character.isLowerCase(identifier.charAt(0));
            this.neighbors = new ArrayList<>();
        }

        public void addNeighbor(Cave cave) {
            this.neighbors.add(cave);
        }
    }
}
