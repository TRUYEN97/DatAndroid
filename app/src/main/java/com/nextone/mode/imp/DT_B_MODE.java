/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode.imp;

import com.nextone.common.ConstKey;
import com.nextone.contest.impCondition.timerCondition.CheckSo3;
import com.nextone.contest.impContest.dtB2.GiamToc;
import com.nextone.contest.impContest.dtB2.KetThuc;
import com.nextone.contest.impContest.dtB2.TangToc;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;
import com.nextone.mode.AbsDuongTruongMode;
import com.nextone.pretreatment.IKeyEvent;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class DT_B_MODE<V extends AbsModeView> extends AbsDuongTruongMode<V> {

    public DT_B_MODE(V truongView, boolean isOnline) {
        this(truongView, ConstKey.MODE_NAME.DUONG_TRUONG, List.of("B1", "B2", "C", "D", "E"), isOnline);
    }

    public DT_B_MODE(V truongView, String name, List<String> ranks, boolean isOnline) {
        super(truongView, name, ranks, isOnline);
        this.conditionHandle.addConditon(new CheckSo3());
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
        events.put(ConstKey.ERR.SWERVED_OUT_OF_LANE, this.errorcodeHandle::addBaseErrorCode);
        events.put(ConstKey.ERR.IGNORED_INSTRUCTIONS, this.errorcodeHandle::addBaseErrorCode);
        events.put(ConstKey.ERR.VIOLATION_TRAFFIC_RULES, this.errorcodeHandle::addBaseErrorCode);
        events.put(ConstKey.ERR.HEAVY_SHAKING, this.errorcodeHandle::addBaseErrorCode);
        events.put(ConstKey.ERR.CAUSED_AN_ACCIDENT, this.errorcodeHandle::addBaseErrorCode);
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
            addContest(new TangToc(ConstKey.CONTEST_NAME.TANG_TOC));
            hasTs = true;
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.GS, (key) -> {
            if (this.carModel.getGearBoxValue() <= 1) {
                return;
            }
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new GiamToc(ConstKey.CONTEST_NAME.GIAM_TOC));
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
            addContest(new KetThuc(ConstKey.CONTEST_NAME.KET_THUC));
            hasKt = true;
        });
    }
}
