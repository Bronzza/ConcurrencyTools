package com.company.lists;

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
    private Integer result;
    private AtomicInteger atomicInteger;

    public ListMapConcurrentClass() {
    }

    public List getInnerList() {
        return innerList;
    }

    public void setInnerList(List innerList) {
        this.innerList = innerList;
    }

    public CopyOnWriteArrayList getInnerListSyncronised() {
        return innerListSyncronised;
    }

    public void setInnerListSyncronised(CopyOnWriteArrayList innerListSyncronised) {
        this.innerListSyncronised = innerListSyncronised;
    }

    public Map getInnerMap() {
        return innerMap;
    }

    public void setInnerMap(Map innerMap) {
        this.innerMap = innerMap;
    }

    public Map getInnerMapSyncronised() {
        return innerMapSyncronised;
    }

    public void setInnerMapSyncronised(Map innerMapSyncronised) {
        this.innerMapSyncronised = innerMapSyncronised;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Integer getResult() {
        return result;
    }

    public  void setResult(Integer result) {
        this.result = result;
    }

    public  AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    public  void setAtomicInteger(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }

    @Override
    public void run() {
    }
}
