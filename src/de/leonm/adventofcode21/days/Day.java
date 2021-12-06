package de.leonm.adventofcode21.days;

import de.leonm.adventofcode21.utils.Reader;

public abstract class Day {

    protected Reader reader = new Reader();

    /**
     * Solves part one of the puzzle
     * @return solution as Integers
     */
    abstract long partOne();
    /**
     * Solves part two of the puzzle
     * @return solution as Integers
     */
    abstract long partTwo();

    public void printSolutions() {
        long prePartOne = System.nanoTime();
        long solution = partOne();
        long prePartTwo = System.nanoTime();
        System.out.printf("Part one solution: %1$d \n\tTime required: %2$.2f ms.\n",
            solution, (prePartTwo - prePartOne)/1000000.0);
        solution = partTwo();
        long postPartTwo = System.nanoTime();
        System.out.printf("Part two solution: %1$d \n\tTime required: %2$.2f ms.\n",
            solution, (postPartTwo - prePartTwo)/1000000.0);
    }
}
