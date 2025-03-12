/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impContest;

import com.nextone.common.Util;
import com.nextone.contest.AbsContest;

/**
 *
 * @author Admin
 */
public abstract class AbsSaHinhContest extends AbsContest {

    public interface Signal {
        boolean isSignal();
    }
    public AbsSaHinhContest(String name, int soundNameId, boolean soundIn, int timeout) {
        super(name, soundNameId, true, soundIn, true, timeout);
    }

    protected boolean isSignal(Signal signal){
        if(signal != null && signal.isSignal()){
            while(signal.isSignal()){
                Util.delay(10);
            }
            return true;
        }
        return false;
    }

    @Override
    public void end() {
        super.end(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.carModel.resetDistance();
    }

}
