package de.knolleknollsen.rock_paper_scissors;


// subclass of Table implements abstract methods from superclass with monitors
public class MonitorTable extends Table {



    @Override
    public synchronized void setPick(int index, RockPaperScissors obj) {
        // while player already set a pick
        while (table.get(index) != null) {
            try {
                // wait for other player and/or referee
                wait();
            // if player gets interrupted while waiting
            } catch (InterruptedException e) {
                //System.out.println("Player was interrupted setting the pick");


                // reset interrupt flag since it was cleared while waiting
                Thread.currentThread().interrupt();
                // return from method
                return;
            }
        }
        // set own pick
        table.put(index, obj);
        // notify other threads that we are done.
        notifyAll();
    }

    @Override
    public synchronized int[] fetchPicks() {
        // int array for the picks
        int[] picks;

        // until both players set their picks
        while (table.get(0) == null || table.get(1) == null) {
            try {
                // wait for one or both players
                wait();
            // if player gets interrupted while waiting
            } catch (InterruptedException e) {
                //System.out.println("Referee was interrupted fetching the picks.");


                // reset interrupt flag since it was cleared while waiting
                Thread.currentThread().interrupt();

                // return from method
                return null;
            }
        }

        // get picks from the players
        picks = new int[]{table.get(0).ordinal(), table.get(1).ordinal()};

        // reset table
        table.put(0, null);
        table.put(1, null);

        // notify the players
        notifyAll();

        // return picks
        return picks;
    }


}
