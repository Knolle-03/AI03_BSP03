package de.knolleknollsen.rock_paper_scissors;


import java.util.HashMap;
import java.util.Map;



// abstract super class for MonitorTable and LockTable
public abstract class Table {

    // table to play on
    protected Map<Integer, RockPaperScissors> table = new HashMap<>();

    // methods subclasses have to implement
    public abstract void setPick(int index, RockPaperScissors obj);
    public abstract int[] fetchPicks();

    // getter for table
    public Map<Integer, RockPaperScissors> getTable() {
        return table;
    }


    // start the game
    public void start(int time) {

        // set up referee
        Referee referee = new Referee(this);
        referee.setName("referee");

        // set up player 0
        Player player0 = new Player(this, 0);
        player0.setName("player 0");

        //set up player 1
        Player player1 = new Player(this, 1);
        player1.setName("player 1");

        // start the threads
        referee.start();
        player0.start();
        player1.start();


        // let game run from the given time period
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {}

        // interrupt all other threads
        player0.interrupt();
        player1.interrupt();


        // wait for other threads to die
        try {
            player0.join();
            player1.join();
            referee.interrupt();
            referee.join();
        } catch (InterruptedException ignored) {}
    }
}
