/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.input.yard;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Setter
@Getter
public class TrafficLightModel {
    
    public static final int GREEN = 0;
    public static final int YELLOW = 1;
    public static final int RED = 2;
    private int trafficLight;
    private int time;

    public TrafficLightModel() {
        this.trafficLight = GREEN;
    }

    public void reset() {
        this.trafficLight = GREEN;
        this.time = 0;
    }
}
