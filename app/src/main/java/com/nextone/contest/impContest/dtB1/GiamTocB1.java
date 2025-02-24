/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.dtB1;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsContest;
import com.nextone.datandroid.R;
/**
 *
 * @author Admin
 */
public class GiamTocB1 extends AbsContest {

    private double oldDistance = 0;
    private double oldV = 0;

    public GiamTocB1() {
        this(ConstKey.CONTEST_NAME.GIAM_TOC, R.raw.gs);
    }

    public GiamTocB1(String name, int soundId) {
        super(name, soundId, true, false, true, 2000);
    }

    @Override
    public boolean loop() {
        double deltaV = oldV - this.carModel.getSpeed();
        if (this.carModel.getDistance() - oldDistance >= 100) {
            if (deltaV < 5) {
               addErrorCode(ConstKey.ERR.FAILED_SHIFTDOWN_GEAR_IN_100M);
            }
            return true;
        } else return this.carModel.getSpeed() == 0 || deltaV >= 5;
    }

    @Override
    protected boolean isIntoContest() {
        oldDistance = this.carModel.getDistance();
        oldV = this.carModel.getSpeed();
        return true;
    }

    @Override
    protected void init() {
    }

}
