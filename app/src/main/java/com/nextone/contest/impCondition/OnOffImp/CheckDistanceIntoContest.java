/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impCondition.OnOffImp;

import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.input.CarModel;
import com.nextone.model.yardConfigMode.ContestConfig;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 *
 * @author Admin
 */
public class CheckDistanceIntoContest {

    @Getter
    private final List<ContestConfig> contestConfigs;
    private final CarModel carModel;
    private ContestConfig contestConfig;

    public CheckDistanceIntoContest(List<ContestConfig> contestConfigs) {
        this.contestConfigs = new ArrayList<>();
        if (contestConfigs != null && !contestConfigs.isEmpty()) {
            this.contestConfigs.addAll(contestConfigs);
        }
        this.carModel = MCUSerialHandler.getInstance().getModel();
    }

    public ContestConfig getContestConfig() {
        return contestConfig == null ? new ContestConfig() : contestConfig;
    }

    public boolean isDistanceOutOfSpec(double oldDistance) {
         if (contestConfigs.isEmpty()) {
            return false;
        }
        double delta = this.carModel.getDistance() - oldDistance;
        ContestConfig config;
        for (int i = 0; i < contestConfigs.size(); i++) {
            if ((config = contestConfigs.get(i)) == null) {
                continue;
            }
            if (delta <= config.getDistanceUpperLimit()) {
                return false;
            }
        }
        return true;
    }

    public int getIndexOfLine(double oldDistance) {
        double delta = this.carModel.getDistance() - oldDistance;
        ContestConfig config;
        for (int i = 0; i < contestConfigs.size(); i++) {
            if ((config = contestConfigs.get(i)) == null) {
                continue;
            }
            if (delta <= config.getDistanceUpperLimit()
                    && delta >= config.getDistanceLowerLimit()) {
                this.contestConfig = config;
                return i;
            }
        }
        return -1;
    }

    public boolean isEnoughMinDistanceSpec(double oldDistance) {
        double delta = this.carModel.getDistance() - oldDistance;
        ContestConfig config;
        for (int i = 0; i < contestConfigs.size(); i++) {
            if ((config = contestConfigs.get(i)) == null) {
                continue;
            }
            if (delta >= config.getDistanceLowerLimit()) {
                return true;
            }
        }
        return false;
    }
}
