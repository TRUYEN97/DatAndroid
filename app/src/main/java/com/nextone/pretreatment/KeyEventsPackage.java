/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.pretreatment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
public class KeyEventsPackage {

    protected final List<IKeyEvent> anyKeyEvents;
    protected final Map<String, IKeyEvent> keyEvents;
    @Setter
    @Getter
    private boolean justMe;
    @Getter
    private final String name;

    public KeyEventsPackage(String name) {
        this(name, false);
    }

    public KeyEventsPackage(String name, boolean justMe) {
        this.keyEvents = new HashMap<>();
        this.anyKeyEvents = new ArrayList<>();
        this.justMe = justMe;
        this.name = name;
    }


    public void merge(KeyEventsPackage keyEventsPackage) {
        putEvents(keyEventsPackage.keyEvents);
        addAllAnyKeyEvent(keyEventsPackage.anyKeyEvents);
    }

    public void addAnyKeyEvent(IKeyEvent event) {
        if (event == null || this.anyKeyEvents.contains(event)) {
            return;
        }
        this.anyKeyEvents.add(event);
    }

    public void addAllAnyKeyEvent(List<IKeyEvent> events) {
        if (events == null) {
            return;
        }
        for (IKeyEvent event : events) {
            addAnyKeyEvent(event);
        }
    }

    public void putEvent(String key, IKeyEvent event) {
        if (event == null || this.keyEvents.containsKey(key)) {
            return;
        }
        this.keyEvents.put(key, event);
    }

    public void putEvents(Map<String, IKeyEvent> modeKeyEvents) {
        if (modeKeyEvents == null) {
            return;
        }
        for (String key : modeKeyEvents.keySet()) {
            if (keyEvents.containsKey(key)) {
                continue;
            }
            this.keyEvents.put(key, modeKeyEvents.get(key));
        }
    }

    public void attack(String key) {
        new Thread(() -> {
            IKeyEvent event = keyEvents.get(key);
            if (event != null) {
                event.action(key);
            } else {
                for (IKeyEvent anyKeyEvent : anyKeyEvents) {
                    if (anyKeyEvent == null) {
                        continue;
                    }
                    anyKeyEvent.action(key);
                }
            }
        }).start();
    }

    boolean isName(String name) {
        if (this.name == null || name == null) {
            return false;
        }
        return this.name.equalsIgnoreCase(name);
    }

    public void clear() {
        this.keyEvents.clear();
        this.anyKeyEvents.clear();
    }

    public void remove(String key) {
        this.keyEvents.remove(key);
    }

    public void remove(IKeyEvent event) {
        this.anyKeyEvents.remove(event);
    }

}
