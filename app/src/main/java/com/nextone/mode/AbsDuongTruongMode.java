/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode;

import com.nextone.common.ConstKey;
import com.nextone.common.Util;
import com.nextone.contest.AbsContest;
import com.nextone.contest.impCondition.ContainContestChecker;
import com.nextone.contest.impCondition.OnOffImp.CheckCM;
import com.nextone.contest.impCondition.OnOffImp.CheckRPM;
import com.nextone.contest.impContest.dtB1.XuatPhatB1;
import com.nextone.model.modelTest.process.ProcessModel;
import com.nextone.pretreatment.IKeyEvent;
import com.nextone.pretreatment.KeyEventManagement;

import java.util.List;
import java.util.Map;

import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;
/**
 * @author Admin
 */
public abstract class AbsDuongTruongMode<V extends AbsModeView> extends AbsTestMode<V> {

    protected boolean runnable;
    protected boolean hasXp = false;
    protected boolean hasKt = false;
    protected boolean hasTs = false;
    protected boolean hasGs = false;
    protected String oldId;

    public AbsDuongTruongMode(V view, String name, List<String> ranks, boolean isOnline) {
        super(view, name, ranks, isOnline);
        this.runnable = false;
        this.oldId = "";
        this.conditionHandle.addConditon(new CheckCM());
        this.conditionHandle.addConditon(new CheckRPM());
        this.conditionHandle.addConditon(new ContainContestChecker(
                ConstKey.CONTEST_NAME.KET_THUC, false, processlHandle));
    }

    @Override
    protected void contestDone() {
    }

    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> maps) {
        maps.put(ConstKey.KEY_BOARD.SBD, (key) -> {
            if (!hasXp) {

            }
        });
        maps.put(ConstKey.KEY_BOARD.CONTEST.XP, (key) -> {
            if (hasXp || !runnable || this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                return;
            }
            addContest(new XuatPhatB1(ConstKey.CONTEST_NAME.XUAT_PHAT));
            hasXp = true;
        });
    }

    @Override
    protected void endTest() {
        System.out.println(processlHandle.toProcessModelJson());
        this.hasXp = false;
        this.hasTs = false;
        this.hasGs = false;
        this.hasKt = false;
        this.runnable = false;
    }

    @Override
    protected boolean loopCheckCanTest() {
        String id = this.processModel.getId();
        if (id == null || id.isBlank()) {
            Util.delay(3000);
            return false;
        }
//        if (isOnline) {
//            if (!runnable || !oldId.equals(id)) {
//                oldId = id;
//                switch (this.apiService.checkRunnable(id)) {
//                    case ApiService.START -> {
//                        runnable = true;
//                    }
//                    case ApiService.ID_INVALID -> {
//                        soundPlayer.userIdHasTest();
//                        runnable = false;
//                        Util.delay(10000);
//                    }
//                    default -> {
//                        runnable = false;
//                        Util.delay(1000);
//                    }
//                }
//            }
//        } else {
        runnable = true;
//        }
        if (runnable && !contests.isEmpty()) {
            AbsContest contest = contests.peek();
            if (contest != null && contest.getName().equals(ConstKey.CONTEST_NAME.XUAT_PHAT)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void begin() {
        super.begin(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        this.soundPlayer.begin();
    }

//    @Override
//    protected int upTestDataToServer() {
//        if (isOnline) {
//            this.timer.restart();
//            return super.upTestDataToServer(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
//        }
//        return ApiService.PASS;
//    }

    @Override
    public void end() {
        try {
            this.conditionHandle.stop();
            this.contests.clear();
            KeyEventManagement.getInstance().remove(prepareEventsPackage);
            KeyEventManagement.getInstance().remove(testEventsPackage);
            Util.delay(2000);
            int score = this.processModel.getScore();
            this.processModel.setContestsResult(score >= scoreSpec ? ProcessModel.PASS : ProcessModel.FAIL);
            updateLog();
            this.soundPlayer.sayResultTest(score, this.processlHandle.isPass());
//            if (isOnline) {
//                int rs = ApiService.FAIL;
//                for (int i = 0; i < 3; i++) {
//                    rs = upTestDataToServer();
//                    if (rs == ApiService.PASS) {
//                        break;
//                    }
//                }
//                if (rs == ApiService.DISCONNECT) {
//                    String id = processModel.getId();
//                    this.soundPlayer.sendlostConnect();
//                    this.fileTestService.saveBackupLog(id, processlHandle.toProcessModelJson().toString(),
//                            CameraRunner.getInstance().getImage());
//                } else if (rs == ApiService.FAIL) {
//                    this.soundPlayer.sendResultFailed();
//                }
//            }
            endTest();
            this.processModel.setId("");
            this.processlHandle.setTesting(false);
            this.soundPlayer.nextId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
