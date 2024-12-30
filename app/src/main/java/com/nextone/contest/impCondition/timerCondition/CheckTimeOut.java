/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest.impCondition.timerCondition;

import com.nextone.contest.impCondition.AbsTimerCondition;
import com.nextone.contest.impCondition.ImportantError;

/**
 *
 * @author Admin
 */
public class CheckTimeOut extends AbsTimerCondition {

    private final String errName;
    private boolean pass = false;

    public CheckTimeOut(ImportantError importantError, int time, String errName) {
        this(importantError, time, errName, true);
    }

    public CheckTimeOut(ImportantError importantError, int time, String errName, boolean isImporttant) {
        super(importantError, time, true);
        setImportant(isImporttant);
        this.errName = errName;
    }

    public void setPass() {
        this.pass = true;
    }

    @Override
    protected boolean signal() {
        return !pass;
    }

    @Override
    protected void action() {
        if (errName == null) {
            return;
        }
        this.setErrorcode(errName);
    }

}
