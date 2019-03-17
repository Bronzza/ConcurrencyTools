package com.company;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class LockPresentation implements Runnable {
    private List innerList;
    private ReentrantLock lock;
    private int counter;

    public LockPresentation(List innerList, ReentrantLock lock, int counter) {
        this.innerList = innerList;
        this.lock = lock;
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            if (lock != null) {
                lock.lock();
            }
            for (int i = 0; i < counter; i++) {
                innerList.add(i);
            }
        } finally {
            if (lock != null) {
                System.out.println(Thread.currentThread().getName() + " finished");
                lock.unlock();
            }
        }
    }
}
