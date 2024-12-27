package com.nextone.contest.impCondition;

import android.os.Handler;

import com.nextone.contest.AbsCondition;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbsTimerCondition extends AbsCondition {

    private Timer timer;
    private boolean timeout;
    private final boolean justOneTime;
    protected final ImportantError importantError;
    private int interval;

    public AbsTimerCondition(int spec, boolean justOneTime) {
        this(null, spec, justOneTime);
    }

    public AbsTimerCondition(ImportantError importantError, int spec, boolean justOneTime) {
        this.timeout = false;
        this.importantError = importantError;
        this.interval = spec * 1000;
        this.justOneTime = justOneTime;
    }

    public void setSpec(int spec) {
        this.interval = spec * 1000;
    }

    @Override
    public void start() {
        super.start();
        this.timeout = false;
        startTimer();
    }

    private void startTimer() {
        if (timer != null) {
            return;
        }
        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (signal()) {
                    timeout = true;
                    if (important) {
                        hasFail = true;
                        if (importantError != null) {
                            importantError.setIsImportantError();
                        }
                    }
                    action();
                    if (justOneTime) {
                        stop();
                    }
                } else {
                    timeout = false;
                    resetTimer();
                }
            }
        }, 0, interval); // Lặp lại mỗi giây
    }

    @Override
    public void stop() {
        super.stop();
        stopTimer();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    protected boolean checkCondition() {
        return stop || !timeout;
    }

    public void resetTimer() {
        stopTimer();
        startTimer();
    }

    protected abstract boolean signal();

    protected abstract void action();
}
