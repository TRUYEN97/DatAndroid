package com.nextone.datandroid.customLayout.grid.impGridLayout;

import android.content.Context;
import android.util.AttributeSet;

import com.nextone.datandroid.customLayout.grid.AbsGridLayoutView;

import com.nextone.datandroid.R;

public class DuongTruongControlView extends AbsGridLayoutView {

    public DuongTruongControlView(Context context) {
        super(context);
        init();
    }

    public DuongTruongControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DuongTruongControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DuongTruongControlView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        super.init(4, 2,
                R.layout.duong_truong_control_view,
                R.id.duongTruongControl,
                android.R.color.holo_purple);
    }

    public void update() {

    }
}
