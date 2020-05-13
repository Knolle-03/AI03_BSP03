package de.knolleknollsen.rock_paper_scissors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTable extends Table{

    private final Lock tableLock = new ReentrantLock();
    private final Condition evaluated = tableLock.newCondition();
    private final Condition played = tableLock.newCondition();


    @Override
    public void setPick(int index, RockPaperScissors obj) {
        // try to acquire lock
        tableLock.lock();
        try {
            // wait fot other player and/or referee
            while (table.get(index) != null) {
                try {
                    // wait for other player and/or referee
                    evaluated.await();
                // if player gets interrupted while waiting
                } catch (InterruptedException ex) {
                    // reset interrupt flag since it was cleared while waiting
                    Thread.currentThread().interrupt();
                    // return from method
                    return;
                }
            }

            // set own pick
            table.put(index, obj);

            // give signal to referee that we set a pick
            played.signal();
        } finally{
            // release lock
            tableLock.unlock();
        }
    }

    @Override
    public int[] fetchPicks() {

        // int array for the picks
        int[] picks;

        // try to acquire lock
        tableLock.lock();
        try {

            // until both players have played
            while (table.get(0) == null || table.get(1) == null) {
                try {
                    // wait for one or both players
                    played.await();
                } catch (InterruptedException ex) {
                    // reset interrupt flag since it was cleared while waiting
                    Thread.currentThread().interrupt();

                    // return from method
                    return null;
                }
            }

            // get picks from players
            picks = new int[] {table.get(0).ordinal(), table.get(1).ordinal()};

            // reset table
            table.put(0, null);
            table.put(1, null);

            // give signal to both players
            evaluated.signalAll();
        }finally {
            // release lock
            tableLock.unlock();
        }

        // return picks
        return picks;
    }
}
