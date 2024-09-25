package src.multiThreads;

import java.util.concurrent.Semaphore;

public class ControlOrderBySemaphore {
    private static Semaphore semA, semB, semC;
    private static int cur = 1, MAX = 6;

    public static void main(String[] args) {
        semA = new Semaphore(1);
        semB = new Semaphore(0);
        semC = new Semaphore(0);

        new Thread(() -> {
            while (cur <= MAX) {
                try {
                    semA.acquire();
                    if (cur <= MAX) {
                        System.out.println("A");
                        cur++;
                    }
                    semB.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (cur <= MAX) {
                try {
                    semB.acquire();
                    if (cur <= MAX) {
                        System.out.println("B");
                        cur++;
                    }
                    semC.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (cur <= MAX) {
                try {
                    semC.acquire();
                    if (cur <= MAX) {
                        System.out.println("C");
                        cur++;
                    }
                    semA.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
