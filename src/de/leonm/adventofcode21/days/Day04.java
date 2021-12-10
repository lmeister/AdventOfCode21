package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04 extends Day {

    int[] drawnNumbers;
    List<BingoBoard> bingoBoards;

    public Day04(String path) throws IOException {
        List<String> input = reader.getStringListFromFile(path);
        this.drawnNumbers = Arrays.stream(input.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        this.bingoBoards = initializeBoards(input.subList(2, input.size()));
    }

    @Override
    public String partOne() {
        // For every drawn number, iterate through all boards and mark the number
        for (int number : drawnNumbers) {
            for (BingoBoard board : bingoBoards) {
                board.markDrawnNumber(number);
                // If current board has won, return solution
                if (board.hasWon()) {
                    return String.valueOf(number * board.sumOfAllUnmarkedNumbers());
                }
            }
        }
        return "-1";
    }

    @Override
    public String partTwo() {
        List<BingoBoard> remainingBoards = new ArrayList<>(bingoBoards); // Used to keep track of remaining boards
        List<BingoBoard> tossedBoards = new ArrayList<>(); // Used to keep track of winning boards, that will be removed

        for (int number : drawnNumbers) {
            for (BingoBoard board : remainingBoards) {
                board.markDrawnNumber(number);
                // Return the last board once it has won
                if (remainingBoards.size() == 1 && remainingBoards.get(0).hasWon()) {
                        return String.valueOf(number * remainingBoards.get(0).sumOfAllUnmarkedNumbers());
                }

                if (board.hasWon()) {
                    tossedBoards.add(board);
                }
            }
            // Toss boards that haven't won, if there are more than 1 boards left
            remainingBoards.removeAll(tossedBoards);
            tossedBoards.clear();
        }
        return "-1";
    }

    /**
     * Reads input list to create a list of bingo boards.
     * @param input The list containing the bingo boards separated by empty lines
     * @return List of bingo board instances
     */
    private List<BingoBoard> initializeBoards(List<String> input) {
        List<BingoBoard> boards = new ArrayList<>();
        // Skip the empty line and jump over the board
        for (int i = 0; i < input.size(); i += BingoBoard.SIZE + 1) {
            boards.add(new BingoBoard(input.subList(i, i + BingoBoard.SIZE)));
        }

        return boards;
    }

    /**
     * Represents a Bingo Board Object.
     * Allows for Creation of boards, marking of numbers as well as evaluating their status.
     */
    static class BingoBoard {

        private final static int SIZE = 5;
        private final BoardNumber[][] board;

        public BingoBoard(List<String> numbers) {
            board = new BoardNumber[SIZE][SIZE];

            for (int i = 0; i < SIZE; i++) {
                Pattern linePattern = Pattern.compile("(\\d)+");
                Matcher matcher = linePattern.matcher(numbers.get(i));
                int[] numbersInLine = matcher.results().map(MatchResult::group).mapToInt(Integer::parseInt).toArray();

                for (int j = 0; j < SIZE; j++) {
                    board[i][j] = new BoardNumber(numbersInLine[j]);
                }
            }
        }

        public boolean hasWon() {
            boolean won = false;
            // check if any row or column is fully marked
            int counterVertical = 0;
            int counterHorizontal = 0;
            for (int i = 0; i < SIZE && !won; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j].isDrawn()) {
                        counterVertical++;
                    }
                    if (board[j][i].isDrawn()) {
                        counterHorizontal++;
                    }
                }

                if (counterVertical == SIZE|| counterHorizontal == SIZE) {
                    won = true;
                }
                counterVertical = 0;
                counterHorizontal = 0;
            }

            return won;
        }

        public void markDrawnNumber(int number) {
            // iterate through board until we find number
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (board[i][j].getNumber() == number) {
                        board[i][j].markDrawnNumber();
                    }
                }
            }
        }

        public long sumOfAllUnmarkedNumbers() {
            long sum = 0;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (!board[i][j].isDrawn()) {
                        sum += board[i][j].getNumber();
                    }
                }
            }
            return sum;
        }


        /**
         * Resembles a BoardNumber/Tile. Combines information of number and whether it has been drawn or not.
         */
        static class BoardNumber {
            private final int number;
            private boolean drawn;


            public BoardNumber(int number) {
                this.number = number;
            }

            public void markDrawnNumber() {
                this.drawn = true;
            }

            public int getNumber() {
                return number;
            }

            public boolean isDrawn() {
                return drawn;
            }
        }
    }
}
