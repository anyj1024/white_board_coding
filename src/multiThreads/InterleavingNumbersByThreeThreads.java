package src.multiThreads;

public class InterleavingNumbersByThreeThreads {
    private static final int MAX = 99;
    private static int curr = 1;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        PrintThread t1 = new PrintThread("线程1", 1);
        PrintThread t2 = new PrintThread("线程2", 2);
        PrintThread t3 = new PrintThread("线程3", 0);

        t1.start();
        t2.start();
        t3.start();
    }

    private static class PrintThread extends Thread {
        String name;
        int tag;

        public PrintThread(String name, int tag) {
            this.name = name;
            this.tag = tag;
        }

        @Override
        public void run() {
            while (curr <= MAX) {
                synchronized (lock) {
                    while (curr % 3 != tag) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (curr <= MAX) {
                        System.out.println(name + "： " + curr++);
                        lock.notifyAll();
                    }
                }
            }
        }
    }
}
