/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.CheckWheelCrossedLine;
import com.nextone.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.nextone.datandroid.R;
import com.nextone.model.input.yard.YardRankModel;
import com.nextone.model.yardConfigMode.ContestConfig;

import java.util.List;
/**
 *
 * @author Admin
 */
public class DuongS extends AbsContestHasMutiLine {

    public DuongS(YardRankModel contestConfig, List<ContestConfig> contestConfigs, int speedLimit) {
        super(ConstKey.CONTEST_NAME.CHU_S, R.raw.dvqc, 120, contestConfigs);
        CheckWheelCrossedLine crossedLine = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(contestConfig.getRoadSs());
        });
        this.conditionBeginHandle.addCondition(new CheckOverSpeedLimit(speedLimit));
        this.conditionRunningHandle.addCondition(crossedLine);
    }

    @Override
    protected void init() {
    }

    @Override
    protected boolean loop() {
        return this.carModel.getDistance() >= getContestConfig().getDistanceOut()
                && isSignal(this.carModel::isT1);
    }

    @Override
    protected boolean isAccept() {
        return isSignal(this.carModel::isT1);
    }

}
