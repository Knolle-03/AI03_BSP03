package de.knolleknollsen.mensa;

import java.util.concurrent.locks.ReentrantLock;

public class Checkout extends Thread {

    private final ReentrantLock lock;
    int index;
    private int customers = 0;
    private int total;
    private int queueSize;


    public Checkout(int index, ReentrantLock lock) {
        this.index = index;
        this.lock = lock;
    }


    @Override
    public void run() {
        System.out.println(this.getName() + " is ready.");
        while (!isInterrupted() || queueSize > 0) {

        }
//        Mensa.updateTotalPaymentRegisteredAt(index, total);
//        Mensa.updateAllStudentsRegisteredAt(index, customers);
    }

//    private synchronized void retrievePayment() {
//        while (payment == 0) {
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        assert payment < 20 && payment > 4;
//        total += payment;
//        payment = 0;
//    }

    public void addToTotal(int payment) {
        this.total += payment;
        customers++;
    }

    public int getCustomers() {
        return customers;
    }

    public ReentrantLock getLock() {
        return lock;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void increaseQueueSize() {
        queueSize++;
    }

    public void decreaseQueueSize() {
        queueSize--;
    }
}
