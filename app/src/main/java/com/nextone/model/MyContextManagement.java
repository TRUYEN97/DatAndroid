package com.nextone.model;

import android.content.Context;

import com.nextone.datandroid.MyActivity;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class MyContextManagement {
    private static volatile MyContextManagement instance;
    @Getter
    @Setter
    private Context aplicationContext;

    private final Map<Class<? extends MyActivity>, MyActivity> myActivities;

    private MyContextManagement() {
        this.myActivities = new HashMap<>();
    }

    public static MyContextManagement getInstance() {
        MyContextManagement ins = instance;
        if (ins == null) {
            synchronized (MyContextManagement.class) {
                ins = instance;
                if (ins == null) {
                    ins = instance = new MyContextManagement();
                }
            }
        }
        return ins;
    }

    public boolean putActivity(MyActivity myActivity) {
        if (myActivity == null) return false;
        myActivities.put(myActivity.getClass(), myActivity);
        return true;
    }

    public MyActivity getActivity(Class<? extends MyActivity> cls) {
        return myActivities.get(cls);
    }

    public void removeActivity(MyActivity myActivity) {
        if (myActivity == null) return;
        myActivities.remove(myActivity.getClass());
    }
}
