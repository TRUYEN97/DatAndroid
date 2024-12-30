package com.nextone.datandroid.customLayout.impConstrainLayout.modeView;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;

import androidx.annotation.LayoutRes;

import com.nextone.datandroid.customLayout.AbsCustomConstraintLayout;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.interfaces.IStart;

public abstract class AbsModeView extends AbsCustomConstraintLayout implements IStart {
    private final Handler handler;
    private int intervalMs = 100;
    private boolean running;

    private Runnable runnable;

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

    @Override
    protected void init(int resource, boolean attachToRoot) {
        super.init(resource, attachToRoot);
        this.runnable = ()->{
            this.running =true;
            this.updateUI();
            this.handler.postDelayed(this.runnable,intervalMs);
        };
    }

    public void setIntervalMs(int intervalMs) {
        this.intervalMs = Math.max(intervalMs, 100);
    }

    protected abstract void updateUI();

    @Override
    public void start() {
        this.handler.postDelayed(runnable, intervalMs);
    }

    @Override
    public void stop() {
        this.handler.removeCallbacks(runnable);
        this.running =false;
    }

    @Override
    public boolean isStarted() {
        return this.running;
    }
}
