package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.*;

/**
 * TODO: Refactor this to use a map instead of an array to save some time
 */
public class Day06 extends Day {


    private final int[] input;

    public Day06(String path) throws IOException {
        input = reader.getIntArrayFromCSV(path);
    }


    // Initial version with OOP, much slower.
    /*
    @Override
    long partOne() {
        List<LanternFish> fish = new LinkedList<>();
        for (int initialVale : input) {
            fish.add(new LanternFish(initialVale));
        }

        for (int day = 1; day <= 80; day++) {
            List<LanternFish> newFish = new ArrayList<>(fish.size());
            for (LanternFish lanternFish : fish) {
                Optional<LanternFish> child = lanternFish.spawnChild();

                if (child.isPresent()) {
                    newFish.add(child.get());
                } else {
                    lanternFish.decrementTimer();
                }
            }
            fish.addAll(newFish);
        }

        return fish.size();
    }
    */

    @Override
    long partOne() {
        return getTotalFishAfterDays(80, input, 6, 8);
    }

    @Override
    long partTwo() {
        return getTotalFishAfterDays(256, input, 6, 8);
    }

    private long getTotalFishAfterDays(int days, int[] start, int postMatingTimer, int initialTimer) {
        Map<Integer, Long> ageAmountMap = new HashMap<>();
        // initialize map with zeros
        for (int i = 0; i <= initialTimer; i++) {
            ageAmountMap.put(i, 0L);
        }

        for (int fishAge : start) {
            ageAmountMap.put(fishAge, ageAmountMap.get(fishAge) + 1);
        }

        for (int day = 1; day <= days; day++) {
            // get amount of children spawned
            long childrenSpawned = ageAmountMap.get(0);

            // Decrement all timers by 1
            for (int age = 1; age <= initialTimer; age++) {
                ageAmountMap.put(age - 1, ageAmountMap.get(age));
            }
            ageAmountMap.put(postMatingTimer, ageAmountMap.get(postMatingTimer) + childrenSpawned);
            ageAmountMap.put(initialTimer, childrenSpawned);
        }

        long totalFish = 0;
        for (long fishInGeneration : ageAmountMap.values()) {
            totalFish += fishInGeneration;
        }
        return totalFish;
    }

    /**
     * Class representing LanternFish.
     * Had better readability but much worse performance for part 1 and not solvable for part 2.
     * List grows too long and objects take up too much space.
     */
    private class LanternFish {
        private static final int TIMER_FOR_YOUNGLINGS = 8;
        private int internalTimer;
        private static final int DECREMENT = 1;

        public LanternFish(int initialTimer) {
            this.internalTimer = initialTimer;
        }

        public void decrementTimer() {
            this.internalTimer -= DECREMENT;
        }

        public int getInternalTimer() {
            return this.internalTimer;
        }

        public Optional<LanternFish> spawnChild() {
            Optional<LanternFish> child = Optional.empty();
            
            if (getInternalTimer() == 0) {
                this.internalTimer = 6;
                child = Optional.of(new LanternFish((TIMER_FOR_YOUNGLINGS)));
            }

            return child;
        }
    }
}
