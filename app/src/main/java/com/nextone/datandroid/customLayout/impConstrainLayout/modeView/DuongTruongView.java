package com.nextone.datandroid.customLayout.impConstrainLayout.modeView;

import android.content.Context;
import android.util.AttributeSet;
import com.nextone.datandroid.R;

public class DuongTruongView extends AbsModeView{
    public DuongTruongView(Context context) {
        super(context, R.layout.duong_truong, true);
    }

    public DuongTruongView(Context context, AttributeSet attrs) {
        super(context, attrs, R.layout.duong_truong, true);
    }

    public DuongTruongView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr, R.layout.duong_truong, true);
    }


    @Override
    protected void updateUI() {

    }
}
