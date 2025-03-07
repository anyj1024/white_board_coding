package src.multiThreads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankTransfer {
    public static void main(String[] args) {
        BankAccount account1 = new BankAccount(1, 1000);
        BankAccount account2 = new BankAccount(2, 1000);

        Thread t1 = new Thread(() -> account1.transferTo(account2, 200));
        Thread t2 = new Thread(() -> account2.transferTo(account1, 300));
        t1.start();
        t2.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account1.getBalance());
        System.out.println(account2.getBalance());
    }

    static class BankAccount {
        private final int id;
        private int balance;

        public BankAccount(int id, int balance) {
            this.id = id;
            this.balance = balance;
        }

        public void transferTo(BankAccount destination, int amount) {
            BankAccount min = this.id < destination.id ? this : destination;
            BankAccount max = this.id > destination.id ? this : destination;

            synchronized (min) {
                synchronized (max) {
                    if (this.balance >= amount) {
                        this.balance -= amount;
                        destination.balance += amount;
                        System.out.println("Transferred " + amount + " from " + min + " to " + max);
                    } else {
                        System.out.println("Not enough money");
                    }
                }
            }
        }

        public int getBalance() {
            return this.balance;
        }
    }
}

