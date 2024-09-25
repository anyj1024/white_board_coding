package src.multiThreads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ControlOrderByReentrantLock {
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition conA  = lock.newCondition(), conB  = lock.newCondition(), conC  = lock.newCondition();
    private static int state = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                while (state != 0) {
                    conA.await();
                }
                System.out.println("A");
                state = 1;
                conB.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                while (state != 1) {
                    conB.await();
                }
                System.out.println("B");
                state = 2;
                conC.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                while (state != 2) {
                    conC.await();
                }
                System.out.println("C");
                state = 1;
                conA.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
