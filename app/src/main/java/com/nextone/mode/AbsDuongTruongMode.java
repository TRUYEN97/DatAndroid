/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode;

import android.util.Log;

import com.nextone.common.ConstKey;
import com.nextone.contest.AbsContest;
import com.nextone.contest.impCondition.ContainContestChecker;
import com.nextone.contest.impCondition.OnOffImp.CheckCM;
import com.nextone.contest.impCondition.OnOffImp.CheckRPM;
import com.nextone.datandroid.fragment.modeView.AbsModeViewFragment;
import com.nextone.model.modelTest.process.ProcessModel;
import com.nextone.pretreatment.IKeyEvent;

import java.util.List;
import java.util.Map;

/**
 * @author Admin
 */
public abstract class AbsDuongTruongMode<V extends AbsModeViewFragment> extends AbsTestMode<V> {

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
        this.conditionHandle.addCondition(new CheckCM());
        this.conditionHandle.addCondition(new CheckRPM());
        this.conditionHandle.addCondition(new ContainContestChecker(
                ConstKey.CONTEST_NAME.KET_THUC, false, processHandle));
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
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
        events.put(ConstKey.ERR.SWERVED_OUT_OF_LANE, this.errorcodeHandle::addBaseErrorCode);
        events.put(ConstKey.ERR.IGNORED_INSTRUCTIONS, this.errorcodeHandle::addBaseErrorCode);
        events.put(ConstKey.ERR.VIOLATION_TRAFFIC_RULES, this.errorcodeHandle::addBaseErrorCode);
        events.put(ConstKey.ERR.HEAVY_SHAKING, this.errorcodeHandle::addBaseErrorCode);
        events.put(ConstKey.ERR.CAUSED_AN_ACCIDENT, this.errorcodeHandle::addBaseErrorCode);
    }

    @Override
    protected void endTest() {
        Log.i("Duong Truong mode", processHandle.toProcessModelJson().toString());
        this.hasXp = false;
        this.hasTs = false;
        this.hasGs = false;
        this.hasKt = false;
        this.runnable = false;
    }

    @Override
    protected boolean loopCheckCanTest() {
        String id = this.processModel.getId();
        runnable = true;
        if (id == null || id.isBlank()) {
            return false;
        }
        if (runnable && !contests.isEmpty()) {
            AbsContest contest = contests.peek();
            return contest != null && contest.getName().equals(ConstKey.CONTEST_NAME.XUAT_PHAT);
        }
        return false;
    }

    @Override
    public boolean begin() {
        if(!super.begin()){
            return false;
        }
        this.soundPlayer.begin();
        return true;
    }

    @Override
    public void end() {
        try {
            super.end();
            int score = this.processModel.getScore();
            this.processModel.setContestsResult(score >= scoreSpec && !this.isCancel()?
                    ProcessModel.PASS : ProcessModel.FAIL);
            updateLog();
            this.soundPlayer.sayResultTest(score, this.processHandle.isPass());
            endTest();
            this.processModel.setId("");
            this.processHandle.setTesting(false);
            if (isOnline) {
                this.soundPlayer.nextId();
            }
        } catch (Exception e) {
            Log.e(this.getClassName(), "end", e);
        }
    }

}
