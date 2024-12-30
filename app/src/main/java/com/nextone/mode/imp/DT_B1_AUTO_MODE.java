/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode.imp;

import com.nextone.common.ConstKey;
import com.nextone.contest.impContest.dtB1.GiamTocB1;
import com.nextone.contest.impContest.dtB1.KetThucB1;
import com.nextone.contest.impContest.dtB1.TangTocB1;
import com.nextone.contest.impContest.dtB1.XuatPhatB1;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;
import com.nextone.mode.AbsDuongTruongMode;
import com.nextone.model.input.UserModel;
import com.nextone.pretreatment.IKeyEvent;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class DT_B1_AUTO_MODE<V extends AbsModeView> extends AbsDuongTruongMode<V> {

    public DT_B1_AUTO_MODE(V truongView, boolean isOnline) {
        this(truongView, ConstKey.MODE_NAME.DUONG_TRUONG, List.of("B1"), isOnline);
    }

    public DT_B1_AUTO_MODE(V truongView, String name, List<String> ranks, boolean isOnline) {
        super(truongView, name, ranks, isOnline);
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
            addContest(new XuatPhatB1());
            hasXp = true;
        });
    }

    @Override
    protected void createTestKeyEvents(Map<String, IKeyEvent> events) {
        events.put(ConstKey.KEY_BOARD.CONTEST.TS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new TangTocB1());
            hasTs = true;
        });
        events.put(ConstKey.KEY_BOARD.CONTEST.GS, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            if (!hasXp || hasKt) {
                return;
            }
            addContest(new GiamTocB1());
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
            addContest(new KetThucB1());
            hasKt = true;
        });
    }
}
