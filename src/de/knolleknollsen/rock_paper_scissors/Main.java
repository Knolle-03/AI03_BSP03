package de.knolleknollsen.rock_paper_scissors;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println("----------- Lock Table -----------");
            Table lockTable = new LockTable();
            lockTable.start( 100);
            System.out.println("---------- Monitor Table ---------");
            Table syncTable = new MonitorTable();
            syncTable.start(100);
        }
    }
}
