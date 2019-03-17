package com.company;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ListMapConcurrentClass implements Runnable {
    private List innerList;
    private CopyOnWriteArrayList innerListSyncronised;
    private Map innerMap;
    private Map innerMapSyncronised;
    int counter;
    private static Integer result = 0;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public ListMapConcurrentClass() {
    }

    public ListMapConcurrentClass(CopyOnWriteArrayList listSynchro, List innerList,
                                  Map mapNormal, Map mapSyncronised, int counter) {
        innerListSyncronised = listSynchro;
        this.innerList = innerList;
        innerMap = mapNormal;
        innerMapSyncronised = mapSyncronised;
        this.counter = counter;
    }

    public int getResult() {
        return result;
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "started");
        for (int i = 0; i < counter; i++) {
            innerList.add(i);
            innerListSyncronised.add(i);
            innerMap.put(i, i);
            innerMapSyncronised.put(i, i);
            result++;
            atomicInteger.incrementAndGet();
        }
        System.out.println(Thread.currentThread().getName() + "finised");
    }
}
