package com.company.lists;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ListMapPresenterBuilder {

    public ListMapPresenterBuilder() {
    }

    public abstract ListMapPresenterBuilder setListNormal(List list);

    public abstract ListMapPresenterBuilder setListSynchronized(CopyOnWriteArrayList list);

    public abstract ListMapPresenterBuilder setListMap(Map map);

    public abstract ListMapPresenterBuilder setMapSynchronized(ConcurrentHashMap concerSafeMap);

    public abstract ListMapPresenterBuilder setAtomicInteger(AtomicInteger atomicInt);

    public abstract ListMapPresenterBuilder setCounter(int counter);

    public abstract ListMapConcurrentClass build();
}
