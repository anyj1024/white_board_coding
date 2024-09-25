package src.multiThreads;

import java.util.concurrent.CountDownLatch;

public class ControlOrderByCountDownLatch {
    private static CountDownLatch latchA, latchB, latchC;

    public static void main(String[] args) {
        latchA = new CountDownLatch(1);
        latchB = new CountDownLatch(1);
        latchC = new CountDownLatch(1);

        new Thread(() -> {
            System.out.println("A");
            latchA.countDown();
        }).start();

        new Thread(() -> {
            try {
                latchA.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("B");
            latchB.countDown();
        }).start();

        new Thread(() -> {
            try {
                latchB.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("C");
            latchC.countDown();
        }).start();
    }
}
