package com.nextone.controller;

import android.annotation.SuppressLint;

import com.nextone.controller.modeController.ModeManagement;
import com.nextone.datandroid.customLayout.impConstrainLayout.BaseModeLayout;
import com.nextone.datandroid.customLayout.impConstrainLayout.ModeChooserView;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.DuongTruongView;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.SaHinhView;
import com.nextone.mode.AbsTestMode;
import com.nextone.mode.imp.DT_B1_AUTO_MODE;
import com.nextone.mode.imp.DT_B_MODE;
import com.nextone.mode.imp.SH_B1_AUTO_MODE;
import com.nextone.mode.imp.SH_B_MODE;
import com.nextone.mode.imp.SH_C_MODE;
import com.nextone.mode.imp.SH_D_MODE;
import com.nextone.mode.imp.SH_E_MODE;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModeChooser {
    private ModeChooserView modeChooserView;
    private final ModeManagement modeManager;

    private final ExecutorService executorService;
    private BaseModeLayout baseModeLayout;

    public ModeChooser() {
        this.modeManager = ModeManagement.getInstance();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void setFrameLayout(BaseModeLayout baseModeLayout) {
        this.baseModeLayout = baseModeLayout;
        this.modeChooserView = new ModeChooserView();
        DuongTruongView dtView = new DuongTruongView();
        SaHinhView shView = new SaHinhView();
        this.modeChooserView.setBtDtOnclick(v -> {
            this.baseModeLayout.addFragment(dtView, "modeDuongTruong", true);
            executorService.execute(() -> {
                modeManager.updateMode(new DT_B_MODE<DuongTruongView>(dtView, false));
            });
        });
        this.modeChooserView.setBtDTB1Onclick(v -> {
            this.baseModeLayout.addFragment(dtView, "modeDuongTruong", true);
            executorService.execute(() -> {
                modeManager.updateMode(new DT_B1_AUTO_MODE<DuongTruongView>(dtView, false));
            });
        });
        this.modeChooserView.setBtShOnclick(v -> {
            this.baseModeLayout.addFragment(shView, "modeSaHinh", true);
            executorService.execute(() -> {
                this.modeManager.updateMode(new SH_B_MODE<SaHinhView>(shView, false));
            });
        });
        this.modeChooserView.setBtShB1Onclick(v -> {
            this.baseModeLayout.addFragment(shView, "modeSaHinh", true);
            executorService.execute(() -> {
                this.modeManager.updateMode(new SH_B1_AUTO_MODE<SaHinhView>(shView, false));
            });
        });
        this.modeChooserView.setBtShCOnclick(v -> {
            this.baseModeLayout.addFragment(shView, "modeSaHinh", true);
            executorService.execute(() -> {
                this.modeManager.updateMode(new SH_C_MODE<SaHinhView>(shView, false));
            });
        });
        this.modeChooserView.setBtShDOnclick(v -> {
            this.baseModeLayout.addFragment(shView, "modeSaHinh", true);
            executorService.execute(() -> {
                this.modeManager.updateMode(new SH_D_MODE<SaHinhView>(shView, false));
            });
        });
        this.modeChooserView.setBtShEOnclick(v -> {
            this.baseModeLayout.addFragment(shView, "modeSaHinh", true);
            executorService.execute(() -> {
                this.modeManager.updateMode(new SH_E_MODE<SaHinhView>(shView, false));
            });
        });
    }
    
    @SuppressLint("SetTextI18n")
    public void show() {
        if (this.baseModeLayout != null && this.isChangeModeAble()) {
            this.modeManager.stopCurrentMode();
            this.baseModeLayout.addFragment(this.modeChooserView, "tabModeChooser", true);
        }
    }

    private boolean isChangeModeAble() {
        AbsTestMode currentMode = this.modeManager.getCurrentMode();
        return currentMode == null || !currentMode.getModeHandle().isTesting();
    }
}
