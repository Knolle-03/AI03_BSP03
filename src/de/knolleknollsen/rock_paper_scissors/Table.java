package de.knolleknollsen.rock_paper_scissors;


public class Table {

    private final RockPaperScissors[] table = new RockPaperScissors[2];



    public synchronized void setPick(int index, RockPaperScissors obj) {
         {
            while (table[index] != null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    
                    System.out.println("Player was interrupted setting the pick");
                }
            }
            table[index] = obj;
        }
        notifyAll();
    }

    public synchronized int[] fetchPicks() {
        int[] picks;
         {
            while (table[0] == null || table[1] == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Referee was interrupted fetching the picks.");
                }
            }
            picks = new int[]{table[0].ordinal(), table[1].ordinal()};
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
        Player player0 = new Player(table, 0);
        Player player1 = new Player(table, 1);
        referee.start();
        player0.start();
        player1.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {

        }

        player0.interrupt();
        player1.interrupt();
        referee.interrupt();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {

        }

        System.out.println(player0.isAlive());
        System.out.println(player1.isAlive());
        System.out.println(referee.isAlive());


        try {
            player0.join();
            player1.join();
            referee.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }
}
