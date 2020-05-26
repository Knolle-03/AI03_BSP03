package de.knolleknollsen.rock_paper_scissors;

import java.util.Random;

public class Player extends Thread {

    // table to play on
    private final Table table;
    // left or right side of the table
    int side;
    // random number generator for picks
    Random rng = new Random();

    public Player(Table table, int side) {
        this.table = table;
        this.side = side;
    }


    @Override
    public void run() {
        // while not interrupted set more picks
        while (!isInterrupted()) table.setPick(side, pick());
    }

    // choose a pick
    private RockPaperScissors pick() {
        int pick = rng.nextInt(3);
        // by "rolling the dice"
        return RockPaperScissors.values()[pick];
    }

}
