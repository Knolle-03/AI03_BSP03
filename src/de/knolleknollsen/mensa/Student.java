package de.knolleknollsen.mensa;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Student extends Thread {

    ReentrantLock checkoutLock;
    ReentrantLock pickCheckoutLock;
    Checkout[] checkouts;
    Checkout currentCheckout;
    Random rng = new Random();

    int totalPayed = 0;


    int[] payedAt;
    int[] timesPayedAt;



    public Student(ReentrantLock pickCheckoutLock, Checkout ... checkouts) {
        this.pickCheckoutLock = pickCheckoutLock;
        this.checkouts = checkouts;
        payedAt = new int[checkouts.length];
        timesPayedAt = new int[checkouts.length];
    }


    public void run() {
        // if the student is not interrupted
        while (!this.isInterrupted()) {
            // calculate cost od the meal
            int payment = rng.nextInt(20 - 5) + 5;
            try {
                // pick shortest checkout
                pickCheckout();
                // lock it or go dormant if locked
                checkoutLock.lock();
                // pay
                pay(payment);
            } finally {
                // unlock every time
                checkoutLock.unlock();

            }

            totalPayed += payment;
            increaseTimesPayedAt(currentCheckout);
            increaseAmountPayedAt(currentCheckout, payment);
            currentCheckout.decreaseQueueSize();


            // eat and study
            eat();
            if (!isInterrupted()) study();
        }

        // before exiting run() update mensa
        for (int i = 0; i < checkouts.length; i++) {
            Mensa.updateAllStudentsAt(i, timesPayedAt[i]);
            Mensa.updateTotalPaymentAt(i, payedAt[i]);
        }
    }


    private void pay(int payment) {
        try {
            System.out.println(this.getName() + " is paying at " + currentCheckout.getName() + ".");
            currentCheckout.addToTotal(payment);
            Thread.sleep(rng.nextInt(100));
        } catch (InterruptedException ex) {
            interrupt();
            System.out.println(this.getName() + " was interrupted while paying.");
        }



    }


    private void pickCheckout() {
        int index = 0;
        int length = checkouts[index].getQueueSize();
        try {
            pickCheckoutLock.lock();
            for (int i = 1; i < checkouts.length; i++) {
                if (checkouts[i].getQueueSize() < length) index = i;
            }

        } finally {
            pickCheckoutLock.unlock();
        }
        currentCheckout = checkouts[index];
        System.out.println(this.getName() + " queues at " + currentCheckout.getName());
        currentCheckout.increaseQueueSize();

        checkoutLock = checkouts[index].getLock();

    }


    private void eat() {
        boolean alreadyInterrupted = isInterrupted();
        try {
            if (alreadyInterrupted) {
                System.out.println(this.getName() + " is eating fast.");
            } else {
                System.out.println(this.getName() + " is eating.");
            }
            Thread.sleep(rng.nextInt(100));
        } catch (InterruptedException ex) {
            interrupt();
        }

        if (!alreadyInterrupted && isInterrupted()) {
            System.out.println(this.getName() + " was interrupted while eating.");
        }
    }

    private void study() {

        try {
            System.out.println(this.getName() + " is studying.");
            Thread.sleep(rng.nextInt(100));
        } catch (InterruptedException ex) {
            interrupt();
        }

        if (isInterrupted()) {
            System.out.println(this.getName() + " was interrupted while studying.");
        }
    }



















    // debugging methods

    private void increaseTimesPayedAt(Checkout checkout) {
        timesPayedAt[findIndexInCheckouts(checkout)]++;
    }

    private void increaseAmountPayedAt(Checkout checkout, int payment) {
        payedAt[findIndexInCheckouts(checkout)] += payment;
    }

    private int findIndexInCheckouts(Checkout checkout) {
        for (int i = 0; i < checkouts.length; i++) {
            if (checkouts[i].equals(checkout)) return i;
        }

        System.out.println("Should never happen!!!!!!!!!!!!!!!!");
        return -1;
    }

}
