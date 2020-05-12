package de.knolleknollsen.rock_paper_scissors;


public class Table {

    private final RockPaperScissors[] table = new RockPaperScissors[2];



    public synchronized boolean setPick(int index, RockPaperScissors obj) {
        {
            while (table[index] != null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Player was interrupted setting the pick");
                    return true;
                }
            }
            table[index] = obj;
        }
        notifyAll();
        return false;
    }

    public synchronized int[] fetchPicks() {
        int[] picks;
         {
            while (table[0] == null || table[1] == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Referee was interrupted fetching the picks.");
                    return null;
                }
            }
            picks = new int[]{table[0].ordinal(), table[1].ordinal()};
            table[0] = null;
            table[1] = null;
        }
        notifyAll();

        return picks;
    }

    public RockPaperScissors[] getTable() {
        return table;
    }



    public static void main(String[] args) {
        Table table = new Table();
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
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }

        player0.interrupt();
        player1.interrupt();
        referee.interrupt();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {

        }

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
