package com.nextone.datandroid.customLayout.grid;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.GridLayout;

import androidx.annotation.ColorRes;

import com.nextone.input.serial.MCUSerialHandler;
import com.nextone.model.input.CarModel;

public abstract class AbsGridLayoutView extends GridLayout{

    protected final CarModel carModel;
    protected final GradientDrawable background;

    public AbsGridLayoutView(Context context) {
        super(context);
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.background = new GradientDrawable();
    }

    public AbsGridLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.background = new GradientDrawable();
    }

    public AbsGridLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.background = new GradientDrawable();
    }

    public AbsGridLayoutView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.carModel = MCUSerialHandler.getInstance().getModel();
        this.background = new GradientDrawable();
    }

    protected void init(int columnCount, int rowCount, int layoutId, int layoutBackground, int color) {
        setColumnCount(columnCount);
        setRowCount(rowCount);
        LayoutInflater.from(getContext()).inflate(layoutId, this, true);
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

    protected abstract void update();

}
