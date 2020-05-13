package de.knolleknollsen.rock_paper_scissors;


import java.util.HashMap;
import java.util.Map;

public abstract class Table {

    protected Map<Integer, RockPaperScissors> table = new HashMap<>();

//    protected RockPaperScissors left;
//    protected RockPaperScissors right;


    public synchronized void setPick(int index, RockPaperScissors obj) {

    }
    public synchronized int[] fetchPicks() {
        return null;
    }





    public Map<Integer, RockPaperScissors> getTable() {
        return table;
    }



    public void start(int time) {
        Referee referee = new Referee(this);
        referee.setName("referee");
        Player player0 = new Player(this, 0);
        player0.setName("player 0");
        Player player1 = new Player(this, 1);
        player1.setName("player 1");
        referee.start();
        player0.start();
        player1.start();

        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {

        }

        player0.interrupt();
        player1.interrupt();
        referee.interrupt();
//        System.out.println(player0.getName() + ": " + player0.isAlive());
//        System.out.println(player1.getName() + ": " + player1.isAlive());
//        System.out.println(referee.getName() + ": " + referee.isAlive());
        try {
            player0.join();
            player1.join();
            referee.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
