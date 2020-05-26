package de.knolleknollsen.rock_paper_scissors;


import java.util.Arrays;
import java.util.Map;

public class Referee extends Thread {
    //                  Player 1
    //          |  ROCK |SCISSORS|PAPER|
    // Player 0 |-------|--------|-----|
    // Rock     |   x   |   +    |  -  |
    // ---------|-------|--------|-----|
    // SCISSORS |   -   |   x    |  +  |
    // ---------|-------|--------|-----|
    // PAPER    |   +   |   -    |  x  |


    // result matrix for the picks
    static int[][] resultMatrix = new int[][] {{0, 1, -1}, {-1, 0, 1}, {1, -1, 0}};

    // stores data regrading the whole game
    // data[0] -> all games played
    // data[1] -> games won by player 0
    // data[2] -> games won by player 1
    int[] data = new int[3];

    // picks from the players
    Map<Integer, RockPaperScissors> picks;
    // table played on
    Table tableObj;


    public Referee(Table table) {
        tableObj = table;
        this.picks = table.getTable();
    }


    @Override
    public void run() {
        // while the game is running
        while (!isInterrupted()) {
            // get picks from players
            int[] picks = tableObj.fetchPicks();
            // if referee was not interrupted while waiting for the picks
            // calculate result
            if (picks != null) calcResults(picks);
        }

        // print results
        System.out.println("Total games played: " + data[0]);
        System.out.println("Total games won by player 0: " + data[1]);
        System.out.println("Total games won by player 1: " + data[2]);
        // draws = total - (won by player 0 + won by player 1)
        System.out.println("Total draws: " + (data[0] - (data[1] + data[2])));
    }


    private void calcResults(int[] picks) {
        RockPaperScissors pick0 = RockPaperScissors.values()[picks[0]];
        RockPaperScissors pick1 = RockPaperScissors.values()[picks[1]];


        // increment games played
        data[0]++;
        // result for player 0
        int outCome = resultMatrix[picks[0]][picks[1]];
        // player 0 won
        if (outCome == 1) {
            data[2]++;
            System.out.println("Player 0 won by playing " + pick0.toString() + " against " + pick1.toString());
        }
        // player 1 won
        else if (outCome == -1) {
            data[1]++;
            System.out.println("Player 1 won by playing " + pick1.toString() + " against " + pick0.toString());
        }
       else {
            System.out.println("Both players picked " + pick0 + ".");
        }
    }
}
