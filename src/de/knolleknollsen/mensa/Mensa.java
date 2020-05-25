package de.knolleknollsen.mensa;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Mensa {
    private static final int checkoutCount = 3;
    private static final int studentCount = 10;
    private static final Checkout[] checkouts = new Checkout[checkoutCount];
//    private static final Student[] students = new Student[studentCount];
    private static final Thread[] allThreads = new Thread[checkoutCount + studentCount];


    //the pairs should be equal
//    private static int totalPayed = 0;
//    private static int totalReceived = 0;
//
//    private static int totalStudents;
//    private static int totalStudentsRegistered;
//
//    private static int[] allStudentsAt = new int[checkoutCount];
//    private static int[] allStudentsRegisteredAt = new int[checkoutCount];
//
//
//    private static int[] totalPaymentsAt = new int[checkoutCount];
//    private static int[] totalPaymentsRegisteredAt = new int[checkoutCount];


    public static void main(String[] args) {

        // instantiate threads
        for (int i = 0; i < checkoutCount ; i++) {
            ReentrantLock reentrantLock = new ReentrantLock(true);
            Checkout checkout = new Checkout(i , reentrantLock);
            checkout.setName("checkout " + (i + 1));
            checkouts[i] = checkout;
            allThreads[i] = checkout;
            checkout.start();

        }
        ReentrantLock pickCheckoutLock = new ReentrantLock();
        for (int i = 0; i < studentCount ; i++) {
            Student student = new Student(pickCheckoutLock, checkouts);
            student.setName("student " + (i + 1));
//            students[i] = student;
            allThreads[checkoutCount + i] = student;
            student.start();

        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {

        }
        System.out.println("closing....");
        close();

        for (Thread thread : allThreads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
        for (Checkout checkout : checkouts) {
            System.out.println(checkout.getCustomers());
        }


//        for (int i = 0; i < checkoutCount; i++) {
//            if (allStudentsAt[i] != allStudentsRegisteredAt[i]) throw new IllegalStateException("\nStudents should: " + allStudentsRegisteredAt[i] + "\nwas: " + allStudentsAt[i]);
//            else if (totalPaymentsAt[i] != totalPaymentsRegisteredAt[i]) throw new IllegalStateException("\nPayments should: " + totalPaymentsRegisteredAt[i] + "\nwas: " + totalPaymentsAt[i]);
//        }


//        System.out.println("\n\n\n\n\nStudentCount from Students:  " + Arrays.toString(allStudentsAt));
//        System.out.println("StudentCount from Checkouts: " + Arrays.toString(allStudentsRegisteredAt) + "\n\n\n");
//
//
//        System.out.println("Total payments from Students:  " + Arrays.toString(totalPaymentsAt));
//        System.out.println("Total payments from Checkouts: " + Arrays.toString(totalPaymentsRegisteredAt));
//




    }


//    // updated from students
//    public synchronized static void updateAllStudentsAt(int at, int times) {
//        allStudentsAt[at] += times;
//    }
//    public synchronized static void updateTotalPaymentAt(int at, int payment) {
//        totalPaymentsAt[at] += payment;
//    }
//
//   //updated from checkouts
//    public synchronized static void updateAllStudentsRegisteredAt(int at, int all) {
//        allStudentsRegisteredAt[at] = all;
//    }
//    public synchronized static void updateTotalPaymentRegisteredAt(int at, int totalPayed) {
//        totalPaymentsRegisteredAt[at] = totalPayed;
//    }



    public static void close() {
        for (Thread allThread : allThreads) {
            allThread.interrupt();
        }

    }




}
