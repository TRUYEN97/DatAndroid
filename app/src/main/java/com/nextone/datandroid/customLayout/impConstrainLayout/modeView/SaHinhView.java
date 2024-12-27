package com.nextone.datandroid.customLayout.impConstrainLayout.modeView;

import android.content.Context;
import android.util.AttributeSet;

import com.nextone.datandroid.R;

public class SaHinhView extends AbsModeView{
    public SaHinhView(Context context) {
        super(context, R.layout.sa_hinh, true);
    }

    public SaHinhView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.sa_hinh, true);
    }

    public SaHinhView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.sa_hinh, true);
    }


    @Override
    protected void updateUI() {

    }
}
