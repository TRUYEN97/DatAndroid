package com.nextone.datandroid.customLayout.grid;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridLayout;

import androidx.annotation.ColorRes;

import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.interfaces.IStart;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.input.CarModel;

import lombok.Getter;
import lombok.Setter;

public abstract class AbsGridLayoutView extends GridLayout implements IStart {

    protected final CarModel carModel;
    protected final GradientDrawable background;
    protected final Handler handlerTimer;
    protected Runnable runnable;
    @Getter
    @Setter
    protected int timeUpdate = 100;
    private boolean running;

    public AbsGridLayoutView(Context context) {
        super(context);
        this.handlerTimer = new Handler();
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.background = new GradientDrawable();
    }

    public AbsGridLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.handlerTimer = new Handler();
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.background = new GradientDrawable();
    }

    public AbsGridLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.handlerTimer = new Handler();
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.background = new GradientDrawable();
    }

    public AbsGridLayoutView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.handlerTimer = new Handler();
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.background = new GradientDrawable();
    }

    protected void init(int columnCount, int rowCount, int layoutId, int layoutBackground, int color) {
        setColumnCount(columnCount);
        setRowCount(rowCount);
        LayoutInflater.from(getContext()).inflate(layoutId, this, true);
        this.runnable = () -> {
            this.update();
            this.handlerTimer.postDelayed(this.runnable, timeUpdate);
        };
        this.background.setColor(getColor(color));
        this.background.setCornerRadii(new float[]{
                25f, 25f,
                25f, 25f,
                25f, 25f,
                25f, 25f
        });
        findViewById(layoutBackground).setBackground(this.background);
    }

    public void setBgColor(int color) {
        this.background.setColor(color);
    }

    public void setBgColorResource(int color) {
        this.background.setColor(getColor(color));
    }

    protected int getColor(@ColorRes int onColor) {
        return getResources().getColor(onColor, getContext().getTheme());
    }


    @Override
    public boolean isStarted() {
        return this.running;
    }

    protected abstract void update();

    public void start() {
        if (this.running) return;
        this.handlerTimer.postDelayed(this.runnable, timeUpdate);
        this.running = true;
    }

    public void stop() {
        this.handlerTimer.removeCallbacks(this.runnable);
        this.running = false;
    }
}
