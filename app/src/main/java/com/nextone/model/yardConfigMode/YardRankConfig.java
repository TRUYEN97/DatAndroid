/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.yardConfigMode;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 *
 * @author Admin
 */
@Getter
public class YardRankConfig {

    private final ContestConfig dungXeChoNg;
    private final ContestConfig dungXeNgangDoc;
    private final List<ContestConfig> vetBanhXe;
    private final List<ContestConfig> duongVuongGoc;
    private final ContestConfig ngaTu1;
    private final ContestConfig ngaTu2;
    private final ContestConfig ngaTu3;
    private final ContestConfig ngaTu4;
    private final ContestConfig tangToc;
    private final List<ContestConfig> duongS;
    private final List<ContestConfig> doXeDoc;
    private final List<ContestConfig> doXeNgang;
    private final ContestConfig duongTau;

    public YardRankConfig() {
        this.dungXeChoNg = new ContestConfig(4, 2.3, 1, 50);
        this.dungXeNgangDoc = new ContestConfig(20, 2.3, 1, 50);
        this.ngaTu1 = new ContestConfig(25, 2.3, 5, 100);
        this.ngaTu2 = new ContestConfig(25, 2.3, 5, 56);
        this.ngaTu3 = new ContestConfig(30, 2.3, 100, 210);
        this.ngaTu4 = new ContestConfig(30, 2.3, 5, 40);
        this.duongTau = new ContestConfig(4, 2.3, 5, 210);
        this.tangToc = new ContestConfig(0, 0, 5, 70);
        this.vetBanhXe = new ArrayList<>();
        this.duongVuongGoc = new ArrayList<>();
        this.doXeDoc = new ArrayList<>();
        this.duongS = new ArrayList<>();
        this.doXeNgang = new ArrayList<>();
        this.vetBanhXe.add(new ContestConfig(20, 12, 3, 100, 14));
        this.duongVuongGoc.add(new ContestConfig(0, 0, 0, 0, 12));
        this.vetBanhXe.add(new ContestConfig(20, 12, 100, 120, 11));
        this.duongVuongGoc.add(new ContestConfig(0, 0, 0, 0, 6));
        this.duongS.add(new ContestConfig(10, 0, 5, 70, 7));
        this.doXeDoc.add(new ContestConfig(10, 0, 5, 18, 10));
        this.doXeDoc.add(new ContestConfig(10, 0, 18, 36, 13));
        this.doXeDoc.add(new ContestConfig(10, 0, 36, 56, 16));
        this.doXeNgang.add(new ContestConfig(3, 0, 5, 115, 3));
        this.doXeNgang.add(new ContestConfig(3, 0, 115, 150, 15));
    }

}
