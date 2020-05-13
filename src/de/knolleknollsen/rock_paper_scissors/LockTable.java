package de.knolleknollsen.rock_paper_scissors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTable extends Table{

    //private final Lock leftTableLock = new ReentrantLock();




    private final Lock tableLock = new ReentrantLock();
    private final Condition evaluated = tableLock.newCondition();
    private final Condition played = tableLock.newCondition();


    @Override
    public void setPick(int index, RockPaperScissors obj) throws InterruptedException {

        tableLock.lock();
        try {
            while (table[index] != null) {
                evaluated.await();
            }
            table[index] = obj;
            played.signal();
        } finally{
            tableLock.unlock();
        }
    }

    @Override
    public int[] fetchPicks() throws InterruptedException {
        int[] picks;
        tableLock.lock();
        try {
            while (table[0] == null || table[1] == null) {
                played.await();
            }
            picks = new int[]{table[0].ordinal(), table[1].ordinal()};
            table[0] = null;
            table[1] = null;
            evaluated.signal();

        }finally {
            tableLock.unlock();
        }


        return picks;
    }
}
