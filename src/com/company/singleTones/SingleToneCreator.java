package com.company.singleTones;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class SingleToneCreator {
    MySingleTone instance = MySingleTone.getInstance();

    public void doS() {
        Class singToneClass = instance.getClass();
        try {
            System.out.println(singToneClass.getDeclaredField("name").getName());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static MySingleTone createMySingleTones() {
        Class MySingleTone = MySingleTone.class;
        Constructor<MySingleTone> constr = null;
        MySingleTone secondCopyOfSingleTone = null;
        try {
            constr = MySingleTone.getDeclaredConstructor();
            constr.setAccessible(true);
            secondCopyOfSingleTone = constr.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
        }
        return secondCopyOfSingleTone;
    }
}
