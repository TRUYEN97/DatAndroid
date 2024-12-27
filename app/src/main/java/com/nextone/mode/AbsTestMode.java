/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode;

import com.nextone.common.ConstKey;
import com.nextone.common.Setting;
import com.nextone.common.Util;
import com.nextone.common.communication.IgetName;
import com.nextone.contest.AbsContest;
import com.nextone.controller.CheckConditionHandle;
import com.nextone.controller.ErrorcodeHandle;
import com.nextone.controller.ProcessModelHandle;
import com.nextone.controller.modeController.ModeHandle;
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
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;

/**
 *
 * @author Admin
 * @param <V> view
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
    protected final ProcessModelHandle processlHandle;
    protected final Queue<AbsContest> contests;
    protected final KeyEventsPackage prepareEventsPackage;
    protected final KeyEventsPackage testEventsPackage;
    protected final ErrorcodeHandle errorcodeHandle;
    protected final CheckConditionHandle conditionHandle;
    protected final MCUSerialHandler mCUSerialHandler;
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
        this.processlHandle = ProcessModelHandle.getInstance();
        this.processModel = this.processlHandle.getProcessModel();
        this.soundPlayer = SoundPlayer.getInstance();
        this.contests = new LinkedList<>();
        this.errorcodeHandle = ErrorcodeHandle.getInstance();
        this.prepareEventsPackage = initPrepareKeyEventPackage();
        this.testEventsPackage = initTestKeyEventPackage(prepareEventsPackage);
        this.conditionHandle = new CheckConditionHandle();
        this.mCUSerialHandler = MCUSerialHandler.getInstance();
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

    public void begin() {
        try {
            this.cancel = false;
            this.processlHandle.setTesting(false);
            KeyEventManagement.getInstance().addKeyEventBackAge(prepareEventsPackage);
            this.errorcodeHandle.clear();
            this.processlHandle.resetModel();
            this.mCUSerialHandler.sendReset();
            this.carModel.setDistance(0);
            this.mCUSerialHandler.sendLedOff();
            while (!loopCheckCanTest() && !this.cancel) {
                Util.delay(1000);
            }
            KeyEventManagement.getInstance().remove(prepareEventsPackage);
            if (!cancel) {
                this.errorcodeHandle.clear();
                this.processlHandle.resetModel();
                this.mCUSerialHandler.sendReset();
                this.carModel.setDistance(0);
                this.processlHandle.startTest();
                KeyEventManagement.getInstance().addKeyEventBackAge(testEventsPackage);
                this.mCUSerialHandler.sendLedGreenOn();
                this.conditionHandle.start();
                updateLog();
//                if (isOnline) {
//                    new Thread(() -> {
//                        TestStatusLogger.getInstance().setTestStatus(
//                                this.processModel.getId(),
//                                this.processModel.getExamId());
//                        upTestDataToServer();
//                    }).start();
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endContest() {
        try {
            this.processlHandle.update();
            updateLog();
//            if (isOnline) {
//                new Thread(() -> {
//                    upTestDataToServer();
//                }).start();
//            }
            contestDone();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateLog() {
//        this.fileTestService.saveLogJson(this.processModel.getId(),
//                this.processlHandle.toProcessModelJson().toString());
//        this.fileTestService.saveImg(this.processModel.getId(),
//                CameraRunner.getInstance().getImage());
    }

//    protected int upTestDataToServer() {
//        if (cancel || !isOnline) {
//            return ApiService.PASS;
//        }
//        String id = processModel.getId();
//        if (id == null || id.isBlank() || id.equals("0")) {
//            return ApiService.PASS;
//        }
//        return this.apiService.sendData(CameraRunner.getInstance().getImage(),
//                processlHandle.toProcessModelJson());
//    }

    public abstract void end();

    private KeyEventsPackage initPrepareKeyEventPackage() {
        Map<String, IKeyEvent> events = new HashMap<>();
//        events.put(ConstKey.KEY_BOARD.SHOW_ERROR, (key) -> {
//            if (this.showErrorcode.isVisible()) {
//                this.showErrorcode.dispose();
//            } else {
//                this.showErrorcode.display();
//            }
//        });
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

    public boolean isTestCondisionsFailed() {
        if (this.conditionHandle.isTestCondisionsFailed()) {
            return true;
        }
        String id = this.processModel.getId();
        int score = getScoreSpec();
        if (id != null && id.equals("0")) {
            score = 20;
        }
        return this.processlHandle.getProcessModel().getScore() < score;
    }

    public void cancelTest() {
        this.cancel = true;
        this.modeEndInit();
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
//        if (this.view != null) {
//            this.view.start();
//        }
        if (this.name != null) {
            if (this.name.equalsIgnoreCase(ConstKey.MODE_NAME.DUONG_TRUONG)) {
                Setting.getInstance().setDuongTruongMode();
            } else {
                Setting.getInstance().setSaHinhMode();
            }
        }
    }

    public void modeEndInit() {
//        if (this.view != null) {
//            this.view.stop();
//        }
    }

    public void clearContest() {
        this.contests.clear();
    }

}
