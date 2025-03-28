/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.nextone.datandroid.R;
import com.nextone.model.yardConfigMode.ContestConfig;

/**
 * @author Admin
 */
public class DungXe extends AbsConstestJustOneLine {

    public DungXe(ContestConfig contestConfig, int speedLimit) {
        this(ConstKey.CONTEST_NAME.DUNG_XE_CNDB, R.raw.dxcndb, contestConfig, speedLimit);
    }

    public DungXe(String name, int soundId, ContestConfig contestConfig, int speedLimit) {
        super(name, soundId, 200, contestConfig);
        this.conditionBeginHandle.addCondition(new CheckOverSpeedLimit(speedLimit));
    }

    @Override
    protected void init() {

    }

    private boolean hasStop = false;

    @Override
    protected boolean loop() {
        double d = this.carModel.getDistance();
        if (!hasStop && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            hasStop = true;
            d = this.carModel.getDistance();
            if (d > this.contestConfig.getDistanceLine()) {
                addErrorCode(ConstKey.ERR.STOP_AFTER_DES);
            } else if (d < this.contestConfig.getDistanceLine() - 0.5f) {
                addErrorCode(ConstKey.ERR.STOP_BEFORE_DES);
            } else {
                soundPlayer.successSound();
            }
        } else if (d > this.contestConfig.getDistanceOut()) {
            if (!hasStop) {
                addErrorCode(ConstKey.ERR.DONT_STOP_AS_REQUIRED);
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean isAccept() {
        if (isSignal(this.carModel::isT1)) {
            hasStop = false;
            return true;
        }
        return false;
    }

}
