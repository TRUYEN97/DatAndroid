/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.model.modelTest;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */

@Getter
public class Errorcode {
    @Setter
    protected String errKey;
    @Setter
    protected int errPoint;
    @Setter
    protected String errName;
    @Getter
    private int soundId = -1;

    public Errorcode() {
    }


    public Errorcode(String errKey, int errPoint, String errName, int soundId) {
        this.errKey = errKey;
        this.errPoint = errPoint;
        this.errName = errName;
        this.soundId = soundId;
    }


}
