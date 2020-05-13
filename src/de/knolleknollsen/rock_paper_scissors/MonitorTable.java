package de.knolleknollsen.rock_paper_scissors;

public class MonitorTable extends Table {

    @Override
    public synchronized void setPick(int index, RockPaperScissors obj) {

            while (table.get(index) != null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Player was interrupted setting the pick");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            table.put(index, obj);
            notifyAll();
    }

    @Override
    public synchronized int[] fetchPicks() {
        int[] picks;

            while (table.get(0) == null || table.get(1) == null) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("Referee was interrupted fetching the picks.");
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            picks = new int[]{table.get(0).ordinal(), table.get(1).ordinal()};
            table.put(0, null);
            table.put(1, null);
            notifyAll();

        return picks;
    }


}
