package com.company;


import com.company.lists.AtomicIntRealizator;
import com.company.lists.ListMapConcurrentClass;
import com.company.lists.ListRealisator;
import com.company.lists.MapRealisator;
import com.company.singleTones.MySingleTone;
import com.company.singleTones.SingleToneCreator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.Queue;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    public static void main(String[] args) {
        MySingleTone singleTone = MySingleTone.getInstance();
        MySingleTone secondInstance = SingleToneCreator.createMySingleTones();
        System.out.println(singleTone == secondInstance);
    }

    static public void listPresentation(int howManyThreads) {
        int counter = 5000;
        List<Integer> normalList = new ArrayList<>();
        CopyOnWriteArrayList<Integer> concurentSafeList = new CopyOnWriteArrayList<>();
        ListRealisator listRealisator = new ListRealisator();
        ListMapConcurrentClass presentationClass = listRealisator
                .setListNormal(normalList)
                .setListSynchronized(concurentSafeList)
                .setCounter(counter)
                .build();

        treadStarter(presentationClass, howManyThreads);
        ThreadSleaper.threadSleeper(2000);

        System.out.println("Concurrent NOT save collection(expected " + counter * howManyThreads + "): "
                + normalList.size());
        System.out.println("Concurrent save collection (expected " + counter * howManyThreads + "): "
                + concurentSafeList.size() + "\n");
    }

    static public void mapPresentation(int howManyThreads) {
        int counter = 5000;
        Map<Integer, Integer> normalMap = new HashMap<>();
        ConcurrentHashMap<Integer, Integer> concurSafeMap = new ConcurrentHashMap<>();
        MapRealisator listRealisator = new MapRealisator();
        ListMapConcurrentClass presentationClass = listRealisator
                .setListMap(normalMap)
                .setMapSynchronized(concurSafeMap)
                .setCounter(counter)
                .build();

        treadStarter(presentationClass, howManyThreads);
        ThreadSleaper.threadSleeper(2000);

        System.out.println("Concurrent NOT save MAP(expected " + counter + "): " + normalMap.size());
        System.out.println("Concurrent  save  MAP(expected " + counter + "): " + concurSafeMap.size() + "\n");
    }

    static public void atomicIntPresentation(int howManyThreads) {
        int counter = 5000;
        AtomicIntRealizator atomicIntRealizator = new AtomicIntRealizator();
        ListMapConcurrentClass presentationClass = atomicIntRealizator
                .setAtomicInteger(new AtomicInteger(0))
                .setCounter(counter)
                .build();

        treadStarter(presentationClass, howManyThreads);
        ThreadSleaper.threadSleeper(2000);

        System.out.println("Concurrent  NOT save int (expected " + counter * howManyThreads + "): "
                + presentationClass.getResult());
        System.out.println("Concurrent  save (expected " + counter * howManyThreads + "): "
                + presentationClass.getAtomicInteger());
    }

    static void cuncurrentQueuePresentation() {
        int numbThreads = 10;
        int counter = 2000;
        Queue<Integer> queue = new ArrayDeque<>();
        ConcurrentLinkedQueue<Integer> concurQueue = new ConcurrentLinkedQueue<>();

        treadStarter(new QueueClassConcurrent(queue, concurQueue, counter), numbThreads);
        ThreadSleaper.threadSleeper(2000);

        System.out.println("Concurrent not save (expected " + counter * numbThreads + "): " + queue.size());
        System.out.println("Concurrent save (expected " + counter * numbThreads + "): " + concurQueue.size());
    }

    public static void lockPresentation() {
        ReentrantLock lock = new ReentrantLock();
        int numbThreads = 10;
        int counter = 3000;

        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        Runnable interfaceRunnable = () -> {
            for (int j = 0; j < counter; j++) {
                list1.add(1);
            }
        };

        treadStarter(new LockPresentation(list, lock, counter), 10);
        treadStarter(interfaceRunnable, 10);
        ThreadSleaper.threadSleeper(2000);

        System.out.println("Concurrent NOT save (expected " + counter * numbThreads + "): " + list1.size());
        System.out.println("Concurrent save (expected " + counter * numbThreads + "): " + list.size());
    }

    public static void semaforPresentation(Presentations name) {
        int treadsNumber = 9;
        Thread[] threads = new Thread[treadsNumber];
        if (name.getDescription().equals("semafor")) {
            Semaphore semaphore = new Semaphore(3);
            treadStarter(new ExampleSemaphorePhaserBarier(semaphore), treadsNumber);
        }
        if (name.getDescription().equals("barier")) {
            CyclicBarrier barrier = new CyclicBarrier(3, () -> {
                System.out.println("Barrier is opened");
                ThreadSleaper.threadSleeper(800);
            });
            treadStarter(new ExampleSemaphorePhaserBarier(barrier), treadsNumber, 800);
        } else if (name.getDescription().equals("phaser")) {
            Phaser phaser = new Phaser(3);
            treadStarter(new ExampleSemaphorePhaserBarier(phaser), treadsNumber, 800);
        }
    }

    static private void treadStarter(Runnable runnable, int numberOfThreads) {
        Thread[] resultArray = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            resultArray[i] = new Thread(runnable);
            resultArray[i].start();
        }
    }

    static private void treadStarter(Runnable runnable, int numberOfThreads, int delayMillis) {
        Thread[] resultArray = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            resultArray[i] = new Thread(runnable);
            ThreadSleaper.threadSleeper(delayMillis);
            resultArray[i].start();
        }
    }
}
