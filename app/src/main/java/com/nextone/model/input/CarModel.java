/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.input;

import com.nextone.model.modelTest.process.LocationModel;
import java.util.ArrayDeque;
import java.util.Queue;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Admin
 */
@Getter
@Setter
@ToString
public class CarModel {

    private int status;
    private double distance;
    private int rpm;
    private double speed;
    private boolean nt;
    private boolean np;
    private boolean at;
    private boolean pt;
    private boolean cm;
    private boolean t1;
    private boolean t2;
    private boolean t3;
    private boolean s1;
    private boolean s2;
    private boolean s3;
    private boolean s4;
    private boolean s5;
    private int gearBoxValue = 0;
    private final LocationModel location;

    public CarModel() {
        this.location = new LocationModel();
    }
    
    private final Queue<String> remoteValues = new ArrayDeque<>();

    public void setRemoteValue(String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        this.remoteValues.add(value);
    }

    public synchronized void setDistance(double distance) {
        this.distance = distance;
    }

    public synchronized void addDistance(double distance) {
        this.distance += distance;
    }

    public void resetDistance(){
        setDistance(0);
    }

    public String peekRemoteVal() {
        return this.remoteValues.peek();
    }

    public String popRemoteVal() {
        return this.remoteValues.poll();
    }

    public boolean haveRemoteValues() {
        return !this.remoteValues.isEmpty();
    }
}
