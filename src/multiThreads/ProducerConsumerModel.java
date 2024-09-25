package src.multiThreads;

import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerModel {
    private static final int QUEUE_SIZE = 10;
    private static final int NUM_PRODUCERS = 3;
    private static final int NUM_CONSUMERS = 2;
    private static final int NUM_TASKS_PER_PRODUCER = 5;

    private static BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(QUEUE_SIZE);
    private static AtomicInteger tasksProduced = new AtomicInteger(0);
    private static AtomicInteger tasksConsumed = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread[] producers = new Thread[NUM_PRODUCERS];
        Thread[] consumers = new Thread[NUM_CONSUMERS];

        for (int i = 0; i < NUM_PRODUCERS; i++) {
            producers[i] = new Thread(new Producer(i));
            producers[i].start();
        }

        for (int i = 0; i < NUM_CONSUMERS; i++) {
            consumers[i] = new Thread(new Consumer(i));
            consumers[i].start();
        }

        try {
            for (Thread producer : producers) {
                producer.join();
            }
            for (Thread consumer : consumers) {
                consumer.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks produced and consumed.");
    }

    static class Producer implements Runnable {
        private final int id;

        public Producer(int id) {
            this.id = id;
        }

        public void run() {
            try {
                for (int i = 1; i <= NUM_TASKS_PER_PRODUCER; i++) {
                    int taskId = tasksProduced.incrementAndGet();
                    queue.put(taskId);
                    System.out.println("Producer " + id + " produced: " + taskId);
                    Thread.sleep((int) (Math.random() * 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer implements Runnable {
        private final int id;

        public Consumer(int id) {
            this.id = id;
        }

        public void run() {
            try {
                while (tasksConsumed.get() < NUM_PRODUCERS * NUM_TASKS_PER_PRODUCER) {
                    int taskId = queue.take();
                    tasksConsumed.incrementAndGet();
                    System.out.println("Consumer " + id + " consumed: " + taskId);
                    Thread.sleep((int) (Math.random() * 100));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
