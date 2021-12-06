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
    public long partOne() {
        return getTotalFishAfterDays(80, input, 6, 8);
    }

    @Override
    public long partTwo() {
        return getTotalFishAfterDays(256, input, 6, 8);
    }

    private long getTotalFishAfterDays(int days, int[] start, final int postMatingTimer, final int initialTimer) {
        // Contains the amount of fish (value) that share an internal timer (key)
        Map<Integer, Long> ageAmountFrequencyMap = new HashMap<>();
        // add existing fish to frequency map
        Arrays.stream(start).forEach(internalTimer -> ageAmountFrequencyMap.merge(internalTimer, 1L, Long::sum));


        for (int day = 1; day <= days; day++) {
            // get amount of fish that spawn children
            long fishThatSpawnChild = ageAmountFrequencyMap.getOrDefault(0, 0L);
            // Decrement all timers by 1 and move it along in map
            for (int age = 1; age <= initialTimer; age++) {
                ageAmountFrequencyMap.put(age - 1, ageAmountFrequencyMap.getOrDefault(age, 0L));
            }
            // Put Children into map and parents back into map
            ageAmountFrequencyMap.put(postMatingTimer, ageAmountFrequencyMap.get(postMatingTimer) + fishThatSpawnChild);
            ageAmountFrequencyMap.put(initialTimer, fishThatSpawnChild);
        }

        long totalFish = 0;
        for (long fishInGeneration : ageAmountFrequencyMap.values()) {
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
        private static final short TIMER_FOR_YOUNGLINGS = 8;
        private short internalTimer;
        private static final short DECREMENT = 1;

        public LanternFish(short initialTimer) {
            this.internalTimer = initialTimer;
        }

        public void decrementTimer() {
            this.internalTimer -= DECREMENT;
        }

        public short getInternalTimer() {
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
