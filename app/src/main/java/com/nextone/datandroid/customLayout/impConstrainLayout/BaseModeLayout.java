package com.nextone.datandroid.customLayout.impConstrainLayout;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.nextone.datandroid.R;
import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.AbsModeView;
import com.nextone.datandroid.customLayout.impConstrainLayout.widget.MyImageLabel;
import com.nextone.input.socket.YardModelHandle;

public class BaseModeLayout extends AbsCustomConstraintLayout {

    private AbsModeView currModeView;

    private ModeChooser modeChooser;

    private MyImageLabel socketAlam;
    private MyImageLabel sensorAlam;

    private YardModelHandle yardModelHandle;
    private boolean hasShowChooser = false;

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

    protected void init(int resource, boolean attachToRoot) {
        super.init(resource, attachToRoot);
        this.socketAlam = findViewById(R.id.socketAlam);
        this.socketAlam.setTextLabel("Server");
        this.sensorAlam = findViewById(R.id.sensorAlam);
        this.sensorAlam.setTextLabel("Cảm biến");
        GradientDrawable background;
//        background = (GradientDrawable) findViewById(R.id.boardMain).getBackground();
//        background.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_orange_light));
        background = (GradientDrawable) findViewById(R.id.informationView).getBackground();
        background.setColor(ContextCompat.getColor(getContext(), android.R.color.darker_gray));
    }

    public void init() {
        if (this.modeChooser == null) {
            this.modeChooser = new ModeChooser(getContext());
            this.modeChooser.setBaseModeLayout(this);
        }
        this.yardModelHandle = YardModelHandle.getInstance();
        this.yardModelHandle.start();
        showChooser();
    }

    public PreviewView getCameraView() {
        return findViewById(R.id.camera);
    }

    private void showChooser() {
        if (!hasShowChooser) {
            hasShowChooser = true;
            setModeView(modeChooser);
        }
    }

    protected void updateUI(AbsModeView modeView) {
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
            this.currModeView.start();
            this.setModeView(modeView);
            hasShowChooser = false;
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "updateUI", e);
        }
    }

    private void setModeView(View modeView) {
        FrameLayout frameLayout = findViewById(R.id.boardModeView);
        frameLayout.removeAllViews();
        frameLayout.addView(modeView);
    }

}
