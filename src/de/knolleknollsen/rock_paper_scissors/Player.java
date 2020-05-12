package de.knolleknollsen.rock_paper_scissors;

import java.util.Random;

public class Player extends Thread {

    private final Table table;
    int side;
    Random rng = new Random();

    public Player(Table table, int side) {
        this.table = table;
        this.side = side;
    }


    @Override
    public void run() {
        while (!isInterrupted()) {
            RockPaperScissors pick = pick();
            if (table.setPick(side, pick)) {
                interrupt();
            }
        }

        System.out.println(this.getName() + " stopped playing");
    }



    private RockPaperScissors pick() {
        return RockPaperScissors.values()[rng.nextInt(3)];
    }

}
