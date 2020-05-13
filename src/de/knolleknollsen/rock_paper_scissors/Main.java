package de.knolleknollsen.rock_paper_scissors;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 100_000; i++) {
//            Table lockTable = new LockTable();
//            lockTable.start(lockTable, 5000);
            Table syncTable = new SyncTable();
            syncTable.start(syncTable, 1000);
        }
    }

}
