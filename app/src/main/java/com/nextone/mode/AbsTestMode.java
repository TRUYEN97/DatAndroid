/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode;

import android.util.Log;

import com.nextone.common.ConstKey;
import com.nextone.common.Setting;
import com.nextone.common.Util;
import com.nextone.common.communication.IgetName;
import com.nextone.contest.AbsContest;
import com.nextone.contest.impCondition.ImportantError;
import com.nextone.controller.CheckConditionHandle;
import com.nextone.controller.ErrorcodeHandle;
import com.nextone.controller.ProcessModelHandle;
import com.nextone.controller.modeController.ModeHandle;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.input.CarModel;
import com.nextone.model.modelTest.process.ProcessModel;
import com.nextone.output.SoundPlayer;
import com.nextone.pretreatment.IKeyEvent;
import com.nextone.pretreatment.KeyEventManagement;
import com.nextone.pretreatment.KeyEventsPackage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import lombok.Getter;
import lombok.Setter;

/**
 * @param <V> view
 * @author Admin
 */
@Getter
@Setter
public abstract class AbsTestMode<V extends AbsModeView> implements IgetName {

    protected final V view;
    protected final String name;
    protected final String fullName;
    protected final List<String> ranks;
    protected final int scoreSpec;
    protected final CarModel carModel;
    protected final ProcessModel processModel;
    protected final SoundPlayer soundPlayer;
    protected final ProcessModelHandle processHandle;
    protected final Queue<AbsContest> contests;
    protected final KeyEventsPackage prepareEventsPackage;
    protected final KeyEventsPackage testEventsPackage;
    protected final ErrorcodeHandle errorcodeHandle;
    protected final CheckConditionHandle conditionHandle;
    protected final MCUSerialHandler mCUSerialHandler;
    protected final ImportantError importantError;
    private ModeHandle modeHandle;
    private boolean cancel;
    protected final boolean isOnline;

    protected AbsTestMode(V view, String name, List<String> ranks, boolean isOnline) {
        this(view, name, 80, ranks, isOnline);
    }

    @Override
    public String getName() {
        return fullName;
    }

    protected AbsTestMode(V view, String name, int scoreSpec, List<String> ranks, boolean isOnline) {
        this.view = view;
        this.name = name;
        this.isOnline = isOnline;
        this.fullName = creareFullName(ranks);
        this.ranks = ranks;
        this.cancel = false;
        this.scoreSpec = scoreSpec;
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.processHandle = ProcessModelHandle.getInstance();
        this.processModel = this.processHandle.getProcessModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.contests = new LinkedList<>();
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.prepareEventsPackage = initPrepareKeyEventPackage();
        this.testEventsPackage = initTestKeyEventPackage(prepareEventsPackage);
        this.conditionHandle = new CheckConditionHandle();
        this.mCUSerialHandler = MCUSerialHandler.getInstance();
        this.importantError = new ImportantError();
        this.conditionHandle.addCondition(importantError);
    }

    private String creareFullName(List<String> ranks) {
        StringBuilder builder = new StringBuilder(name);
        builder.append(" (");
        for (String rank : ranks) {
            builder.append(" ").append(rank);
        }
        builder.append(" )");
        if (!isOnline) {
            builder.append("F");
        }
        return builder.toString();
    }

    protected abstract boolean loopCheckCanTest();

    protected abstract void contestDone();

    protected abstract void endTest();

    protected abstract void createPrepareKeyEvents(Map<String, IKeyEvent> events);

    protected abstract void createTestKeyEvents(Map<String, IKeyEvent> events);

    protected void addContest(AbsContest contest) {
        if (contest == null) {
            return;
        }
        this.contests.add(contest);
    }

    public String getClassName() {
        return getClass().getSimpleName();
    }

    public AbsContest peekContests() {
        return this.contests.peek();
    }

    public AbsContest pollContests() {
        return this.contests.poll();
    }

    public boolean begin() {
        this.importantError.reset();
        this.cancel = false;
        this.processHandle.setTesting(false);
        KeyEventManagement.getInstance().addKeyEventBackAge(prepareEventsPackage);
        this.errorcodeHandle.clear();
        this.processHandle.resetModel();
        this.mCUSerialHandler.sendReset();
        this.carModel.setDistance(0);
        this.mCUSerialHandler.sendLedOff();
        while (!loopCheckCanTest()) {
            Util.delay(1000);
            if (cancel) {
                return false;
            }
        }
        KeyEventManagement.getInstance().remove(prepareEventsPackage);
        this.errorcodeHandle.clear();
        this.processHandle.resetModel();
        this.mCUSerialHandler.sendReset();
        this.carModel.setDistance(0);
        this.processHandle.startTest();
        KeyEventManagement.getInstance().addKeyEventBackAge(testEventsPackage);
        this.mCUSerialHandler.sendLedGreenOn();
        this.conditionHandle.start();
        updateLog();
        return !cancel;
    }

    public boolean isRunning() {
        return this.modeHandle.isRunning();
    }

    public void endContest() {
        try {
            this.processHandle.update();
            updateLog();
            contestDone();
        } catch (Exception e) {
            Log.e(this.getClassName(), "endContest", e);
        }
    }

    protected void updateLog() {
//        this.fileTestService.saveLogJson(this.processModel.getId(),
//                this.processlHandle.toProcessModelJson().toString());
//        this.fileTestService.saveImg(this.processModel.getId(),
//                CameraRunner.getInstance().getImage());
    }

    public void end() {
        this.conditionHandle.stop();
        this.contests.clear();
        KeyEventManagement.getInstance().remove(prepareEventsPackage);
        KeyEventManagement.getInstance().remove(testEventsPackage);
        Util.delay(2000);
    }

    private KeyEventsPackage initPrepareKeyEventPackage() {
        Map<String, IKeyEvent> events = new HashMap<>();
        KeyEventsPackage epg = new KeyEventsPackage(name + "Prepare");
        createPrepareKeyEvents(events);
        epg.putEvents(events);
        return epg;
    }

    private KeyEventsPackage initTestKeyEventPackage(KeyEventsPackage prepareEventsPackage) {
        Map<String, IKeyEvent> events = new HashMap<>();
        KeyEventsPackage epg = new KeyEventsPackage(name + "Testing", true);
        createTestKeyEvents(events);
        epg.putEvents(events);
        epg.merge(prepareEventsPackage);
        return epg;
    }

    public boolean isTestConditionsFailed() {
        if (this.conditionHandle.isTestConditionsFailed()) {
            return true;
        }
        String id = this.processModel.getId();
        int score = getScoreSpec();
        if (id != null && id.equals("0")) {
            score = 20;
        }
        return this.processHandle.getProcessModel().getScore() < score;
    }

    public void cancelTest() {
        this.importantError.setIsImportantError();
        this.cancel = true;
        this.conditionHandle.stop();
        this.contests.clear();
        KeyEventManagement.getInstance().remove(prepareEventsPackage);
        KeyEventManagement.getInstance().remove(testEventsPackage);
    }

    public boolean isMode(String modeName, String rank) {
        if (modeName == null || rank == null) {
            return false;
        }
        if (this.name.equalsIgnoreCase(modeName)) {
            for (String rk : ranks) {
                if (rk != null && rk.equalsIgnoreCase(rank)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void modeInit() {
        if (this.view != null) {
            this.view.start();
        }
        if (this.name != null) {
            if (this.name.equalsIgnoreCase(ConstKey.MODE_NAME.DUONG_TRUONG)) {
                Setting.getInstance().setDuongTruongMode();
            } else {
                Setting.getInstance().setSaHinhMode();
            }
        }
    }

    public void modeEndInit() {
        cancelTest();
        if (this.view != null) {
            this.view.stop();
        }
    }

    public void clearContest() {
        this.contests.clear();
    }

}
