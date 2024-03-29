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
        concurrentPresentation(Presentations.PHASER, 10);
        MySingleTone singleTone = MySingleTone.getInstance();
        MySingleTone secondInstance = SingleToneCreator.createMySingleTones();
        System.out.println(singleTone == secondInstance);
    }

    private static void listPresentation(int howManyThreads) {
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

    private static void mapPresentation(int howManyThreads) {
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

    private static void atomicIntPresentation(int howManyThreads) {
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

    static private void cuncurrentQueuePresentation(int numbOfThreads) {
        int counter = 2000;
        Queue<Integer> queue = new ArrayDeque<>();
        ConcurrentLinkedQueue<Integer> concurQueue = new ConcurrentLinkedQueue<>();

        treadStarter(new QueueClassConcurrent(queue, concurQueue, counter), numbOfThreads);
        ThreadSleaper.threadSleeper(2000);

        System.out.println("Concurrent not save (expected " + counter * numbOfThreads + "): " + queue.size());
        System.out.println("Concurrent save (expected " + counter * numbOfThreads + "): " + concurQueue.size());
    }

    private static void lockPresentation(int howManyThreads) {
        ReentrantLock lock = new ReentrantLock();
        int counter = 3000;

        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        Runnable interfaceRunnable = () -> {
            for (int j = 0; j < counter; j++) {
                list1.add(1);
            }
        };

        treadStarter(new LockPresentation(list, lock, counter), howManyThreads);
        treadStarter(interfaceRunnable, howManyThreads);
        ThreadSleaper.threadSleeper(2000);

        System.out.println("Concurrent NOT save (expected " + counter * howManyThreads + "): " + list1.size());
        System.out.println("Concurrent save (expected " + counter * howManyThreads + "): " + list.size());
    }

    private static void semaforPresentation(Presentations name) {
        int treadsNumber = 9;
        Thread[] threads = new Thread[treadsNumber];
        switch (name) {
            case SEMAFOR:
                Semaphore semaphore = new Semaphore(3);
                treadStarter(new ExampleSemaphorePhaserBarier(semaphore), treadsNumber);
                break;
            case BARIER:
                CyclicBarrier barrier = new CyclicBarrier(3, () -> {
                    System.out.println("Barrier is opened");
                    ThreadSleaper.threadSleeper(700);
                });
                treadStarter(new ExampleSemaphorePhaserBarier(barrier), treadsNumber, 700);
                break;
            case PHASER:
                Phaser phaser = new Phaser(3);
                treadStarter(new ExampleSemaphorePhaserBarier(phaser), treadsNumber, 700);
                break;
        }
    }

    static private void treadStarter(Runnable runnable, int numberOfThreads) {
        Thread[] resultArray = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            resultArray[i] = new Thread(runnable);
            resultArray[i].start();
        }
    }

    public static void concurrentPresentation(Presentations name, int numberOfThreads) {
        switch (name) {
            case LIST:
                listPresentation(numberOfThreads);
                break;
            case MAP:
                mapPresentation(numberOfThreads);
                break;
            case LOCK:
                lockPresentation(numberOfThreads);
                break;
            case SEMAFOR:
                semaforPresentation(Presentations.SEMAFOR);
                break;
            case BARIER:
                semaforPresentation(Presentations.BARIER);
                break;
            case PHASER:
                semaforPresentation(Presentations.PHASER);
                break;
            case INTEGER:
                atomicIntPresentation(numberOfThreads);
                break;
            case QUEUE:
                cuncurrentQueuePresentation(numberOfThreads);
                break;
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
