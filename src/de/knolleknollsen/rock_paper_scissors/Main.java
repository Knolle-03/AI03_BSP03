package de.knolleknollsen.rock_paper_scissors;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
//            System.out.println("----------- Lock Table -----------");
//            Table lockTable = new LockTable();
//            lockTable.start( 10000);
            System.out.println("---------- Monitor Table ---------");
            Table monitorTable = new MonitorTable();
            monitorTable.start(100);
        }
    }
}
