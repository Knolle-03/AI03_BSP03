package de.knolleknollsen.rock_paper_scissors;


public abstract class Table {

    protected RockPaperScissors[] table = new RockPaperScissors[2];

//    protected RockPaperScissors left;
//    protected RockPaperScissors right;


    public synchronized void setPick(int index, RockPaperScissors obj) throws InterruptedException {

    }
    public synchronized int[] fetchPicks() throws InterruptedException {
        return null;
    }





    public RockPaperScissors[] getTable() {
        return table;
    }



    public void start(Table table, int time) {
        Referee referee = new Referee(table);
        referee.setName("referee");
        Player player0 = new Player(table, 0);
        player0.setName("player 0");
        Player player1 = new Player(table, 1);
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
