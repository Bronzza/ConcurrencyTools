package com.company;

public class ThreadSleaper {
    public static void threadSleeper (int timeMillis){
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
