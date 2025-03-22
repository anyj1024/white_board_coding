package src.multiThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ThreadPool {

    private final BlockingQueue<Runnable> taskQueue;
    private final List<Worker> workers;
    private volatile boolean isShutdown = false;

    public ThreadPool(int numThreads) {
        taskQueue = new ArrayBlockingQueue<Runnable>(numThreads);
        workers = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            Worker worker = new Worker("Worker-" + i);
            workers.add(worker);
            worker.start();
        }
    }

    public void execute(Runnable task) {
        if (isShutdown) {
            throw new IllegalStateException("ThreadPool is closed");
        }
        taskQueue.offer(task);
    }

    public void shutdown() {
        isShutdown = true;
        for (Worker worker : workers) {
            worker.interrupt();
        }
    }

    public List<Runnable> shutdownNow() {
        shutdown();
        List<Runnable> notExecuted = new ArrayList<>();
        taskQueue.drainTo(notExecuted);
        return notExecuted;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long endTime = System.currentTimeMillis() + unit.toMillis(timeout);
        for (Worker worker : workers) {
            long timeLeft = endTime - System.currentTimeMillis();
            if (timeLeft > 0) {
                worker.join(timeLeft);
            }
        }

        for (Worker worker : workers) {
            if (worker.isAlive()) {
                return false;
            }
        }
        return true;
    }

    private class Worker extends Thread {

        public Worker(String name) {super(name);}

        @Override
        public void run() {
            try {
                while (!isShutdown || !taskQueue.isEmpty()) {
                    Runnable task = taskQueue.poll(100, TimeUnit.MILLISECONDS);
                    if (task != null) {
                        try {
                            task.run();
                        } catch (RuntimeException e) {
                            System.err.println("Task execution failed: " + e.getMessage());
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
