package de.knolleknollsen.rock_paper_scissors;


import java.util.Map;

public class Referee extends Thread {

    static int[][] resultMatrix = new int[][] {{0, 1, -1}, {-1, 0, 1}, {1, -1, 0}};
    int[] data = new int[3];
    Map<Integer, RockPaperScissors> actualTable;
    Table tableObj;

    public Referee(Table table) {
        tableObj = table;
        this.actualTable = table.getTable();
    }


    @Override
    public void run() {


        while (!isInterrupted()) {
            int[] picks = tableObj.fetchPicks();
            System.out.println(this.getName() + " got the picks.");
            if (picks != null) calcResults(picks);
            System.out.println("calculation done!");
        }


        System.out.println("Total games played: " + data[0]);
        System.out.println("Total games won by player 1: " + data[1]);
        System.out.println("Total games won by player 2: " + data[2]);
        System.out.println("Total draws: " + (data[0] - (data[1] + data[2])));
        assert data[0] - (data[0] - (data[1] + data[2])) - data[1] - data[2] == 0;
        System.out.println("Referee is done.");
    }


    private void calcResults(int[] picks) {
        data[0]++;
        //System.out.println(Arrays.toString(picks));

        // result for player 0
        int outCome = resultMatrix[picks[0]][picks[1]];

        // player 0 won
        if (outCome == 1) {
            data[2]++;
        // player 1 won
        } else if (outCome == -1) {
            data[1]++;
        }
    }
}
