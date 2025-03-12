/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest;

import android.util.Log;

import com.nextone.common.Util;
import com.nextone.contest.impCondition.ImportantError;
import com.nextone.contest.impCondition.timerCondition.TimeOutContest;
import com.nextone.controller.CheckConditionHandle;
import com.nextone.controller.ContestModelHandle;
import com.nextone.controller.ErrorcodeHandle;
import com.nextone.interfaces.IgetTime;
import com.nextone.controller.ProcessModelHandle;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.modelTest.contest.ContestDataModel;
import com.nextone.model.input.CarModel;
import com.nextone.output.SoundPlayer;

import lombok.Getter;

public abstract class AbsContest implements IgetTime {

    public static final int WAIT = 0;
    public static final int RUNNING = 1;
    public static final int DONE = 2;
    private final ProcessModelHandle processlHandle;
    protected final MCUSerialHandler mcuSerialHandler;
    protected final CarModel carModel;
    @Getter
    protected final ContestDataModel contestModel;
    protected final SoundPlayer soundPlayer;
    protected final ContestModelHandle contestModelHandle;
    protected final ErrorcodeHandle errorcodeHandle;
    protected final boolean playSoundWhenIn;
    protected final boolean playSoundWhenOut;
    protected final CheckConditionHandle conditionBeginHandle;
    protected final CheckConditionHandle conditionRunningHandle;
    @Getter
    protected final int nameSoundId;
    protected final ImportantError importantError;
    @Getter
    protected int status;
    protected boolean stop;
    protected int timeOut;
    private final boolean sayContestName;

    public AbsContest(String name, int nameSoundId,
            boolean sayContestName, boolean soundIn,
            boolean soundOut, int timeout) {
        this.timeOut = Math.max(timeout, 0);
        this.sayContestName = sayContestName;
        this.nameSoundId = nameSoundId;
        this.mcuSerialHandler = MCUSerialHandler.getInstance();
        this.processlHandle = ProcessModelHandle.getInstance();
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.playSoundWhenIn = soundIn;
        this.playSoundWhenOut = soundOut;
        this.contestModel = new ContestDataModel(name);
        this.contestModelHandle = new ContestModelHandle(contestModel);
        this.conditionBeginHandle = new CheckConditionHandle(this.contestModel);
        this.conditionRunningHandle = new CheckConditionHandle(this.contestModel);
        TimeOutContest timeOutContest = new TimeOutContest(this);
        this.importantError = new ImportantError();
        this.conditionBeginHandle.addCondition(importantError);
        this.conditionRunningHandle.addCondition(importantError);
        this.conditionRunningHandle.addCondition(timeOutContest);
        this.status = DONE;
        this.stop = false;
    }

    protected double getDeltaDistance(double oldDistance) {
        return this.carModel.getDistance() - oldDistance;
    }

    protected void addErrorCode(String errorKey) {
        this.errorcodeHandle.addContestErrorCode(errorKey, contestModel);
    }

    public boolean isTestConditionsFailed() {
        boolean st = this.conditionBeginHandle.isTestConditionsFailed();
        if (this.conditionRunningHandle.isTestConditionsFailed()) {
            st = true;
        }
        return st;
    }

    public final String getName() {
        return this.contestModel.getContestName();
    }

    protected abstract void init();

    protected abstract boolean loop();

    protected abstract boolean isIntoContest();

    public void stop() {
        this.stop = true;
        this.importantError.setIsImportantError();
        Util.delay(500);
    }

    @Override
    public long getTestTime() {
        return this.contestModelHandle.getTestTime();
    }

    public Runnable begin() {
        return () -> {
            try {
                boolean isFisrtContest =  this.processlHandle.getProcessModel().getContests().isEmpty();
                this.contestModelHandle.reset();
                this.status = WAIT;
                this.stop = false;
                this.processlHandle.setContest(this);
                this.processlHandle.addContestModel(contestModel);
                init();
                this.conditionBeginHandle.start();
                if (this.sayContestName) {
                    this.soundPlayer.contestName(nameSoundId, isFisrtContest);
                }
                while (!isIntoContest() && !stop) {
                    Util.delay(10);
                }
                this.conditionRunningHandle.start();
                if (this.playSoundWhenIn) {
                    this.soundPlayer.startContest();
                }
                this.contestModelHandle.start();
                this.status = RUNNING;
            } catch (Exception e) {
                Log.e("AbsContest", "begin",e);
            }
        };
    }

    public Runnable test() {
        return () -> {
            try {
                status = RUNNING;
                while (!stop && !loop()) {
                    Util.delay(10);
                }
                status = DONE;
            } catch (Exception e) {
                Log.e("AbsContest", "test",e);
            }
        };
    }

    public void end() {
        try {
            this.conditionBeginHandle.stop();
            this.conditionBeginHandle.clear();
            this.conditionRunningHandle.stop();
            this.conditionRunningHandle.clear();
            status = DONE;
            this.contestModelHandle.end();
            if (this.playSoundWhenOut) {
                this.soundPlayer.endContest();
            }
            this.processlHandle.setContest(null);
        } catch (Exception e) {
            Log.e("AbsContest", "end", e);
        }
    }

    public int getTimeout() {
        return timeOut;
    }

}
