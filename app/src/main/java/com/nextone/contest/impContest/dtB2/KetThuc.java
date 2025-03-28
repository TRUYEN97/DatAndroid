/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest.dtB2;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsContest;
import com.nextone.datandroid.R;
/**
 *
 * @author Admin
 */
public class KetThuc extends AbsContest {

    public KetThuc() {
        this(ConstKey.CONTEST_NAME.KET_THUC, R.raw.kt);
    }

    public KetThuc(String name, int soundId) {
        super(name, soundId, true, false, true, 2000);
    }
    private long oldMill;
    private boolean isStop = false;
    private boolean kpt = true;
    private boolean so = true;
    private long oldTime = 0;
    private boolean checkNp = false;

    @Override
    protected void init() {
    }

    @Override
    public boolean loop() {
        if (!checkNp && System.currentTimeMillis() - oldTime >= 5000
                && !this.carModel.isNp()) {
            this.addErrorCode(ConstKey.ERR.NO_SIGNAL_RIGHT_END);
            checkNp = true;
        }
        if (this.carModel.getStatus() == ConstKey.CAR_ST.STOP || isStop) {
            this.isStop = true;
            if (!checkNp && !this.carModel.isNp()) {
                this.addErrorCode(ConstKey.ERR.NO_SIGNAL_RIGHT_END);
                checkNp = true;
            }
            if (this.carModel.getGearBoxValue() == 0 && this.carModel.isPt()) {
                return true;
            }
            long deta = System.currentTimeMillis() - oldMill;
            if (so && deta >= 3000 && this.carModel.getGearBoxValue() != 0) {
                this.addErrorCode(ConstKey.ERR.FAILED_SHIFTTO_NEUTRAL);
                so = false;
            }
            if (kpt && deta >= 5000 && !this.carModel.isPt()) {
                this.addErrorCode(ConstKey.ERR.FAILED_APPLY_PARKING_BRAKE);
                kpt = false;
                return true;
            }
        } else {
            oldMill = System.currentTimeMillis();
        }
        return false;
    }

    @Override
    protected boolean isIntoContest() {
        oldMill = System.currentTimeMillis();
        oldTime = System.currentTimeMillis();
        kpt = true;
        so = true;
        checkNp = false;
        return true;
    }

}
