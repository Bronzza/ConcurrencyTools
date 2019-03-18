package com.company.singleTones;

public class MySingleTone {

    private static volatile MySingleTone mySingleToneInstance = null;
    private String name = "I'm the one";

    private MySingleTone() {
    }

    public String getName() {
        return name;
    }

    public static MySingleTone getInstance() {
        MySingleTone localLnstance = mySingleToneInstance;
        if (localLnstance == null) {
            synchronized (MySingleTone.class){
                localLnstance = mySingleToneInstance;
                if (localLnstance == null){
                    mySingleToneInstance = localLnstance = new MySingleTone();
                }
            }
        }
        return localLnstance;
    }
}
