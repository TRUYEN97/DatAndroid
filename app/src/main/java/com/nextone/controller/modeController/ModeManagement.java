/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.modeController;

import com.nextone.mode.AbsTestMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 */
public class ModeManagement {

    public static String DEFAULT_MODE;
    //    private final ViewMain viewMain;
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

    public List<AbsTestMode> getModes() {
        return modes;
    }

    public boolean isMode(String modeName, String rank) {
        AbsTestMode currMode = this.modeHandle.getTestMode();
        return currMode != null && currMode.isMode(modeName, rank);
    }

    public boolean updateMode(String modeName, String rank) {
        for (AbsTestMode mode : modes) {
            if (mode != null && mode.isMode(modeName, rank)) {
                return updateMode(mode);
            }
        }
        return false;
    }

    public boolean updateMode(AbsTestMode testMode) {
        AbsTestMode currMode = this.modeHandle.getTestMode();
        if (currMode != null && currMode.equals(testMode)) {
            return true;
        }
        try {
            this.modeHandle.setWait(true);
            if (this.modeHandle.isRunning()) {
                this.modeHandle.stop();
            }
            if (this.modeHandle.setTestMode(testMode)) {
                this.modeHandle.start();
//                CameraRunner.getInstance().setTestModeView(testMode.getView());
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            this.modeHandle.setWait(false);
        }
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

}
