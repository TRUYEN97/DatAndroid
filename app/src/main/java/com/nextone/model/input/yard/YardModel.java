/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.input.yard;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 *
 * @author Admin
 */
@Getter
public class YardModel {

    private final YardRankModel rankB;
    private final YardRankModel rankC;
    private final YardRankModel rankD;
    private final YardRankModel rankE;
    private final TrafficLightModel trafficLightModel;
    private final TrafficLightModel trafficLightModel1;
    private final List<Boolean> inputs;



    public YardModel() {
        this.rankB = new YardRankModel();
        this.rankC = new YardRankModel();
        this.rankD = new YardRankModel();
        this.rankE = new YardRankModel();
        this.trafficLightModel = new TrafficLightModel();
        this.trafficLightModel1 = new TrafficLightModel();
        this.inputs = new ArrayList<>();
    }


    public void reset(){
        this.rankB.reset();
        this.rankC.reset();
        this.rankD.reset();
        this.rankE.reset();
        this.trafficLightModel.reset();
        this.trafficLightModel1.reset();
        this.inputs.clear();
    }

}
