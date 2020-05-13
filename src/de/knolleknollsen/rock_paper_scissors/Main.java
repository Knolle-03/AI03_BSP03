package de.knolleknollsen.rock_paper_scissors;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println("------------ new Game ----------");
            Table lockTable = new LockTable();
            lockTable.start( 10000);
            Table syncTable = new MonitorTable();
            syncTable.start(10000);
        }
    }
}
