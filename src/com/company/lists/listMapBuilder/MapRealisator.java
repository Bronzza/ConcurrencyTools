package com.company.lists.listMapBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MapRealisator extends ListMapPresenterBuilder {
    ListMapConcurrentClass localInstance = new ListMapConcurrentClass() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "started");
            for (int i = 0; i < counter; i++) {
                getInnerMap().put(i, i);
                getInnerMapSyncronised().put(i, i);
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
        localInstance.setInnerMap(map);
        return this;
    }

    @Override
    public ListMapPresenterBuilder setMapSynchronized(ConcurrentHashMap concarSafeMap) {
        localInstance.setInnerMapSyncronised(concarSafeMap);
        return this;
    }

    @Override
    public ListMapPresenterBuilder setAtomicInteger(AtomicInteger atomicInt) {
        return null;
    }

    @Override
    public ListMapPresenterBuilder setCounter(int counter) {
        localInstance.setCounter(counter);
        return this;
    }

    @Override
    public ListMapConcurrentClass build() {
        if (localInstance.getInnerMap() != null && localInstance.getInnerMapSyncronised() != null
                && localInstance.getCounter() != 0) {
            return localInstance;
        } else {
            return null;
        }
    }
}
