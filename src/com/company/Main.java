package com.company;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) throws InterruptedException, NoSuchFieldException {
//        Main.listMapPresentation();
        Main.cuncurrentQueuePresentation();
//        Main.lockPresentation();
//        semaforPresentation(Presentations.BARIER);
//        semaforPresentation(Presentations.PHASER);
//        semaforPresentation(Presentations.SEMAFOR);
    }

    static public void listMapPresentation() throws InterruptedException {
        Object o = new Object();
        int numbThreads = 10;
        int counter = 5000;
        List<Integer> normalList = new LinkedList<>();
        CopyOnWriteArrayList<Integer> concurentSafeList = new CopyOnWriteArrayList<>();
        Map<Integer, Integer> normalMap = new HashMap<>();
        Map<Integer, Integer> concurSafeMap = new ConcurrentHashMap<>();

        Thread[] arrayThread = new Thread[numbThreads];

        for (int i = 0; i < numbThreads; i++) {
            arrayThread[i] = new Thread(new ListMapConcurrentClass(concurentSafeList, normalList, normalMap, concurSafeMap, counter));
            arrayThread[i].start();
        }
        synchronized (o) {
            o.wait(2000);
        }
        System.out.println("Concurrent NOT save collection(expected " + counter * numbThreads + "): " + normalList.size());
        System.out.println("Concurrent save collection (expected" + counter * numbThreads + "): " + concurentSafeList.size() + "\n");
        System.out.println("Concurrent NOT save MAP(expected " + counter + "): " + normalMap.size());
        System.out.println("Concurrent  save  MAP(expected " + counter + "): " + concurSafeMap.size() + "\n");
        System.out.println("Concurrent  NOT save int (expected " + counter * numbThreads + "): " + new ListMapConcurrentClass().getResult());
        System.out.println("Concurrent  save (expected " + counter * numbThreads + "): " + new ListMapConcurrentClass().getAtomicInteger());
    }

    static void cuncurrentQueuePresentation() throws InterruptedException {
        int numbThreads = 10;
        int counter = 2000;
        Queue<Integer> queue = new PriorityQueue<>();
        ConcurrentLinkedQueue<Integer> concurQueue = new ConcurrentLinkedQueue<>();

        Thread[] arrayThread = new Thread[numbThreads];

        for (int i = 0; i < numbThreads; i++) {
            arrayThread[i] = new Thread(new QueueClassConcurrent(queue, concurQueue, counter));
            arrayThread[i].start();
        }
        Thread.sleep(2000);
        System.out.println("Concurrent not save (expected " + counter * numbThreads + "): " + queue.size());
        System.out.println("Concurrent save (expected " + counter * numbThreads + "): " + concurQueue.size());
    }

    public static void lockPresentation() {
        ReentrantLock lock = new ReentrantLock();
        int numbThreads = 10;
        int counter = 3000;
        int result=0;
        List <Integer> list = new ArrayList<>();
        List <Integer> list1 = new ArrayList<>();

        Thread[] arrayThread = new Thread[numbThreads];
        Thread[] arrayThread2 = new Thread[numbThreads];

        for (int i = 0; i < numbThreads; i++) {
            arrayThread[i] = new Thread(new LockPresentation(list, lock, counter));
            arrayThread[i].start();
            arrayThread2[i] = new Thread(() ->{
                for (int j = 0; j < counter; j++) {
                    list1.add(1);
                }
            });
            arrayThread2[i].start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Concurrent NOT save (expected " + counter * numbThreads + "): " + list1.size());
        System.out.println("Concurrent save (expected " + counter * numbThreads + "): " + list.size());
    }

    public static void semaforPresentation(Presentations name) {
        int treadsNumber = 9;
        Thread[] threads = new Thread[treadsNumber];
        if (name.getDescription().equals("semafor")) {
            Semaphore semaphore = new Semaphore(3);
            for (int i = 0; i < treadsNumber; i++) {
                threads[i] = new Thread(new exampleSemaphorePhaserBarier(semaphore));
                threads[i].start();
            }
        }
        if (name.getDescription().equals("barier")) {
            CyclicBarrier barrier = new CyclicBarrier(3, () -> {
                System.out.println("Barrier is opened");
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            for (int i = 0; i < treadsNumber; i++) {
                threads[i] = new Thread(new exampleSemaphorePhaserBarier(barrier));
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threads[i].start();
            }
        } else if (name.getDescription().equals("phaser")) {
            Phaser phaser = new Phaser(3);
            for (int i = 0; i < treadsNumber; i++) {
                threads[i] = new Thread(new exampleSemaphorePhaserBarier(phaser));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threads[i].start();
            }
        }
    }

}
