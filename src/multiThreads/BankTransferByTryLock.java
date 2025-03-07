package src.multiThreads;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankTransferByTryLock {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount(1, 1000);
        BankAccount account2 = new BankAccount(2, 1000);

        Thread t1 = new Thread(() -> account1.transferTo(account2, 200));
        Thread t2 = new Thread(() -> account2.transferTo(account1, 300));
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
    }

    static class BankAccount {
        private final int id;
        private int balance;
        private final Lock lock = new ReentrantLock();

        public BankAccount(int id, int balance) {
            this.id = id;
            this.balance = balance;
        }

        public void transferTo(BankAccount destination, int amount) {
            while (true) {
                try {
                    if (this.lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                        try {
                            if (destination.lock.tryLock(500, TimeUnit.MILLISECONDS)) {
                                try {
                                    if (this.balance >= amount) {
                                        this.balance -= amount;
                                        destination.balance += amount;
                                        System.out.println(id + " transferred " + amount + " to " + destination);
                                    } else {
                                        System.out.println("No Enough Money.");
                                    }
                                    return;
                                } finally {
                                    destination.lock.unlock();
                                }
                            }
                        } finally {
                            this.lock.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Transfer interrupted.");
                }
            }
        }

        public int getBalance() {
            return this.balance;
        }
    }
}

