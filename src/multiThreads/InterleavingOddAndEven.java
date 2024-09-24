package src.multiThreads;

public class InterleavingOddAndEven {
    private static final int MAX = 100;
    private static int curr = 1;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread oddThread = new Thread(() -> {
           while (curr <= MAX) {
               synchronized (lock) {
                   if (curr % 2 != 0) {
                       System.out.println("ODD: " + curr++);
                       lock.notifyAll();
                   } else {
                       try {
                           lock.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }
           }
        });

        Thread evenThread = new Thread(() -> {
           while (curr <= MAX) {
               synchronized (lock) {
                   if (curr % 2 == 0) {
                       System.out.println("EVEN: " + curr++);
                       lock.notifyAll();
                   } else {
                       try {
                           lock.wait();
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                   }
               }
           }
        });

        oddThread.start();
        evenThread.start();
    }
}
