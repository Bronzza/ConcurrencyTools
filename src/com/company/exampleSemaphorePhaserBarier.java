package com.company;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;

public class exampleSemaphorePhaserBarier implements Runnable {
    Semaphore semaphore = null;
    CyclicBarrier cyclicBarrier = null;
    Phaser phaser = null;

    public exampleSemaphorePhaserBarier(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public exampleSemaphorePhaserBarier(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public exampleSemaphorePhaserBarier(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        if (semaphore != null) {
            try {
                System.out.println(Thread.currentThread().getName() + " is ready to work");
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " is working");
                Thread.sleep(900);
                System.out.println(Thread.currentThread().getName() + " just finished");
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (cyclicBarrier != null) {
            System.out.println(Thread.currentThread().getName() + " come to barrier");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " passed barrier");
        } else if (phaser != null) {
            System.out.println(Thread.currentThread().getName() + " is ready to work");
            phaser.arriveAndAwaitAdvance();
            System.out.println(Thread.currentThread().getName() + " is working");
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " just finished");
        }
    }
}
