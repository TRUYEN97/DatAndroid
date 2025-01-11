/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.modeController;

import android.util.Log;

import com.nextone.datandroid.fragment.modeView.AbsModeViewFragment;
import com.nextone.datandroid.fragment.modeView.DuongTruongViewFragment;
import com.nextone.datandroid.fragment.modeView.SaHinhViewFragment;
import com.nextone.mode.AbsTestMode;
import com.nextone.mode.imp.DT_B1_AUTO_MODE;
import com.nextone.mode.imp.DT_B_MODE;
import com.nextone.mode.imp.SH_B1_AUTO_MODE;
import com.nextone.mode.imp.SH_B_MODE;
import com.nextone.mode.imp.SH_C_MODE;
import com.nextone.mode.imp.SH_D_MODE;
import com.nextone.mode.imp.SH_E_MODE;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;

/**
 * @author Admin
 */
public class ModeManagement {

    @Getter
    private final List<ModeModel> modes;
    private final Map<Class<? extends AbsModeViewFragment>, AbsModeViewFragment> viewFragmentMap;
    private final Map<ModeModel, AbsTestMode> testModeMap;
    private final ModeHandle modeHandle;
    private static ModeManagement instance;

    @Getter
    public static class ModeModel {
        private final Class<? extends AbsTestMode> modeClass;
        private final Class<? extends AbsModeViewFragment> viewClass;
        private final String name;
        private final boolean isOnline;

        private ModeModel(Class<? extends AbsTestMode> modeClass,
                          Class<? extends AbsModeViewFragment> viewClass,
                          boolean isOnline, String name) {
            this.modeClass = modeClass;
            this.viewClass = viewClass;
            this.isOnline = isOnline;
            this.name = name;
        }
    }

    private ModeManagement() {
        this.modes = new ArrayList<>();
        this.viewFragmentMap = new HashMap<>();
        this.testModeMap = new HashMap<>();
        this.modeHandle = new ModeHandle();
        initMode();
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

    public void addMode(Class<? extends AbsTestMode> modeClass,
                        Class<? extends AbsModeViewFragment> viewClass, boolean isOnline, String name) {
        if (modeClass == null) return;
        this.modes.add(new ModeModel(modeClass, viewClass, isOnline, name));
    }

    private void initMode() {
        this.addMode(DT_B_MODE.class,
                DuongTruongViewFragment.class, false, "Đường trường\n(Số sàn)");
        this.addMode(DT_B1_AUTO_MODE.class,
                DuongTruongViewFragment.class, false, "Đường trường\n(Số tự động)");
        this.addMode(SH_B_MODE.class,
                SaHinhViewFragment.class, false, "Sa hình - Hạng B\n(Số sàn)");
        this.addMode(SH_B1_AUTO_MODE.class,
                SaHinhViewFragment.class, false, "Sa hình\n(Số tự động)");
        this.addMode(SH_C_MODE.class,
                SaHinhViewFragment.class, false, "Sa hình - Hạng C");
        this.addMode(SH_D_MODE.class,
                SaHinhViewFragment.class, false, "Sa hình - Hạng D");
        this.addMode(SH_E_MODE.class,
                SaHinhViewFragment.class, false, "Sa hình - Hạng E");
    }


    public boolean isOffLineMode() {
        AbsTestMode<? extends AbsModeViewFragment> testMode = this.modeHandle.getTestMode();
        return testMode == null || !testMode.isOnline();
    }

    public boolean isMode(String modeName, String rank) {
        AbsTestMode<? extends AbsModeViewFragment> currMode = this.modeHandle.getTestMode();
        return currMode != null && currMode.isMode(modeName, rank);
    }

    public boolean startMode(ModeModel modeModel) {
        if (modeModel == null) return false;
        try {
            AbsTestMode testMode = this.testModeMap.get(modeModel);
            AbsModeViewFragment viewFragment = null;
            if (testMode == null) {
                viewFragment = this.viewFragmentMap.get(modeModel.viewClass);
                if (viewFragment == null) {
                    viewFragment = modeModel.viewClass.getDeclaredConstructor().newInstance();
                    this.viewFragmentMap.put(modeModel.viewClass, viewFragment);
                }
                Constructor<?> testModeConstructor
                        = modeModel.modeClass.getDeclaredConstructor(AbsModeViewFragment.class, boolean.class);
                testMode = (AbsTestMode) testModeConstructor.newInstance(viewFragment, modeModel.isOnline);
                this.testModeMap.put(modeModel, testMode);
            }
            return startMode(testMode);
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "StartMode:", e);
            return false;
        }
    }


    public boolean startMode(AbsTestMode testMode) {
        if (testMode == null) return false;
        AbsTestMode currMode = this.modeHandle.getTestMode();
        if (currMode != null && currMode.equals(testMode)) {
            return true;
        }
        try {
            this.modeHandle.stop();
            if (this.modeHandle.setTestMode(testMode)) {
                this.modeHandle.start();
                return true;
            }
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "startMode2:", e);
        }
        return false;
    }

    public AbsTestMode getCurrentMode() {
        return this.modeHandle.getTestMode();
    }

    public void stopCurrentMode() {
        if (this.modeHandle.getTestMode() != null && !this.modeHandle.getTestMode().isRunning()) {
            this.modeHandle.stop();
        }
    }
}
