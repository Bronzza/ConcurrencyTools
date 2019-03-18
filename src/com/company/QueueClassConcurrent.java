package com.company;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueClassConcurrent implements Runnable {

    private Queue<Integer> normalQueue;
    private ConcurrentLinkedQueue<Integer> concurrentSafeQueue;
    private int counter;

    public QueueClassConcurrent(Queue<Integer> normalQueue, ConcurrentLinkedQueue<Integer> concurrentSafeQueue,
                                int counter) {
        this.normalQueue = normalQueue;
        this.concurrentSafeQueue = concurrentSafeQueue;
        this.counter = counter;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started");
        for (int i = 0; i < counter; i++) {
            normalQueue.offer(i);
            concurrentSafeQueue.offer(i);
        }
        System.out.println(Thread.currentThread().getName() + " finished");
    }
}
