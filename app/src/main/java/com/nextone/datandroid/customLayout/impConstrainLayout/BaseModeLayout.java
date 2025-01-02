package com.nextone.datandroid.customLayout.impConstrainLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;

import com.nextone.controller.ProcessModelHandle;
import com.nextone.controller.SettingTab;
import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.interfaces.IStart;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.input.socket.YardModelHandle;

import lombok.Getter;

public class BaseModeLayout extends AbsCustomConstraintLayout implements IStart {


    private MyImageLabel socketAlam;
    private MyImageLabel sensorAlam;

    private Handler handler;
    @Getter
    private FrameLayout frameLayout;

    @Getter
    private Button btMode;

    @Getter
    private PreviewView previewView;

    private SettingTab settingTab;


    public BaseModeLayout(@NonNull Context context) {
        super(context);
        init(R.layout.mode, true);
    }

    public BaseModeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(R.layout.mode, true);
    }

    public BaseModeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(R.layout.mode, true);
    }

    private void update() {
        this.socketAlam.setStatus(YardModelHandle.getInstance().isConnect());
        this.sensorAlam.setStatus(MCUSerialHandler.getInstance().isConnect());
        this.handler.postDelayed(this::update, 100);
    }

    public void setSettingTab(SettingTab settingTab) {
        this.settingTab = settingTab;
        findViewById(R.id.btSetting).setOnClickListener(v -> {
            if (settingTab != null && !ProcessModelHandle.getInstance().isTesting()) {
                addView(settingTab.getView());
            }
        });
    }

    private boolean running = false;

    public synchronized void start() {
        if (this.running) return;
        this.running = true;
        this.handler.postDelayed(this::update, 100);
    }

    @Override
    public void stop() {
        this.handler.removeCallbacks(this::update);
        running = false;
    }

    @Override
    public boolean isStarted() {
        return running;
    }

    @SuppressLint("SetTextI18n")
    protected void init(int resource, boolean attachToRoot) {
        super.init(resource, attachToRoot);
        this.handler = new Handler();
        this.socketAlam = findViewById(R.id.socketAlam);
        this.socketAlam.setTextLabel("Server");
        this.socketAlam.setTextLabelColor(Color.BLACK);
        this.sensorAlam = findViewById(R.id.sensorAlam);
        this.sensorAlam.setTextLabel("Cảm biến");
        this.sensorAlam.setTextLabelColor(Color.BLACK);
        this.btMode = findViewById(R.id.btMode);
        this.btMode.setText("Chọn chế độ");
        this.frameLayout = findViewById(R.id.boardModeView);
        this.previewView = findViewById(R.id.camera);
        MyImageLabel lbSetting = findViewById(R.id.btSetting);
        lbSetting.setImage(R.drawable.setting_icon);
        lbSetting.setTextLabel("Cài đặt");
        lbSetting.setTextLabelColor(Color.BLACK);
        lbSetting.setOnColorResource(android.R.color.holo_blue_dark);
        lbSetting.setOffColorResource(android.R.color.holo_blue_light);
        lbSetting.setButtonMode(true);
        GradientDrawable background = new GradientDrawable();
        background.setColor(getResources().getColor(android.R.color.darker_gray, getContext().getTheme()));
        background.setCornerRadii(new float[]{
                25f, 25f,
                25f, 25f,
                25f, 25f,
                25f, 25f
        });
        findViewById(R.id.informationView).setBackground(background);
    }

    @Override
    public void addView(View view) {
        try {
            if (view == null) {
                return;
            }
            this.frameLayout.removeAllViews();
            this.frameLayout.addView(view);
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "addView", e);
        }
    }

}
