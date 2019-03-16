package com.company;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class LockPresentation implements Runnable {
    private List innerList;
    ReentrantLock lock;
    int counter;

    public LockPresentation(List innerList, ReentrantLock lock, int counter) {
        this.innerList = innerList;
        this.lock = lock;
        this.counter = counter;
    }

    @Override
    public void run() {
        if (lock != null) {
            lock.lock();
        }
        for (int i = 0; i < counter; i++) {
            innerList.add(i);
        }
        if (lock != null && lock.isLocked()) {
            System.out.println(Thread.currentThread().getName() + " finished");
            lock.unlock();
        }
    }
}