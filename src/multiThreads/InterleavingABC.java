package src.multiThreads;

public class InterleavingABC {
    private static final int PRINT_COUNT = 5;
    private static int curr = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        PrintThread AThread = new PrintThread('A');
        PrintThread BThread = new PrintThread('B');
        PrintThread CThread = new PrintThread('C');

        AThread.start();
        BThread.start();
        CThread.start();
    }

    private static class PrintThread extends Thread {
        private final char letter;

        public PrintThread(char letter) {
            this.letter = letter;
        }

        @Override
        public void run() {
            for (int i = 0; i < PRINT_COUNT; i++) {
                synchronized (lock) {
                    while (curr % 3 != (letter - 'A')) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(letter);
                    curr++;
                    lock.notifyAll();
                }
            }
        }
    }
}
