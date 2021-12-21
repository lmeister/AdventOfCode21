package de.leonm.adventofcode21.days;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Day21 extends Day {

    private final int startPlayerOne;
    private final int startPlayerTwo;

    public Day21(String path) throws IOException {
        List<String> input = reader.getStringListFromFile(path);
        this.startPlayerOne = Character.getNumericValue(input.get(0).charAt(input.get(0).length() - 1));
        this.startPlayerTwo = Character.getNumericValue(input.get(1).charAt(input.get(1).length() - 1));

        System.out.println("P1 start: " + startPlayerOne + ", P2: " + startPlayerTwo);
    }


    @Override
    public String partOne() {
        int scorePlayerOne = 0;
        int scorePlayerTwo = 0;
        int dieCounter = 0;

        DeterministicDice die = new DeterministicDice(100);

        int playerOnePosition = startPlayerOne - 1;
        int playerTwoPosition = startPlayerTwo - 1;

        while (true) {
            int p1 = die.roll();
            int p2 = die.roll();
            int p3 = die.roll();
            dieCounter += 3;
            int playerOneThrow = p1 + p2 + p3;
            playerOnePosition = (playerOnePosition + playerOneThrow) % 10;
            scorePlayerOne += playerOnePosition + 1;

            //System.out.println("Player 1 rolled: " + p1 + "+" + p2 + "+" + p3 +
            //        " and moves to space " + playerOnePosition + " for a total score of " + scorePlayerOne);

            if (scorePlayerOne >= 1000) break;

            int p4 = die.roll();
            int p5 = die.roll();
            int p6 = die.roll();
            dieCounter += 3;
            int playerTwoThrow = p4 + p5 + p6;
            playerTwoPosition = (playerTwoPosition + playerTwoThrow) % 10;
            scorePlayerTwo += playerTwoPosition + 1;

            //System.out.println("Player 2 rolled: " + p4 + "+" + p5 + "+" + p6 +
            //        " and moves to space " + playerTwoPosition + " for a total score of " + scorePlayerTwo);

            if (scorePlayerTwo >= 1000) break;

        }
        return String.valueOf(dieCounter * Math.min(scorePlayerOne, scorePlayerTwo));
    }

    @Override
    public String partTwo() {
        int scorePlayerOne = 0;
        int scorePlayerTwo = 0;

        int playerOnePosition = startPlayerOne - 1;
        int playerTwoPosition = startPlayerTwo - 1;

        Map<GameState, long[]> memoizedOutcomes = new HashMap<>();

        long[] wins = countWin(scorePlayerOne, playerOnePosition, scorePlayerTwo, playerTwoPosition, memoizedOutcomes);
        return String.valueOf(Math.max(wins[0], wins[1]));
    }

    /**
     * @param scoreCurrentPlayer
     * @param positionCurrentPlayer
     * @param scoreOpponent
     * @param positionOpponent
     * @return first index = player 1 wins, second index = player 2 wins
     */
    private long[] countWin(int scoreCurrentPlayer, int positionCurrentPlayer, int scoreOpponent, int positionOpponent, Map<GameState, long[]> memoized) {
        GameState gameState = new GameState(positionCurrentPlayer, scoreCurrentPlayer, positionOpponent, scoreOpponent);
        if (scoreCurrentPlayer >= 21) {
            return new long[]{1L, 0L};
        }

        if (scoreOpponent >= 21) {
            return new long[]{0L, 1L};
        }

        if (memoized.containsKey(gameState)) {
            return memoized.get(gameState);
        }

        long[] result = new long[]{0L, 0L};
        for (int d1 = 1; d1 <= 3; d1++) {
            for (int d2 = 1; d2 <= 3; d2++) {
                for (int d3 = 1; d3 <= 3; d3++) {
                    // only move one player at a time
                    int newPositionCurrentPlayer = (positionCurrentPlayer + d1 + d2 + d3) % 10;
                    int newScoreCurrentPlayer = scoreCurrentPlayer + newPositionCurrentPlayer + 1;

                    // make move for next player
                    long[] next = countWin(scoreOpponent, positionOpponent, newScoreCurrentPlayer, newPositionCurrentPlayer, memoized);
                    result[0] = result[0] + next[1];
                    result[1] = result[1] + next[0];
                }
            }
        }

        memoized.put(gameState, result);
        return result;
    }

    private class DeterministicDice {
        int maxValue;
        int currentValue = 0;


        public DeterministicDice(int maxValue) {
            this.maxValue = maxValue;
        }

        public int roll() {
            currentValue++;
            if (currentValue > maxValue) {
                currentValue = 1;
            }
            return currentValue;
        }
    }

    private class GameState {
        private int playerOnePos;
        private int playerOneScore;
        private int playerTwoPos;
        private int playerTwoScore;

        public GameState(int playerOnePos, int playerOneScore, int playerTwoPos, int playerTwoScore) {
            this.playerOnePos = playerOnePos;
            this.playerOneScore = playerOneScore;
            this.playerTwoPos = playerTwoPos;
            this.playerTwoScore = playerTwoScore;
        }

        public void incrementPlayerOneScore(int val) {
            this.playerOneScore += val;
        }

        public void incrementPlayerTwoScore(int val) {
            this.playerTwoScore += val;
        }

        public void setPlayerOnePos(int playerOnePos) {
            this.playerOnePos = playerOnePos;
        }

        public void setPlayerTwoPos(int playerTwoPos) {
            this.playerTwoPos = playerTwoPos;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GameState gameState = (GameState) o;
            return playerOnePos == gameState.playerOnePos &&
                    playerOneScore == gameState.playerOneScore &&
                    playerTwoPos == gameState.playerTwoPos &&
                    playerTwoScore == gameState.playerTwoScore;
        }

        @Override
        public int hashCode() {
            return Objects.hash(playerOnePos, playerOneScore, playerTwoPos, playerTwoScore);
        }
    }

}
