package com.nextone.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;

import com.nextone.controller.modeController.ModeManagement;
import com.nextone.datandroid.customLayout.impConstrainLayout.ModeChooserView;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.DuongTruongView;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.SaHinhView;
import com.nextone.mode.AbsTestMode;
import com.nextone.mode.imp.DT_B_MODE;
import com.nextone.mode.imp.SH_B_MODE;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ModeChooser {
    private ModeChooserView modeChooserView;
    private final ModeManagement modeManager;
    private boolean hasShowChooser = false;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private AbsModeView currModeView;
    private Button btMode;
    private Handler handler;
    private FrameLayout frameLayout;

    public ModeChooser() {
        this.modeManager = ModeManagement.getInstance();
        this.hasShowChooser = false;
    }

    public void setFrameLayout(FrameLayout frameLayout) {
        Context context;
        if (frameLayout == null || (context = frameLayout.getContext()) == null) {
            this.frameLayout = null;
            this.modeChooserView = null;
            return;
        }
        this.frameLayout = frameLayout;
        this.modeChooserView = new ModeChooserView(context);
        this.handler = new Handler(Looper.getMainLooper());
        this.modeChooserView.setBtDtOnclick(v -> {
            DuongTruongView view = new DuongTruongView(context);
            updateUI(view);
            executorService.execute(() -> {
                if (modeManager.updateMode(new DT_B_MODE<DuongTruongView>(view, false))) {
                    handler.post(() -> {
                        btMode.setText(modeManager.getCurrentMode().getFullName());
                    });
                }
            });
        });
        this.modeChooserView.setBtShOnclick(v -> {
            SaHinhView view = new SaHinhView(context);
            updateUI(view);
            executorService.execute(() -> {
                if (this.modeManager.updateMode(new SH_B_MODE<SaHinhView>(view, false))) {
                    handler.post(() -> {
                        btMode.setText(modeManager.getCurrentMode().getFullName());
                    });
                }
            });
        });
    }


    public void setBtMode(Button btMode) {
        if (btMode != null) {
            btMode.setOnClickListener(v -> {
                show();
            });
        }
        this.btMode = btMode;
    }
    
    @SuppressLint("SetTextI18n")
    public void show() {
        if (this.frameLayout != null && !this.hasShowChooser && this.isChangeModeAble()) {
            this.modeManager.stopCurrentMode();
            this.frameLayout.removeAllViews();
            this.frameLayout.addView(this.modeChooserView);
            this.hasShowChooser = true;
            if (this.btMode != null) {
                this.btMode.setText("Chọn chế độ");
            }
        }
    }

    private boolean isChangeModeAble() {
        AbsTestMode currentMode = this.modeManager.getCurrentMode();
        return currentMode == null || !currentMode.getModeHandle().isTesting();
    }

    private void updateUI(AbsModeView modeView) {
        try {
            if (modeView == null) {
                return;
            }
            if (this.currModeView != null) {
                if (!this.currModeView.equals(modeView)) {
                    this.currModeView.stop();
                } else {
                    return;
                }
            }
            this.currModeView = modeView;
            this.frameLayout.removeAllViews();
            this.frameLayout.addView(modeView);
            this.hasShowChooser = false;
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "updateUI", e);
        }
    }

}
