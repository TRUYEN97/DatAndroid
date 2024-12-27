package com.nextone.contest.impCondition;

import android.os.Handler;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsCondition;

public class CheckWheelCrossedLine extends AbsCondition {

    private final Handler handler;
    private final ConditionActionListener actionListener;
    private final Runnable timerRunnable;
    private final int interval; // Interval in milliseconds

    private boolean running;

    public CheckWheelCrossedLine(int spec, ConditionActionListener actionListener) {
        this.actionListener = actionListener;
        this.interval = spec * 1000; // Convert seconds to milliseconds
        this.handler = new Handler();

        this.timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (stop) {
                    stop();
                    return;
                }
                setErrorcode(ConstKey.ERR.WHEEL_CROSSED_LINE);
            }
        };
    }

    @Override
    public void start() {
        super.start();
        // Ensure the timer starts when the condition requires it
        if (!stop && !running) {
            startTimer();
            setErrorcode(ConstKey.ERR.WHEEL_CROSSED_LINE);
        }
    }

    @Override
    public void stop() {
        stopTimer();
        super.stop();
    }

    @Override
    protected boolean checkCondition() {
        if (actionListener.activate()) {
            if (!stop && !running) {
                startTimer();
                setErrorcode(ConstKey.ERR.WHEEL_CROSSED_LINE);
            }
            return false;
        } else {
            stopTimer();
        }
        return true;
    }

    private void startTimer() {
        handler.postDelayed(timerRunnable, interval);
        this.running = true;
    }

    private void stopTimer() {
        handler.removeCallbacks(timerRunnable);
        this.running = false;
    }
}
