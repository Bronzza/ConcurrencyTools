package com.company;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;

public class ExampleSemaphorePhaserBarier implements Runnable {
    private Semaphore semaphore = null;
    private CyclicBarrier cyclicBarrier = null;
    private Phaser phaser = null;

    public ExampleSemaphorePhaserBarier(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public ExampleSemaphorePhaserBarier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public ExampleSemaphorePhaserBarier(Phaser phaser) {
        this.phaser = phaser;
    }

    private void semaphorePresent() {
        try {
            System.out.println(Thread.currentThread().getName() + " is ready to work");
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " is working");
            ThreadSleaper.threadSleeper(800);
            System.out.println(Thread.currentThread().getName() + " just finished");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void barierPresent() {
        try {
            System.out.println(Thread.currentThread().getName() + " come to barrier");
            cyclicBarrier.await();
            System.out.println(Thread.currentThread().getName() + " passed barrier");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    private void phaserPresent() {
        System.out.println(Thread.currentThread().getName() + " is ready to work");
        phaser.arriveAndAwaitAdvance();
        System.out.println(Thread.currentThread().getName() + " is working");
        ThreadSleaper.threadSleeper(800);
        System.out.println(Thread.currentThread().getName() + " just finished");
    }

    @Override
    public void run() {
        if (phaser != null) {
            phaserPresent();
        } else if (cyclicBarrier != null) {
            barierPresent();
        } else if (semaphore != null) {
            semaphorePresent();
        }
    }
}
