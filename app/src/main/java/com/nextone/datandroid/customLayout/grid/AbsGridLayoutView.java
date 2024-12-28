package com.nextone.datandroid.customLayout.grid;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import com.nextone.model.input.CarModel;
import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.datandroid.customLayout.impConstrainLayout.modeView.interfaces.IStart;

import lombok.Getter;
import lombok.Setter;

public abstract class AbsGridLayoutView extends GridLayout implements IStart{

    protected final CarModel carModel;
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
    }

    public AbsGridLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.handlerTimer = new Handler();
        this.carModel = MCUSerialHandler.getInstance().getModel();
    }

    public AbsGridLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.handlerTimer = new Handler();
        this.carModel = MCUSerialHandler.getInstance().getModel();
    }

    public AbsGridLayoutView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.handlerTimer = new Handler();
        this.carModel = MCUSerialHandler.getInstance().getModel();
    }

    protected void init(int columnCount, int rowCount,int layoutId, int layoutBackground, int color){
        setColumnCount(columnCount);
        setRowCount(rowCount);
        LayoutInflater.from(getContext()).inflate(layoutId, this, true);
        GradientDrawable background = (GradientDrawable) findViewById(layoutBackground).getBackground();
        background.setColor(getResources().getColor(color));
        this.runnable = () -> {
            update();
            this.running = true;
            this.handlerTimer.postDelayed(this.runnable, timeUpdate);
        };
    }
    @Override
    public boolean isStarted() {
        return this.running;
    }

    protected abstract void update();

    public void start(){
        this.handlerTimer.postDelayed(this.runnable,timeUpdate);
    }

    public void stop(){
        this.handlerTimer.removeCallbacks(this.runnable);
        this.running = false;
    }
}
