/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.yardConfigMode;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Setter
@Getter
public class ContestConfig {

    private double distanceOut = 0;
    private double distanceLine = 0;
    private double distanceLowerLimit = 0;
    private double distanceUpperLimit = 0;
    private Integer indexOfYardInput = 0;
    
    public ContestConfig(){}

    public ContestConfig(double distanceOut, double distanceLine, double distanceLowerLimit, double distanceUpperLimit) {
        this(distanceOut, distanceLine, distanceLowerLimit, distanceUpperLimit, -1);
    }

    public ContestConfig(double distanceOut, double distanceLine,
            double distanceLowerLimit, double distanceUpperLimit,
            int indexOfYardInput) {
        this.distanceOut = distanceOut;
        this.distanceLine = distanceLine;
        this.distanceLowerLimit = distanceLowerLimit;
        this.distanceUpperLimit = distanceUpperLimit;
        this.indexOfYardInput = indexOfYardInput;
    }

    @NonNull
    public ContestConfig clone(){
        return new ContestConfig(distanceOut, distanceLine, distanceLowerLimit, distanceUpperLimit, indexOfYardInput);
    }

    public void update(ContestConfig config){
        if(config == null) return;
        this.distanceOut = config.distanceOut;
        this.distanceLine = config.distanceLine;
        this.distanceLowerLimit = config.distanceLowerLimit;
        this.distanceUpperLimit = config.distanceUpperLimit;

    }

}
