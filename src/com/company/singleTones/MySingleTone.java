package com.company.singleTones;

public class MySingleTone {

    private static MySingleTone mySingleToneInstance = null;
    private String name = "I'm the one";

    private MySingleTone() {
    }

    public String getName() {
        return name;
    }

    public static MySingleTone getInstance() {
        if (mySingleToneInstance != null) {
            return mySingleToneInstance;
        } else {
            return mySingleToneInstance = new MySingleTone();
        }
    }

}

