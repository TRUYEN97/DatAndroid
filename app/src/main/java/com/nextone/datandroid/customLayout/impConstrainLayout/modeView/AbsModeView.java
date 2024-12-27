package com.nextone.datandroid.customLayout.impConstrainLayout.modeView;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.annotation.LayoutRes;

import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.interfaces.IStart;

import android.os.Handler;

public abstract class AbsModeView extends AbsCustomConstraintLayout implements IStart {
    private final Handler handler;
    private int intervalMs = 100;
    private boolean running;

    protected AbsModeView(Context context, @LayoutRes int resource, boolean attachToRoot) {
        super(context);
        this.handler = new Handler(Looper.getMainLooper());
        this.init(resource, attachToRoot);
    }

    protected AbsModeView(Context context, AttributeSet attrs, @LayoutRes int resource, boolean attachToRoot) {
        super(context, attrs);
        this.handler = new Handler();
        init(resource, attachToRoot);
    }

    protected AbsModeView(Context context, AttributeSet attrs, int defStyleAttr, @LayoutRes int resource, boolean attachToRoot) {
        super(context, attrs, defStyleAttr);
        this.handler = new Handler();
        init(resource, attachToRoot);
    }

    public void setIntervalMs(int intervalMs) {
        this.intervalMs = Math.max(intervalMs, 100);
    }

    protected abstract void updateUI();

    @Override
    public void start() {
        this.handler.postDelayed(this::updateUI, intervalMs);
        this.running =true;
    }

    @Override
    public void stop() {
        this.handler.removeCallbacks(this::updateUI);
        this.running =false;
    }

    @Override
    public boolean isStarted() {
        return this.running;
    }
}
