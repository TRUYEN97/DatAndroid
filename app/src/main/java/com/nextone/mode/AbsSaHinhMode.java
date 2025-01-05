/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode;

import android.util.Log;

import com.nextone.common.ConstKey;
import com.nextone.common.YardConfig;
import com.nextone.contest.impCondition.ContainContestChecker;
import com.nextone.contest.impCondition.OnOffImp.CheckCM;
import com.nextone.contest.impCondition.OnOffImp.CheckRPM;
import com.nextone.contest.impCondition.timerCondition.TatalTimeOut;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.input.socket.YardModelHandle;
import com.nextone.model.input.UserModel;
import com.nextone.model.input.yard.YardRankModel;
import com.nextone.model.modelTest.process.ProcessModel;
import com.nextone.model.yardConfigMode.YardConfigModel;
import com.nextone.model.yardConfigMode.YardRankConfig;
import com.nextone.pretreatment.IKeyEvent;

import java.util.List;
import java.util.Map;

/**
 * @author Admin
 */
public abstract class AbsSaHinhMode<V extends AbsModeView> extends AbsTestMode<V> {

    protected static enum MODEL_RANK_NAME {
        RANK_B, RANK_C, RANK_D, RANK_E
    }

    ;
    protected final YardRankModel yardRankModel;
    protected final int speedLimit;
    protected final YardModelHandle yardModelHandle;
    protected final YardRankConfig yardRankConfig;

    public AbsSaHinhMode(V hinhView, int speedLimit, int timeOut,
                         MODEL_RANK_NAME modelRank, List<String> ranks, boolean isOnline) {
        super(hinhView, ConstKey.MODE_NAME.SA_HINH, ranks, isOnline);
        this.speedLimit = speedLimit;
        this.conditionHandle.addCondition(new TatalTimeOut(timeOut, processModel));
        this.conditionHandle.addCondition(new CheckCM());
        this.conditionHandle.addCondition(new CheckRPM());
        this.conditionHandle.addCondition(new ContainContestChecker(
                ConstKey.CONTEST_NAME.KET_THUC, false, processHandle));
        this.yardModelHandle = YardModelHandle.getInstance();
        YardConfigModel yardConfig = YardConfig.getInstance().getYardConfigModel();
        switch (modelRank) {
            case RANK_C:
                this.yardRankConfig = yardConfig.getC();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankC();
                break;
            case RANK_D:
                this.yardRankConfig = yardConfig.getD();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankD();
                break;
            case RANK_E:
                this.yardRankConfig = yardConfig.getE();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankE();
                break;
            default:
                this.yardRankConfig = yardConfig.getB();
                this.yardRankModel = this.yardModelHandle.getYardModel().getRankB();
                break;
        }
    }

    private boolean st = false;

    @Override
    protected boolean loopCheckCanTest() {
        if (this.carModel.isNt() && this.carModel.getStatus() == ConstKey.CAR_ST.STOP) {
            if (!st) {
                st = true;
                this.mCUSerialHandler.sendLedRedOn();
            }
            String id = this.processModel.getId();
            if (id == null || id.isBlank()) {
                return false;
            }
            createContestList();
            return true;
        } else if (st) {
            st = false;
            this.mCUSerialHandler.sendLedOff();
        }
        return false;
    }

    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> maps) {
        maps.put(ConstKey.KEY_BOARD.CONTEST.XP, (key) -> {
            if (this.processHandle.isTesting() || this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                return;
            }
            UserModel userModel = new UserModel();
            userModel.setId("0");
            userModel.setName("Chế độ luyện tập");
            userModel.setExamId("0");
            this.processHandle.setUserModel(userModel);
        });
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> maps) {
        maps.put(ConstKey.KEY_BOARD.CONTEST.KT, (key) -> {
            if (!this.processHandle.isTesting() || this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                return;
            }
            this.cancelTest();
        });
    }

    protected abstract void createContestList();

    @Override
    public void end() {
        try {
            super.end();
            int score = this.processModel.getScore();
            this.processModel.setContestsResult(score >= scoreSpec && !this.isCancel() ?
                    ProcessModel.PASS : ProcessModel.FAIL);
            updateLog();
            this.soundPlayer.sayResultTest(score, this.processHandle.isPass());
            if (this.processHandle.isPass()) {
                MCUSerialHandler.getInstance().sendLedGreenOn();
            } else {
                MCUSerialHandler.getInstance().sendLedRedOn();
            }
            endTest();
            this.processHandle.setTesting(false);
            this.processModel.setId("");
        } catch (Exception e) {
            Log.e(this.getClassName(), "end", e);
        }
    }

    @Override
    protected void endTest() {
    }


    @Override
    protected void contestDone() {
    }

}
