package com.company.lists;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntRealizator extends ListMapPresenterBuilder {

    ListMapConcurrentClass localInstance = new ListMapConcurrentClass() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "started");
            for (int i = 0; i < counter; i++) {
                getAtomicInteger().incrementAndGet();
                setResult(getResult() + 1);
            }
            System.out.println(Thread.currentThread().getName() + "finised");
        }
    };

    @Override
    public ListMapPresenterBuilder setListNormal(List list) {
        return null;
    }

    @Override
    public ListMapPresenterBuilder setListSynchronized(CopyOnWriteArrayList list) {
        return null;
    }

    @Override
    public ListMapPresenterBuilder setListMap(Map map) {
        return null;
    }

    @Override
    public ListMapPresenterBuilder setMapSynchronized(ConcurrentHashMap concerSafeMap) {
        return null;
    }

    @Override
    public ListMapPresenterBuilder setAtomicInteger(AtomicInteger atomicInt) {
        localInstance.setAtomicInteger(atomicInt);
        localInstance.setResult(atomicInt.get());
        return this;
    }

    @Override
    public ListMapPresenterBuilder setCounter(int counter) {
        localInstance.setCounter(counter);
        return this;
    }

    @Override
    public ListMapConcurrentClass build() {
        return localInstance.getAtomicInteger() != null && localInstance.getResult()
                != null && localInstance.getCounter() != 0 ? localInstance : null;
    }
}
