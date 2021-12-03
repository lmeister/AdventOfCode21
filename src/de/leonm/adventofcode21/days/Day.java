package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.Reader;

public abstract class Day {

    protected Reader reader = new Reader();

    /**
     * Solves part one of the puzzle
     * @return solution as Integers
     */
    abstract int partOne();
    /**
     * Solves part two of the puzzle
     * @return solution as Integers
     */
    abstract int partTwo();

    public void printSolutions() {
        long prePartOne = System.currentTimeMillis();
        int solution = partOne();
        long prePartTwo = System.currentTimeMillis();
        System.out.printf("Part one solution: %1$d \n\tTime required: %2$d ms.\n",
            solution, (prePartTwo - prePartOne));
        solution = partTwo();
        long postPartTwo = System.currentTimeMillis();
        System.out.printf("Part two solution: %1$d \n\tTime required: %2$d ms.\n",
            solution, (postPartTwo - prePartTwo));
    }
}
