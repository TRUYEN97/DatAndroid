/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode.imp;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.timerCondition.CheckSo3;
import com.nextone.contest.impContest.dtB2.XuatPhat;
import com.nextone.contest.impContest.dtB2.GiamToc;
import com.nextone.contest.impContest.dtB2.KetThuc;
import com.nextone.contest.impContest.dtB2.TangToc;
import com.nextone.datandroid.fragment.modeView.AbsModeViewFragment;
import com.nextone.mode.AbsDuongTruongMode;
import com.nextone.model.input.UserModel;
import com.nextone.pretreatment.IKeyEvent;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class DT_B_MODE<V extends AbsModeViewFragment> extends AbsDuongTruongMode<V> {

    public DT_B_MODE(V truongView, boolean isOnline) {
        this(truongView, ConstKey.MODE_NAME.DUONG_TRUONG, List.of( "B", "C1", "C", "D1", "D2", "D"), isOnline);
    }

    public DT_B_MODE(V truongView, String name, List<String> ranks, boolean isOnline) {
        super(truongView, name, ranks, isOnline);
        this.conditionHandle.addCondition(new CheckSo3());
    }

    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> maps) {
        super.createPrepareKeyEvents(maps);
        maps.put(ConstKey.KEY_BOARD.CONTEST.XP, (key) -> {
            if (hasXp || !runnable || this.carModel.getStatus() != ConstKey.CAR_ST.STOP) {
                return;
            }
            UserModel userModel = new UserModel();
            userModel.setId("0");
            userModel.setName("Chế độ luyện tập");
            userModel.setExamId("0");
            this.processHandle.setUserModel(userModel);
            addContest(new XuatPhat());
            hasXp = true;
        });
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
        events.put(ConstKey.KEY_BOARD.CONTEST.TS, (key) -> {
            if (this.carModel.getGearBoxValue() >= 5) {
                return;
            }
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new TangToc());
            hasTs = true;
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.GS, (key) -> {
            if(this.carModel.getSpeed() < 5){
                return;
            }
            if (this.carModel.getGearBoxValue() <= 1) {
                return;
            }
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new GiamToc());
            hasGs = true;
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.KT, (key) -> {
            String id = processModel.getId();
            int distance = id == null || id.equals("0") || !isOnline ? 200 : 2000;
            if (this.carModel.getDistance() < distance) {
                return;
            }
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || !hasTs || !hasGs || hasKt) {
                return;
            }
            addContest(new KetThuc());
            hasKt = true;
        });
    }
}
