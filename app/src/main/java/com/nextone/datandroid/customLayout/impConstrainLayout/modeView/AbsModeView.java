package com.nextone.datandroid.customLayout.impConstrainLayout.modeView;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.LayoutRes;

import com.nextone.datandroid.AbsFragment;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.interfaces.IStart;

public abstract class AbsModeView extends AbsFragment implements IStart {
    private final Handler handler;
    private int intervalMs = 100;
    private boolean running;

    private Runnable runnable;

    protected AbsModeView(@LayoutRes int resource) {
        super(resource);
        this.handler = new Handler(Looper.getMainLooper());
        this.running = false;
        this.runnable = () -> {
            this.updateUI();
            this.handler.postDelayed(this.runnable, intervalMs);
        };
    }
    @Override
    protected void onInitCreateView(View view) {

    }

    @Override
    protected void onInitViewCreated(View view) {
        this.running = false;
    }

    public void setIntervalMs(int intervalMs) {
        this.intervalMs = Math.max(intervalMs, 100);
    }

    protected abstract void updateUI();

    @Override
    public void start() {
        if (this.running) return;
        this.handler.postDelayed(runnable, intervalMs);
        this.running = true;
    }

    @Override
    public void stop() {
        this.handler.removeCallbacks(runnable);
        this.running = false;
    }

    @Override
    public boolean isStarted() {
        return this.running;
    }

    public abstract void showModeName(String fullName) ;
}
