/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.yardConfigMode;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Setter
@Getter
public class YardConfigModel {
    private YardRankConfig b;
    private YardRankConfig c1;
    private YardRankConfig c;
    private YardRankConfig d1;
    private YardRankConfig d2;
    private YardRankConfig d;

    public YardConfigModel() {
        this.b = new YardRankConfig();
        this.c1 = new YardRankConfig();
        this.c = new YardRankConfig();
        this.d1 = new YardRankConfig();
        this.d2 = new YardRankConfig();
        this.d = new YardRankConfig();
    }

}
