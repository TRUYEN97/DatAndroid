/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.modeController;

import android.util.Log;

import com.nextone.common.Util;
import com.nextone.controller.ProcessModelHandle;
import com.nextone.interfaces.IStarter;
import com.nextone.mode.AbsTestMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.Getter;

/**
 * @author Admin
 */
public class ModeHandle implements IStarter, Runnable {

    private final ProcessModelHandle processModelHandle;
    private final ContestRunner contestRunner;
    private final ExecutorService threadPool;
    @Getter
    private AbsTestMode testMode;
    @Getter
    private boolean running = false;
    private boolean stop = false;
    private final Object lock = new Object();
    private boolean paused = false;
    private Future<?> testFuture;

    public ModeHandle() {
        this.processModelHandle = ProcessModelHandle.getInstance();
        this.contestRunner = new ContestRunner();
        this.threadPool = Executors.newSingleThreadExecutor();
    }

    public boolean setTestMode(AbsTestMode testMode) {
        try {
            if (testMode == null) {
                return false;
            }
            this.testMode = testMode;
            this.testMode.setModeHandle(this);
            this.processModelHandle.setMode(testMode);
            this.contestRunner.setTestMode(testMode);
            return true;
        } catch (Exception e) {
            Log.e("ModeHandle", "setTestMode", e);
            return false;
        }
    }

    @Override
    public void run() {
        if (isRunning() || this.testMode == null) {
            return;
        }
        stop = false;
        while (!stop) {
            try {
                while (paused) {
                    Util.delay(500);
                }
                if (begin()) {
                    test();
                    end();
                }
            } catch (Exception e) {
                Log.e("ModelHandle", "run: ", e);
                this.running = false;
            }
        }
    }

    public void pause() {
        this.paused = true;
        if (this.testMode != null && this.testMode.isInBeginWhile()) {
            this.testMode.cancelTest();
        }
    }

    public void resume() {
        this.paused = false;
    }


    @Override
    public boolean isStarted() {
        return this.testFuture != null && !this.testFuture.isDone();
    }

    public boolean isTesting() {
        return this.contestRunner.isRunning() && running;
    }

    private boolean begin() {
        if (this.testMode.begin() && !this.paused && !this.stop) {
            this.running = true;
            return true;
        }
        return false;
    }

    private void test() {
        this.contestRunner.run();
    }

    private void end() {
        if (!running) {
            return;
        }
        this.running = false;
        this.processModelHandle.endTest();
        if (this.testMode != null) {
            this.testMode.end();
        }
    }

    @Override
    public void stop() {
        this.stop = true;
        stopTest();
        while (this.testFuture != null && !this.testFuture.isDone()) {
            this.testFuture.cancel(true);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
    }

    public void stopTest() {
        this.contestRunner.stop();
        if (this.testMode != null) {
            this.testMode.modeEndInit();
        }
    }

    @Override
    public void start() {
        if (isStarted()) {
            return;
        }
        if (this.testMode != null) {
            this.testMode.modeInit();
        }
        this.testFuture = this.threadPool.submit(this);
    }

    public boolean hasPause() {
        return this.paused;
    }
}
