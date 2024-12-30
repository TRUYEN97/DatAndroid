package com.nextone.contest.impCondition;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsCondition;

import java.util.Timer;
import java.util.TimerTask;

public class CheckWheelCrossedLine extends AbsCondition {

    private Timer timer;
    private final ConditionActionListener actionListener;
    private final int interval; // Interval in milliseconds


    public CheckWheelCrossedLine(int spec, ConditionActionListener actionListener) {
        this.actionListener = actionListener;
        this.interval = spec * 1000; // Convert seconds to milliseconds
    }

    private synchronized void startTimer() {
        if (timer != null) {
            return;
        }
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (stop) {
                    stop();
                    return;
                }
                setErrorcode(ConstKey.ERR.WHEEL_CROSSED_LINE);
            }
        }, 0, interval); // Lặp lại mỗi giây
    }


    @Override
    public void stop() {
        super.stop();
        stopTimer();
    }

    @Override
    protected boolean checkCondition() {
        if (actionListener.activate()) {
            if (!stop && timer == null) {
                startTimer();
            }
            return false;
        } else {
            stopTimer();
        }
        return true;
    }

    private synchronized void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
