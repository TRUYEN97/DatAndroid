package com.nextone.contest.impCondition;

import com.nextone.common.ConstKey;
import com.nextone.common.timer.WaitTime.Class.TimeS;
import com.nextone.contest.AbsCondition;
public class CheckWheelCrossedLine extends AbsCondition {

    private final TimeS timer;
    private final ConditionActionListener actionListener;


    public CheckWheelCrossedLine(int spec, ConditionActionListener actionListener) {
        this.actionListener = actionListener;
        this.timer = new TimeS(spec);
    }

    @Override
    protected boolean checkCondition() {
        if (actionListener.activate()) {
            if (running && !this.timer.onTime()) {
                this.timer.update();
                setErrorcode(ConstKey.ERR.WHEEL_CROSSED_LINE);
            }
            return false;
        }
        return true;
    }
}
