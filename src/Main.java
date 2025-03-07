package src;


public class Main {
    private static final int PRINT_COUNT = 10000;
    private static int curr = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread pA = new Thread(new PrintThread('A')), pB = new Thread(new PrintThread('B')), pC = new Thread(new PrintThread('C'));
        pA.start();
        pB.start();
        pC.start();
    }

    static class PrintThread implements Runnable {
        private final char letter;

        public PrintThread(char letter) {
            this.letter = letter;
        }

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
