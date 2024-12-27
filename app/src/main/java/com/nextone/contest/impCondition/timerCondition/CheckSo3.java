/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impCondition.timerCondition;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.AbsTimerCondition;

/**
 *
 * @author Admin
 */
public class CheckSo3 extends AbsTimerCondition {

    public CheckSo3() {
        super(null, 3, false);
    }

    @Override
    protected boolean signal() {
        if (this.carModel.getGearBoxValue() >= 3) {
            return this.carModel.getSpeed() < 20;
        }
        return false;
    }

    @Override
    protected void action() {
        this.setErrorcode(ConstKey.ERR.USED_GEAR_3_UNDER_20KMH);
    }

}
