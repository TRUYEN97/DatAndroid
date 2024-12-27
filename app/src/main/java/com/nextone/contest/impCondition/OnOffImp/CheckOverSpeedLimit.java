/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impCondition.OnOffImp;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.AbsTimerCondition;

/**
 *
 * @author Admin
 */
public class CheckOverSpeedLimit extends AbsTimerCondition {

    private final int specSpeed;

    public CheckOverSpeedLimit(int specSpeed) {
        super(null , 3, false);
        this.specSpeed = specSpeed;
    }

    @Override
    protected boolean signal() {
        return this.carModel.getSpeed() > specSpeed;
    }

    @Override
    protected void action() {
        setErrorcode(ConstKey.ERR.SPEED_LIMIT_EXCEEDED);
    }

}
