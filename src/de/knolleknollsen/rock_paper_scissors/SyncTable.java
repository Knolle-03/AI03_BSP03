package de.knolleknollsen.rock_paper_scissors;

public class SyncTable extends Table {

    @Override
    public synchronized void setPick(int index, RockPaperScissors obj) {

            while (table[index] != null) {
                try {
                    wait();
                } catch (InterruptedException e) {
//                    System.out.println("Player was interrupted setting the pick");
//                    Thread.currentThread().interrupt();
//                    return;
                }
            }
            table[index] = obj;
            notifyAll();
    }

    @Override
    public synchronized int[] fetchPicks() {
        int[] picks;

            while (table[0] == null || table[1] == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
//                    System.out.println("Referee was interrupted fetching the picks.");
//                    Thread.currentThread().interrupt();
//                    return null;
                }
            }
            picks = new int[]{table[0].ordinal(), table[1].ordinal()};
            table[0] = null;
            table[1] = null;
            notifyAll();

        return picks;
    }


}
