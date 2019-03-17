package com.company.lists.listMapBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ListRealisator extends ListMapPresenterBuilder {
    final ListMapConcurrentClass localInstance = new ListMapConcurrentClass() {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "started");
            for (int i = 0; i < counter; i++) {
                getInnerList().add(i);
                getInnerListSyncronised().add(i);
            }
            System.out.println(Thread.currentThread().getName() + "finised");
        }
    };

    @Override
    public ListMapPresenterBuilder setListNormal(List list) {
        localInstance.setInnerList(list);
        return this;
    }

    @Override
    public ListMapPresenterBuilder setListSynchronized(CopyOnWriteArrayList list) {
        localInstance.setInnerListSyncronised(list);
        return this;
    }

    @Override
    public ListMapPresenterBuilder setListMap(Map map) {
        return null;
    }

    @Override
    public ListMapPresenterBuilder setMapSynchronized(ConcurrentHashMap concarSafeMap) {
        return null;
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
        if (localInstance.getInnerList() != null && localInstance.getInnerListSyncronised()
                != null &&  localInstance.getCounter() != 0) {
            return localInstance;
        } else {
            return null;
        }
    }
}
