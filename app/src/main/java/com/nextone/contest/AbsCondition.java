/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.contest;

import com.nextone.controller.ErrorcodeHandle;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.input.CarModel;
import com.nextone.model.modelTest.contest.ContestDataModel;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
public abstract class AbsCondition {

    protected final CarModel carModel;
    @Setter
    protected ContestDataModel contestDataModel;
    private final ErrorcodeHandle codeHandle;
    @Getter
    @Setter
    protected boolean important;
    @Getter
    protected boolean running;
    protected boolean hasFail;

    public AbsCondition() {
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.codeHandle = ErrorcodeHandle.getInstance();
        this.important = false;
        this.running = false;
        this.hasFail = false;
    }

    public void start() {
        this.running = true;
        this.hasFail = false;
    }

    public void stop() {
        this.running = false;
    }

    public boolean checkPassed() {
        if (hasFail) {
            return false;
        }
        if (!running) {
            return true;
        }
        if (!checkCondition()) {
            if (important) {
                hasFail = true;
            }
            return false;
        }
        return true;
    }

    protected abstract boolean checkCondition();

    protected void setErrorcode(String errorcode) {
        if (contestDataModel == null) {
            this.codeHandle.addBaseErrorCode(errorcode);
        } else {
            this.codeHandle.addContestErrorCode(errorcode, contestDataModel);
        }
    }

    public boolean isTestConditionsFailed() {
        return !checkPassed() && important;
    }
}
