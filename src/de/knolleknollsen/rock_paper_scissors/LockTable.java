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

        tableLock.lock();
        try {
            while (table.get(index) != null) {
                try {
                    System.out.println(Thread.currentThread().getName() + " awaits signal.");
                    evaluated.await();
                    System.out.println(Thread.currentThread().getName() + " got a signal.");
                } catch (InterruptedException ex) {
                    System.out.println("Player was interrupted setting the pick");
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            table.put(index, obj);
            System.out.println(Thread.currentThread().getName() + " sent a signal.");
            played.signal();
        } finally{
            tableLock.unlock();
        }
    }

    @Override
    public int[] fetchPicks() {
        int[] picks = new int[2];
        tableLock.lock();
        try {
            while (table.get(0) == null || table.get(1) == null) {
                try {
                    System.out.println(Thread.currentThread().getName() + " awaits signal.");
                    played.await();
                    System.out.println(Thread.currentThread().getName() + " got a signal.");
                } catch (InterruptedException ex) {
                    System.out.println("Referee was interrupted fetching the picks.");
                    Thread.currentThread().interrupt();
                    return null;
                }
            }

            picks[0] = table.get(0).ordinal();
            picks[1] = table.get(1).ordinal();
            System.out.println(table.get(0) + "    " + table.get(1));
            table.put(0, null);
            table.put(1, null);
            System.out.println(Thread.currentThread().getName() + " sent a signal.");
            evaluated.signalAll();
        }finally {
            tableLock.unlock();
        }


        return picks;
    }
}
