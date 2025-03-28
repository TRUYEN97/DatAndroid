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
 * @author Admin
 */
public class DoXeNgang extends AbsContestHasMutiLine {

    public DoXeNgang(YardRankModel yardRankModel, List<ContestConfig> contestConfigs, int speedLimit) {
        super(ConstKey.CONTEST_NAME.GHEP_XE_NGANG, R.raw.gxn, 120, contestConfigs);
        CheckWheelCrossedLine crossedLine = new CheckWheelCrossedLine(5, () -> {
            return getWheelCrosserLineStatus(yardRankModel.getPackings1());
        });
        this.conditionBeginHandle.addCondition(new CheckOverSpeedLimit(speedLimit));
        this.conditionRunningHandle.addCondition(crossedLine);
    }

    @Override
    protected void init() {
    }

    private boolean success = false;
    private boolean hasIntoPacking = false;


    @Override
    protected boolean loop() {
        if (!hasIntoPacking && this.carModel.getStatus() == ConstKey.CAR_ST.BACKWARD
            && this.carModel.isT3() && this.carModel.isT2()) {
                hasIntoPacking = true;
        }
        if (hasIntoPacking && !success && this.carModel.isT1() && this.carModel.isT2()
                && this.carModel.isT3()) {
            success = true;
            soundPlayer.successSound();
        }
        if (this.carModel.getDistance() > getContestConfig().getDistanceOut()) {
            if (!success) {
                if (hasIntoPacking) {
                    addErrorCode(ConstKey.ERR.PARCKED_WRONG_POS);
                } else {
                    addErrorCode(ConstKey.ERR.IGNORED_PARKING);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean isAccept() {
        if (isSignal(this.carModel::isT3) && this.carModel.getStatus() == ConstKey.CAR_ST.BACKWARD) {
            hasIntoPacking = false;
            success = false;
            return true;
        }
        return false;
    }

}
