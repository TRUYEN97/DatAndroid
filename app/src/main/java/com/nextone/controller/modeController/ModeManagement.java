/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.modeController;

import android.util.Log;

import com.nextone.mode.AbsTestMode;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * @author Admin
 */
public class ModeManagement {

    public static String DEFAULT_MODE;
    @Getter
    private final List<AbsTestMode> modes;
    private final ModeHandle modeHandle;
    private static ModeManagement instance;

    private ModeManagement() {
        this.modes = new ArrayList<>();
        this.modeHandle = new ModeHandle();
    }

    public static ModeManagement getInstance() {
        ModeManagement ins = instance;
        if (ins == null) {
            synchronized (ModeManagement.class) {
                ins = instance;
                if (ins == null) {
                    instance = ins = new ModeManagement();
                }
            }
        }
        return ins;
    }

    public boolean isOffLineMode() {
        AbsTestMode testMode = this.modeHandle.getTestMode();
        return testMode == null || !testMode.isOnline();
    }

    public boolean isMode(String modeName, String rank) {
        AbsTestMode currMode = this.modeHandle.getTestMode();
        return currMode != null && currMode.isMode(modeName, rank);
    }

    public boolean updateMode(AbsTestMode testMode) {
        if (testMode == null) return false;
        AbsTestMode currMode = this.modeHandle.getTestMode();
        if (currMode != null && currMode.equals(testMode)) {

        }
        try {
            this.modeHandle.stop();
            if (this.modeHandle.setTestMode(testMode)) {
                this.modeHandle.start();
                return true;
            }
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "updateMode", e);
        }
        return false;
    }

    public AbsTestMode getCurrentMode() {
        return this.modeHandle.getTestMode();
    }

    public void addMode(AbsTestMode absTestMode) {
        if (absTestMode == null) {
            return;
        }
        if (this.modes.isEmpty() && !this.modeHandle.isStarted()) {
            DEFAULT_MODE = absTestMode.getName();
        }
        this.modes.add(absTestMode);
    }

    public void stopCurrentMode() {
        if (this.modeHandle.getTestMode() != null && !this.modeHandle.getTestMode().isRunning()) {
            this.modeHandle.stop();
        }
    }
}
