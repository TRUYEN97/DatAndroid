/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.modeController;

import com.nextone.common.Util;
import com.nextone.contest.AbsContest;
import com.nextone.mode.AbsTestMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Admin
 */
public class ContestRunner implements Runnable {

    private final ExecutorService threadPool;
    private AbsTestMode testMode;
    private boolean testDone = false;

    public ContestRunner() {
        this.threadPool = Executors.newSingleThreadExecutor();
    }

    public boolean setTestMode(AbsTestMode testMode) {
        if (testMode == null) {
            return false;
        }
        this.testMode = testMode;
        return true;
    }

    @Override
    public void run() {
        this.testDone = false;
        AbsContest currContest;
        while (this.testMode != null && !this.testDone) {
            currContest = this.testMode.peekContests();
            if (this.testMode == null || testMode.isTestConditionsFailed()) {
                stop();
                break;
            }
            if (currContest == null) {
                Util.delay(200);
                continue;
            }
            runPart(currContest.begin(), currContest);
            if (!testDone) {
                runPart(currContest.test(), currContest);
            }
            currContest.end();
            if (this.testMode != null) {
                this.testMode.endContest();
                this.testMode.pollContests();
            }
        }
    }

    private void runPart(Runnable runnable, AbsContest currContest) {
        Future<?> future = this.threadPool.submit(runnable);
        while (!future.isDone() && !this.testDone) {
            if (currContest.isTestConditionsFailed()) {
                stop(currContest);
            }
            if (testMode == null || testMode.isTestConditionsFailed()) {
                stop(currContest);
            }
        }
        while (!future.isDone()) {
            future.cancel(true);
            if (currContest.isTestConditionsFailed()) {
                stop(currContest);
            }
            if (testMode == null || testMode.isTestConditionsFailed()) {
                stop(currContest);
            }
            Util.delay(100);
        }
    }

    private void stop(AbsContest currContest) {
        if (currContest == null) return;
        currContest.stop();
        stop();
    }

    public void stop() {
        this.testDone = true;
        if (testMode != null) {
            testMode.clearContest();
        }
    }

    public boolean isRunning() {
        return !testDone;
    }

}
