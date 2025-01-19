/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.shB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.OnOffImp.CheckOverSpeedLimit;
import com.nextone.contest.impCondition.timerCondition.CheckTimeOut;
import com.nextone.model.input.yard.TrafficLightModel;
import com.nextone.model.input.yard.YardModel;
import com.nextone.model.yardConfigMode.ContestConfig;
import com.nextone.datandroid.R;
/**
 *
 * @author Admin
 */
public class NgaTu extends AbsConstestJustOneLine {

    private final CheckTimeOut checkTimeOut20s;
    private final CheckTimeOut checkTimeOut30s;
    private final TrafficLightModel trafficLightModel;
    private final int times;

    public NgaTu(int times, YardModel yardModel, ContestConfig contestConfig, int speedLimit) {
        super(ConstKey.CONTEST_NAME.NGAT_TU, R.raw.qnt, 120, contestConfig);
        this.times = times;
        this.checkTimeOut30s = new CheckTimeOut(importantError, 30, ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_30S);
        this.checkTimeOut20s = new CheckTimeOut(null, 20, ConstKey.ERR.FAILED_PASS_INTERSECTION_OVER_20S, false);
        if (times == 1 || times == 3) {
            this.trafficLightModel = yardModel.getTrafficLightModel();
        } else {
            this.trafficLightModel = yardModel.getTrafficLightModel1();
        }
        this.conditionBeginHandle.addCondition(new CheckOverSpeedLimit(speedLimit));
    }

    private boolean hasStop = false;

    @Override
    public void end() {
        super.end();
        this.checkTimeOut20s.stop();
        this.checkTimeOut30s.stop();
    }

    private boolean ranRedLight;
    private boolean dontTurnOnNt;
    private boolean dontTurnOnNp;
    private boolean firstTime;

    @Override
    protected boolean loop() {
        double d = this.carModel.getDistance();
        if (d > this.contestConfig.getDistanceLine()) {
            if (firstTime) {
                firstTime = false;
                checkCondition();
            }
            if (checkEndTest()) {
                this.checkTimeOut20s.setPass();
                this.checkTimeOut30s.setPass();
                return true;
            }
        }
        if (!hasStop && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            hasStop = true;
            d = this.carModel.getDistance();
            if (d >= this.contestConfig.getDistanceLine()) {
                addErrorCode(ConstKey.ERR.STOP_AFTER_DES);
            } else if (d < this.contestConfig.getDistanceLine() - 0.5) {
                addErrorCode(ConstKey.ERR.STOP_BEFORE_DES);
            } else {
                soundPlayer.successSound();
            }
        }
        return false;
    }

    private boolean checkEndTest() {
        double d = this.carModel.getDistance();
        if (times == 2 || times == 3) {
            if (this.carModel.isT1() || this.carModel.isT2()) {
                if (times == 3 && d < 14) {
                    addErrorCode(ConstKey.ERR.WRONG_WAY);
                    stop();
                }
                return true;
            }
            if (d >= this.contestConfig.getDistanceOut()) {
                addErrorCode(ConstKey.ERR.WRONG_WAY);
                stop();
                return true;
            }
        } else {
            if (this.carModel.isT1() || this.carModel.isT2()) {
                addErrorCode(ConstKey.ERR.WRONG_WAY);
                stop();
                return true;
            }
            return d >= this.contestConfig.getDistanceOut();
        }
        return false;
    }

    private void checkCondition() {
        this.checkTimeOut20s.start();
        this.checkTimeOut30s.start();
        if (!ranRedLight && this.trafficLightModel.getTrafficLight() == TrafficLightModel.RED) {
            addErrorCode(ConstKey.ERR.RAN_A_RED_LIGHT);
            ranRedLight = true;
        }
        if (!this.dontTurnOnNt && times == 3 && !this.carModel.isNt()) {
            addErrorCode(ConstKey.ERR.NO_SIGNAL_TURN_LEFT);
            this.dontTurnOnNt = true;
        }
        if (!this.dontTurnOnNp && times == 4 && !this.carModel.isNp()) {
            addErrorCode(ConstKey.ERR.NO_SIGNAL_TURN_RIGHT);
            this.dontTurnOnNp = true;
        }
    }

    @Override
    protected void init() {
    }

    @Override
    protected boolean isAccept() {
        if (this.carModel.isT1() || this.carModel.isT2()) {
            this.dontTurnOnNt = false;
            this.dontTurnOnNp = false;
            this.ranRedLight = false;
            this.firstTime = true;
            return true;
        }
        return false;
    }

}
