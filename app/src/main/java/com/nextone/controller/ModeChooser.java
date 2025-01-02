package com.nextone.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;

import com.nextone.controller.modeController.ModeManagement;
import com.nextone.datandroid.customLayout.impConstrainLayout.BaseModeLayout;
import com.nextone.datandroid.customLayout.impConstrainLayout.ModeChooserView;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;
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

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Button btMode;
    private Handler handler;
    private BaseModeLayout baseModeLayout;

    public ModeChooser() {
        this.modeManager = ModeManagement.getInstance();
    }

    public void setFrameLayout(BaseModeLayout baseModeLayout) {
        Context context;
        if (baseModeLayout == null || (context = baseModeLayout.getContext()) == null) {
            this.baseModeLayout = null;
            this.modeChooserView = null;
            return;
        }
        this.baseModeLayout = baseModeLayout;
        this.modeChooserView = new ModeChooserView(context);
        this.handler = new Handler(Looper.getMainLooper());
        DuongTruongView dtView = new DuongTruongView(context);
        SaHinhView shView = new SaHinhView(context);
        this.modeChooserView.setBtDtOnclick(v -> {
            updateUI(dtView);
            executorService.execute(() -> {
                if (modeManager.updateMode(new DT_B_MODE<DuongTruongView>(dtView, false))) {
                    handler.post(() -> {
                        btMode.setText(modeManager.getCurrentMode().getFullName());
                    });
                }
            });
        });
        this.modeChooserView.setBtDTB1Onclick(v -> {
            updateUI(dtView);
            executorService.execute(() -> {
                if (modeManager.updateMode(new DT_B1_AUTO_MODE<DuongTruongView>(dtView, false))) {
                    handler.post(() -> {
                        btMode.setText(modeManager.getCurrentMode().getFullName());
                    });
                }
            });
        });
        this.modeChooserView.setBtShOnclick(v -> {
            updateUI(shView);
            executorService.execute(() -> {
                if (this.modeManager.updateMode(new SH_B_MODE<SaHinhView>(shView, false))) {
                    handler.post(() -> {
                        btMode.setText(modeManager.getCurrentMode().getFullName());
                    });
                }
            });
        });
        this.modeChooserView.setBtShB1Onclick(v -> {
            updateUI(shView);
            executorService.execute(() -> {
                if (this.modeManager.updateMode(new SH_B1_AUTO_MODE<SaHinhView>(shView, false))) {
                    handler.post(() -> {
                        btMode.setText(modeManager.getCurrentMode().getFullName());
                    });
                }
            });
        });
        this.modeChooserView.setBtShCOnclick(v -> {
            updateUI(shView);
            executorService.execute(() -> {
                if (this.modeManager.updateMode(new SH_C_MODE<SaHinhView>(shView, false))) {
                    handler.post(() -> {
                        btMode.setText(modeManager.getCurrentMode().getFullName());
                    });
                }
            });
        });
        this.modeChooserView.setBtShDOnclick(v -> {
            updateUI(shView);
            executorService.execute(() -> {
                if (this.modeManager.updateMode(new SH_D_MODE<SaHinhView>(shView, false))) {
                    handler.post(() -> {
                        btMode.setText(modeManager.getCurrentMode().getFullName());
                    });
                }
            });
        });
        this.modeChooserView.setBtShEOnclick(v -> {
            updateUI(shView);
            executorService.execute(() -> {
                if (this.modeManager.updateMode(new SH_E_MODE<SaHinhView>(shView, false))) {
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
        if (this.baseModeLayout != null && this.isChangeModeAble()) {
            this.modeManager.stopCurrentMode();
            this.baseModeLayout.addView(this.modeChooserView);
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
            this.baseModeLayout.addView(modeView);
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "updateUI", e);
        }
    }

}
