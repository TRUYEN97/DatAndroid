/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode.imp;

import com.nextone.common.ConstKey;
import com.nextone.contest.impContest.shB2.DoXeDoc;
import com.nextone.contest.impContest.shB2.DoXeNgang;
import com.nextone.contest.impContest.shB2.DungXe;
import com.nextone.contest.impContest.shB2.DungXeNgangDoc;
import com.nextone.contest.impContest.shB2.DuongS;
import com.nextone.contest.impContest.shB2.DuongTau;
import com.nextone.contest.impContest.shB2.KetThuc;
import com.nextone.contest.impContest.shB2.KhanCap;
import com.nextone.contest.impContest.shB2.NgaTu;
import com.nextone.contest.impContest.shB2.TangTocDuongThang;
import com.nextone.contest.impContest.shB2.VetBanhXe;
import com.nextone.contest.impContest.shB2.XuatPhat;
import com.nextone.datandroid.fragment.modeView.AbsModeViewFragment;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.mode.AbsSaHinhMode;
import com.nextone.model.input.UserModel;
import com.nextone.pretreatment.IKeyEvent;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Admin
 */
public class SH_OFF<V  extends AbsModeViewFragment> extends AbsSaHinhMode<V> {

    public SH_OFF(V hinhView) {
        super(hinhView, 24, 1800, MODEL_RANK_NAME.RANK_B, List.of("off"), false);
    }

    @Override
    protected boolean loopCheckCanTest() {
        if (!this.contests.isEmpty()) {
            UserModel userModel = new UserModel();
            userModel.setId("0");
            userModel.setExamId("0");
            this.processHandle.setUserModel(userModel);
            return true;
        }
        return false;
    }

    @Override
    protected void createContestList() {
    }

    @Override
    protected void createPrepareKeyEvents(Map<String, IKeyEvent> events) {
//        events.put(ConstKey.KEY_BOARD.SBD, (key) -> {
//            KeyBoardFrame boardFrame = new KeyBoardFrame();
//            boardFrame.setMaxNum(2);
//            String val = boardFrame.getValue("Chọn bài");
//            if (val == null || val.isBlank()) {
//                return;
//            }
//            carModel.setRemoteValue(val);
//        });
        super.createPrepareKeyEvents(events);
        events.put(ConstKey.KEY_BOARD.VK_1, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            addContest(new XuatPhat(speedLimit));
        });
        events.put(ConstKey.KEY_BOARD.VK_2, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new DungXe(yardRankConfig.getDungXeChoNg(), speedLimit));
        });
        events.put(ConstKey.KEY_BOARD.VK_3, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new DungXeNgangDoc(yardRankConfig.getDungXeNgangDoc(), speedLimit));
        });
        events.put(ConstKey.KEY_BOARD.VK_4, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new VetBanhXe(yardRankModel, yardRankConfig.getVetBanhXe(), speedLimit));
        });
        events.put(ConstKey.KEY_BOARD.VK_5, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new NgaTu(1, yardModelHandle.getYardModel(),
                    yardRankConfig.getNgaTu1(), speedLimit));
        });
        events.put(ConstKey.KEY_BOARD.VK_6, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new DuongS(yardRankModel, yardRankConfig.getDuongS(), speedLimit));
        });
        events.put(ConstKey.KEY_BOARD.VK_7, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new NgaTu(2, yardModelHandle.getYardModel(),
                    yardRankConfig.getNgaTu2(), speedLimit));
        });
        events.put(ConstKey.KEY_BOARD.VK_8, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new DoXeDoc(yardRankModel, yardRankConfig.getDoXeDoc(), speedLimit));
        });
        events.put(ConstKey.KEY_BOARD.VK_9, (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new NgaTu(3, yardModelHandle.getYardModel(),
                    yardRankConfig.getNgaTu3(), speedLimit));
        });
        events.put("10", (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new DuongTau(yardRankConfig.getDuongTau(), speedLimit));
        });
        events.put("11", (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new TangTocDuongThang(1, speedLimit, yardRankConfig.getTangToc()));
        });
        events.put("12", (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new DoXeNgang(yardRankModel, yardRankConfig.getDoXeNgang(), speedLimit));
        });
        events.put("13", (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new NgaTu(4, yardModelHandle.getYardModel(),
                    yardRankConfig.getNgaTu4(), speedLimit));
        });
        events.put("14", (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new KetThuc(speedLimit));
        });
        events.put("15", (key) -> {
            if (!contests.isEmpty()) {
                return;
            }
            MCUSerialHandler.getInstance().sendReset();
            this.carModel.setDistance(0);
            addContest(new KhanCap(0));
        });
    }


}
