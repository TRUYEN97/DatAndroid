/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.mode.imp;

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
import com.nextone.mode.AbsSaHinhMode;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Admin
 */
public class SH_D2_MODE<V  extends AbsModeViewFragment> extends AbsSaHinhMode<V> {

    public SH_D2_MODE(V hinhView, boolean isOnline) {
        super(hinhView, 24, 900, MODEL_RANK_NAME.RANK_D2, List.of("D2"), isOnline);
    }
    @Override
    protected void createContestList() {
        contests.clear();
        int rd = new Random().nextInt(3);
        System.out.println(rd);
        contests.add(new XuatPhat(speedLimit));
        contests.add(new DungXe(yardRankConfig.getDungXeChoNg(), speedLimit));
        contests.add(new DungXeNgangDoc(yardRankConfig.getDungXeNgangDoc(), speedLimit));
        contests.add(new VetBanhXe(yardRankModel, yardRankConfig.getVetBanhXe(), speedLimit));
        if (rd == 0) {
            contests.add(new KhanCap(20));
        }
        contests.add(new NgaTu(1, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu1(), speedLimit));
        contests.add(new DuongS(yardRankModel, yardRankConfig.getDuongS(), speedLimit));
        contests.add(new NgaTu(2, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu2(), speedLimit));
        contests.add(new DoXeNgang(yardRankModel, yardRankConfig.getDoXeNgang(), speedLimit));
        contests.add(new NgaTu(3, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu3(), speedLimit));
        if (rd == 1) {
            contests.add(new KhanCap(40));
        }
        contests.add(new DuongTau(yardRankConfig.getDuongTau(), speedLimit));
        contests.add(new TangTocDuongThang(1, speedLimit, yardRankConfig.getTangToc()));
        if (rd == 2) {
            contests.add(new KhanCap(30));
        }
        contests.add(new NgaTu(4, yardModelHandle.getYardModel(),
                yardRankConfig.getNgaTu4(), speedLimit));
        contests.add(new KetThuc(speedLimit));
    }

}
