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
        this.dungXeChoNg = new ContestConfig(3.3, 2.5, 0, 50);
        this.dungXeNgangDoc = new ContestConfig(20, 2.5, 0, 50);
        this.ngaTu1 = new ContestConfig(25, 2.5, 0, 55);
        this.ngaTu2 = new ContestConfig(25, 2.5, 0, 35);
        this.ngaTu3 = new ContestConfig(30, 2.5, 100, 180);
        this.ngaTu4 = new ContestConfig(30, 2.5, 0, 40);
        this.duongTau = new ContestConfig(4, 2.5, 0, 100);
        this.tangToc = new ContestConfig(0, 0, 0, 70);
        this.vetBanhXe = new ArrayList<>();
        this.duongVuongGoc = new ArrayList<>();
        this.doXeDoc = new ArrayList<>();
        this.duongS = new ArrayList<>();
        this.doXeNgang = new ArrayList<>();
        this.vetBanhXe.add(new ContestConfig(20, 12, 0, 88, 0));
        this.duongVuongGoc.add(new ContestConfig(0, 0, 0, 0, 1));
        this.vetBanhXe.add(new ContestConfig(20, 12, 88, 120, 2));
        this.duongVuongGoc.add(new ContestConfig(0, 0, 0, 0, 3));
        this.duongS.add(new ContestConfig(5, 0, 0, 62, 4));
        this.duongS.add(new ContestConfig(5, 0, 62, 74, 5));
        this.doXeDoc.add(new ContestConfig(5, 0, 0, 18, 6));
        this.doXeDoc.add(new ContestConfig(5, 0, 18, 36, 7));
        this.doXeDoc.add(new ContestConfig(5, 0, 36, 56, 8));
        this.doXeNgang.add(new ContestConfig(3, 0, 0, 110, 9));
        this.doXeNgang.add(new ContestConfig(3, 0, 110, 120, 10));
    }

}
