package src.multiThreads;

public class DeadLock {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("线程1已经持有资源1了。");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程1等待资源2。。。");
                synchronized (lock2) {
                    System.out.println("线程1已经持有资源2了。");
                }
                System.out.println("线程1已经获取所有资源，可以工作了。");
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("线程2已经持有资源2了。");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程2等待资源1。。。");
                synchronized (lock1) {
                    System.out.println("线程2已经持有资源1了。");
                }
                System.out.println("线程2已经获取所有资源，可以工作了。");
            }
        });

        t1.start();
        t2.start();
    }
}
